package com.example.todofirebase.fregments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.todofirebase.R
import com.example.todofirebase.databinding.FragmentAddTodoPopupBinding
import com.example.todofirebase.utils.ToDoData
import com.google.android.material.animation.AnimatableView.Listener
import com.google.android.material.textfield.TextInputEditText

class AddTodoPopupFragment : DialogFragment() {
    //DialogFragment is used to display a floating window on top of activity
    private lateinit var binding: FragmentAddTodoPopupBinding
    private var listener: DialogNextBtnClickListener?=null
    private var todoData: ToDoData? = null
    //listener is used when  child object wants to emit events upwards to parent object
    // and allow that object to respond.

    fun setListener(listener: DialogNextBtnClickListener) {
        this.listener = listener
    }

    companion object {
        const val TAG = "AddTodoPopupFragment"

        @JvmStatic
        fun newInstance(taskId: String, task: String) = AddTodoPopupFragment().apply {
            arguments = Bundle().apply {
                putString("taskId", taskId)
                putString("task", task)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTodoPopupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            todoData = ToDoData(
                arguments?.getString("taskId").toString(),
                arguments?.getString("task").toString()
            )
            binding.todoEt.setText(todoData?.task)
        }
        registerEvents()
    }

    private fun registerEvents() {

        binding.todoNextBtn.setOnClickListener {
            val todoTask = binding.todoEt.text.toString()
            if (todoTask.isNotEmpty()) {
                if(todoData==null){
                    listener?.onSaveTask(todoTask, binding.todoEt)
                }
                else{
                    todoData!!.task=todoTask
                    listener?.onUpdateTask(todoData!!,binding.todoEt)
                }
            } else {
                Toast.makeText(context, "Please write something to Add", Toast.LENGTH_SHORT).show()
            }
        }
        binding.todoClose.setOnClickListener {
            dismiss()
        }
    }

    interface DialogNextBtnClickListener {
        fun onSaveTask(todo: String, todoET: TextInputEditText)
        fun onUpdateTask(toDoData: ToDoData, todoET: TextInputEditText)
    }
}