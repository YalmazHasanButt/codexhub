package com.funkymaster.demo.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.funkymaster.demo.data.network.ApiInterface
import com.funkymaster.demo.R
import com.funkymaster.demo.data.network.RetrofitInstance
import com.funkymaster.demo.data.model.UserInfo
import com.funkymaster.demo.utilities.Util
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class RegisterActivity: AppCompatActivity() {

    private lateinit var registerBtn: Button
    private lateinit var passwordField: EditText
    private lateinit var retypePasswordField: EditText
    private lateinit var emailField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        iniVariables()
        button_workings()
    }   //end of on create method

    fun iniVariables(){
        registerBtn = findViewById(R.id.registerBtn)
        passwordField = findViewById(R.id.registerPasswordField)
        retypePasswordField = findViewById(R.id.registerRePasswordField)
        emailField = findViewById(R.id.registerEmailField)
    }

    fun button_workings(){
        registerBtn.setOnClickListener(View.OnClickListener {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()
            val  retypePassword = retypePasswordField.text.toString()
            if(email.equals("") || password.equals("") || retypePassword.equals(""))
                Toast.makeText(this,"All Fields are required", Toast.LENGTH_LONG).show()
            else{
                if(!Util.isEmailValid(email))
                    Toast.makeText(this,"Email format not valid", Toast.LENGTH_LONG).show()
                else{
                    if(password.equals(retypePassword))
                        register(email, password)
                    else
                        Toast.makeText(this,"Passwords donot match!", Toast.LENGTH_LONG).show()
                }
            }
        })

    }   //end of method button workings


    private fun register(email: String, password: String){
        val retIn = RetrofitInstance.getRetrofitInstance()
            .create(ApiInterface::class.java)
        val registerInfo =
            UserInfo(email, password)
        retIn.registerUser(registerInfo).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    this@RegisterActivity,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onResponse(call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                if (response.code() == 200) { //success
                    //   System.out.println("Mars: "+ response.body()!!.string())
                    Toast.makeText(this@RegisterActivity, "Registration success!", Toast.LENGTH_LONG)
                        .show()
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                } else{
                    Toast.makeText(this@RegisterActivity, "registration failed!", Toast.LENGTH_SHORT)
                        .show()
                } //end of else
            }   //end of on response method
        })
    }   //end of register method



} //end of class