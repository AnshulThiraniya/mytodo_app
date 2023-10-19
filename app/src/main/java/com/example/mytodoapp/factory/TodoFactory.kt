package com.example.mytodoapp.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mytodoapp.repository.TodoRepository
import com.example.mytodoapp.viewmodel.TodoViewmodel
import java.lang.IllegalArgumentException

class TodoFactory(var context: Context, var todoRepository : TodoRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewmodel::class.java)){
            return TodoViewmodel(context,todoRepository) as T
        }
        throw IllegalArgumentException("Illegal class")
    }
}