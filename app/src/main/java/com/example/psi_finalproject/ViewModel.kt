package com.example.psi_finalproject

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.ReplaySubject
import java.util.*
import kotlin.collections.ArrayList

class ViewModel: ViewModel() {

    var user:FirebaseUser?
    var database:FirebaseDatabase
    final var productList: ArrayList<Product>
    val cart_count = MutableLiveData<String>()
    private var cart_list = mutableListOf<Orders>()

    init {
        var auth = Firebase.auth
        user = auth.currentUser
        database = Firebase.database
        productList = ArrayList<Product>()
        cart_count.value = cart_list.count().toString()
    }

    var cart_obs = PublishSubject.create<Orders>()
    var cart_sub = cart_obs.subscribeBy (
                onNext = {
                    cart_list.add(it)
                    cart_count.value = cart_list.count().toString()
                    Log.d("viewmodelOBS", "added ${it.date}")
                }
            )

    fun addToCart(orders: Orders) {
        /*++count
        cart_count.value = count.toString()
        Log.d("viewmodelOBS", "val = ${cart_count.value}")*/
        cart_obs.onNext(orders)
    }

    fun setCartList(list:MutableList<Orders>) {
        cart_list.clear()
        cart_list = list
    }

    fun getCartList():MutableList<Orders> {
        return cart_list
    }

    fun storeOrderList(context: FragmentActivity?) {
        val ref = database.getReference("orders").child(user!!.uid)
        for (item in cart_list) {
            var uuid = UUID.randomUUID().toString()
            ref.child(uuid).setValue(item).addOnSuccessListener {
                Toast.makeText(context, "Order Placed", Toast.LENGTH_SHORT).show()
                clearList()
            }.addOnFailureListener {
                Toast.makeText(context, "Failed to Place Order", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun clearList() {
        cart_list.clear()
        cart_count.value = "0"
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

    /*fun addOrder(order:Orders) {
        database.getReference("title").equalTo(order.ti)
    }*/

}