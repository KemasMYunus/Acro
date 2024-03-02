package com.acropora.acro.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.acropora.acro.R
import com.google.firebase.auth.FirebaseAuth

class SettingActivity : AppCompatActivity() {
    private lateinit var ivLogout: LinearLayout
    private lateinit var tvName: TextView

    private var auth = FirebaseAuth.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val user = auth.currentUser

        ivLogout = findViewById(R.id.llLogout)
        tvName = findViewById(R.id.tvName)

        if (user != null) {
            tvName.text = user.displayName
        } else {
            val intent = Intent(this@SettingActivity, LoginAcivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        ivLogout.setOnClickListener {
            val intent = Intent(this@SettingActivity, LoginAcivity::class.java)

            auth.signOut()
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}