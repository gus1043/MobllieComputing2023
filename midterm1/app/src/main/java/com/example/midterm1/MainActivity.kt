package com.example.midterm1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.midterm1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var num1: Double
        var num2: Double
        var result: Double
        var array: Array<Double>
        binding.part1.setOnClickListener {
            if (array=emptyArray()){
                num1=part1
            }
            else (num2=part1)
        }
        binding.part2.setOnClickListener {
            if (array= emptyArray()){
                num1=part2
            }
            else (num2=part2)
        }
        binding.part3.setOnClickListener {
            if (array= emptyArray()){
                num1=part3
            }
            else (num2=part3)
        }
        binding.add.setOnClickListener {
            num1=binding.part1.toString().toDouble()
            num2=binding.part1.text.toString().toDouble()
            result=num1+num2
            binding.result.text=getString(R.string.results, result.toString())
        }
        binding.sub.setOnClickListener {
            num1=binding.num1.text.toString().toDouble()
            num2=binding.num2.text.toString().toDouble()
            result=num1-num2
            binding.result.text=getString(R.string.results, result.toString())
        }
        binding.mul.setOnClickListener {
            num1=binding.num1.text.toString().toDouble()
            num2=binding.num2.text.toString().toDouble()
            result=num1*num2
            binding.result.text=getString(R.string.results, result.toString())
        }
        binding.div.setOnClickListener {
            num1=binding.num1.text.toString().toDouble()
            num2=binding.num2.text.toString().toDouble()
            if(num2==0){
                binding.result.text="div by zero. "
            }
            else {
            result=num1/num2
            binding.result.text=getString(R.string.results, result.toString())}
        }
    }
}