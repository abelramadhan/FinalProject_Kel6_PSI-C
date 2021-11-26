package com.example.psi_finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var signinBtn:TextView
    private lateinit var username:EditText
    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var registerBtn:Button
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth
        username = findViewById(R.id.username)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        registerBtn = findViewById(R.id.register_btn)
        signinBtn = findViewById(R.id.signin_btn)

        var database = Firebase.database
        val default_avatar = "https://firebasestorage.googleapis.com/v0/b/psi-finalproject.appspot.com/o/profile_images%2Fdefault%20avatar.jpg?alt=media&token=c325e692-637f-4ce9-a8e3-be19d3a194f0"

        signinBtn.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        registerBtn.setOnClickListener {
            val username_val = username.getText().toString()
            val email_val = email.getText().toString()
            val password_val = password.getText().toString()

            auth.createUserWithEmailAndPassword(email_val, password_val)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        var user = User(username_val, email_val, default_avatar)
                        database.getReference("users")
                            .child(FirebaseAuth.getInstance().currentUser!!.uid)
                            .setValue(user).addOnCompleteListener(this) { task ->
                                if(task.isSuccessful) {
                                    Toast.makeText(baseContext, "Account Registered Successfully.",
                                        Toast.LENGTH_SHORT).show()
                                    intent = Intent(this, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(baseContext, "Register Failed.",
                                        Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }


    }

}