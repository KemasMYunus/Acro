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
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.acropora.acro.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var tlName: TextInputLayout
    private lateinit var etName: TextInputEditText
    private lateinit var tlEmail: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var tlPassword: TextInputLayout
    private lateinit var etPassword: TextInputEditText
    private lateinit var tlUlangPassword: TextInputLayout
    private lateinit var etUlangPassword: TextInputEditText
    private lateinit var btnSignUp: Button
    private lateinit var tvSignIn: TextView
    private lateinit var progressDialog: ProgressDialog

    private var auth = FirebaseAuth.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        tlName = findViewById(R.id.tlName)
        etName = findViewById(R.id.etName)
        tlEmail = findViewById(R.id.tlEmail)
        etEmail = findViewById(R.id.etEmail)
        tlPassword = findViewById(R.id.tlPassword)
        etPassword = findViewById(R.id.etPassword)
        tlUlangPassword = findViewById(R.id.tlUlangPassword)
        etUlangPassword = findViewById(R.id.etUlangPassword)
        btnSignUp = findViewById(R.id.btnSignUp)
        tvSignIn = findViewById(R.id.tvSignIn)
        progressDialog = ProgressDialog(this)

        progressDialog.setTitle("Loading")
        progressDialog.setMessage("Tunggu sebentar...")

        etName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                validateName(etName, tlName)
            }

        })

        etEmail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                validateEmail(etEmail, tlEmail)
            }

        })

        etPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                validatePassword(etPassword, tlPassword)
            }

        })

        etUlangPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                validateUlangPassword(etUlangPassword, tlUlangPassword)
            }

        })

        btnSignUp.setOnClickListener {
            val password = etPassword.text.toString().trim()
            val ulangPassword = etUlangPassword.text.toString().trim()

            if (validateName(etName, tlName) && validateEmail(etEmail, tlEmail) && validatePassword(etPassword, tlPassword) && validateUlangPassword(etUlangPassword, tlUlangPassword))
            {
                if (password == ulangPassword)
                {
                    processRegister()
                }
                else
                {
                    Toast.makeText(this, "Kata sandi tidak cocok", Toast.LENGTH_SHORT).show()
                }

            }

        }

        tvSignIn.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginAcivity::class.java))
        }
    }

    private fun processRegister()
    {
        val name = etName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        progressDialog.show()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                {
                    val userUpdateProfile = userProfileChangeRequest {
                        displayName = name
                    }

                    auth.currentUser?.sendEmailVerification()
                        ?.addOnSuccessListener {
                            Toast.makeText(this, "Silahkan vreifikasi email terlebih dahulu", Toast.LENGTH_SHORT).show()
                        }

                    val user = task.result.user
                    user?.updateProfile(userUpdateProfile)
                        ?.addOnSuccessListener {
                            val intent = Intent(this@RegisterActivity, LoginAcivity::class.java)

                            progressDialog.dismiss()
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                        ?.addOnFailureListener {error2 ->
                            Toast.makeText(this, error2.localizedMessage, Toast.LENGTH_SHORT).show()
                            Log.d("Register2", error2.message.toString())
                        }
                }
                else
                {
                    progressDialog.dismiss()
                }
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
                Log.d("Register", error.message.toString())
            }
    }

    private fun validateUlangPassword(etUlangPassword: TextInputEditText, tlUlangPassword: TextInputLayout): Boolean {
        val ulangPassword = etUlangPassword.text.toString().trim()

        return when {
            ulangPassword.isEmpty() -> {
                tlUlangPassword.error = "Password wajib diisi"
                false
            }

            ulangPassword.length <= 8 -> {
                tlUlangPassword.error = "Password minimal 8 karakter"
                false
            }

            !ulangPassword.matches(".*[A-Z].*".toRegex()) -> {
                tlUlangPassword.error = "Password minimal terdapat 1 huruf besar"
                false
            }

            !ulangPassword.matches(".*[a-z].*".toRegex()) -> {
                tlUlangPassword.error = "Password minimal terdapat 1 huruf kecil"
                false
            }

            !ulangPassword.matches(".*[@#\$^&+=].*".toRegex()) -> {
                tlUlangPassword.error = "Password minimal terdapat 1 karakter spesial (@#\$^&+=)"
                false
            }

            !ulangPassword.matches(".*[0-9].*".toRegex()) -> {
                tlUlangPassword.error = "Password minimal terdapat 1 angka"
                false
            }

            else -> {
                tlUlangPassword.error = null
                true
            }
        }
    }

    private fun validatePassword(etPassword: TextInputEditText, tlPassword: TextInputLayout): Boolean {
        val password = etPassword.text.toString().trim()

        return when {
            password.isEmpty() -> {
                tlPassword.error = "Password wajib diisi"
                false
            }
            password.length <= 8 -> {
                tlPassword.error = "Password minimal 8 karakter"
                false
            }
            !password.matches(".*[A-Z].*".toRegex()) -> {
                tlPassword.error = "Password minimal terdapat 1 huruf besar"
                false
            }
            !password.matches(".*[a-z].*".toRegex()) -> {
                tlPassword.error = "Password minimal terdapat 1 huruf kecil"
                false
            }
            !password.matches(".*[@#\$^&+=].*".toRegex()) -> {
                tlPassword.error = "Password minimal terdapat 1 karakter spesial (@#\$^&+=)"
                false
            }
            !password.matches(".*[0-9].*".toRegex()) -> {
                tlPassword.error = "Password minimal terdapat 1 angka"
                false
            }
            else -> {
                tlPassword.error = null
                true
            }
        }
    }

    private fun validateEmail(etEmail: TextInputEditText, tlEmail: TextInputLayout): Boolean {
        val email = etEmail.text.toString().trim()

        return when {
            email.isEmpty() -> {
                tlEmail.error = "Email wajib diisi"
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                tlEmail.error = "Format email tidak sesuai"
                false
            }
            else -> {
                tlEmail.error = null
                true
            }
        }
    }

    private fun validateName(etName: TextInputEditText, tlName: TextInputLayout): Boolean {
        val name = etName.text.toString().trim()

        return when{
            name.isEmpty() ->{
                tlName.error = "Nama wajib diisi"
                false
            }
            else ->{
                tlName.error = null
                true
            }
        }
    }
}