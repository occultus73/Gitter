package io.github.occultus73.gitter.utils

interface AuthListner {
    fun onLoading()
    fun onSuccess()
    fun onFailure(errorMessage : String)
}