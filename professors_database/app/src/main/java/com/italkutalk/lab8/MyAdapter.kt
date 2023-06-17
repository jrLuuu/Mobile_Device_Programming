package com.italkutalk.lab8

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val professorList: List<Contact>) : RecyclerView.Adapter<MyAdapter.ProfessorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfessorViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_row, parent, false)
        return ProfessorViewHolder(itemView)
    }

    inner class ProfessorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvMail: TextView = itemView.findViewById(R.id.tv_mail)
        val tvPhone: TextView = itemView.findViewById(R.id.tv_phone)
        val tvLab: TextView = itemView.findViewById(R.id.tv_lab)
    }

    override fun getItemCount(): Int {
        return professorList.size
    }

    override fun onBindViewHolder(holder: ProfessorViewHolder, position: Int) {
        val currentInfo = professorList[position]
        holder.tvName.text = "Name: ${currentInfo.name}"
        holder.tvMail.text = "Email: ${currentInfo.mail}"
        holder.tvPhone.text = "Phone: ${currentInfo.phone}"
        holder.tvLab.text = "Lab: ${currentInfo.lab}"

        holder.itemView.setOnClickListener {
            showInfo(it.context, currentInfo.name, currentInfo.mail, currentInfo.phone, currentInfo.lab)
        }
    }

    private fun showInfo(context: Context, name: String, mail: String, phone: String, lab: String) {
        val edName = (context as Activity).findViewById<EditText>(R.id.ed_name)
        val edMail = (context as Activity).findViewById<EditText>(R.id.ed_mail)
        val edPhone = (context as Activity).findViewById<EditText>(R.id.ed_phone)
        val edLab = (context as Activity).findViewById<EditText>(R.id.ed_lab)
        edName?.setText(name)
        edMail?.setText(mail)
        edPhone?.setText(phone)
        edLab?.setText(lab)
    }
}