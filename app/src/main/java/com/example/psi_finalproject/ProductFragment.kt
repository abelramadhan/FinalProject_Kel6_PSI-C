package com.example.psi_finalproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class ProductFragment : Fragment() {

    private lateinit var image: ImageView
    private lateinit var title: TextView
    private lateinit var designer: TextView
    private lateinit var price: TextView
    private lateinit var size_RDgroup: RadioGroup
    private lateinit var order_btn: Button
    private lateinit var size: String
    private lateinit var cancel_btn: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        // Inflates the custom fragment layout
        val view: View = inflater.inflate(R.layout.fragment_product, container, false)

        // Finds the TextView in the custom fragment
        image = view.findViewById<View>(R.id.image) as ImageView
        title = view.findViewById<View>(R.id.title) as TextView
        designer = view.findViewById<View>(R.id.designer) as TextView
        price = view.findViewById<View>(R.id.price) as TextView
        size_RDgroup = view.findViewById<View>(R.id.size_group) as RadioGroup
        order_btn = view.findViewById<View>(R.id.order_btn) as Button
        cancel_btn = view.findViewById<View>(R.id.cancel) as TextView

        val bundle = arguments
        val id:String = bundle!!.getString("id").toString()
        val title_val = bundle!!.getString("title").toString()
        title.text = title_val
        designer.text = bundle!!.getString("designer")
        price.text = bundle!!.getString("price")
        Glide.with(this).load(bundle!!.getString("pic_url")).into(image)

        order_btn.setOnClickListener {
            val selectedId = size_RDgroup!!.checkedRadioButtonId
            if (selectedId!=-1) {
                val radioButton:RadioButton = view.findViewById<View>(selectedId) as RadioButton
                size = radioButton.text.toString()
                val currentTime: String = SimpleDateFormat("dd-MM-yy", Locale.getDefault()).format(Date())
                val order = Orders(id, title_val, size, currentTime)
                (activity as HomeActivity?)!!.addCart(order)
                Toast.makeText(activity, "added to cart", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressed()
            } else {
                Toast.makeText(activity, "no size selected", Toast.LENGTH_SHORT).show()
            }
        }

        cancel_btn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return view
    }

}