package io.github.occultus73.gitter.model.network

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import io.github.occultus73.gitter.utils.AuthListner

class FirebaseHelper {

    lateinit var authListner : AuthListner

     val firebaseAuth : FirebaseAuth by lazy {
         FirebaseAuth.getInstance()
    }
    var userId = firebaseAuth.currentUser?.uid
 }