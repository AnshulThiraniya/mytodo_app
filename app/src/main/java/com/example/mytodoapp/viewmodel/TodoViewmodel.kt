package com.example.mytodoapp.viewmodel

import android.app.Dialog
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytodoapp.Event
import com.example.mytodoapp.repository.TodoRepository
import com.example.mytodoapp.roomdb.TodoDao
import com.example.mytodoapp.roomdb.TodoDatabase
import com.example.mytodoapp.roomdb.TodoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TodoViewmodel(var context: Context, var todoRepository: TodoRepository) : ViewModel() {
    var title: MutableLiveData<String> = MutableLiveData()
    var btnSaveUpdateText: MutableLiveData<String> = MutableLiveData()
    var cancleBtn: MutableLiveData<Boolean> = MutableLiveData()
    var createdialog: MutableLiveData<Boolean> = MutableLiveData()
    var btnSaveUpdate: MutableLiveData<Boolean> = MutableLiveData()
    var todoTitle: MutableLiveData<String> = MutableLiveData()
    var todoDescription: MutableLiveData<String> = MutableLiveData()
    var statusMessage: MutableLiveData<Event<String>> = MutableLiveData<Event<String>>()
    var todoLiveData : LiveData<List<TodoEntity>> = todoRepository.todoData
    var rowid :MutableLiveData<Int> = MutableLiveData()
    var dismissDialog: MutableLiveData<Boolean> = MutableLiveData()
    init {
        title.value = "Create"
        btnSaveUpdateText.value = "Save"
        todoTitle.value = ""
        todoDescription.value = ""
        cancleBtn.value = false
        createdialog.value = false
        btnSaveUpdate.value = false
        rowid.value = 0
    }

    fun insertOrUpdateTodo() {
        if (todoDescription.value.isNullOrEmpty() || todoTitle.value.isNullOrEmpty()) {
            statusMessage.value = Event("Fields  Are Mandatory")
        } else {
            if (btnSaveUpdate.value!!) {
                viewModelScope.launch(Dispatchers.IO){

                    var updatedrow = todoRepository.updateTOdo(TodoEntity(rowid.value!!,todoTitle.value!!,todoDescription.value!!))

                    withContext(Dispatchers.Main){
                        if(updatedrow >0 ) {
                            statusMessage.value = Event("Updated Successfully")
                            todoTitle.value = ""
                            todoDescription.value = ""
                            btnSaveUpdate.value = false
                            dismissDialog.value = true
                        }else{
                            statusMessage.value = Event("Something went wrong")
                        }
                    }

                }
            } else {
                viewModelScope.launch(Dispatchers.IO) {
                    var rowId = todoRepository.InsertTodo(TodoEntity(0,todoTitle.value!!,todoDescription.value!!))
                    withContext(Dispatchers.Main) {
                        if (rowId > 0) {
                            statusMessage.value = Event("Saved SuccessFully")
                            todoTitle.value = ""
                            todoDescription.value = ""
                            dismissDialog.value = true
                        } else {
                            statusMessage.value = Event("Something went wrong")
                        }
                    }

                }
            }
        }
    }

    fun DeleteATodo(TodoEntity: TodoEntity){
        viewModelScope.launch(Dispatchers.IO) {
            var deletedRow = todoRepository.deleteATodo(TodoEntity)
            withContext(Dispatchers.Main){
                if (deletedRow > 0){
                    statusMessage.value = Event("Deleted Successfully")
                } else {
                    statusMessage.value = Event("Something went Wrong")
                }
            }
        }
    }

    fun createDialog() {
        createdialog.value =true
        title.value = "Create"
        btnSaveUpdateText.value = "Save"
        todoTitle.value = ""
        todoDescription.value = ""

    }

    fun cancelDialog() {
        cancleBtn.value = true
    }
    fun updateTodo(TodoEntity: TodoEntity){
        todoTitle.value = TodoEntity.TodoTitle
        todoDescription.value = TodoEntity.TodoDiscription
        createdialog.value = true
        rowid.value = TodoEntity.TodoId
        title.value = "Update"
        btnSaveUpdateText.value = "Update"
        btnSaveUpdate.value = true
    }

    fun resetDismissDialog() {
        dismissDialog.value = false
    }

}