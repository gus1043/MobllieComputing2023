package com.example.tablayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tablayout.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val tab = binding.tab
        val tab1: TabLayout.Tab = tab.newTab()
        tab1.text = "tab1"
        tab.addTab(tab1)
        tab.addTab(tab.newTab().setText("tab2"))
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val transaction = supportFragmentManager.beginTransaction()
                when (tab?.text) {
                    "tab1" -> transaction.replace(R.id.tabContent, ExampleFragment())
                    "tab2" -> transaction.replace(binding.tabContent.id, ExampleFragmentTwo())
                }
                transaction.commit()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                /* */
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                /* */
            }
        })
    }
}