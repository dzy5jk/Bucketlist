package com.ryanadoremos.bucketlist

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class EditActivity : AppCompatActivity() {

    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button
    private lateinit var itemNameEditText: EditText
    private lateinit var itemDueEditText: EditText
    private lateinit var itemCompletedCheckBox: CheckBox
    private lateinit var itemDoneEditText: EditText
    private lateinit var db: DBHelper

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        saveButton = findViewById(R.id.button4)
        cancelButton = findViewById(R.id.button5)
        itemNameEditText = findViewById(R.id.editTextText2)
        itemDueEditText = findViewById(R.id.editTextDate3)
        itemCompletedCheckBox = findViewById(R.id.checkBox2)
        itemDoneEditText = findViewById(R.id.editTextDate4)
        db = DBHelper(this, null)

        val itemId = intent.getIntExtra("ITEM_ID", -1)
        if (itemId != -1) {
            val cursor = db.getItemById(itemId)
            cursor?.use { cursor ->
                if (cursor.moveToFirst()) {
                    itemNameEditText.setText(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)))
                    itemDueEditText.setText(cursor.getString(cursor.getColumnIndex(DBHelper.DUE_COL)))
                    itemCompletedCheckBox.isChecked = cursor.getInt(cursor.getColumnIndex(DBHelper.CMPT_COL)) == 1
                    itemDoneEditText.setText(cursor.getString(cursor.getColumnIndex(DBHelper.DONE_COL)))
                }
            }
        }

        saveButton.setOnClickListener {
            val newName = itemNameEditText.text.toString()
            val newDue = itemDueEditText.text.toString()
            val newComplete = if (itemCompletedCheckBox.isChecked) 1 else 0
            val newDone = itemDoneEditText.text.toString()
            db.updateItem(itemId, newName, newDue, newComplete, newDone)
            Toast.makeText(this, "Item updated!", Toast.LENGTH_SHORT).show()
            finish()
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }
}