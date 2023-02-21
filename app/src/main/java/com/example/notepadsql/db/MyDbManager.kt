package com.example.notepadsql.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class MyDbManager(context: Context) {
    private val myDbHelper = MyDbHelper(context)
    private var db: SQLiteDatabase? = null


    fun openDb() {
        db = myDbHelper.writableDatabase
    }

    fun insertToDb(title: String, content: String, uri: String) {
        val values = ContentValues().apply {
            put(MyDbNameClass.COLUMN_NAME_TITLE, title)
            put(MyDbNameClass.COLUMN_NAME_CONTENT, content)
            put(MyDbNameClass.COLUMN_NAME_URI, uri)
        }
        db?.insert(MyDbNameClass.TABLE_NAME, null, values)
    }

    fun readDbData(): ArrayList<ListItem> {
        val dataList = ArrayList<ListItem>()
        val cursor = db?.query(MyDbNameClass.TABLE_NAME, null, null, null, null, null, null)

        with(cursor) {
            while (this?.moveToNext()!!) {
                val dataText = getString(getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_TITLE))
                val dataContent = getString(getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_CONTENT))
                val dataUri = getString(getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_URI))

                val item = ListItem()

                item.title = dataText
                item.desc = dataContent
                item.uri = dataUri
                dataList.add(item)
            }
        }
        cursor?.close()
        return dataList
    }

    fun closeDb() {
        myDbHelper.close()
    }

}