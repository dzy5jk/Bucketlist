package com.ryanadoremos.bucketlist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var addButton: Button
    private lateinit var db: DBHelper

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.lview)
        addButton = findViewById(R.id.button3)
        db = DBHelper(this, null)

        addButton.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val cursor = db.getName()
            cursor?.let {
                if (it.moveToPosition(position)) {
                    val itemId = it.getInt(it.getColumnIndex(DBHelper.ID_COL))
                    val intent = Intent(this, EditActivity::class.java).apply {
                        putExtra("ITEM_ID", itemId)
                    }
                    startActivity(intent)
                } else {

                }
            } ?: run {
            }
        }

        displayItems()
    }

    @SuppressLint("Range")
    private fun displayItems() {
        val cursor = db.getName()
        val itemsList = ArrayList<String>()
        cursor?.use {
            while (it.moveToNext()) {
                val name = it.getString(it.getColumnIndex(DBHelper.NAME_COl))
                val due = it.getString(it.getColumnIndex(DBHelper.DUE_COL))
                var complete = it.getInt(it.getColumnIndex(DBHelper.CMPT_COL)).toString()
                val done = it.getString(it.getColumnIndex(DBHelper.DONE_COL))
                itemsList.add("Item: $name\nDue: $due\nCompleted: $complete\nDone: $done")
            }
        } ?: run {

        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, itemsList)
        listView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        displayItems()
    }
}