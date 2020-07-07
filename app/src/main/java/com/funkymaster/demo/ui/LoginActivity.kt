package com.funkymaster.demo.ui


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.funkymaster.demo.R
import com.funkymaster.demo.data.AppDatabase
import com.funkymaster.demo.data.dao.FailedUserDao
import com.funkymaster.demo.data.dao.UserDao
import com.funkymaster.demo.data.entity.FailedLogin
import com.funkymaster.demo.data.entity.User
import com.funkymaster.demo.data.model.UserInfo
import com.funkymaster.demo.data.network.ApiInterface
import com.funkymaster.demo.data.network.RetrofitInstance
import com.funkymaster.demo.utilities.MyApplication
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.InetAddress
import java.text.SimpleDateFormat
import java.util.*


class LoginActivity : AppCompatActivity() {


    private var db: AppDatabase? = null
    private var userDao: UserDao? = null
    private var failedUserDao: FailedUserDao? = null
    private lateinit var registerBtn: Button
    private lateinit var loginBtn: Button
    private lateinit var passwordField: EditText
    private lateinit var emailField: EditText


    fun inserFailedUser(emaiL: String){
        Observable.fromCallable({
            db = AppDatabase.getAppDataBase(context = this)
            failedUserDao = db?.failedUserDaO()

            val formatedDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
            val formatedTime = SimpleDateFormat("HH:mm").format(Date())
            val DateTime = "$formatedDate  $formatedTime"
            var faileduser = FailedLogin(email = emaiL, timestamp =  DateTime)


            with(failedUserDao){
                this?.insertFailedUser(faileduser)
            }
            db?.failedUserDaO()?.getFailedUsers()
        }).doOnNext({ list ->
            var finalString = ""
            list?.map { finalString+= it.email+" - " }
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }   //end of database


    fun insertUser(emaiL: String, rolE: String, statuS: String, shop_iD: String, role_iD: Int){
        Observable.fromCallable({
            db = AppDatabase.getAppDataBase(context = this)
            userDao = db?.useRDao()

            var user1 = User(
                email = emaiL,
                role = rolE,
                status = statuS,
                shop_id = shop_iD,
                role_id = role_iD
            )
            with(userDao){
                this?.insertUser(user1)
            }
            db?.useRDao()?.getUsers()
        }).doOnNext({ list ->
            var finalString = ""
            list?.map { finalString+= it.email+" - " }
            System.out.println("Mars: "+finalString)

        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }   //end of database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        iniVariables()
        button_workings()
    }   //end of on create method

    fun iniVariables(){
        registerBtn = findViewById(R.id.registerActivityBtn)
        loginBtn = findViewById(R.id.loginBtn)
        passwordField = findViewById(R.id.passwordField)
        emailField = findViewById(R.id.emailField)
    }

    fun button_workings(){
        registerBtn.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        })

        loginBtn.setOnClickListener(View.OnClickListener {
                val email = emailField.text.toString()
                val password = passwordField.text.toString()
                //   val email = "ahmedmonire2@gmail.com"
                //     val password = "asd123"
                if (email.equals("") || password.equals(""))
                    Toast.makeText(this, "All Fields are required", Toast.LENGTH_LONG).show()
                else {
                    if (!com.funkymaster.demo.utilities.Util.isEmailValid(email))
                        Toast.makeText(this, "Email format not valid!", Toast.LENGTH_LONG).show()
                    else
                        login(email, password)
                }
        })
    }   //end of method button workings

    private fun login(email: String, password: String){
        val retIn = RetrofitInstance.getRetrofitInstance()
            .create(ApiInterface::class.java)
        val signInInfo =
            UserInfo(email, password)
        retIn.login(signInInfo).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200) {
                    if(response.body() != null){
                        var jsonobject: JSONObject = JSONObject(response.body()!!.string())
                        var dataObject: JSONObject =  jsonobject.getJSONObject("data")
                        val token = dataObject.getString("token")
                        MyApplication.token = token
                        val role = dataObject.getString("role")
                        val role_id = dataObject.getInt("role_id")
                        val shop_id = dataObject.getString("shop_id")
                        val status = dataObject.getString("status")
                        insertUser(email, role, status, shop_id, role_id)
                    }
                    Toast.makeText(applicationContext, "Login success!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Login Failed!", Toast.LENGTH_SHORT).show()
                    inserFailedUser(email)
                }
            }
        })
    }

    fun isInternetAvailable(): Boolean {
        return try {
            val ipAddr: InetAddress = InetAddress.getByName("google.com")
            //You can replace it with your name
            !ipAddr.equals("")
        } catch (e: Exception) {
            false
        }
    }   //check internet available
}   //end of main activity class