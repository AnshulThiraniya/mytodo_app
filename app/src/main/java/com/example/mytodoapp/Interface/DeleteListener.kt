package com.example.mytodoapp.Interface

import com.example.mytodoapp.roomdb.TodoEntity

interface DeleteListener {
    fun deleteTodo(position:Int ,List :TodoEntity)
}