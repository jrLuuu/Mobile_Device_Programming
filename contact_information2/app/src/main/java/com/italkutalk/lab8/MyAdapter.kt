package com.italkutalk.lab8

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import android.net.Uri
import android.content.Intent

class MyAdapter(private val data: ArrayList<Contact>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    private lateinit var context: Contact
    private val URL = "https://www.google.com/search?q="
    //實作 RecyclerView.ViewHolder 來儲存 View
    class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        //連結畫面中的元件
        val img_photo = v.findViewById<ImageView>(R.id.img_photo)
        val tv_name = v.findViewById<TextView>(R.id.tv_name)
        val tv_department = v.findViewById<TextView>(R.id.tv_department)
        val tv_phone = v.findViewById<TextView>(R.id.tv_phone)
    }
    //回傳資料數量
    override fun getItemCount() = data.size
    //建立 ViewHolder 與 Layout 並連結彼此
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int):
            ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_row, viewGroup, false)
        return ViewHolder(v)
    }
    //將資料指派給元件呈現
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.img_photo.setImageResource(data[position].photo)
        holder.tv_name.text = data[position].name
        holder.tv_department.text = data[position].department
        holder.tv_phone.text = data[position].phone
        //設定點擊事件
        val item = data[position].name
        holder.itemView.setOnClickListener {
            val queryUrl: Uri = Uri.parse("${URL}${"ncyu"}${item}")
            val intent = Intent(Intent.ACTION_VIEW, queryUrl)
            holder.itemView.context.startActivity(intent)
        }
    }
}