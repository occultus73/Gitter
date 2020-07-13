package com.example.unittesting.login.model

import android.util.Log
import android.util.Patterns

// This class was added inorder to carry out login validation Test
class LoginValidator {

    companion object {
        val EMPTY = ""
        val MIN_PASSWORD_LENGTH = 8
        val patterns = Patterns.EMAIL_ADDRESS
    }

    fun validateLogin(login: String): Boolean {
        return login != EMPTY
    }

    fun validatePassword(password: String): Boolean {
        return password.length >= MIN_PASSWORD_LENGTH
    }

    fun isLoginDataValid(email: String, password: String): Int {
            return if (!patterns.matcher(email).matches()) 0
            else if (password.length < 8) 1
            else -1
    }

}
