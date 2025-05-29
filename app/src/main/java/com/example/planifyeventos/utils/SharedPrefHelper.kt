package com.example.planifyeventos.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPrefHelper {
    private const val PREF_NAME = "planify_prefs"
    private const val KEY_EMAIL = "email_usuario"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun salvarEmail(context: Context, email: String) {
        val editor = getPreferences(context).edit()
        editor.putString(KEY_EMAIL, email)
        editor.apply()
    }

    fun obterEmail(context: Context): String? {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_EMAIL, null)
    }
    fun recuperarEmail(context: Context): String? {
        return getPreferences(context).getString(KEY_EMAIL, null)
    }

    fun deslogar(context: Context) {
        val editor = getPreferences(context).edit()
        editor.remove(KEY_EMAIL)
        editor.apply()
    }
}