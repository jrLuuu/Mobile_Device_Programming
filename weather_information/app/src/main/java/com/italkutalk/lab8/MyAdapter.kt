package com.italkutalk.lab8

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val data: ArrayList<Info>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    //實作 RecyclerView.ViewHolder 來儲存 View
    class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        //連結畫面中的元件
        val img_photo = v.findViewById<ImageView>(R.id.img_photo)
        val tv_temp = v.findViewById<TextView>(R.id.tv_temp)
        val tv_description = v.findViewById<TextView>(R.id.tv_description)
        val tv_deg = v.findViewById<TextView>(R.id.tv_deg)
        val tv_dt_txt = v.findViewById<TextView>(R.id.tv_dt_txt)
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
        holder.tv_temp.text = data[position].temp
        holder.tv_description.text = data[position].description
        holder.tv_deg.text = data[position].deg
        holder.tv_dt_txt.text = data[position].dt_txt
    }
}