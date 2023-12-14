package com.example.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import com.example.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()?.setTitle("그림책 만들기")
        val numberMan = findViewById<NumberPicker>(R.id.NumMan)
        val manDecrease = findViewById<Button>(R.id.minusMan)
        val manIncrease = findViewById<Button>(R.id.minusMan)
        val numberWoman = findViewById<NumberPicker>(R.id.NumWoman)
        val womanDecrease = findViewById<Button>(R.id.minusWoman)
        val womanIncrease = findViewById<Button>(R.id.minusWoman)

        manDecrease.setOnClickListener {
            // NumberPicker 값을 감소시키는 로직 추가
            val currentValue = numberMan.value
            if (currentValue > numberMan.minValue) {
                numberMan.value = currentValue - 1
            }
        }

        manIncrease.setOnClickListener {
            // NumberPicker 값을 증가시키는 로직 추가
            val currentValue = numberMan.value
            if (currentValue < numberMan.maxValue) {
                numberMan.value = currentValue + 1
            }
        }

        womanDecrease.setOnClickListener {
            // NumberPicker 값을 감소시키는 로직 추가
            val currentValue = numberMan.value
            if (currentValue > numberMan.minValue) {
                numberMan.value = currentValue - 1
            }
        }

        womanIncrease.setOnClickListener {
            // NumberPicker 값을 증가시키는 로직 추가
            val currentValue = numberMan.value
            if (currentValue < numberMan.maxValue) {
                numberMan.value = currentValue + 1
            }
        }

        binding.btnwrite.setOnClickListener {
            val intent: Intent =Intent(this,SubActivity::class.java).apply {
                putExtra("next", "level")
            }
            val selectedGenre = binding.genre.selectedItem.toString()
            val selectedEra = binding.era.selectedItem.toString()
            intent.putExtra("selectedGenre", selectedGenre)
            intent.putExtra("selectedEra", selectedEra)
            intent.putExtra("NumMan", numberMan.value)
            intent.putExtra("NumWoman", numberWoman.value)
            intent.putExtra("num", 30)
            intent.putExtra("summary", binding.writesum.text.toString())
            startActivity(intent)
        }

    }
}