package com.example.notepadsql

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notepadsql.databinding.ActivityMainBinding
import com.example.notepadsql.db.MyDbManager
import kotlinx.coroutines.NonCancellable.children
import java.io.File

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val myDbManager = MyDbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClickListener()

    }

    private fun onClickListener() {
//        binding.button.setOnClickListener {
//            binding.tvTest.text = ""
//            myDbManager.openDb()
//            myDbManager.insertToDb(binding.edTitle.text.toString(), binding.edContent.text.toString())
//            val dataList = myDbManager.readDbData()
//            for (item in dataList) {
//                binding.tvTest.append(item)
//                binding.tvTest.append("\n")
//            }
//        }

        binding.fbNew.setOnClickListener {
            val intent = Intent(this,EditActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onDestroy() {
        myDbManager.closeDb()
        super.onDestroy()
    }


}