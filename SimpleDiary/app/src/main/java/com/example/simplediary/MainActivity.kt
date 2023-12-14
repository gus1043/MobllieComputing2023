package com.example.simplediary

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.simplediary.databinding.ActivityMainBinding
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var fName: String // 파일 이름 변수 추가

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate (layoutInflater)
        setContentView(binding.root)
        val context = applicationContext
        val TAG="Debug"
        var cal = Calendar.getInstance()
        var cYear = cal.get(Calendar. YEAR)
        var cMonth = cal.get(Calendar. MONTH)
        var cDay = cal.get(Calendar.DAY_OF_MONTH)

        // fName 초기화
        fName = "${cYear}_${cMonth + 1}_${cDay}.txt"

        binding.datePicker.init(cYear, cMonth, cDay) {view, year, month, day ->
            fName = "${year}_${month+1}_${day}.txt"
            Log.d( "TAG", fName)
            var str = readDiary (fName)
            binding.editText.setText (str)
        }

        binding.btnSmt.setOnClickListener {
            val diaryContent = binding.editText.text.toString()
            if (diaryContent.isEmpty()) {
                Toast.makeText(applicationContext, "일기 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                saveDiary(fName, diaryContent)
                binding.btnSmt.text = "수정하기"
            }
        }
    }

    fun readDiary (fName:String): String? {
        var diaryStr : String?=null
        try{
            openFileInput (fName). bufferedReader ().forEachLine{
                if(diaryStr==null){diaryStr=it}
                else{diaryStr+= "\n"+it}
                Log.d( "TAG", diaryStr!!)
                binding.btnSmt.text="87"
            }
        } catch (e: IOException) {
            binding.editText.hint= "일기 없음"
            binding.btnSmt.text = "새로 저장"
        }
        return diaryStr
    }
    fun saveDiary(fName: String, content: String) {
        openFileOutput(fName, Context.MODE_PRIVATE).use {
            it.write(content.toByteArray())
        }
        val toastMessage = "$fName"
        Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_SHORT).show()
    }

}
