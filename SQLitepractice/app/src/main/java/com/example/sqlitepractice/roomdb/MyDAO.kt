package com.example.sqlitepractice.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MyDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(myTable: MyTable)

    @Update
    fun update(myTable: MyTable)


    @Delete
    fun delete(myTable: MyTable)

    @Query("SELECT * FROM MyTable ORDER BY rank ASC")
    fun selectAll(): MutableList<MyTable>

    @Query("DELETE FROM MYTable WHERE title=:title")
    fun deleteByTitle(title: String)

    @Query("SELECT * FROM MyTable WHERE title=:title")
    fun getByTitle(title: String): MyTable?
}