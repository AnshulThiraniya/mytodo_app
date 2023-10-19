package com.example.mytodoapp.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [TodoEntity::class], version = 1)
abstract class TodoDatabase :RoomDatabase() {
    abstract val  todoDao :TodoDao
    companion object{
        @Volatile
        private var INSTANCE : TodoDatabase? = null

        fun getInstance(context: Context):TodoDatabase{
            var instance = INSTANCE
            if (instance == null){
                instance = Room.databaseBuilder(context,TodoDatabase::class.java,"TodoDb").build()
            }
            return instance
        }


    }

}