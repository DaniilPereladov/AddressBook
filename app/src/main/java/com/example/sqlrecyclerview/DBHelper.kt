package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.database.getStringOrNull
import com.example.sqlrecyclerview.Task

class DBHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        // версия БД
        const val DATABASE_VERSION = 1
        // название БД
        const val DATABASE_NAME = "tasks-db-ver-4"
        // название таблицы
        const val TABLE_NAME = "tasks"
        // названия полей
        const val KEY_ID = "id"
        const val KEY_FNAME = "fname"
        const val KEY_LNAME = "lname"
        const val KEY_BIRTH = "birth"
        const val KEY_PHONE = "phone"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE $TABLE_NAME (
            $KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $KEY_FNAME TEXT NOT NULL,
            $KEY_LNAME TEXT NOT NULL,
            $KEY_PHONE TEXT NOT NULL,
            $KEY_BIRTH TEXT NOT NULL 
            )""")
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun getAllTasks(): List<Task> {
        val result = mutableListOf<Task>()
        val database = this.writableDatabase
        val cursor: Cursor = database.query(
            TABLE_NAME, null, null, null,
            null, null, null
        )
        if (cursor.moveToFirst()) {
            val idIndex: Int = cursor.getColumnIndex(KEY_ID)
            val fnameIndex: Int = cursor.getColumnIndex(KEY_FNAME)
            val lnameIndex: Int = cursor.getColumnIndex(KEY_LNAME)
            val birthIndex: Int = cursor.getColumnIndex(KEY_BIRTH)
            val phoneIndex: Int = cursor.getColumnIndex(KEY_PHONE)
            do {
                val todo = Task(
                    cursor.getLong(idIndex),
                    cursor.getString(fnameIndex),
                    cursor.getString(lnameIndex),
                    cursor.getString(phoneIndex),
                    cursor.getStringOrNull(birthIndex)
                )
                result.add(todo)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return result
    }

    fun getById(id: Int): Task? {
        var result: Task? = null
        val database = this.writableDatabase
        val cursor: Cursor = database.query(
            TABLE_NAME, null, "$KEY_ID = ?", arrayOf(id.toString()),
            null, null, null
        )
        if (cursor.moveToFirst()) {
            val idIndex: Int = cursor.getColumnIndex(KEY_ID)
            val fnameIndex: Int = cursor.getColumnIndex(KEY_FNAME)
            val lnameIndex: Int = cursor.getColumnIndex(KEY_LNAME)
            val birthIndex: Int = cursor.getColumnIndex(KEY_BIRTH)
            val phoneIndex: Int = cursor.getColumnIndex(KEY_PHONE)
            result = Task(
                cursor.getLong(idIndex),
                cursor.getString(fnameIndex),
                cursor.getString(lnameIndex),
                cursor.getString(phoneIndex),
                cursor.getString(birthIndex)
            )
        }
        cursor.close()
        return result
    }

    fun getByName(fname: String): List<Task> {
        var result=mutableListOf<Task>()
        val database = this.writableDatabase
        val cursor: Cursor = database.query(
            TABLE_NAME, null, "fname = ?", arrayOf(fname.toString()),
            null, null, null
        )
        if (cursor.moveToFirst()) {
            val idIndex: Int = cursor.getColumnIndex(KEY_ID)
            val fnameIndex: Int = cursor.getColumnIndex(KEY_FNAME)
            val lnameIndex: Int = cursor.getColumnIndex(KEY_LNAME)
            val birthIndex: Int = cursor.getColumnIndex(KEY_BIRTH)
            val phoneIndex: Int = cursor.getColumnIndex(KEY_PHONE)
            val todo = Task(
                cursor.getLong(idIndex),
                cursor.getString(fnameIndex),
                cursor.getString(lnameIndex),
                cursor.getString(phoneIndex),
                cursor.getString(birthIndex)
            )
            result.add(todo)
        }
        cursor.close()
        return result
    }

    fun addTask(fname: String,lname: String,phone:String,birth:String): Long {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_FNAME, fname)
        contentValues.put(KEY_LNAME,lname)
        contentValues.put(KEY_PHONE,phone)
        contentValues.put(KEY_BIRTH,birth)
        val id = database.insert(TABLE_NAME, null, contentValues)
        close()
        return id
    }

    fun updateTask(id: Long, fname: String,lname: String,phone:String,birth: String) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_FNAME, fname)
        contentValues.put(KEY_LNAME,lname)
        contentValues.put(KEY_PHONE,phone)
        contentValues.put(KEY_BIRTH,birth)
        database.update(TABLE_NAME, contentValues, "$KEY_ID = ?", arrayOf(id.toString()))
        close()
    }

    fun deleteTask(id: Long) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, "$KEY_ID = ?", arrayOf(id.toString()))
        close()
    }
}