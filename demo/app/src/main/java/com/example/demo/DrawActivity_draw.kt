package com.example.demo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.databinding.ActivityAipicBinding
import com.example.demo.databinding.ActivityDrawBinding


class DrawActivity_draw : AppCompatActivity() {
    companion object {
        const val Reget = 1
        const val draw = 2
        var curShape = Reget
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAipicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "책 제목인데 받아오는 걸로 수정해야함"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(0, 1, 0, "그림 다시 생성하기")
        menu?.add(0, 2, 0, "내가 그림 그리기")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            1 -> {
                curShape = draw
                val binding = ActivityAipicBinding.inflate(layoutInflater)
                setContentView(binding.root)
                return true
            }
            2 -> {
                curShape = Reget
                val binding = ActivityDrawBinding.inflate(layoutInflater)
                setContentView(binding.root)

                return true
            }
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}


