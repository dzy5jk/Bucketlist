package com.ryanadoremos.bucketlist


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " (" +
                ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_COl + " TEXT, " +
                DUE_COL + " TEXT, " +
                CMPT_COL + " INTEGER, " +
                DONE_COL + " TEXT)")
        db.execSQL(query)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }


    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database downgrade if necessary
    }


    fun addName(name: String, due: String, complete: Int, done: String) {
        val values = ContentValues().apply {
            put(NAME_COl, name)
            put(DUE_COL, due)
            put(CMPT_COL, complete)
            put(DONE_COL, done)
        }
        writableDatabase.use { db ->
            db.insert(TABLE_NAME, null, values)
        }
    }


    fun getName(): Cursor? = readableDatabase.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY $NAME_COl", null)


    fun getItemById(id: Int): Cursor? {
        val db = this.readableDatabase
        return db.query(TABLE_NAME, null, "$ID_COL = ?", arrayOf(id.toString()), null, null, null)
    }


    fun updateItem(id: Int, name: String, due: String, completed: Int, done: String) {
        val values = ContentValues().apply {
            put(NAME_COl, name)
            put(DUE_COL, due)
            put(CMPT_COL, completed)
            put(DONE_COL, done)
        }
        val db = this.writableDatabase
        db.update(TABLE_NAME, values, "$ID_COL = ?", arrayOf(id.toString()))
        db.close()
    }


    companion object {
        private const val DATABASE_NAME = "BucketList"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "Items"
        const val ID_COL = "id"
        const val NAME_COl = "name"
        const val DUE_COL = "due"
        const val CMPT_COL = "complete"
        const val DONE_COL = "done"
    }
}
