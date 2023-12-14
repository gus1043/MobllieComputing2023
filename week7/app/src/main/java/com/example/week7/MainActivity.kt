package com.example.week7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.week7.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setSupportActionBar(binding.toolbar)
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        supportActionBar!!.setDisplayShowHomeEnabled(true)
//        supportActionBar!!.setDisplayShowTitleEnabled(false)
        binding.button1.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(binding.fragView.id, ExampleFragment())
                .commit()
        }
        binding.button2.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frag_view, ExampleFragmentTwo())
                .commit()
        }
    }


}

