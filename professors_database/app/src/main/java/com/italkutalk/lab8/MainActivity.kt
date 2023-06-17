package com.italkutalk.lab8

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: MyAdapter
    private lateinit var dbrw: SQLiteDatabase
    private val myDBHelper = MyDBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListener()

        // 取得資料庫實體
        dbrw = MyDBHelper(this).writableDatabase

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        val professorList = myDBHelper.readInfoFromDatabase()
        adapter = MyAdapter(professorList)
        recyclerView.adapter = adapter
    }
    override fun onDestroy() {
        //關閉資料庫
        dbrw.close()
        super.onDestroy()
    }

    //設定監聽器
    private fun setListener() {
        val edName = findViewById<EditText>(R.id.ed_name)
        val edMail = findViewById<EditText>(R.id.ed_mail)
        val edPhone = findViewById<EditText>(R.id.ed_phone)
        val edLab = findViewById<EditText>(R.id.ed_lab)
        findViewById<Button>(R.id.btn_insert).setOnClickListener {
            //判斷是否有填入姓名或信箱或電話或研究室
            if(edName.length() < 1 || edMail.length() < 1 || edPhone.length() < 1 || edLab.length() < 1)
                showToast("欄位請勿留空")
            else
                try {
                    dbrw.execSQL(
                        "INSERT INTO myTable(name, mail, phone, lab) VALUES(?, ?, ?, ?)",
                        arrayOf(
                            edName.text.toString(),
                            edMail.text.toString(),
                            edPhone.text.toString(),
                            edLab.text.toString()
                        )
                    )
                    val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                    val professorList = myDBHelper.readInfoFromDatabase()
                    adapter = MyAdapter(professorList)
                    recyclerView.adapter = adapter
                    showToast("新增成功 -> Name: ${edName.text}, Email: ${edMail.text}, Phone: ${edPhone.text}, Lab: ${edLab.text}")
                    clearEditText()
                } catch (e: Exception) {
                    showToast("新增失敗 -> $e")
                }
        }
        findViewById<Button>(R.id.btn_update).setOnClickListener {
            //判斷是否有填入姓名或信箱或電話或研究室
            if(edName.length() < 1 || edMail.length() < 1 || edPhone.length() < 1 || edLab.length() < 1)
                showToast("欄位請勿留空")
            else
                try {
                    dbrw.execSQL(
                        "UPDATE myTable SET mail = ?, phone = ?, lab = ? WHERE name LIKE ?",
                        arrayOf(
                            edMail.text.toString(),
                            edPhone.text.toString(),
                            edLab.text.toString(),
                            edName.text.toString()
                        )
                    )
                    val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                    val professorList = myDBHelper.readInfoFromDatabase()
                    adapter = MyAdapter(professorList)
                    recyclerView.adapter = adapter
                    showToast("更新成功 -> Name: ${edName.text}, Email: ${edMail.text}, Phone: ${edPhone.text}, Lab: ${edLab.text}")
                    clearEditText()
                } catch(e: Exception) {
                    showToast("更新失敗 -> $e")
                }
        }
        findViewById<Button>(R.id.btn_delete).setOnClickListener {
            //判斷是否有填入姓名
            if(edName.length() < 1)
                showToast("Name欄位請勿留空")
            else
                try {
                    dbrw.execSQL(
                        "DELETE FROM myTable WHERE name LIKE ?",
                        arrayOf(edName.text.toString())
                    )
                    val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                    val professorList = myDBHelper.readInfoFromDatabase()
                    adapter = MyAdapter(professorList)
                    recyclerView.adapter = adapter
                    showToast("刪除成功 -> Name: ${edName.text}")
                    clearEditText()
                } catch (e: Exception) {
                    showToast("刪除失敗 -> $e")
                }
        }
    }
    
    private fun showToast(text: String) =
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    
    private fun clearEditText() {
        findViewById<EditText>(R.id.ed_name).setText("")
        findViewById<EditText>(R.id.ed_mail).setText("")
        findViewById<EditText>(R.id.ed_phone).setText("")
        findViewById<EditText>(R.id.ed_lab).setText("")
    }
}
//設計新的類別定義聯絡人的資料結構
data class Contact (
    val name: String, //姓名
    val mail: String, //信箱
    val phone: String, //電話
    val lab: String //研究室
)
//contacts.add(Contact("陳耀輝", "ychen@mail.ncyu.edu.tw", "05-2717737", "A16-518"))
//contacts.add(Contact("林楚迪", "chutilin@mail.ncyu.edu.tw", "05-2717227", "A16-607"))
//contacts.add(Contact("郭煌政", "hckuo@mail.ncyu.edu.tw", "05-2717731", "A16-614"))
//contacts.add(Contact("章定遠", "dychan@mail.ncyu.edu.tw", "05-2717727", "A16-517"))
//contacts.add(Contact("葉瑞峰", "ralph@mail.ncyu.edu.tw", "05-2717709", "A16-506"))
//contacts.add(Contact("盧天麒", "tclu@mail.ncyu.edu.tw", "05-2717730", "A16-615"))
//contacts.add(Contact("邱志義", "cychiu@mail.ncyu.edu.tw", "05-2717228", "A16-606"))
//contacts.add(Contact("陳宗和", "thchen@mail.ncyu.edu.tw", "05-2717723", "A16-616"))
//contacts.add(Contact("許政穆", "hsujm@mail.ncyu.edu.tw", "05-2717742", "A16-516"))
//contacts.add(Contact("王智弘", "wangch@mail.ncyu.edu.tw", "05-2717736", "A16-507"))
//contacts.add(Contact("李龍盛", "sheng@mail.ncyu.edu.tw", "05-2717733", "A16-509"))
//contacts.add(Contact("王皓立", "haoli@mail.ncyu.edu.tw", "05-2717724", "A16-618"))
//contacts.add(Contact("柯建全", "kocc@mail.ncyu.edu.tw", "05-2717732", "A16-617"))
//contacts.add(Contact("賴泳伶", "yllai@mail.ncyu.edu.tw", "05-2717735", "A16-514"))
//contacts.add(Contact("洪燕竹", "andrew@mail.ncyu.edu.tw", "05-2717728", "A16-515"))