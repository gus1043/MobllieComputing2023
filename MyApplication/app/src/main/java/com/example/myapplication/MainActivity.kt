package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding
import java.math.BigDecimal
import java.util.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private var lastButtonClickTime = 0L
    private val BUTTON_CLICK_DELAY = 1000L // 1초 지연

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.add.setOnClickListener { calculate("+") }
        binding.sub.setOnClickListener { calculate("-") }
        binding.mul.setOnClickListener { calculate("*") }
        binding.div.setOnClickListener { calculate("/") }
        binding.reset.setOnClickListener {
            if (System.currentTimeMillis() - lastButtonClickTime >= BUTTON_CLICK_DELAY) {
                // Add your button click logic here
                binding.num1.text = null
                binding.num2.text = null
                binding.result.text = getString(R.string.results, "0.0")
                lastButtonClickTime = System.currentTimeMillis()
                Toast.makeText(this,"초기화 되었습니다. ",Toast.LENGTH_SHORT).show()
            } else {
                // Add your delayed button click logic here
                Toast.makeText(this,"1초 뒤에 다시 눌러주세요",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun calculate(operator: String) {
        try {
            val num1 = binding.num1.text.toString().toBigDecimal()
            val num2 = binding.num2.text.toString().toBigDecimal()
            val result = when (operator) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                "*" -> num1 * num2
                "/" -> {
                    if (num2.compareTo(BigDecimal.ZERO) == 0) {
                        Toast.makeText(this, "Error: 0으로 나눌 수 없습니다.", Toast.LENGTH_SHORT).show()
                        return
                    } else {
                        num1 / num2
                    }
                }
                else -> throw IllegalArgumentException("Invalid operator: $operator")
            }
            binding.result.text = getString(R.string.results, result.toString())
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Error: 숫자 미입력 시 계산할 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}
