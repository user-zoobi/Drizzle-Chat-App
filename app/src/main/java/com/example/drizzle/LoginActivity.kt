package com.example.drizzle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.drizzle.ui.SignUpActivity
import com.example.drizzle.ui.UserDetails
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        login_button.setOnClickListener {
            userCredentials()
        }
        signUp()
    }

    private fun userCredentials(){

        //Firebase Authentication
        mAuth = FirebaseAuth.getInstance()
        val userEmail = login_user_email.text.toString()
        val userPassword = login_user_password.text.toString()


            //Validation for userEmail

            if (TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword)){
                Toast.makeText(this, EMPTY_MESSAGE, Toast.LENGTH_SHORT).show()
            }else{
                login(userEmail,userPassword)
            }
        }
    private fun signUp(){

        //move to signup
        signup_button.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    private fun login(email:String, password:String){

        //user login with firebase
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task->
                if(task.isSuccessful){
                    userList()
                }
                else{
                    Toast.makeText(this, EMPTY_MESSAGE,Toast.LENGTH_SHORT).show()
                }
            }
        }

    private fun userList(){
        val intent = Intent(this, UserDetails::class.java)
        startActivity(intent)
    }
}


