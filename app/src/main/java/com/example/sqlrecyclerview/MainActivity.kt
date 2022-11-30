package com.example.sqlrecyclerview

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DBHelper

class MainActivity : AppCompatActivity() {

    private val list = mutableListOf<Task>()
    private val adapter = RecyclerAdapter(list)
    private val dbHelper = DBHelper(this)

    companion object {
        const val REQUEST_CODE = 1
        const val ITEM_ID_KEY = "ITEM_ID_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list.addAll(dbHelper.getAllTasks())
        val editText= findViewById<EditText>(R.id.editTextTextPersonName)
        val button=findViewById<Button>(R.id.button)
        val searchButton=findViewById<Button>(R.id.button9)
        var s=""
        adapter.onItemClick = {
            val intent = Intent(this, ContactDetails::class.java)
            intent.putExtra(ITEM_ID_KEY, list[it].id)
            startActivityForResult(intent,  REQUEST_CODE )
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        searchButton.setOnClickListener {
            list.clear()
            val name = editText.text.toString()
            list.addAll(dbHelper.getByName(name))
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        button.setOnClickListener {
            val intent = Intent(this, CreateActivity::class.java)
            startActivityForResult(intent, MainActivity.REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            //val result = data?.getStringExtra(EditActivity.RESULT_KEY)
            val id = data?.getLongExtra(ITEM_ID_KEY, 0)
            val fname = data?.getStringExtra(CreateActivity.ITEM_FNAME)
            val lname = data?.getStringExtra(CreateActivity.ITEM_LNAME)
            val phone = data?.getStringExtra(CreateActivity.ITEM_PHONE)
            val birth = data?.getStringExtra(CreateActivity.ITEM_BIRTH)
            if (id != null) {
                list.add(Task(id, fname,lname,phone,birth))
                adapter.notifyItemChanged(list.lastIndex)
            }
        }
    }
}






