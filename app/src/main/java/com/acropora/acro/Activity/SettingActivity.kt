package com.acropora.acro.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.acropora.acro.R
import com.acropora.acro.databinding.ActivityMainBinding
import com.acropora.acro.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            tombolLogout.setOnClickListener{
                startActivity(Intent(this@SettingActivity,LoginAcivity::class.java))
                overridePendingTransition(0, 0) // Menghilangkan animasi
                finish()
            }
        }
    }
}