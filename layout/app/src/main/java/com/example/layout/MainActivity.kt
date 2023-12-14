package com.example.layout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.layout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bluebutton.setOnClickListener(this)
        binding.redbutton.setOnClickListener(this)
        binding.greenbutton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        binding.redview.visibility=View.INVISIBLE
        binding.blueview.visibility=View.INVISIBLE
        binding.greenview.visibility=View.INVISIBLE

        when(v?.id){
            binding.redbutton.id->binding.redview.visibility=View.VISIBLE
            binding.greenbutton.id->binding.greenview.visibility=View.VISIBLE
            binding.bluebutton.id->binding.blueview.visibility=View.VISIBLE

        }

    }
}