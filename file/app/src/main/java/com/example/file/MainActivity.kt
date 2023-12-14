package com.example.file

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.example.file.databinding.ActivityMainBinding
import java.io.*
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val context = applicationContext
        var cal = Calendar.getInstance()
        var cYear=cal.get(Calendar.YEAR)
        var cMonth = cal.get(Calendar.MONTH)
        var cDay=cal.get(Calendar.DAY_OF_MONTH)
        lateinit var fName:String
        binding.datePicker.init(cYear, cMonth,cDay){view, year, month, day ->
            fName = "$year-${month + 1}-$day.txt"
            val file = File(context.filesDir, fName)

            if (file.exists()) {
                val text = file.readText()
                binding.editText.setText(text)
            } else {
                binding.editText.setText("일기없음")
            }
        }
        binding.btnSmt.setOnClickListener {
            val file = File(context.filesDir, fName)
            val text = binding.editText.text.toString()

            file.outputStream().use {
                it.write(text.toByteArray())
            }
            Toast.makeText(context, "저장되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }
    fun readDiary(fName: String): String? {
        var diaryStr: String? = null
        try {
            val inputStream = openFileInput(fName)
            inputStream.bufferedReader().forEachLine { line ->
                diaryStr = diaryStr?.let { "$it\n$line" } ?: line
            }
            inputStream.close()
        } catch (e: IOException) {
            diaryStr = "일기 없음"
        }
        return diaryStr
    }
}