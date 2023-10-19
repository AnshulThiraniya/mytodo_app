package com.example.mytodoapp.activity

import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mytodoapp.Interface.DeleteListener
import com.example.mytodoapp.Interface.EditListener
import com.example.mytodoapp.R
import com.example.mytodoapp.adapter.TodoAdapter
import com.example.mytodoapp.databinding.ActivityMainBinding
import com.example.mytodoapp.databinding.AddUpdateDialogBinding
import com.example.mytodoapp.factory.TodoFactory
import com.example.mytodoapp.repository.TodoRepository
import com.example.mytodoapp.roomdb.TodoDatabase
import com.example.mytodoapp.roomdb.TodoEntity
import com.example.mytodoapp.viewmodel.TodoViewmodel



class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var todoViewModel : TodoViewmodel
    lateinit var todoRecyclerViewAdapter: TodoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        var todoDao = TodoDatabase.getInstance(this).todoDao
        var repository= TodoRepository(todoDao)

        todoViewModel = ViewModelProvider(this,
            TodoFactory(this,repository))[TodoViewmodel::class.java]

        binding.lifecycleOwner = this
        binding.myViewModel = todoViewModel

        var bindingdialog : AddUpdateDialogBinding = AddUpdateDialogBinding.inflate(layoutInflater)
        var createDialog = Dialog(this)
        createDialog.setContentView(bindingdialog.root)
        var layoutManager = WindowManager.LayoutParams()
        layoutManager.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutManager.height = WindowManager.LayoutParams.WRAP_CONTENT
        createDialog.window!!.attributes = layoutManager
        bindingdialog.myViewModel = todoViewModel

        bindingdialog.lifecycleOwner = this
        createDialog.setCancelable(false)

        todoViewModel.dismissDialog.observe(this, Observer {
            if (it == true) {
                createDialog.dismiss()
                todoViewModel.resetDismissDialog()
            }
        })

        todoViewModel.createdialog.observe(this, Observer {
            if (it== true){
                createDialog.show()
            }

        })

        todoViewModel.cancleBtn.observe(this, Observer {
            if (it == true){
                createDialog.dismiss()
            }
        })

        todoViewModel.statusMessage.observe(this, Observer {
            it.getContentIfNotHandled()!!.apply {
                Toast.makeText(this@MainActivity, "$this", Toast.LENGTH_SHORT).show()
            }
        })

        binding.rvTodoMain.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        todoViewModel.todoLiveData.observe(this, Observer {
            todoRecyclerViewAdapter = TodoAdapter(it,object : DeleteListener{
                override fun deleteTodo(position: Int, List: TodoEntity) {
                    alertMessage(List)
                }

            }, object : EditListener{
                override fun editTodo(position: Int, List: TodoEntity) {
                    todoViewModel.updateTodo(List)
                }
            })
            binding.rvTodoMain.adapter = todoRecyclerViewAdapter
            todoRecyclerViewAdapter.notifyDataSetChanged()
        })

    }

    fun alertMessage(todoDbTable: TodoEntity){
        var appname = applicationInfo.loadLabel(packageManager).toString()
        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("$appname")
        alertDialog.setMessage("Do you want to delete?")
        alertDialog.setPositiveButton("Yes",object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                todoViewModel.DeleteATodo(todoDbTable)
                dialog!!.dismiss()
            }
        })
        alertDialog.setNegativeButton("No", object :DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog!!.dismiss()
            }

        })

        var dialogalert = alertDialog.create()
        dialogalert.show()
    }


}