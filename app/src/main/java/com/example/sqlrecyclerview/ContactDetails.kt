package com.example.sqlrecyclerview

import androidx.appcompat.app.AppCompatActivity

class ContactDetails: AppCompatActivity()
{
    private val dbHelper = DBHelper(this)

    companion object {
        const val REQUEST_CODE = 1
        const val ITEM_KEY = "ITEM_KEY"
        const val ITEM_ID_KEY = "ITEM_ID_KEY"
    }

}