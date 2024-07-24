package com.example.esgithub.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.esgithub.R
import com.example.esgithub.ui.viewModels.SignupViewModel
import com.google.android.material.textfield.TextInputEditText
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.HttpException

class SignupActivity : AppCompatActivity() {
    private val signupViewModel: SignupViewModel by viewModel()

    private lateinit var firstNameInput: TextInputEditText
    private lateinit var lastNameInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var usernameInput: TextInputEditText
    private lateinit var signupButton: Button
    private lateinit var loginTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        firstNameInput = findViewById(R.id.firstNameEditText)
        lastNameInput = findViewById(R.id.lastNameEditText)
        passwordInput = findViewById(R.id.passwordEditText)
        usernameInput = findViewById(R.id.usernameEditText)
        emailInput = findViewById(R.id.emailEditText)
        signupButton = findViewById(R.id.signupButton)
        loginTv = findViewById(R.id.loginTv)

        observeSignupSuccess()
        observeSignupFailure()

        loginTv.setOnClickListener {
            redirectToLoginActivity()
        }

        signupButton.setOnClickListener {
            register()
        }
    }

    private fun observeSignupSuccess() {
        signupViewModel.signupResult.observe(this@SignupActivity) {
           /* Utils.displayToast(
                applicationContext,
                R.layout.success_toast,
                "Un email de confirmation à été envoyé",
                Toast.LENGTH_SHORT
            )*/
            Log.d("signup", it.toString())
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }

    private fun observeSignupFailure() {
        signupViewModel.signupErr.observe(this@SignupActivity) {
            if (it is HttpException) {
                val errCode = it.code()
                if (errCode == 409) {
                   /* Utils.displayToast(
                        applicationContext,
                        R.layout.error_toast,
                        "Email ou nom d'utilisateur exist déja",
                        Toast.LENGTH_SHORT
                    )*/
                    Log.d("error", "Email ou nom d'utilisateur exist déja")
                }
            } else {
               /* Utils.displayToast(
                    applicationContext,
                    R.layout.error_toast,
                    "Une erreur se produit essayez plus tard",
                    Toast.LENGTH_SHORT
                )*/
                Log.d("error", "Une erreur se produit essayez plus tard")
            }
        }
    }

    private fun register() {
        val firstName = firstNameInput.text.toString().trim()
        val lastName = lastNameInput.text.toString().trim()
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()
        val username = usernameInput.text.toString().trim()

        if (email.isEmpty() ||
            password.isEmpty() ||
            username.isEmpty() ||
            firstName.isEmpty() ||
            lastName.isEmpty()
        ) {
            /*Utils.displayToast(
                applicationContext,
                R.layout.error_toast,
                "Merci de remplire tous les champs!",
                Toast.LENGTH_SHORT
            )*/
            Log.d("error", "Merci de remplire tous les champs!")
            return
        }

        if (password.length < 8) {
            /*Utils.displayToast(
                applicationContext,
                R.layout.error_toast,
                "Le mot de passe doit contenir au moins 6 caractères.",
                Toast.LENGTH_SHORT
            )*/
            Log.d("error", "Le mot de passe doit contenir au moins 8 caractères.")
            return
        }

        signupViewModel.signup(firstName, lastName, email, password, username)
    }

    private fun redirectToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
