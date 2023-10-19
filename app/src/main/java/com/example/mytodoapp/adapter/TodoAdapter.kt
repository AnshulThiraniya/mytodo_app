package com.example.mytodoapp.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodoapp.Interface.DeleteListener
import com.example.mytodoapp.Interface.EditListener
import com.example.mytodoapp.databinding.TodoItemFormateBinding
import com.example.mytodoapp.roomdb.TodoEntity

class TodoAdapter(var list: List<TodoEntity>, var deleteClickListner:DeleteListener,var setOnItemEditClickListner: EditListener) : RecyclerView.Adapter<TodoAdapter.MyViewHolder>() {

    inner class MyViewHolder(var binding: TodoItemFormateBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding = TodoItemFormateBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var todoItem =list[position]
        holder.binding.title.text ="Title:- ${todoItem.TodoTitle}"
        holder.binding.discription.text ="Description:- ${todoItem.TodoDiscription}"
        holder.binding.deleteIcon.setOnClickListener {
            deleteClickListner.deleteTodo(position,todoItem)
        }
        holder.binding.editIcon.setOnClickListener {
            setOnItemEditClickListner.editTodo(position,todoItem)
        }
    }
}