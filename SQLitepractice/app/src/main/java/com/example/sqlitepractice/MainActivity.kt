import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sqlitepractice.MyAdapter
import com.example.sqlitepractice.databinding.ActivityMainBinding
import com.example.sqlitepractice.roomdb.MyDaoDatabase
import com.example.sqlitepractice.roomdb.MyTable
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MyAdapter
    private lateinit var db: MyDaoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeDatabase()
        setupRecyclerView()
        loadInitialData()

        binding.submit.setOnClickListener {
            val title = binding.title.text.toString()
            val rank = binding.rank.text.toString().toInt()

            CoroutineScope(Dispatchers.IO).launch {
                // Check if a row with the same title exists
                val existingRow = db.myDAO().getByTitle(title)

                if (existingRow != null) {
                    // Row with the same title exists, update the rank
                    val updatedRow = existingRow.copy(rank = rank)
                    db.myDAO().update(updatedRow)
                } else {
                    // Row with the same title doesn't exist, insert a new row
                    val newRow = MyTable(
                        rank,
                        title,
                        binding.artist.text.toString(),
                        binding.album.text.toString(),
                        binding.numLike.text.toString().toInt()
                    )
                    db.myDAO().insert(newRow)
                }


                // Retrieve updated list
                val newList = db.myDAO().selectAll()

                withContext(Dispatchers.Main) {
                    adapter.setList(newList as MutableList<MyTable>)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun initializeDatabase() {
        db = MyDaoDatabase.getDatabase(applicationContext)!!
        db.clearAllTables() // Clear all tables on app restart
    }

    private fun setupRecyclerView() {
        adapter = MyAdapter(mutableListOf())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
        binding.recyclerView.adapter = adapter

        adapter.setItemClickListener(object : MyAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                CoroutineScope(Dispatchers.Main).launch {
                    val selectedTitle = adapter.getElement(position).title

                    // Delete the entry by title
                    withContext(Dispatchers.IO) {
                        db.myDAO().deleteByTitle(selectedTitle)
                    }

                    // Retrieve updated list
                    val newList = withContext(Dispatchers.IO) {
                        db.myDAO().selectAll()
                    }

                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            applicationContext,
                            "$selectedTitle is deleted",
                            Toast.LENGTH_SHORT
                        ).show()

                        adapter.setList(newList as MutableList<MyTable>)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }
    private fun loadInitialData() {
        val entryArr = mutableListOf(
            MyTable(1, "aespa", "Spicy", "MY WORLD", 68136),
            MyTable(2, "Ive(아이브)", "I AM", "I've IVE", 153643),
            MyTable(3, "LE SERAFIM(르세라핌)", "UNFORGIVEN", "UNFORGIVEN", 85725),
            MyTable(4, "퀸카(Queencard)", "(여자)아이들", "I feel", 44554),
            MyTable(5, "손오공", "세븐틴(SEVENTEEN)", "SEVENTEEN 10th", 114227)
        )

        CoroutineScope(Dispatchers.IO).launch {
            for (entry in entryArr) {
                db.myDAO().insert(entry)
            }

            val getList = db.myDAO().selectAll()

            withContext(Dispatchers.Main) {
                adapter.setList(getList as MutableList<MyTable>)
            }
        }
    }
}
