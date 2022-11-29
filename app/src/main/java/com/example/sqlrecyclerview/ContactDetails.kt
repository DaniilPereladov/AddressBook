package com.example.sqlrecyclerview

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.DBHelper


class ContactDetails: AppCompatActivity()
{
    private val dbHelper = DBHelper(this)
    private val list = mutableListOf<Task>()

        companion object {
            const val REQUEST_CODE = 1
            const val RESULT_KEY = "RESULT_KEY"
            const val ITEM_ID_KEY = "ITEM_ID_KEY"
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val textFname = findViewById<TextView>(R.id.textView7)
        val textLname = findViewById<TextView>(R.id.textView15)
        val textPhone = findViewById<TextView>(R.id.textView10)
        val textBirth = findViewById<TextView>(R.id.textView9)
        val buttonBack = findViewById<Button>(R.id.button2)
        val buttonEdit = findViewById<Button>(R.id.button3)
        val buttonDelete = findViewById<Button>(R.id.button4)


        var id = intent.getLongExtra(MainActivity.ITEM_ID_KEY, 0)
        if (id.toInt()==0)
        {
            id = intent.getLongExtra(ContactDetails.ITEM_ID_KEY, 0)
        }
        val task = dbHelper.getById(id.toInt())
        textFname.text = task?.fname
        textLname.text = task?.lname
        textBirth.text = task?.birth
        textPhone.text = task?.phone
        textPhone.setOnClickListener {
            val phoneNumbers = "tel:"+textPhone.text
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse(phoneNumbers))
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }
        buttonEdit.setOnClickListener{
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra(ContactDetails.ITEM_ID_KEY, id)
            intent.putExtra(ContactDetails.RESULT_KEY, REQUEST_CODE)
            startActivityForResult(intent, ContactDetails.REQUEST_CODE)
        }

        buttonBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        buttonDelete.setOnClickListener {
            val myDialogFragment = DialogActivity()
            val manager = supportFragmentManager
            myDialogFragment.show(manager, "myDialog")
        }

    }

    fun okClicked() {
        var id = intent.getLongExtra(MainActivity.ITEM_ID_KEY, 0)
        dbHelper.deleteTask(id)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun cancelClicked() {
        var id = intent.getLongExtra(MainActivity.ITEM_ID_KEY, 0)
        val intent = Intent(this, ContactDetails::class.java)
        intent.putExtra(ContactDetails.ITEM_ID_KEY, id)
        startActivity(intent)
        finish()
    }

}