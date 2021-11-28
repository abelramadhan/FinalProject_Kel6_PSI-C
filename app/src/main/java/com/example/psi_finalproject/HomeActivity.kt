package com.example.psi_finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.*
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
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
    private lateinit var rcview: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var userData: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        hello_text = findViewById(R.id.hello)
        avatar = findViewById(R.id.avatar_image)
        rcview = findViewById(R.id.RCview)
        progressBar = findViewById(R.id.progressBar)
        layout = findViewById(R.id.layout)
        layout.isVisible = false

        auth = Firebase.auth
        var user = auth.currentUser

        var viewmodel = ViewModelProvider(this).get(ViewModel::class.java)

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





    }
}