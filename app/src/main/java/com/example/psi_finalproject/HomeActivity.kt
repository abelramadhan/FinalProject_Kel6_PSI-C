package com.example.psi_finalproject

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.disposables.Disposable
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var layout: LinearLayout
    private lateinit var hello_text: TextView
    private lateinit var avatar: ImageView
    private lateinit var rcview: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var cart: LinearLayout
    private lateinit var cart_count: TextView
    private lateinit var viewmodel: ViewModel
    private lateinit var sharedpref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        hello_text = findViewById(R.id.hello)
        avatar = findViewById(R.id.avatar_image)
        rcview = findViewById(R.id.RCview)
        progressBar = findViewById(R.id.progressBar)
        layout = findViewById(R.id.layout)
        cart = findViewById(R.id.cart)
        cart_count = findViewById((R.id.cart_count))

        layout.isVisible = false

        auth = Firebase.auth
        var user = auth.currentUser

        viewmodel = ViewModelProvider(this).get(ViewModel::class.java)

        sharedpref = getSharedPreferences("cartList", Context.MODE_PRIVATE)
        viewmodel.setCartList(getPreference())

        if(user == null) {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        viewmodel.getCurrentUserData(this, hello_text, avatar)

        rcview.isNestedScrollingEnabled = false
        viewmodel.updateRCview(this, rcview)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            progressBar.isGone = true
            layout.isVisible = true
        },2000)

        cart.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragLayout, CartFragment())
                addToBackStack(null)
                commit()
            }
        }

        viewmodel.cart_count.observe(this, Observer {
            val count = viewmodel.getCartList().count()
            if (count == 0) {
                cart_count.isVisible = false
            } else {
                cart_count.isVisible = true
                cart_count.text = count.toString()
            }
        })

    }

    fun replaceFrag(fragment: Fragment, product:Product) {
        val Bundle = Bundle()
        Bundle.putString("id",product.id)
        Bundle.putString("title",product.title)
        Bundle.putString("designer",product.designer)
        Bundle.putString("price",product.price)
        Bundle.putString("pic_url",product.pic_url)
        fragment.arguments = Bundle
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragLayout, fragment)
            addToBackStack(null)
            commit()
        }
    }

    fun setPreference() {
        val editor = sharedpref.edit()
        val gson = Gson()
        val json: String = gson.toJson(viewmodel.getCartList())
        editor.putString("cart", json)
        editor.commit()
    }

    fun getPreference():MutableList<Orders> {
        val gson = Gson()
        val string = sharedpref.getString("cart", null)
        var cartList:MutableList<Orders> = viewmodel.getCartList()
        val type = object : TypeToken<MutableList<Orders>>() {}!!.type
        if (string != null) {
            cartList = gson.fromJson(string, type)
        }
        return cartList
    }

    fun getViewModel():ViewModel {
        return viewmodel
    }

    fun addCart(orders: Orders) {
        viewmodel.addToCart(orders)
    }

    override fun onStop() {
        super.onStop()
        setPreference()
        viewmodel.cart_sub.dispose()
    }
}