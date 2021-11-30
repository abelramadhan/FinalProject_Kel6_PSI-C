package com.example.psi_finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {

    private lateinit var username: TextView
    private lateinit var email: TextView
    private lateinit var avatar: ImageView
    private lateinit var RCview: RecyclerView
    private lateinit var back: ImageView
    private lateinit var edit_profile: TextView
    private lateinit var logout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        username = findViewById(R.id.username)
        email = findViewById(R.id.email)
        avatar = findViewById(R.id.avatar_image)
        RCview = findViewById(R.id.RCview)
        back = findViewById(R.id.back)
        edit_profile = findViewById(R.id.edit_profile)
        logout = findViewById(R.id.logout)

        val user = Firebase.auth.currentUser
        val orderList = mutableListOf<Orders>()
        val context = this

        back.setOnClickListener {
            this.onBackPressed()
        }

        logout.setOnClickListener {
            Firebase.auth.signOut()
            Log.d("userAuth", "${Firebase.auth.currentUser}")
            intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        val userRef = FirebaseDatabase.getInstance().getReference("users")
        userRef.child(user!!.uid).get().addOnSuccessListener {
            username.text = it.child("username").value.toString()
            email.text = it.child("email").value.toString()
            val url = it.child("ppicture_url").value.toString()
            Glide.with(context).load(url).into(avatar)
        }

        val orderRef = FirebaseDatabase.getInstance().getReference("orders").child(user!!.uid)
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children) {
                    val order = item.getValue(Orders::class.java)
                    orderList.add(order!!)
                }
                RCview.adapter = OrderAdapter(context, orderList)
                RCview.layoutManager = LinearLayoutManager(context)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        orderRef.addValueEventListener(listener)
    }
}