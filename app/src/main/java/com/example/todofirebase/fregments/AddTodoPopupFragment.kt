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
import com.google.android.material.animation.AnimatableView.Listener
import com.google.android.material.textfield.TextInputEditText

class AddTodoPopupFragment : DialogFragment() {
    //DialogFragment is used to display a floating window on top of activity
    private lateinit var binding: FragmentAddTodoPopupBinding
    private lateinit var listener: DialogNextBtnClickListener
    //listener is used when  child object wants to emit events upwards to parent object
    // and allow that object to respond.

    fun setListener(listener: DialogNextBtnClickListener){
        this.listener=listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentAddTodoPopupBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerEvents()
    }

    private fun registerEvents() {

        binding.todoNextBtn.setOnClickListener {
            val todoTask=binding.todoEt.text.toString()
            if(todoTask.isNotEmpty()){
                listener.onSaveTask(todoTask,binding.todoEt)
            }
            else{
                Toast.makeText(context,"Please write something to Add",Toast.LENGTH_SHORT).show()
            }
        }
        binding.todoClose.setOnClickListener {
            dismiss()
        }
    }

    interface DialogNextBtnClickListener{
        fun onSaveTask(todo:String , todoET:TextInputEditText)
    }
}