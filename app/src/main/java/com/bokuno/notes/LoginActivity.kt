package com.bokuno.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bokuno.notes.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth=FirebaseAuth.getInstance()
        if(mAuth.currentUser!=null){
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }
        binding.btnLogin.setOnClickListener {
            val email=binding.etEmail.text.toString()
            val pass=binding.etPassword.text.toString()
            if(email.isNotEmpty() && pass.isNotEmpty()){
                mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
                    if(it.isSuccessful) {
                        val mainIntent = Intent(this, MainActivity::class.java)
                        startActivity(mainIntent)
                        finish()
                    }
                    else{
                        Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                Toast.makeText(this,"Fields are not complete", Toast.LENGTH_SHORT).show()
            }

        }

        binding.btnSignUp.setOnClickListener {
            val registerIntent=Intent(this,RegisterActivity::class.java)
            startActivity(registerIntent)
        }
    }
}