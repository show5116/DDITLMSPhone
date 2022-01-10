package com.example.dditlmsphone.activity

import android.os.Build
import android.os.Bundle
import android.webkit.CookieManager
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.example.dditlmsphone.R
import com.example.dditlmsphone.util.TokenUtil
import com.example.dditlmsphone.util.VolleyUtil

class LoginActivity : AppCompatActivity() {
    val cookieManager = CookieManager.getInstance()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var loginBtn = findViewById<Button>(R.id.buttonLogin)
        var idText = findViewById<EditText>(R.id.editTextId)
        var passwordText = findViewById<EditText>(R.id.editTextPassword)

        val queue = VolleyUtil.getInstance(this.applicationContext).requestQueue
        val tokenUtil = TokenUtil.getInstance(this.applicationContext)
        loginBtn.setOnClickListener{
           println("클릭")
            val url = "http://192.168.146.3:8090/android/login?token="
            val content = StringBuilder()
            content.append("id=")
            content.append(idText.text)
            content.append("&password=")
            content.append(passwordText.text)
            val token = tokenUtil.createToken(content.toString());
            val stringRequest = StringRequest(Request.Method.GET, url+token,
                { response ->
                    println(response)
                },
                { error ->
                    println(error)
                })
            VolleyUtil.getInstance(this).addToRequestQueue(stringRequest)
        }
    }
}