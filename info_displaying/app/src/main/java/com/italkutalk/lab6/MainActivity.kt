package com.italkutalk.lab6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //將變數與 XML 元件綁定
        val btn_toast = findViewById<Button>(R.id.btn_toast)
        val btn_custom = findViewById<Button>(R.id.btn_custom)
        val btn_snackbar = findViewById<Button>(R.id.btn_snackbar)
        val btn_dialog1 = findViewById<Button>(R.id.btn_dialog1)
        val btn_dialog2 = findViewById<Button>(R.id.btn_dialog2)
        val btn_dialog3 = findViewById<Button>(R.id.btn_dialog3)
        //建立要顯示在的列表上的字串
        val item = arrayOf(
            "麥當勞1",
            "肯德基1",
            "漢堡王1",
            "摩斯1",
            "頂呱呱1",
            "麥當勞2",
            "肯德基2",
            "漢堡王2",
            "摩斯2",
            "頂呱呱2",
            "麥當勞3",
            "肯德基3",
            "漢堡王3",
            "摩斯3",
            "頂呱呱4"
        )
        val item1 = arrayOf("羽球", "保齡球", "桌球", "籃球", "金排球", "手遊")

        //Button 點擊事件
        btn_toast.setOnClickListener {
            //showToast("Hello Toast")
            //Toast.makeText(this, "Android is super strong", Toast.LENGTH_LONG).show()
            val myToast = Toast.makeText(this, getString(R.string.toast_msg), Toast.LENGTH_SHORT)
            myToast.show()
        }

        btn_custom.setOnClickListener {
            //宣告 Toast
            val toast = Toast(this)
            //Toast 在畫面中顯示的位置
            toast.setGravity(Gravity.CENTER, 0, 50)
            //Toast 在畫面中顯示的持續時間
            toast.duration = Toast.LENGTH_SHORT
            //放入自定義的畫面 custom_toast.xml
            //toast.view = layoutInflater.inflate(R.layout.custom_toast, null)
            val layout = layoutInflater.inflate(R.layout.custom_toast, null)
            val textView = layout.findViewById<TextView>(R.id.tv_custom_toast)
            textView.text = "Android is a mobile device"
            toast.view = layout
            //顯示於螢幕
            toast.show()
        }

        btn_snackbar.setOnClickListener {
            //建立 Snackbar 物件
            Snackbar.make(it, "Your message has been sent", Snackbar.LENGTH_SHORT)
                .setAction("OK"){
                    showToast("Confirm")
                }.show()
        }

        btn_dialog1.setOnClickListener {
            //建立 AlertDialog 物件
            AlertDialog.Builder(this)
                .setTitle("按鈕式 AlertDialog")
                .setMessage("AlertDialog 內容")
                .setNeutralButton("左按鈕") { dialog, which ->
                    showToast("Left button")
                }
                .setNegativeButton("中按鈕") { dialog, which ->
                    showToast("Middle button")
                }
                .setPositiveButton("右按鈕") { dialog, which ->
                    showToast("Right button")
                }.show()
        }

        btn_dialog2.setOnClickListener {
            //建立 AlertDialog 物件
            AlertDialog.Builder(this)
                .setTitle("晚餐吃什麼")
                .setItems(item) { dialogInterface, i ->
                    showToast("You chose ${item[i]}")
                }.show()
        }

        btn_dialog3.setOnClickListener {
            var position = 0
            //建立 AlertDialog 物件
            AlertDialog.Builder(this)
                .setTitle("系內盃比什麼")
                .setSingleChoiceItems(item1, 3) { dialogInterface, i ->
                    position = i
                }
                .setPositiveButton("Confirm") { dialog, which ->
                    showToast("You chose ${item1[position]}")
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    showToast("You chose CANCEL")
                }.show()
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}