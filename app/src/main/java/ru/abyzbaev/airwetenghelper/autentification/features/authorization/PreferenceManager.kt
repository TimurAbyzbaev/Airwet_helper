package ru.abyzbaev.airwetenghelper.autentification.features.authorization

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "AuthPreferences",
        Context.MODE_PRIVATE
    )

    companion object {
        const val EMAIL_KEY = "email"
        const val PASSWORD_KEY = "password"
    }

    var email: String?
        get() = sharedPreferences.getString(EMAIL_KEY, null)
        set(value) = sharedPreferences.edit().putString(EMAIL_KEY, value).apply()

    var password: String?
        get() = sharedPreferences.getString(PASSWORD_KEY, null)
        set(value) = sharedPreferences.edit().putString(PASSWORD_KEY, value).apply()
}
