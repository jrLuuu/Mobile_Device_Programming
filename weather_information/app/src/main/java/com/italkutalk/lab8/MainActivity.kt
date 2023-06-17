package com.italkutalk.lab8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    private lateinit var resultArray: Array<String>
    private lateinit var btn_query: Button
    private lateinit var adapter : MyAdapter
    private val info = ArrayList<Info>()
    class MyObject {
        lateinit var list: Array<List>
        class List {
            lateinit var main: Main
            class Main {
                var temp = ""
            }
            lateinit var weather: Array<Weather>
            class Weather {
                var description = ""
            }
            lateinit var wind: Wind
            class Wind {
                var deg = ""
            }
            var dt_txt = ""
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_query = findViewById(R.id.btn_query)
        btn_query.setOnClickListener() {
            //關閉按鈕避免再次查詢
            btn_query.isEnabled = false
            //發送請求
            sendRequest()
        }

        //將變數與 XML 元件綁定
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        //加入範例資料
        info.add(Info(R.drawable.unknown, "溫度：30", "天氣：晴天", "風向：360", "時間：2023-04-17 15:00:00"))

        //創建 LinearLayoutManager 物件，設定垂直排列
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        //創建 MyAdapter 並連結 recyclerView
        adapter = MyAdapter(info)
        recyclerView.adapter = adapter
        //將列表資料更新到畫面上
        adapter.notifyDataSetChanged()
    }
    private fun sendRequest() {
        val url = "https://api.openweathermap.org/data/2.5/forecast?q=Taipei,tw&APPID=ecd85f9c669093c4cfeacbf1be5f60de&lang=zh_tw&units=metric"
        //建立 Request.Builder 物件，藉由 url() 將網址傳入，再建立 Request 物件
        val req = Request.Builder()
            .url(url)
            .build()
        //建立 OkHttpClient 物件，藉由 newCall() 發送請求，並在 enqueue() 接收回傳
        OkHttpClient().newCall(req).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                //使用 response.body?.string()
                val json = response.body?.string()
                //建立 Gson 並使用其 fromJson() 方法，將 JSON 字串以 MyObject 格式輸出
                val myObject = Gson().fromJson(json, MyObject::class.java)
                //顯示結果
                showDialog(myObject)
                //清空範例資料
                info.clear()
                //加入更新後的資料
                myObject.list.forEach { data ->
                    val temperature = data.main.temp.toString()
                    val weather = data.weather[0].description
                    val windDirection = data.wind.deg.toString()
                    val time = data.dt_txt
                    val drawableId = when (weather) {
                        "晴" -> R.drawable.sunny
                        "多雲" -> R.drawable.cloudy
                        "晴，少雲" -> R.drawable.min_cloudy
                        "陰，多雲" -> R.drawable.max_cloudy
                        "小雨" -> R.drawable.small_rain
                        "中雨" -> R.drawable.middle_rain
                        "雷雨" -> R.drawable.thunder_rain
                        else -> R.drawable.unknown
                    }
                    info.add(Info(drawableId, temperature+"°C", weather, windDirection+"°", time))
                }
                //將列表資料更新到畫面上
                runOnUiThread {
                    adapter.notifyDataSetChanged()
                    //開啟按鈕可再次查詢
                    btn_query.isEnabled = true
                }
            }
            //發送失敗執行此方法
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    //開啟按鈕可再次查詢
                    btn_query.isEnabled = true
                    Toast.makeText(this@MainActivity, "查詢失敗$e", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun showDialog(myObject: MyObject) {
        //建立一個字串陣列，用於存放 .main.temp 與 wind.deg 與 dt_txt 資訊
        val items = arrayOfNulls<String>(myObject.list.size)
        //將 API 資料取出並建立字串，並存放到字串陣列
        myObject.list.forEachIndexed { index, data ->
            //建立一個字串陣列，用於存放 description 資訊
            val weatherDescriptions = mutableListOf<String>()
            //將 API 資料取出並建立字串，並存放到字串陣列
            data.weather.forEach { weather ->
                weatherDescriptions.add(weather.description)
            }
            val weatherDescriptionString = weatherDescriptions.joinToString(", ")
            items[index] = "溫度：${data.main.temp}, 天氣：$weatherDescriptionString, 風向：${data.wind.deg}, 時間：${data.dt_txt}"
            resultArray = items.map { it ?: "" }.toTypedArray() // 將 null 值轉換為空字串
        }
        //切換到主執行緒將畫面更新
        runOnUiThread {
            //開啟按鈕可再次查詢
            btn_query.isEnabled = true
            //建立 AlertDialog 物件並顯示字串陣列
            AlertDialog.Builder(this@MainActivity)
                .setTitle("臺北市天氣狀況")
                .setItems(items, null)
                .show()
        }
    }
}

//設計新的類別定義天氣資訊的資料結構
data class Info(
    val photo: Int, //圖片
    val temp: String, //溫度
    val description: String, //天氣
    val deg: String, //風向
    val dt_txt: String //時間
)