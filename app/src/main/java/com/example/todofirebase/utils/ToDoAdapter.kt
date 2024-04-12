package com.example.todofirebase.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todofirebase.databinding.EachTodoItemBinding

class ToDoAdapter(private val list: MutableList<ToDoData>):
    RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {

    private var listener:ToDoAdapterClickListener?=null

    fun setListener(listener:ToDoAdapterClickListener){
        this.listener=listener
    }

    inner class ToDoViewHolder(val binding:EachTodoItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val binding=EachTodoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ToDoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        with(holder){
            with(list[position]){
                binding.todoTask.text=this.task

                binding.deleteTask.setOnClickListener {
                    listener?.onDeleteTaskBtnClicked(this)
                }
                binding.editTask.setOnClickListener {
                    listener?.onEditTaskBtnClicked(this)
                }
            }
        }
    }

    interface ToDoAdapterClickListener{
        fun onDeleteTaskBtnClicked(toDoData:ToDoData)
        fun onEditTaskBtnClicked(toDoData:ToDoData)
    }
}