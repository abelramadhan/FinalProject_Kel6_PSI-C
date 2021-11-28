package com.example.psi_finalproject

import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ViewModel: ViewModel() {

    var user:FirebaseUser?
    var database:FirebaseDatabase
    final var productList: ArrayList<Product>

    init {
        var auth = Firebase.auth
        user = auth.currentUser
        database = Firebase.database
        productList = ArrayList<Product>()
    }

    fun getCurrentUserData(context: Context, text:TextView, image:ImageView) {
        database.getReference("users").child(user!!.uid).get()
            .addOnSuccessListener {
                text.text = "Hello, ${it.child("username").value.toString()}"
                Glide.with(context).load(it.child("ppicture_url").value).into(image)
        }
    }

    fun updateRCview(context: Context, rcView:RecyclerView) {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()
                for (item in snapshot.children) {
                    val product = item.getValue(Product::class.java)
                    productList.add(product!!)
                }
                var adapter = ProductAdapter(context, productList)
                rcView.adapter = adapter
                rcView.layoutManager = GridLayoutManager(context,2)

            }
            override fun onCancelled(error: DatabaseError){

            }
        }
        database.getReference("products").addValueEventListener(listener)
    }

}