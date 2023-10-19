package com.example.mytodoapp.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {

    @Insert()
    suspend fun insetTodo(todotable:TodoEntity) :Long

    @Update
    suspend fun updateTodo(todotable: TodoEntity):Int

    @Query("SELECT * FROM  TodoEntity")
    fun fetchTodo() :LiveData<List<TodoEntity>>

    @Delete
    suspend fun deleteARow(todotable: TodoEntity):Int


}