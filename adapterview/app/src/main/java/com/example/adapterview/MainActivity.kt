package com.example.adapterview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.adapterview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dataSet = arrayOf(
            ImageElement("마라샹궈", R.drawable.item1, "이걸") ,
            ImageElement("마라샹궈", R.drawable.item2, "보니까"),
            ImageElement("마라샹궈", R.drawable.item3, "배고프네")
        )
        binding.mainTxt.text="무언가"
        binding.viewpager2.adapter=MyAdapter(dataSet)

        ArrayAdapter.createFromResource(
            this,
            R.array.foods_array,
            android.R.layout.simple_spinner_dropdown_item)
        ).also{ adapter->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.foodSpinner.adapter=adpater
        }
        binding.foodSpinner.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun on ItemSelected(parent:AdapterView<*>, view: View?, pos: Int, id: Long) {
                binding.viewpager2.currentItem=pos
            }
            override fun onNothingSelected(parent:AdapterVeiw<*>)
        }
    }

}