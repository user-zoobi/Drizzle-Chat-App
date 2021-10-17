package com.example.drizzle.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.drizzle.*
import com.example.drizzle.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        mAuth = FirebaseAuth.getInstance()
        supportActionBar?.hide()

       signup_user_button.setOnClickListener {
           signUpDetails()
       }
    }

    private fun signUpDetails(){

        val userName = signup_user_name.text.toString()
        val userEmail = signup_user_email.text.toString()
        val userPassword = signup_user_password.text.toString()

            if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword)){
                Toast.makeText(this, EMPTY_MESSAGE, Toast.LENGTH_SHORT).show()
            }else{
                signUpUser(userName,userEmail,userPassword)
            }
    }

    private fun signUpUser(name:String ,email:String, password:String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task->
                if (task.isSuccessful){
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    Toast.makeText(this, SUCCESS_MESSAGE,Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, EMPTY_MESSAGE,Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name:String, email:String, uid:String){
        dbRef = FirebaseDatabase.getInstance().getReference()
        dbRef.child("user").child(uid).setValue(User(name, email, uid))
    }
}