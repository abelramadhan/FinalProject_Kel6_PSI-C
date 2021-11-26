package com.example.psi_finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var layout: LinearLayout
    private lateinit var hello_text: TextView
    private lateinit var avatar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        layout = findViewById(R.id.layout)
        layout.isVisible = false

        auth = Firebase.auth
        var database = Firebase.database
        var user = auth.currentUser
        hello_text = findViewById(R.id.hello)
        avatar = findViewById(R.id.avatar_image)

        if(user == null) {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        database.getReference("users").child(user!!.uid).child("username").get()
            .addOnSuccessListener {
            hello_text.text = "Hello, ${it.value}!"
                layout.isVisible = true
        }

        database.getReference("users").child(user!!.uid).child("ppicture_url").get()
            .addOnSuccessListener {
                Glide.with(this).load(it.value).into(avatar)
            }








    }
}