package com.example.midterm2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import com.example.midterm2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var count = SystemClock.elapsedRealtime()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.start.setOnClickListener {
            binding.timer.start()
            binding.stop.text="stop"
            binding.start.text="Lab"}

        binding.stop.setOnClickListener {
            binding.timer.stop()
            binding.stop.text="start"
            binding.start.text="reset"}

        binding.add.setOnClickListener {
            count=count+5
        }
        binding.sub.setOnClickListener {
            count=count-5
        }
    }
}