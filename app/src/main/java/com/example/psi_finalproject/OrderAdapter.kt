package com.example.psi_finalproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class OrderAdapter(fragment: CartFragment, var order: List<Orders>) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    inner class OrderViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)
        val mContext = fragment
        val imageRef = FirebaseDatabase.getInstance().getReference("products")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_items, parent, false)
        return OrderViewHolder(view)
    }
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.itemView.apply {
            imageRef.child(order[position].productID).child("pic_url").get().addOnSuccessListener {
                Glide.with(mContext).load(it.value.toString()).into(findViewById(R.id.image))
                findViewById<TextView>(R.id.title).text = order[position].productTitle
                findViewById<TextView>(R.id.size).text = "Size : ${order[position].size}"
                findViewById<TextView>(R.id.date).text = "added on ${order[position].date}"
            }.addOnFailureListener {
                Toast.makeText(context, "failed to retrieve data", Toast.LENGTH_SHORT).show()
            }

        }

    }
    override fun getItemCount(): Int {
        return order.size
    }
}