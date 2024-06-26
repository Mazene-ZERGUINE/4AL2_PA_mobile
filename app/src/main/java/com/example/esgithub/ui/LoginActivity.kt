package com.example.esgithub.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.esgithub.R
import com.example.esgithub.di.injectModuleDependencies
import com.example.esgithub.di.parseAndInjectConfiguration
import com.example.esgithub.ui.viewModels.LoginViewModel
import com.example.esgithub.utils.TokenManager
import com.google.android.material.textfield.TextInputEditText
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var loginButton: Button
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var signupTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        parseAndInjectConfiguration()
        injectModuleDependencies(this)

        loginButton = findViewById(R.id.button)
        emailInput = findViewById(R.id.emailEt)
        passwordInput = findViewById(R.id.passET)
        signupTv = findViewById(R.id.signupTv)

        observeLoginError()
        observeLoginSuccess()

        loginButton.setOnClickListener {
            this.login()
        }

        signupTv.setOnClickListener {
            redirectToSignupScreen()
        }
    }

    private fun observeLoginError() {
        loginViewModel.loginError.observe(this) { _ ->
            /*Utils.displayToast(
                applicationContext,
                R.layout.error_toast,
                "Ce compte n'éxiste pas",
                Toast.LENGTH_SHORT
            )*/
            Log.d("login", "Ce compte n'éxiste pas")
        }
    }

    private fun login() {
        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            loginViewModel.login(email, password)
        } else {
            // Utils.displayToast(applicationContext, R.layout.error_toast, "Merci de remplire tous les champs!", Toast.LENGTH_SHORT)
            Log.d("login", "Merci de remplire tous les champs!")
        }
    }

    private fun observeLoginSuccess() {
        loginViewModel.loginResult.observe(this) { loginResponse ->
            val token: String = loginResponse.getToken()
            TokenManager.setToken(token)
            TokenManager.setPseudoAndUserIdFromToken()

           /* Utils.displayToast(
                applicationContext,
                R.layout.success_toast,
                "Bonjour ^^",
                Toast.LENGTH_SHORT
            )*/
            Log.d("login", token)
            TokenManager.storeAccessToken(this, loginResponse.getToken())
            /*val mainActivityIntent = Intent(this, MainActivity::class.java)
            startActivity(mainActivityIntent)*/
        }
    }

    private fun redirectToSignupScreen() {
        val signupIntent = Intent(this, SignupActivity::class.java)
        startActivity(signupIntent)
    }
}
