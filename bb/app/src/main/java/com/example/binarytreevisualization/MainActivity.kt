import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.binarytreevisualization.databinding.ActivityMainBinding
import kotlin.math.ceil
import kotlin.math.log
import kotlin.math.min
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val heapArray = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.pushButton.setOnClickListener {
            val valueStr = binding.editText.text.toString()
            if (valueStr.isNotEmpty()) {
                val value = valueStr.toInt()
                heapArray.add(value)
                heapifyUp(heapArray.size - 1)
            }
            binding.editText.text.clear()
        }

        binding.popButton.setOnClickListener {
            if (heapArray.isNotEmpty()) {
                heapArray[0] = heapArray[heapArray.size - 1]
                heapArray.removeAt(heapArray.size - 1)
                heapifyDown(0)
            }
        }

        binding.viewButton.setOnClickListener {
            binding.logTextView.text = heapArray.toString()
        }

        binding.visualizationButton.setOnClickListener {
            visualizeHeapTree()
        }
    }

    private fun heapifyUp(index: Int) {
        var currentIndex = index
        var parentIndex = (currentIndex - 1) / 2
        while (currentIndex > 0 && heapArray[currentIndex] > heapArray[parentIndex]) {
            val temp = heapArray[currentIndex]
            heapArray[currentIndex] = heapArray[parentIndex]
            heapArray[parentIndex] = temp
            currentIndex = parentIndex
            parentIndex = (currentIndex - 1) / 2
        }
    }

    private fun heapifyDown(index: Int) {
        var currentIndex = index
        var maxChildIndex: Int
        while (currentIndex < heapArray.size / 2) {
            val leftChildIndex = 2 * currentIndex + 1
            val rightChildIndex = 2 * currentIndex + 2
            maxChildIndex = if (rightChildIndex < heapArray.size && heapArray[rightChildIndex] > heapArray[leftChildIndex]) {
                rightChildIndex
            } else {
                leftChildIndex
            }

            if (heapArray[currentIndex] < heapArray[maxChildIndex]) {
                val temp = heapArray[currentIndex]
                heapArray[currentIndex] = heapArray[maxChildIndex]
                heapArray[maxChildIndex] = temp
                currentIndex = maxChildIndex
            } else {
                break
            }
        }
    }

    private fun visualizeHeapTree() {
        if (heapArray.isEmpty()) {
            return
        }

        val treeDepth = ceil(log(heapArray.size + 1.toDouble(), 2.0)).toInt()
        val maxNodesAtLevel = 2.0.pow(treeDepth - 1).toInt()
        val maxTreeWidth = maxNodesAtLevel * 80

        val bitmap = Bitmap.createBitmap(maxTreeWidth, treeDepth * 120, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL
        paint.textSize = 30f
        paint.isAntiAlias = true

        drawTree(canvas, paint, 0, 0, 0, maxTreeWidth, treeDepth - 1)

        binding.treeImageView.setImageBitmap(bitmap)
    }

    private fun drawTree(canvas: Canvas, paint: Paint, x: Int, y: Int, level: Int, maxWidth: Int, maxLevel: Int) {
        if (level > maxLevel || heapArray.isEmpty()) {
            return
        }

        val nodesAtLevel = 2.0.pow(level.toDouble()).toInt()
        val nodeWidth = maxWidth / nodesAtLevel
        val xOffset = nodeWidth / 2

        val startIndex = 2.0.pow(level.toDouble()).toInt() - 1
        val endIndex = min(heapArray.size, startIndex + nodesAtLevel)

        for (i in startIndex until endIndex) {
            val nodeX = x + (i - startIndex) * nodeWidth + xOffset
            val nodeY = y + level * 120
            drawNode(canvas, paint, nodeX, nodeY, heapArray[i])

            val leftChildIndex = 2 * i + 1
            val rightChildIndex = 2 * i + 2

            if (leftChildIndex < heapArray.size) {
                val childX = x + (leftChildIndex - startIndex) * nodeWidth + xOffset
                val childY = y + (level + 1) * 120
                canvas.drawLine(nodeX.toFloat(), (nodeY + 30).toFloat(), childX.toFloat(), childY.toFloat(), paint)
                drawTree(canvas, paint, childX, childY, level + 1, maxWidth, maxLevel)
            }

            if (rightChildIndex < heapArray.size) {
                val childX = x + (rightChildIndex - startIndex) * nodeWidth + xOffset
                val childY = y + (level + 1) * 120
                canvas.drawLine(nodeX.toFloat(), (nodeY + 30).toFloat(), childX.toFloat(), childY.toFloat(), paint)
                drawTree(canvas, paint, childX, childY, level + 1, maxWidth, maxLevel)
            }
        }
    }

    private fun drawNode(canvas: Canvas, paint: Paint, x: Int, y: Int, value: Int) {
        canvas.drawCircle(x.toFloat(), y.toFloat(), 30f, paint)
        paint.color = Color.WHITE
        val textWidth = paint.measureText(value.toString())
        canvas.drawText(value.toString(), x - textWidth / 2, y + 10, paint)
        paint.color = Color.BLACK
    }
}

