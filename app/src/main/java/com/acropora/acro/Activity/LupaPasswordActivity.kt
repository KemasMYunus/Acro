package com.acropora.acro.Activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import com.acropora.acro.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class LupaPasswordActivity : AppCompatActivity() {
    private lateinit var tlResetPasswordEmail: TextInputLayout
    private lateinit var etResetPasswordEmail: TextInputEditText
    private lateinit var btnResetPassword: Button

    private var auth = FirebaseAuth.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lupa_password)

        tlResetPasswordEmail = findViewById(R.id.tlResetPasswordEmail)
        etResetPasswordEmail = findViewById(R.id.etResetPasswordEmail)
        btnResetPassword = findViewById(R.id.btnResetPassword)

        etResetPasswordEmail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                validateEmail(etResetPasswordEmail, tlResetPasswordEmail)
            }

        })

        btnResetPassword.setOnClickListener {
            if (validateEmail(etResetPasswordEmail, tlResetPasswordEmail))
            {
                processResetPassword()
            }
        }
    }

    private fun processResetPassword() {
        val email = etResetPasswordEmail.text.toString().trim()

        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                Toast.makeText(this, "Cek email anda", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }

    private fun validateEmail(etResetPasswordEmail: TextInputEditText, tlResetPasswordEmail: TextInputLayout): Boolean {
        val email = etResetPasswordEmail.text.toString().trim()

        return when {
            email.isEmpty() -> {
                tlResetPasswordEmail.error = "Email wajib diisi"
                false
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                tlResetPasswordEmail.error = "Format email tidak sesuai"
                false
            }

            else -> {
                tlResetPasswordEmail.error = null
                true
            }
        }
    }
}