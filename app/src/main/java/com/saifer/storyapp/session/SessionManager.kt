package com.saifer.storyapp.session

import android.content.Context
import android.content.SharedPreferences

class SessionManager(var context: Context){
    private var sessionPref: SharedPreferences? = context.getSharedPreferences("isLogin", 0)
    private val editor: SharedPreferences.Editor? = sessionPref?.edit()

    fun setLogin(isLogin: Boolean){
        editor?.putBoolean("is_login", isLogin)
        editor?.apply()
    } // true for login, false for logout

    fun checkLogin() : Boolean?{
        return sessionPref?.getBoolean("is_login", false)
    }

    fun setToken(token : String?){
        editor?.putString("key", token)
        editor?.apply()
    }

    fun getToken() : String? {
        return sessionPref?.getString("key", "Not Found")
    }

    fun logout(){
        editor?.clear()
        editor?.apply()
    }


}