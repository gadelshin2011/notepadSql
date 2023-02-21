package com.example.notepadsql

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.notepadsql.databinding.ActivityEditBinding
import com.example.notepadsql.db.MyDbManager
import com.example.notepadsql.db.MyIntentConstants

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    private val myDbManager = MyDbManager(this)

    var id = 0
    var isEditState = false
    private val imageRequestCode = 10
    var tempImageUri = "empty"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListener()
        getMyIntents()

    }

    override fun onResume() {
        myDbManager.openDb()
        super.onResume()
    }

    override fun onDestroy() {
        myDbManager.closeDb()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == imageRequestCode) {
            binding.imMainImage.setImageURI(data?.data)
            tempImageUri = data?.data.toString()
            contentResolver.takePersistableUriPermission(
                data?.data!!, Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }
    }


    private fun setOnClickListener() {
        binding.fbAddImage.setOnClickListener {
            binding.mainImageLayout.visibility = View.VISIBLE
            binding.fbAddImage.visibility = View.GONE


        }
        binding.bnDeleteImage.setOnClickListener {
            binding.mainImageLayout.visibility = View.GONE
            binding.fbAddImage.visibility = View.VISIBLE

        }

        binding.bnEditImage.setOnClickListener {
            onClickOpenGallery()
        }

        binding.fbSave.setOnClickListener {
            onClickSave()
        }
        binding.fbEditText.setOnClickListener {
            onEditEnable()
        }
    }

    private fun onClickOpenGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"


        startActivityForResult(intent, imageRequestCode)
    }

    private fun onClickSave() {
        val myTitle = binding.edTitle.text.toString()
        val myDesc = binding.edDesc.text.toString()

        if (myTitle != "" && myDesc != "") {
            if (isEditState) {
                myDbManager.updateItemFromDb(id, myTitle, myDesc, tempImageUri)
            } else {
                myDbManager.insertToDb(myTitle, myDesc, tempImageUri)

            }
            this.finish()
        }


    }

    private fun onEditEnable() {
        binding.edTitle.isEnabled = true
        binding.edDesc.isEnabled = true
        binding.fbEditText.visibility = View.GONE
    }

    private fun getMyIntents() {
        val i = intent
        if (i != null) {
            if (i.getStringExtra(MyIntentConstants.I_TITLE_KEY) != null) {

                binding.fbAddImage.visibility = View.INVISIBLE
                binding.fbEditText.visibility = View.VISIBLE


                binding.edTitle.setText(i.getStringExtra(MyIntentConstants.I_TITLE_KEY))
                binding.edDesc.setText(i.getStringExtra(MyIntentConstants.I_DESC_KEY))
                isEditState = true
                binding.edTitle.isEnabled = false
                binding.edDesc.isEnabled = false



                id = i.getIntExtra(MyIntentConstants.I_ID_KEY, 0)


                if (i.getStringExtra(MyIntentConstants.I_URI_KEY) != "empty") {
                    binding.mainImageLayout.visibility = View.VISIBLE
                    binding.bnDeleteImage.visibility = View.GONE
                    binding.bnEditImage.visibility = View.GONE



                    binding.imMainImage.setImageURI(Uri.parse(i.getStringExtra(MyIntentConstants.I_URI_KEY)))

                }
            }
        }
    }

}