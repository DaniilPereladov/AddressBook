package com.example.sqlrecyclerview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.ColorUtils
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator
import com.example.myapplication.DBHelper

class EditActivity : AppCompatActivity() {

    lateinit var backgroundLayout: ConstraintLayout
    lateinit var windowLayout: ConstraintLayout
    private val dbHelper = DBHelper(this)
    companion object {
        const val RESULT_KEY_FNAME = "RESULT_KEY_FNAME"
        const val RESULT_KEY_LNAME = "RESULT_KEY_LNAME"
        const val ITEM_PHONE= "ITEM_PHONE"
        const val ITEM_BIRTH = "ITEM_BIRTH"
        const val ITEM_FNAME = "ITEM_FNAME"
        const val ITEM_LNAME = "ITEM_LNAME"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val editFName = findViewById<EditText>(R.id.editTextTextPersonName2)
        val editBirth = findViewById<EditText>(R.id.editTextTextPersonName3)
        val editPhone = findViewById<EditText>(R.id.editTextPhone)
        val editLName = findViewById<EditText>(R.id.editTextTextPersonName9)
        val saveButton = findViewById<Button>(R.id.button5)
        val cancelButton = findViewById<Button>(R.id.button6)
            val id2 = intent.getLongExtra(ContactDetails.ITEM_ID_KEY, 0)
            val task = dbHelper.getById(id2.toInt())
            editFName.setText(task?.fname)
            editBirth.setText(task?.birth)
            editLName.setText(task?.lname)
            editPhone.setText(task?.phone.toString())

            saveButton.setOnClickListener {
            val fname = editFName.text.toString()
                val lname = editLName.text.toString()
            val phone = editPhone.text.toString()
            val birth = editBirth.text.toString()
            if (title.isNotBlank() && phone.isNotBlank() && birth.isNotBlank()) {
                dbHelper.updateTask(id2, fname,lname,phone,birth)
                val returnIntent = Intent()
                returnIntent.putExtra(RESULT_KEY_FNAME, fname)
                returnIntent.putExtra(RESULT_KEY_LNAME, lname)
                returnIntent.putExtra(MainActivity.ITEM_ID_KEY, id2)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
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