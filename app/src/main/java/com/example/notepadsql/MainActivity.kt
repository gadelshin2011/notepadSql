package com.example.notepadsql

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notepadsql.databinding.ActivityMainBinding
import com.example.notepadsql.db.MyAdapter
import com.example.notepadsql.db.MyDbManager


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val myDbManager = MyDbManager(this)
    private val myAdapter= MyAdapter(ArrayList(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClickListener()
        init()

    }

    override fun onResume() {
        super.onResume()
        myDbManager.openDb()
        fillAdapter()
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
    fun init(){
        binding.rcView.layoutManager= LinearLayoutManager(this)
        binding.rcView.adapter = myAdapter
    }
    fun fillAdapter(){
        myAdapter.updateAdapter(myDbManager.readDbData())
    }

    override fun onDestroy() {
        myDbManager.closeDb()
        super.onDestroy()
    }


}