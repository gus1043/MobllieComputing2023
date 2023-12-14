package com.example.graphic

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View

class MainActivity : AppCompatActivity() {
    companion object {
        const val LINE = 1
        const val CIRCLE = 2
        const val SQUARE = 3
        var curShape = LINE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(SimplePainter(this))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(0, 1, 0, "Draw Line")
        menu?.add(0, 2, 0, "Draw Circle")
        menu?.add(0, 3, 0, "Draw Square")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            1 -> {
                curShape = LINE
                return true
            }
            2 -> {
                curShape = CIRCLE
                return true
            }
            3 -> {
                curShape = SQUARE
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private class SimplePainter(context: Context) : View(context) {
        private val shapeList = ArrayList<MyShape>()

        private var startX = 0f
        private var startY = 0f
        private var stopX = 0f
        private var stopY = 0f
        private val paint = Paint()

        override fun onTouchEvent(event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = event.x
                    startY = event.y
                    stopX = event.x
                    stopY = event.y
                    invalidate()
                }
                MotionEvent.ACTION_MOVE -> {
                    stopX = event.x
                    stopY = event.y
                    invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    stopX = event.x
                    stopY = event.y
                    shapeList.add(
                        MyShape(
                            curShape,
                            startX.toInt(),
                            startY.toInt(),
                            stopX.toInt(),
                            stopY.toInt(),
                            Paint(paint)
                        )
                    )
                    invalidate()
                }
            }
            return true
        }
        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)

            for (shape in shapeList) {
                shape.paint.style = Paint.Style.STROKE  // 선으로 도형 그리기
                shape.paint.color = Color.RED  // 빨간색 설정
                shape.paint.strokeWidth = 5f

                when (shape.shapeType) {
                    LINE -> {
                        canvas.drawLine(
                            shape.startX.toFloat(),
                            shape.startY.toFloat(),
                            shape.stopX.toFloat(),
                            shape.stopY.toFloat(),
                            shape.paint
                        )
                    }
                    CIRCLE -> {
                        val radius = Math.sqrt(
                            Math.pow((shape.stopX - shape.startX).toDouble(), 2.0) +
                                    Math.pow((shape.stopY - shape.startY).toDouble(), 2.0)
                        )
                        canvas.drawCircle(
                            shape.startX.toFloat(),
                            shape.startY.toFloat(),
                            radius.toFloat(),
                            shape.paint
                        )
                    }
                    SQUARE -> {
                        val left = Math.min(shape.startX, shape.stopX)
                        val right = Math.max(shape.startX, shape.stopX)
                        val top = Math.min(shape.startY, shape.stopY)
                        val bottom = Math.max(shape.startY, shape.stopY)
                        canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), shape.paint)
                    }
                }
            }

            paint.style = Paint.Style.STROKE
            paint.color = Color.RED
            paint.strokeWidth = 5f

            when (curShape) {
                LINE -> {
                    canvas.drawLine(startX, startY, stopX, stopY, paint)
                }
                CIRCLE -> {
                    val radius =
                        Math.sqrt(Math.pow((stopX - startX).toDouble(), 2.0) + Math.pow((stopY - startY).toDouble(), 2.0))
                    canvas.drawCircle(startX, startY, radius.toFloat(), paint)
                }
                SQUARE -> {
                    val left = Math.min(startX, stopX)
                    val right = Math.max(startX, stopX)
                    val top = Math.min(startY, stopY)
                    val bottom = Math.max(startY, stopY)
                    canvas.drawRect(left, top, right, bottom, paint)
                }
            }
        }
    }

    private data class MyShape(
        val shapeType: Int,
        val startX: Int,
        val startY: Int,
        val stopX: Int,
        val stopY: Int,
        val paint: Paint
    )
}
