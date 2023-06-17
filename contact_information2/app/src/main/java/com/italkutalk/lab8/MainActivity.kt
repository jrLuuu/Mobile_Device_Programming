package com.italkutalk.lab8

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var adapter : MyAdapter
    private val contacts = ArrayList<Contact>()
    //接收回傳資料
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.extras?.let {
            if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
                val photo = it.getInt("photo") ?: return@let
                val name = it.getString("name") ?: return@let
                val department = it.getString("department") ?: return@let
                val phone = it.getString("phone") ?: return@let
                //新增聯絡人資料
                contacts.add(Contact(photo, name, department, phone))
                //更新列表
                adapter.notifyDataSetChanged()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //將變數與 XML 元件綁定
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        //加入資料
        contacts.add(Contact(R.drawable.p1, "應數系", "蘭潭校區-理工學院", "2717861"))
        contacts.add(Contact(R.drawable.p2, "應化系", "蘭潭校區-理工學院", "2717899"))
        contacts.add(Contact(R.drawable.p3, "應物系", "蘭潭校區-理工學院", "2717911"))
        contacts.add(Contact(R.drawable.p4, "生機系", "蘭潭校區-理工學院", "2717641"))
        contacts.add(Contact(R.drawable.p5, "土木系", "蘭潭校區-理工學院", "2717681~82"))
        contacts.add(Contact(R.drawable.p6, "資工系", "蘭潭校區-理工學院", "2717740"))
        contacts.add(Contact(R.drawable.p00, "物流所", "蘭潭校區-理工學院", "2717980"))
        contacts.add(Contact(R.drawable.p8, "電機系", "蘭潭校區-理工學院", "2717588"))
        contacts.add(Contact(R.drawable.p9, "農藝系", "蘭潭校區-農學院", "2717381"))
        contacts.add(Contact(R.drawable.p00, "園藝系", "蘭潭校區-農學院", "2717421~22"))
        contacts.add(Contact(R.drawable.p11, "森林系", "蘭潭校區-農學院", "2717461~62"))
        contacts.add(Contact(R.drawable.p12, "動科系", "蘭潭校區-農學院", "2717522"))
        contacts.add(Contact(R.drawable.p00, "林產系", "蘭潭校區-農學院", "2717491~92"))
        contacts.add(Contact(R.drawable.p14, "獸醫系", "蘭潭校區-農學院", "2717561"))
        contacts.add(Contact(R.drawable.p15, "生農系", "蘭潭校區-農學院", "2717751"))
        contacts.add(Contact(R.drawable.p00, "農生所", "蘭潭校區-農學院", "2717751"))
        contacts.add(Contact(R.drawable.p00, "景觀系", "蘭潭校區-農學院", "2717633"))
        contacts.add(Contact(R.drawable.p18, "食品系", "蘭潭校區-生命科學院", "2717591~92"))
        contacts.add(Contact(R.drawable.p00, "水生系", "蘭潭校區-生命科學院", "2717841"))
        contacts.add(Contact(R.drawable.p20, "生資系", "蘭潭校區-生命科學院", "2717811"))
        contacts.add(Contact(R.drawable.p21, "微免系", "蘭潭校區-生命科學院", "2717830"))
        contacts.add(Contact(R.drawable.p00, "生化系", "蘭潭校區-生命科學院", "2717781"))
        contacts.add(Contact(R.drawable.p00, "生藥所", "蘭潭校區-生命科學院", "2717793"))
        contacts.add(Contact(R.drawable.p24, "教育系", "民雄校區-師範學院", "2263411-1801"))
        contacts.add(Contact(R.drawable.p25, "數位系", "民雄校區-師範學院", "2263411-1511"))
        contacts.add(Contact(R.drawable.p00, "國教所", "民雄校區-師範學院", "2263411-2401"))
        contacts.add(Contact(R.drawable.p00, "教政所", "民雄校區-師範學院", "2263411-2421"))
        contacts.add(Contact(R.drawable.p00, "科教所", "民雄校區-師範學院", "2263411-1901"))
        contacts.add(Contact(R.drawable.p00, "數教所", "民雄校區-師範學院", "2263411-1971"))
        contacts.add(Contact(R.drawable.p30, "幼教所", "民雄校區-師範學院", "2263411-2201"))
        contacts.add(Contact(R.drawable.p31, "特教所", "民雄校區-師範學院", "2263411-2300"))
        contacts.add(Contact(R.drawable.p32, "輔諮系", "民雄校區-師範學院", "2263411-2600"))
        contacts.add(Contact(R.drawable.p33, "體健所", "民雄校區-師範學院", "2263411-3001"))
        contacts.add(Contact(R.drawable.p34, "史地系", "民雄校區-人文藝術學院", "2263411-2001"))
        contacts.add(Contact(R.drawable.p35, "中文系", "民雄校區-人文藝術學院", "2263411-2101~03"))
        contacts.add(Contact(R.drawable.p36, "外語系", "民雄校區-人文藝術學院", "2263411-2151~52"))
        contacts.add(Contact(R.drawable.p00, "音樂所", "民雄校區-人文藝術學院", "2263411-2701~02"))
        contacts.add(Contact(R.drawable.p38, "美術系", "民雄校區-人文藝術學院", "2263411-2801"))
        contacts.add(Contact(R.drawable.p39, "企管系", "新民校區-管理學學院", "2732825"))
        contacts.add(Contact(R.drawable.p40, "行銷所", "新民校區-管理學學院", "2732823"))
        contacts.add(Contact(R.drawable.p00, "管研所", "新民校區-管理學學院", "2732824"))
        contacts.add(Contact(R.drawable.p42, "應經系", "新民校區-管理學學院", "2732852"))
        contacts.add(Contact(R.drawable.p00, "生管系", "新民校區-管理學學院", "2732872"))
        contacts.add(Contact(R.drawable.p44, "資管系", "新民校區-管理學學院", "2732892"))
        contacts.add(Contact(R.drawable.p45, "財金系", "新民校區-管理學學院", "2732869"))
        contacts.add(Contact(R.drawable.p46, "休管所", "新民校區-管理學學院", "2732922"))
        //創建 LinearLayoutManager 物件，設定垂直排列
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        //創建 MyAdapter 並連結 recyclerView
        adapter = MyAdapter(contacts)
        recyclerView.adapter = adapter
        //將列表資料更新到畫面上
        adapter.notifyDataSetChanged()
    }
}

//設計新的類別定義聯絡資訊的資料結構
data class Contact(
    val photo: Int, //圖片
    val name: String, //名稱
    val department: String, //地點
    val phone: String //電話
)