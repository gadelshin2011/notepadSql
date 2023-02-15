package com.example.notepadsql.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class MyDbManager(context: Context) {
    private val myDbHelper = MyDbHelper(context)
    private var db: SQLiteDatabase? = null


    fun openDb() {
        db = myDbHelper.writableDatabase
    }

    fun insertToDb(title: String, content: String) {
        val values = ContentValues().apply {
            put(MyDbNameClass.COLUMN_NAME_TITLE, title)
            put(MyDbNameClass.COLUMN_NAME_CONTENT, content)
        }
        db?.insert(MyDbNameClass.TABLE_NAME, null, values)
    }

    fun readDbData(): ArrayList<String> {
        val dataList = ArrayList<String>()
        val cursor = db?.query(MyDbNameClass.TABLE_NAME, null, null, null, null, null, null)

        with(cursor) {
            while (this?.moveToNext()!!) {
                val dataText = getString(getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_TITLE))
                dataList.add(dataText.toString())
            }
        }
        cursor?.close()
        return dataList
    }

    fun closeDb() {
        myDbHelper.close()
    }

}