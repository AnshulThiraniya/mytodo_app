package com.example.mytodoapp.repository

import androidx.lifecycle.LiveData
import com.example.mytodoapp.roomdb.TodoDao
import com.example.mytodoapp.roomdb.TodoEntity
class TodoRepository(var todoDao: TodoDao) {

    suspend fun InsertTodo(TodoEntity: TodoEntity):Long{
        return todoDao.insetTodo(TodoEntity)
    }
    var todoData :LiveData<List<TodoEntity>> = todoDao.fetchTodo()

    suspend fun updateTOdo(TodoEntity: TodoEntity) :Int{

        return todoDao.updateTodo(TodoEntity)
    }

    suspend fun deleteATodo(TodoEntity: TodoEntity):Int{
        return todoDao.deleteARow(TodoEntity)
    }
}