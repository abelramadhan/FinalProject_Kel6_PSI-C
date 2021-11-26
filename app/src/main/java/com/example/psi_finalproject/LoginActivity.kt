package com.example.psi_finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var email_ET:EditText
    private lateinit var password_ET:EditText
    private lateinit var login_Btn: Button
    private lateinit var register_Btn:TextView
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email_ET = findViewById(R.id.email)
        password_ET = findViewById(R.id.password)
        login_Btn = findViewById(R.id.signin_btn)
        register_Btn = findViewById(R.id.register_btn)
        auth = Firebase.auth

        register_Btn.setOnClickListener {
            intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        login_Btn.setOnClickListener {
            val email = email_ET.getText().toString()
            val password = password_ET.getText().toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(baseContext, "Hi, welcome back",
                            Toast.LENGTH_SHORT).show()
                        intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}