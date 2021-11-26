package com.example.psi_finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var username_text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = Firebase.auth
        var database = Firebase.database
        var user = auth.currentUser

        if(user == null) {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        username_text = findViewById(R.id.username)

        database.getReference("users").child(user!!.uid).child("username").get()
            .addOnSuccessListener {
                username_text.text = it.value.toString()
        }






    }
}