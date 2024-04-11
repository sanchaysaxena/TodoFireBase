package com.example.todofirebase.fregments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.todofirebase.R
import com.example.todofirebase.databinding.FragmentHomeBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class HomeFragment : Fragment(), AddTodoPopupFragment.DialogNextBtnClickListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var navController:NavController
    private lateinit var databaseRef:DatabaseReference
    private lateinit var binding: FragmentHomeBinding
    private lateinit var popUpFragment:AddTodoPopupFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        registerEvents()
    }

    private fun registerEvents() {
        binding.addButtonHome.setOnClickListener {
            popUpFragment= AddTodoPopupFragment()
            popUpFragment.setListener(this)
            popUpFragment.show(childFragmentManager,"AddTodoFragment")
        }
    }

    private fun init(view: View) {
        auth=FirebaseAuth.getInstance()
        navController=Navigation.findNavController(view)
        databaseRef=FirebaseDatabase.getInstance().reference
            .child("Tasks").child(auth.currentUser?.uid.toString())
    }

    override fun onSaveTask(todo: String, todoET: TextInputEditText) {
        databaseRef.push().setValue(todo).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(context,"Task added Successfully",Toast.LENGTH_SHORT).show()
                todoET.text=null
            }
            else{
                Toast.makeText(context,it.exception?.message,Toast.LENGTH_SHORT).show()
            }
            popUpFragment.dismiss()
        }
    }
}