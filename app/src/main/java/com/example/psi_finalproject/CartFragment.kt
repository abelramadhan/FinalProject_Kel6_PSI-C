package com.example.psi_finalproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartFragment : Fragment() {

    private lateinit var backBtn: ImageView
    private lateinit var orderBtn: Button
    private lateinit var RCview: RecyclerView
    private lateinit var viewmodel: ViewModel
    private lateinit var clear_btn: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        // Inflates the custom fragment layout
        val view: View = inflater.inflate(R.layout.fragment_cart, container, false)

        backBtn = view.findViewById(R.id.back)
        orderBtn = view.findViewById(R.id.order_btn)
        RCview = view.findViewById(R.id.RCview)
        clear_btn = view.findViewById(R.id.clear)

        (activity as HomeActivity?)!!.getViewModel().also { viewmodel = it }
        val cart_list = viewmodel.getCartList()
        RCview.adapter = CartAdapter(this, cart_list)
        RCview.layoutManager = LinearLayoutManager(activity)

        backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        orderBtn.setOnClickListener {
            viewmodel.storeOrderList(activity)
            requireActivity().onBackPressed()
        }

        clear_btn.setOnClickListener {
            val cart_list = viewmodel.getCartList()
            RCview.adapter = CartAdapter(this, cart_list)
            RCview.layoutManager = LinearLayoutManager(activity)
            viewmodel.clearList()
            (activity as HomeActivity?)!!.setPreference()
        }

        return view
    }

}