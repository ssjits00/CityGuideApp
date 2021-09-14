package com.example.guideme

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.FirebaseError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileFragment : Fragment() {
    private val fAuth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var rootNode: FirebaseDatabase
    lateinit var reference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)


    rootNode = FirebaseDatabase.getInstance()
    reference = rootNode.getReference("users")



    val rootRef = reference.child("users")
    rootRef.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onCancelled(error: DatabaseError) {
            println(error!!.message)
        }

        override fun onDataChange(snapshot: DataSnapshot) {
            reference.child("email").get().addOnSuccessListener {
                Log.i("firebase", "Got value ${it.value}")
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
            val children = snapshot!!.children
            children.forEach {
                println(it.toString())
            }
        }
    })
        return view
    }
}
//child("mshiva00000@gmail.com").child("name")