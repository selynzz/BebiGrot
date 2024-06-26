package com.pukimen.babygrowth.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.pukimen.babygrowth.ui.ViewModelFactory
import com.pukimen.babygrowth.databinding.ActivityLoginBinding
import com.pukimen.babygrowth.ui.InputBabyActivity
import com.pukimen.babygrowth.utils.Results
import com.pukimen.babygrowth.utils.validation

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this,application)
        val viewModel: AuthViewModel by viewModels { factory }

        binding.loginButton.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            val isValid = validation("LoginActivity", email, password, this, null)
            if (isValid) {
                viewModel.login(email, password).observe(this@LoginActivity) { result ->
                    if(result != null){
                        when(result){
                            is Results.Loading ->{
                                binding.progressBar.visibility = View.VISIBLE
                            }
                            is Results.Success<*> -> {
                                binding.progressBar.visibility = View.GONE
                                val intent = Intent(this, InputBabyActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            is Results.Error -> {
                                binding.progressBar.visibility = View.GONE
                                if (result.error == "Unauthorized"){
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Email atau password salah",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }else{
                                    Log.e("LoginActivity", result.error)
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Terjadi kesalahan",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            }
                        }
                    }
                }
            }
        }

        binding.signUpText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

//        val visibilityToggleButton: EditTextPassword = findViewById(R.id.passwordInput)
//        val visibilityToggleOff = ContextCompat.getDrawable(this, R.drawable.ic_invisibility)!!
//        visibilityToggleOff.setBounds(0, 0, visibilityToggleOff.intrinsicWidth, visibilityToggleOff.intrinsicHeight)
//        visibilityToggleButton.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, visibilityToggleOff, null)
    }
}