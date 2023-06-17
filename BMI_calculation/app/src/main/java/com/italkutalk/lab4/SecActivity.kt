package com.italkutalk.lab4

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class SecActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sec)

        //將變數與 XML 元件綁定
        val btn_send = findViewById<Button>(R.id.btn_send)
        val ed_name = findViewById<EditText>(R.id.ed_name)
        val ed_height = findViewById<EditText>(R.id.ed_height)
        val ed_weight = findViewById<EditText>(R.id.ed_weight)

        btn_send.setOnClickListener {
            if (ed_name.length() < 1)
                Toast.makeText(
                    this, "請輸入你的名稱",
                    Toast.LENGTH_SHORT
                ).show()
            else {
                //宣告 Bundle
                val b = Bundle()
                //取得 EditText 字串內容，把名稱、身高與體重資訊放入 Bundle
                b.putString("name", ed_name.text.toString())
                b.putString("height", ed_height.text.toString())
                b.putString("weight", ed_weight.text.toString())
                //用 Activity.RESULT_OK 標記執行狀態並記錄 Intent
                setResult(Activity.RESULT_OK, Intent().putExtras(b))
                finish()
            }
        }
    }
}