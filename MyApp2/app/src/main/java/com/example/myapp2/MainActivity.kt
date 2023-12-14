package com.example.myapp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
//    lateinit var button1 : Button
    lateinit var button2 : Button
    lateinit var button3 : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        button1=findViewById<Button>(R.id.button)
//        button1.setOnClickListener{
//            Toast.makeText(applicationContext, "Button clicked", Toast.LENGTH_SHORT).show()
//        }
        button2=findViewById<Button>(R.id.button2)
        button2.setOnClickListener{
            Toast.makeText(applicationContext, "CHOI JI JYEON", Toast.LENGTH_LONG).show()
        }
        button3=findViewById<Button>(R.id.button3)
        button3.setOnClickListener{
            Toast.makeText(applicationContext, "60211704", Toast.LENGTH_LONG).show()
        }
    }
}