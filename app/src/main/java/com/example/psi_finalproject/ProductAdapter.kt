package com.example.psi_finalproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductAdapter(context: Context, var product: List<Product>) :
        RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
        inner class ProductViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.product_items, parent, false)
            return ProductViewHolder(view)
        }
        override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
            holder.itemView.apply {
                Glide.with(context).load(product[position].pic_url).into(findViewById(R.id.image))
                findViewById<TextView>(R.id.title).text = product[position].title
                findViewById<TextView>(R.id.designer).text = "by ${product[position].designer}"
                findViewById<TextView>(R.id.price).text = product[position].price
            }
            holder.itemView.setOnClickListener {
                Toast.makeText(holder.itemView.context,"clicked", Toast.LENGTH_SHORT).show()
            }
        }
        override fun getItemCount(): Int {
            return product.size
        }
}