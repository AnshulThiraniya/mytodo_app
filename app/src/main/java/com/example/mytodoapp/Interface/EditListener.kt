package com.example.mytodoapp.Interface

import com.example.mytodoapp.roomdb.TodoEntity

interface EditListener {
    fun editTodo(position:Int ,List : TodoEntity)
}