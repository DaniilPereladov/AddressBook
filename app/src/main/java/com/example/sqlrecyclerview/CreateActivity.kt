package com.example.sqlrecyclerview

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.DBHelper

class CreateActivity:AppCompatActivity() {
    private val dbHelper = DBHelper(this)
    companion object {
        const val RESULT_KEY = "RESULT_KEY"
        const val ITEM_PHONE= "ITEM_PHONE"
        const val ITEM_BIRTH = "ITEM_BIRTH"
        const val ITEM_FNAME = "ITEM_FNAME"
        const val ITEM_LNAME = "ITEM_LNAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        val editFName = findViewById<EditText>(R.id.editTextTextPersonName5)
        val editLName = findViewById<EditText>(R.id.editTextTextPersonName8)
        val editBirth = findViewById<EditText>(R.id.editTextTextPersonName6)
        val editPhone = findViewById<EditText>(R.id.editTextPhone2)
        val saveButton = findViewById<Button>(R.id.button7)
        val cancelButton = findViewById<Button>(R.id.button8)
        saveButton.setOnClickListener {
            val fname = editFName.text.toString()
            val lname = editLName.text.toString()
            val phone = editPhone.text.toString()
            val birth = editBirth.text.toString()
            if (fname.isNotBlank() && phone.isNotBlank() && birth.isNotBlank()&& lname.isNotBlank()) {
                val id = dbHelper.addTask(fname,lname,phone,birth)
                val returnIntent = Intent()
                returnIntent.putExtra(EditActivity.ITEM_FNAME,fname)
                returnIntent.putExtra(EditActivity.ITEM_LNAME,lname)
                returnIntent.putExtra(EditActivity.ITEM_PHONE,phone)
                returnIntent.putExtra(EditActivity.ITEM_BIRTH,birth)
                returnIntent.putExtra(MainActivity.ITEM_ID_KEY,id)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        }
        cancelButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}