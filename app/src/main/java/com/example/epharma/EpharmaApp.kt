package com.example.epharma

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp

class EpharmaApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.e("mana","Initialised")

        FirebaseApp.initializeApp(this)
    }
}
