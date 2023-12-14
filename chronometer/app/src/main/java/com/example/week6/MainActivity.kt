package com.example.week6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.KeyEvent
import android.widget.Chronometer
import android.widget.Toast
import com.example.week6.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var initTime = 0L
    var pauseTime = 0L
    var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            binding.start.setOnClickListener {
                if (!isRunning) {
                    binding.timer.base = SystemClock.elapsedRealtime() - pauseTime
                    binding.timer.start()
                    binding.start.text = "Stop"
                    binding.stop.text = "Stop"
                    isRunning = true
                } else {
                    binding.timer.stop()
                    pauseTime = SystemClock.elapsedRealtime() - binding.timer.base
                    binding.start.text = "Start"
                    binding.stop.text = "Start"
                    isRunning = false
                }
            }

            binding.stop.setOnClickListener {
                if (!isRunning) {
                    binding.stop.text = "Stop"
                    binding.start.text = "Start"
                } else if (binding.timer.base == initTime) {
                    isRunning = false
                    binding.stop.text = "Stop"
                    binding.start.text = "Start"
                } else if (!isRunning) {
                    binding.timer.base = SystemClock.elapsedRealtime() - pauseTime
                    binding.timer.start()
                    binding.stop.text = "Stop"
                    binding.start.text = "Stop"
                    isRunning = true
                } else {
                    binding.timer.stop()
                    pauseTime = SystemClock.elapsedRealtime() - binding.timer.base
                    binding.stop.text = "Start"
                    binding.start.text = "Start"
                    isRunning = false
                }
            }

            binding.reset.setOnClickListener {
                if (!isRunning) {
                    binding.timer.base = SystemClock.elapsedRealtime()
                    pauseTime = 0
                    binding.start.text = "Start"
                    binding.stop.text = "Stop"
                }
            }
        } catch (e: NullPointerException) {
        }
    }
}