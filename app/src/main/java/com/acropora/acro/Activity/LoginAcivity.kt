package com.acropora.acro.Activity

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.acropora.acro.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class LoginAcivity : AppCompatActivity() {
    private lateinit var tlLoginEmail: TextInputLayout
    private lateinit var etLoginEmail: TextInputEditText
    private lateinit var tlLoginPassword: TextInputLayout
    private lateinit var etLoginPassword: TextInputEditText
    private lateinit var tvLupaPassword: TextView
    private lateinit var tvSignUp: TextView
    private lateinit var btnSignIn: Button
    private lateinit var progressDialog: ProgressDialog

    private var auth = FirebaseAuth.getInstance()


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_acivity)

        tlLoginEmail = findViewById(R.id.tlLoginEmail)
        etLoginEmail = findViewById(R.id.etLoginEmail)
        tlLoginPassword = findViewById(R.id.tlLoginPassword)
        etLoginPassword = findViewById(R.id.etLoginPassword)
        tvLupaPassword = findViewById(R.id.tvLupaPassword)
        tvSignUp = findViewById(R.id.tvSignUp)
        btnSignIn = findViewById(R.id.btnSignIn)
        progressDialog = ProgressDialog(this)

        progressDialog.setTitle("Loading")
        progressDialog.setMessage("Tunggu sebentar...")

        etLoginEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                validateEmail(etLoginEmail, tlLoginEmail)
            }
        })

        etLoginPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                validatePassword(etLoginPassword, tlLoginPassword)
            }

        })

        btnSignIn.setOnClickListener {
            if (validateEmail(etLoginEmail, tlLoginEmail) && validatePassword(
                    etLoginPassword,
                    tlLoginPassword
                )
            ) {
                processLogin()
            }
        }

        tvSignUp.setOnClickListener {
            val intent = Intent(this@LoginAcivity, RegisterActivity::class.java)

            startActivity(intent)
        }

        tvLupaPassword.setOnClickListener {
            val intent = Intent(this@LoginAcivity, LupaPasswordActivity::class.java)

            startActivity(intent)
        }
    }

    private fun processLogin() {
        val email = etLoginEmail.text.toString()
        val password = etLoginPassword.text.toString()

        progressDialog.show()
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                    val user = auth.currentUser

                    if (user?.isEmailVerified == true)
                    {
                        val intent = Intent(this@LoginAcivity, MainActivity::class.java)

                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }
                    else
                    {
                        user?.sendEmailVerification()
                            ?.addOnSuccessListener {
                                Toast.makeText(this, "Verifikasi email terlebih dahulu", Toast.LENGTH_SHORT).show()
                            }
                            ?.addOnFailureListener {
                                Toast.makeText(this, "Email belum diverifikasi", Toast.LENGTH_SHORT).show()
                            }
                    }
            }
            .addOnCompleteListener {
                progressDialog.dismiss()
            }
    }

    private fun validatePassword(
        etLoginPassword: TextInputEditText,
        tlLoginPassword: TextInputLayout
    ): Boolean {
        val loginPassword = etLoginPassword.text.toString().trim()

        return when {
            loginPassword.isEmpty() -> {
                tlLoginPassword.error = "Password wajib diisi"
                false
            }

            loginPassword.length <= 8 -> {
                tlLoginPassword.error = "Password minimal 8 karakter"
                false
            }

            !loginPassword.matches(".*[A-Z].*".toRegex()) -> {
                tlLoginPassword.error = "Password minimal terdapat 1 huruf besar"
                false
            }

            !loginPassword.matches(".*[a-z].*".toRegex()) -> {
                tlLoginPassword.error = "Password minimal terdapat 1 huruf kecil"
                false
            }

            !loginPassword.matches(".*[@#\$^&+=].*".toRegex()) -> {
                tlLoginPassword.error = "Password minimal terdapat 1 karakter spesial (@#\$^&+=)"
                false
            }

            !loginPassword.matches(".*[0-9].*".toRegex()) -> {
                tlLoginPassword.error = "Password minimal terdapat 1 angka"
                false
            }

            else -> {
                tlLoginPassword.error = null
                true
            }
        }
    }

    private fun validateEmail(
        etLoginEmail: TextInputEditText,
        tlLoginEmail: TextInputLayout
    ): Boolean {
        val loginEmail = etLoginEmail.text.toString().trim()

        return when {
            loginEmail.isEmpty() -> {
                tlLoginEmail.error = "Email wajib diisi"
                false
            }

            !Patterns.EMAIL_ADDRESS.matcher(loginEmail).matches() -> {
                tlLoginEmail.error = "Format email tidak sesuai"
                false
            }

            else -> {
                tlLoginEmail.error = null
                true
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if (auth.currentUser != null && auth.currentUser?.isEmailVerified == true)
        {
            val intent = Intent(this@LoginAcivity, MainActivity::class.java)

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}