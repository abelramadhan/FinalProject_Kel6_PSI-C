package com.example.psi_finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.HandlerCompat.postDelayed
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var auth = Firebase.auth
        var database = Firebase.database
        supportActionBar?.hide()



        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            if (auth.currentUser != null) {
                database.getReference("users").child(auth.currentUser!!.uid).child("username").get().addOnSuccessListener {
                    val name = it.value
                    Toast.makeText(baseContext, "Hi, $name",
                        Toast.LENGTH_SHORT).show()
                }
                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            },3000)
    }
}