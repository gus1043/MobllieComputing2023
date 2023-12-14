package com.example.myapplication

import android.annotation.SuppressLint
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.ceil
import kotlin.math.log2
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private lateinit var binaryTreeLayout: FrameLayout
    private lateinit var pushButton: Button
    private lateinit var popButton: Button
    private lateinit var viewButton: Button
    private lateinit var visualizationButton: Button
    private lateinit var editText: EditText
    private lateinit var logTextView: TextView

    private val binaryHeap = mutableListOf<Int>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binaryTreeLayout = findViewById(R.id.binaryTreeLayout)
        pushButton = findViewById(R.id.pushButton)
        popButton = findViewById(R.id.popButton)
        viewButton = findViewById(R.id.viewButton)
        visualizationButton = findViewById(R.id.visualizationButton)
        editText = findViewById(R.id.editText)
        logTextView = findViewById(R.id.logTextView)

        pushButton.setOnClickListener {
            val input = editText.text.toString().trim()
            if (input.isNotEmpty()) {
                val value = input.toInt()
                binaryHeap.add(value)
                heapifyUp(binaryHeap.size - 1)
                editText.setText("")
            }
        }

        popButton.setOnClickListener {
            if (binaryHeap.isNotEmpty()) {
                swap(0, binaryHeap.size - 1)
                binaryHeap.removeAt(binaryHeap.size - 1)
                heapifyDown(0, binaryHeap.size)
            }
        }

        viewButton.setOnClickListener {
            heapSort()
            logHeap()
        }

        visualizationButton.setOnClickListener {
            visualizeHeap()
        }
    }

    private fun heapSort() {
        buildMinHeap()

        for (i in binaryHeap.size - 1 downTo 1) {
            swap(0, i)
            heapifyDown(0, i)
        }
    }

    private fun buildMinHeap() {
        val startIndex = binaryHeap.size / 2 - 1
        for (i in startIndex downTo 0) {
            heapifyDown(i, binaryHeap.size)
        }
    }


    private fun heapifyUp(index: Int) {
        var currentIndex = index
        var parentIndex = (currentIndex - 1) / 2
        while (currentIndex > 0 && binaryHeap[currentIndex] > binaryHeap[parentIndex]) {
            swap(currentIndex, parentIndex)
            currentIndex = parentIndex
            parentIndex = (currentIndex - 1) / 2
        }
    }

    private fun heapifyDown(index: Int, heapSize: Int) {
        val leftChildIndex = 2 * index + 1
        val rightChildIndex = 2 * index + 2
        var smallestIndex = index

        if (leftChildIndex < heapSize && binaryHeap[leftChildIndex] < binaryHeap[smallestIndex])
            smallestIndex = leftChildIndex

        if (rightChildIndex < heapSize && binaryHeap[rightChildIndex] < binaryHeap[smallestIndex])
            smallestIndex = rightChildIndex

        if (smallestIndex != index) {
            swap(index, smallestIndex)
            heapifyDown(smallestIndex, heapSize)
        }
    }

    private fun swap(i: Int, j: Int) {
        val temp = binaryHeap[i]
        binaryHeap[i] = binaryHeap[j]
        binaryHeap[j] = temp
    }

    private fun logHeap() {
        val sb = StringBuilder()
        for (i in binaryHeap.indices) {
            sb.append(binaryHeap[i]).append(" ")
        }
        logTextView.text = sb.toString()
    }

    private fun getDepth(size: Int): Int {
        return ceil(log2(size.toDouble() + 1)).toInt()
    }

    private fun visualizeHeap() {
        binaryTreeLayout.removeAllViews()

        val nodeRadius = 50
        val levelHeight = nodeRadius * 2 + 20
        val screenWidth = resources.displayMetrics.widthPixels

        val positions = mutableListOf<Point>()
        val depth = getDepth(binaryHeap.size)
        val nodesInBottomLevel = 2.0.pow(depth - 1).toInt()
        val maxWidth = nodesInBottomLevel * nodeRadius * 4 * (1 shl (depth - 1))

        val startingX = (screenWidth - maxWidth) / 2

        for (i in 0 until depth) {
            val nodesInLevel = 2.0.pow(i).toInt()
            val levelWidth = nodesInLevel * nodeRadius * 4
            val xOffset = (maxWidth - levelWidth) / 2
            for (j in 0 until nodesInLevel) {
                val x = startingX + xOffset + j * (levelWidth / maxOf(1, nodesInLevel - 1).toFloat())
                val y = i * levelHeight + 100
                positions.add(Point(x.toInt(), y))
            }
        }

        for (i in binaryHeap.indices) {
            val value = binaryHeap[i]
            val position = positions[i]
            drawCircle(position.x, position.y, nodeRadius, Color.BLUE)
            drawText(value.toString(), position.x, position.y, Color.WHITE)

            if (i > 0) {
                val parentIndex = (i - 1) / 2
                val parentPosition = positions[parentIndex]
                drawLine(parentPosition.x, parentPosition.y, position.x, position.y, nodeRadius, levelHeight)
            }

            val leftChildIndex = 2 * i + 1
            val rightChildIndex = 2 * i + 2

            if (leftChildIndex < binaryHeap.size) {
                val leftChildPosition = positions[leftChildIndex]
                drawLine(position.x, position.y, leftChildPosition.x, leftChildPosition.y, nodeRadius, levelHeight)
            }

            if (rightChildIndex < binaryHeap.size) {
                val rightChildPosition = positions[rightChildIndex]
                drawLine(position.x, position.y, rightChildPosition.x, rightChildPosition.y, nodeRadius, levelHeight)
            }
        }
    }



    private fun drawLine(startX: Int, startY: Int, endX: Int, endY: Int, nodeRadius: Int, levelHeight: Int) {
        val view = View(this)
        val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        view.layoutParams = params
        view.background = object : Drawable() {
            override fun draw(canvas: Canvas) {
                val linePaint = Paint()
                linePaint.color = Color.BLACK
                linePaint.style = Paint.Style.STROKE
                linePaint.strokeWidth = 2f

                // 시작 노드와 끝 노드의 중심 좌표 계산
                val startXCenter = startX+nodeRadius/2
                val startYCenter = startY+nodeRadius/2
                val endXCenter = endX+nodeRadius/2
                val endYCenter = endY+nodeRadius/2

                // 연결선 그리기
                val path = Path()
                path.moveTo(startXCenter.toFloat(), startYCenter.toFloat())
                path.lineTo(endXCenter.toFloat(), endYCenter.toFloat())
                canvas.drawPath(path, linePaint)
            }

            override fun setAlpha(alpha: Int) {}
            override fun setColorFilter(colorFilter: ColorFilter?) {}
            override fun getOpacity(): Int {
                return PixelFormat.TRANSLUCENT
            }
        }
        binaryTreeLayout.addView(view)
    }
    private fun drawCircle(x: Int, y: Int, radius: Int, color: Int) {
        val view = View(this)
        val params = FrameLayout.LayoutParams(radius * 2, radius * 2)
        val centerX = x - radius  // 중심 위치 계산
        val centerY = y - radius  // 중심 위치 계산
        params.setMargins(centerX, centerY, 0, 0)  // 중심 위치 설정
        view.layoutParams = params
        view.background = CircleDrawable(color)
        binaryTreeLayout.addView(view)
    }

    private fun drawText(text: String, x: Int, y: Int, color: Int) {
        val textView = TextView(this)
        textView.text = text
        textView.setTextColor(Color.YELLOW)
        textView.textSize = 18f

        // 텍스트의 너비와 높이 계산
        val paint = Paint()
        paint.textSize = textView.textSize
        val textWidth = paint.measureText(text)
        val textHeight = paint.descent() - paint.ascent()

        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        // 텍스트를 동그라미 중심에 위치시키기 위해 오프셋 계산
        val offsetX = (textWidth / 2).toInt()
        val offsetY = (textHeight / 2).toInt()
        params.setMargins(x - offsetX, y - offsetY, 0, 0)
        textView.layoutParams = params
        binaryTreeLayout.addView(textView)
    }



    inner class CircleDrawable(private val color: Int) : Drawable() {

        private val paint = Paint()

        override fun draw(canvas: Canvas) {
            paint.color = Color.BLACK
            paint.style = Paint.Style.FILL
            canvas.drawCircle(bounds.exactCenterX(), bounds.exactCenterY(), bounds.width() / 2f, paint)
        }

        override fun setAlpha(alpha: Int) {
            paint.alpha = alpha
        }

        override fun setColorFilter(colorFilter: ColorFilter?) {
            paint.colorFilter = colorFilter
        }

        override fun getOpacity(): Int {
            return PixelFormat.TRANSLUCENT}}}
