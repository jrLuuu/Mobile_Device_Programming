package com.italkutalk.lab7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //將變數與 XML 元件綁定
        val listView = findViewById<ListView>(R.id.listView)
        val item = ArrayList<Item>() //儲存聯絡資訊
        val array = resources.obtainTypedArray(R.array.image_list) //從R類別讀取圖檔
        val name_array = resources.getStringArray(R.array.name_list)
        val department_array = resources.getStringArray(R.array.department_list)
        val phone_array = resources.getStringArray(R.array.phone_list)

        for(i in 0 until array.length()) {
            val photo = array.getResourceId(i, 0) //聯絡圖片Id
            val name = name_array[i]
            val department = department_array[i]
            val phone = phone_array[i]
            item.add(Item(photo, name, department, phone)) //新增聯絡資訊
        }
        array.recycle() //釋放圖檔資源

        //建立 MyAdapter 物件，並傳入 adapter_horizontal 作為畫面
        listView.adapter = MyAdapter(this, item, R.layout.adapter_horizontal)
    }
}

//設計新的類別定義聯絡資訊的資料結構
data class Item(
    val photo: Int, //圖片
    val name: String, //名稱
    val department: String, //地點
    val phone: String //電話
)