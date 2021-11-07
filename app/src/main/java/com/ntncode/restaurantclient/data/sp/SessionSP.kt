package com.ntncode.restaurantclient.data.sp

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.ntncode.restaurantclient.R

class SessionSP(val context: Context) {

    private val PREFS_NAME = "SESSION_SP"
    private val STATE_VALUE = "denied"
    private val OB_VALUE = "no"

    val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    @SuppressLint("CommitPrefEdits")
    fun setStateSession(state: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(STATE_VALUE, state)
        editor.apply()
    }

    @SuppressLint("CommitPrefEdits")
    fun setStateOnBoarding(state: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(OB_VALUE, state)
        editor.apply()
    }

    fun getStateOnBoarding(): String? {
        return sharedPref.getString(OB_VALUE, context.getString(R.string.status_ob_no))
    }

    fun getStateSession(): String? {
        return sharedPref.getString(STATE_VALUE, context.getString(R.string.status_denied))
    }

    fun logoutSession() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(STATE_VALUE, context.getString(R.string.status_denied))
        editor.apply()
    }

    fun clearState() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }

    fun removeValue(state: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(state)
        editor.apply()
    }
}