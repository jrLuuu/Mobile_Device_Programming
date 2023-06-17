package com.italkutalk.lab4

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.extras?.let {
            //驗證發出對象，確認 SecActivity 執行的狀態
            if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
                val numeratorString: String? = it.getString("weight")
                val numerator: Float? = numeratorString?.toFloat()
                val heightString: String? = it.getString("height")
                val height: Float? = heightString?.toFloat()
                val denominator: Float? = height?.times(height)
                val BMIval: Float? = numerator?.div(denominator ?: 0f)
                var BMIinfo: String = ""

                while (BMIval != null) {
                    if (BMIval < 18.5f) { BMIinfo = "體重過輕" }
                    else if (BMIval >= 18.5f && BMIval < 24f) { BMIinfo = "健康體重" }
                    else if (BMIval >= 24f && BMIval < 27f) { BMIinfo = "體重過重" }
                    else if (BMIval >= 27f && BMIval < 30f) { BMIinfo = "輕度肥胖" }
                    else if (BMIval >= 30f && BMIval < 35f) { BMIinfo = "中度肥胖" }
                    else if (BMIval >= 35f) { BMIinfo = "重度肥胖" }
                    break;
                }

                //讀取 Bundle 資料
                findViewById<TextView>(R.id.tv_value).text =
                    "名稱: ${it.getString("name")}\n\n" +
                            "身高: ${heightString}m\n\n" +
                            "體重: ${numeratorString}kg\n\n" +
                            "BMI值: ${BMIval}\n\n" +
                            "BMI體位: ${BMIinfo}"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_calculate).setOnClickListener {
            //透過 Intent 切換至 SecActivity 並傳遞 requestCode 作為識別編號
            val intent = Intent(this, SecActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }
}