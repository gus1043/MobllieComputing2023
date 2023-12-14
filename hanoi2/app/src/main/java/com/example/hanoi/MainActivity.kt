package com.example.hanoi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val n = 3 // or any desired number of disks
        val hanoi = Hanoi(n)
        hanoi.solve()

        val messageTextView = findViewById<TextView>(R.id.message_text_view)
        messageTextView.text = hanoi.getMovesString()
    }
}

class Hanoi(val n: Int) {

    private val pegs = Array(3) { Stack<Int>() }
    private var moves = 0
    private val mvesListo = mutableListOf<String>()

    init {
        for (i in n downTo 1) {
            pegs[0].push(i)
        }
    }

    fun solve() {
        move(n, 0, 1, 2)
    }

    private fun move(disk: Int, source: Int, dest: Int, spare: Int) {
        if (disk == 1) {
            pegs[dest].push(pegs[source].pop())
            moves++
            val moveString = "Move $moves: Move disk $disk from peg ${source + 1} to peg ${dest + 1}"
            movesList.add(moveString)
            println(moveString)
        } else {
            move(disk - 1, source, spare, dest)
            move(1, source, dest, spare)
            move(disk - 1, spare, dest, source)
        }
    }

    fun getMovesString(): String {
        return movesList.joinToString("\n")
    }
}
