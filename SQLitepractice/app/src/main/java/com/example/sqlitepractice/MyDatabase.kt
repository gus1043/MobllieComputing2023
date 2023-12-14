import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import com.example.sqlitepractice.MyElement

class MyDatabase {
    object MyDBContract {
        object MyEntry : BaseColumns {
            const val TABLE_NAME = "myDBfile"
            const val rank = "rank"
            const val title = "title"
            const val artist = "artist"
            const val album = "album"
            const val num_like = "num_like"
        }

    }

    class MyDbHelper(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${MyDBContract.MyEntry.TABLE_NAME} (" +
                    "${MyDBContract.MyEntry.rank} INTEGER," +
                    "${MyDBContract.MyEntry.title} TEXT PRIMARY KEY," +
                    "${MyDBContract.MyEntry.artist} TEXT," +
                    "${MyDBContract.MyEntry.album} TEXT," +
                    "${MyDBContract.MyEntry.num_like} INTEGER)"
        val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${MyDBContract.MyEntry.TABLE_NAME}"
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(SQL_CREATE_ENTRIES)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL(SQL_DELETE_ENTRIES)
            onCreate(db)
        }

        override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            onUpgrade(db, oldVersion, newVersion)
        }

        companion object {
            const val DATABASE_VERSION = 1
            const val DATABASE_NAME = "myDBfile.db"
        }

        fun selectAll(): MutableList<MyElement> {
            val readList = mutableListOf<MyElement>()
            val db = readableDatabase
            val cursor = db.rawQuery("SELECT * FROM " + MyDBContract.MyEntry.TABLE_NAME + ";", null)
            Log.d(
                "TAG",
                "Select All Query: " + "SELECT * FROM " + MyDBContract.MyEntry.TABLE_NAME + ";"
            )
            Log.d("TAG", cursor.toString())
            with(cursor) {
                while (moveToNext()) {
                    readList.add(
                        MyElement(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getInt(4)
                        )
                    )
                }
            }
            cursor.close()
            db.close()
            return readList
        }
    }
}
