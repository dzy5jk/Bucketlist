package com.ryanadoremos.bucketlist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val saveBtn: Button = findViewById(R.id.button)
        val cancelBtn: Button = findViewById(R.id.button2)
        val itemEditText: EditText = findViewById(R.id.editTextText)
        val dueEditText: EditText = findViewById(R.id.editTextDate)
        val cmpltCheckBox: CheckBox = findViewById(R.id.checkBox)
        val doneEditText: EditText = findViewById(R.id.editTextDate2)

        saveBtn.setOnClickListener {
            val item = itemEditText.text.toString()
            val due = dueEditText.text.toString()
            val cmplt = if (cmpltCheckBox.isChecked) 1 else 0
            val done = doneEditText.text.toString()


            val db = DBHelper(this, null)
            db.addName(item, due, cmplt, done)

            Toast.makeText(this, "$item added to database", Toast.LENGTH_LONG).show()


            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        cancelBtn.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
