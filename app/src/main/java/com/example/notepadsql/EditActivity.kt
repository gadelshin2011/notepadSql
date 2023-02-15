package com.example.notepadsql

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.notepadsql.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListener()
    }


    private fun setOnClickListener(){
        binding.fbAddImage.setOnClickListener {
            binding.mainImageLayout.visibility = View.VISIBLE
            binding.fbAddImage.visibility = View.INVISIBLE
        }
        binding.bnDeleteImage.setOnClickListener {
            binding.mainImageLayout.visibility = View.GONE
            binding.fbAddImage.visibility = View.VISIBLE
        }
    }
}