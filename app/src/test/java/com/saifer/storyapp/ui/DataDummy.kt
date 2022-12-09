package com.saifer.storyapp.ui

import com.saifer.storyapp.data.remote.model.LoginModel
import com.saifer.storyapp.data.remote.model.RegisterModel

object DataDummy {
    fun generateDummyLoginData(): LoginModel{
        return  LoginModel(
            "user@email.com",
            "userpassword"
        )
    }
    fun generateDummyRegisterData(): RegisterModel{
        return  RegisterModel(
            "dummyusername",
            "user@email.com",
            "userpassword"
        )
    }

    fun generateDummyToken(): String{
        return  "nsdajkasasfahsasahsdaskdbaksbdkasdnladlasm"
    }
}