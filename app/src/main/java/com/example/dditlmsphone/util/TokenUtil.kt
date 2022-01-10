package com.example.dditlmsphone.util

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.crypto.spec.SecretKeySpec

class TokenUtil constructor(context: Context){
    companion object {
        @Volatile
        private var INSTANCE: TokenUtil? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: TokenUtil(context).also {
                    INSTANCE = it
                }
            }

        val secretKey = "ashjkdlehfaljksdhfcxbnakljshedfnjkawlkasjhdgfasjdfgasjdgfwakvbszmjhdbvlasmehfkljahsdkljfhasdkljfhalsjkdfh"
        @RequiresApi(Build.VERSION_CODES.O)
        val key = SecretKeySpec(Base64.getEncoder().encode(secretKey.toByteArray(StandardCharsets.UTF_8)),SignatureAlgorithm.HS512.jcaName)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createToken(content : String): String{
        val token = Jwts.builder()
            .setSubject(content)
            .setExpiration(Date.from(LocalDateTime.now().plusHours(2).atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(key,SignatureAlgorithm.HS512)
            .compact()
        return token;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getToken(token : String): Jws<Claims>{
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
    }
}