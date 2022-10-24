package com.bokuno.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bokuno.notes.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding:ActivityRegisterBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth=FirebaseAuth.getInstance()
        binding.btnRegister.setOnClickListener{
            val email=binding.etEmail.text.toString()
            val pass=binding.etPassword.text.toString()
            val confirmPass=binding.etPasswordConfirm.text.toString()
            if(email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()){
                if(pass == confirmPass){
                    mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
                        if(it.isSuccessful){
                            val loginIntent = Intent(this,LoginActivity::class.java)
                            startActivity(loginIntent)
                            finish()
                        }else{
                            Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else{
                    Toast.makeText(this,"Password not matching",Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this,"Fields are not complete",Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnSignIn.setOnClickListener {
            val loginIntent = Intent(this,LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }
}