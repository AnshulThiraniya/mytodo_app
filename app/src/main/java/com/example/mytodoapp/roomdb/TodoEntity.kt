package com.example.mytodoapp.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("TodoId")
    var TodoId:Int,

    @ColumnInfo("TodoTitle")
    var TodoTitle:String,

    @ColumnInfo("TodoDiscription")
    var  TodoDiscription:String
)
