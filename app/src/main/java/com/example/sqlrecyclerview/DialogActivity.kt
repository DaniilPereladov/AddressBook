package com.example.sqlrecyclerview

import android.R
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment


class DialogActivity: DialogFragment() {
    companion object {
        const val REQUEST_CODE = 1
        const val RESULT_KEY = "RESULT_KEY"
        const val ITEM_ID_KEY = "ITEM_ID_KEY"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)


            .setTitle(" Потдвердите удаление")
            .setMessage("")
            .setPositiveButton("OK",
                DialogInterface.OnClickListener { _, _ -> (activity as ContactDetails).okClicked() })
            .setNegativeButton("Отмена",
                DialogInterface.OnClickListener { _, _ -> (activity as ContactDetails).cancelClicked() })
        return builder.create()
    }
}