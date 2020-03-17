package com.Kcolis.android.model.cache

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.Kcolis.android.model.Const


class SessionManager (val context:Context) {


    private val PREF_NAME = "Kcolis-database"
    private var PRIVATE_MODE = 0

    fun createLoginSession(id : Int) {
            val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE)
            val editor = sharedPref.edit()
            editor.putBoolean(Const.IS_LOGIN,true)
            editor.putInt(Const.Key_ID,id)
            editor.apply()
    }

    public fun getUserDetail(): Map<String, String> {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE)
        val user = HashMap<String, String>()
        user.put(Const.Key_ID, sharedPref.getInt(Const.Key_ID,0).toString())
        return user
    }

    public fun logoutUser() {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE)
        val editor = sharedPref.edit()
        editor.clear()
        editor.commit()
    }

    public fun IsLoggedIn(): Boolean {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE)

        return sharedPref.getBoolean(Const.IS_LOGIN,false)
    }




}