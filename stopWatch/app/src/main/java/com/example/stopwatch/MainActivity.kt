package com.example.stopwatch

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Toast
import com.example.stopwatch.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var isChronoRunning = false
    var strArray = Array<String>(5) { "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rdoCal.visibility = View.INVISIBLE
        binding.rdoTime.visibility = View.INVISIBLE
        binding.calenderView.visibility = View.INVISIBLE
        binding.timePicker.visibility = View.INVISIBLE

        // Get the default date and time
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        // Set the default date and time to the array
        strArray[0] = year.toString()
        strArray[1] = (month + 1).toString()
        strArray[2] = day.toString()
        strArray[3] = hour.toString()
        strArray[4] = minute.toString()

        binding.rdoCal.setOnClickListener {
            binding.calenderView.visibility = View.VISIBLE
            binding.timePicker.visibility = View.INVISIBLE
        }

        binding.rdoTime.setOnClickListener {
            binding.calenderView.visibility = View.INVISIBLE
            binding.timePicker.visibility = View.VISIBLE
        }

        binding.chrono.setOnClickListener {
            if (!isChronoRunning) {
                binding.chrono.base = SystemClock.elapsedRealtime()
                binding.chrono.start()
                binding.chrono.setTextColor(Color.RED)
                binding.rdoCal.visibility = View.VISIBLE
                binding.rdoTime.visibility = View.VISIBLE
                isChronoRunning = true
            }
        }

        binding.textbar.setOnLongClickListener {
            if (isChronoRunning) {
                binding.chrono.stop()
                binding.chrono.setTextColor(Color.BLUE)
                binding.rdoCal.visibility = View.INVISIBLE
                binding.rdoTime.visibility = View.INVISIBLE
                binding.calenderView.visibility = View.INVISIBLE
                binding.timePicker.visibility = View.INVISIBLE
                isChronoRunning = false
                binding.res.text = getString(R.string.done, strArray[0], strArray[1], strArray[2], strArray[3], strArray[4])
            }
            true
        }


        binding.calenderView.setOnDateChangeListener { calendarView, year, month, day ->
            strArray[0] = year.toString()
            strArray[1] = (month+1).toString()
            strArray[2] = day.toString()
        }

        binding.timePicker.setOnTimeChangedListener { timePicker, hour, min ->
            strArray[3] = hour.toString()
            strArray[4] = min.toString()
        }
    }
}
