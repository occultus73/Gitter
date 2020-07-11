package io.github.occultus73.gitter.utils

import android.app.Dialog
import android.content.Context
import android.util.Patterns
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import io.github.occultus73.gitter.R

fun isLoginDataValid(email: String, password: String): Int {
    return if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) 0
    else if (password.length < 8) 1
    else -1
}

fun isSignUpDataValid(
    eMail: String,
    passWord: String,
    confirmPassword: String
): Int {
    return if (!Patterns.EMAIL_ADDRESS.matcher(eMail).matches()) 0
    else if (passWord.length < 8) 1
    else if (passWord != confirmPassword) 2
    else -1
}

fun showToast(context: Context?, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

