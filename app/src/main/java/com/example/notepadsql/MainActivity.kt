package com.example.notepadsql

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notepadsql.databinding.ActivityMainBinding
import com.example.notepadsql.db.MyAdapter
import com.example.notepadsql.db.MyDbManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val myDbManager = MyDbManager(this)
    private val myAdapter = MyAdapter(ArrayList(), this)
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClickListener()
        init()
        initSearchView()

    }

    override fun onResume() {
        super.onResume()
        myDbManager.openDb()
        fillAdapter("")
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
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }


    }

    fun init() {
        binding.rcView.layoutManager = LinearLayoutManager(this)
        val swapHelper = getSwapManager()
        swapHelper.attachToRecyclerView(binding.rcView)

        binding.rcView.adapter = myAdapter
    }

    private fun initSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
//                val list = myDbManager.readDbData(newText!!)
//                myAdapter.updateAdapter(list)
//                Log.d("MyLog", "NewText: $newText")
                fillAdapter(newText!!)
                return true
            }
        })
    }

    private fun fillAdapter(text: String) {
      job?.cancel()
      job = CoroutineScope(Dispatchers.Main).launch {

            val list = myDbManager.readDbData(text)

            myAdapter.updateAdapter(list)
            if (list.size > 0) {
                binding.tvNoElement.visibility = View.GONE
            } else {
                binding.tvNoElement.visibility = View.VISIBLE
            }
        }


    }

    private fun getSwapManager(): ItemTouchHelper {
        return ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                myAdapter.removeItem(viewHolder.adapterPosition, myDbManager)

            }
        })
    }

    override fun onDestroy() {
        myDbManager.closeDb()
        super.onDestroy()
    }


}


