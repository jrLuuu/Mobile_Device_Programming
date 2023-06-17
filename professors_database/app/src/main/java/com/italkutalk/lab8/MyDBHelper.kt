package com.italkutalk.lab8

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBHelper(
    context: Context,
    name: String = database,
    factory: SQLiteDatabase.CursorFactory? = null,
    version: Int = v
) : SQLiteOpenHelper(context, name, factory, version) {
    companion object {
        private const val database = "myDatabase" //資料庫名稱
        private const val v = 1 //資料庫版本
    }

    override fun onCreate(db: SQLiteDatabase) {
        //建立 myTable 資料表，表內有 name、mail、phone、lab 字串欄位
        db.execSQL("CREATE TABLE myTable(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name text NOT NULL, mail text NOT NULL, phone text NOT NULL, lab text NOT NULL)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //升級資料庫版本時，刪除舊資料表，並重新執行 onCreate()，建立新資料表
        db.execSQL("DROP TABLE IF EXISTS myTable")
        onCreate(db)
    }

    @SuppressLint("Range")
    fun readInfoFromDatabase(): List<Contact> {
        val professorList = mutableListOf<Contact>()
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM myTable", null)
        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndex("name"))
            val mail = cursor.getString(cursor.getColumnIndex("mail"))
            val phone = cursor.getString(cursor.getColumnIndex("phone"))
            val lab = cursor.getString(cursor.getColumnIndex("lab"))

            val info = Contact(name, mail, phone, lab)
            professorList.add(info)
        }
        cursor.close()

        return professorList
    }
}