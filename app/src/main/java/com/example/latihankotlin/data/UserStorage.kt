// Satu-satunya class yang mengatur baca-tulis data user dan sesi ke SharedPreferences.
package com.example.latihankotlin.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

class UserStorage(context: Context) {
    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    // Password disimpan plain text hanya untuk pembelajaran; cara ini TIDAK aman untuk aplikasi sungguhan.
    // Semua user disimpan dalam satu string JSON agar contoh penyimpanannya mudah dipelajari.
    fun registerUser(user: User): Boolean {
        if (findUser(user.username) != null) {
            return false
        }

        val users = readUsers()
        users.put(
            JSONObject()
                .put(KEY_NAME, user.nama)
                .put(KEY_USERNAME, user.username)
                .put(KEY_PASSWORD, user.password)
        )
        preferences.edit().putString(KEY_USERS, users.toString()).apply()
        return true
    }

    fun findUser(username: String): User? {
        val users = readUsers()
        for (index in 0 until users.length()) {
            val savedUser = users.getJSONObject(index)
            if (savedUser.getString(KEY_USERNAME) == username) {
                return User(
                    nama = savedUser.getString(KEY_NAME),
                    username = savedUser.getString(KEY_USERNAME),
                    password = savedUser.getString(KEY_PASSWORD)
                )
            }
        }
        return null
    }

    fun saveSession(username: String) {
        preferences.edit().putString(KEY_SESSION_USERNAME, username).apply()
    }

    fun getSessionUser(): User? {
        val username = preferences.getString(KEY_SESSION_USERNAME, null) ?: return null
        return findUser(username)
    }

    fun clearSession() {
        preferences.edit().remove(KEY_SESSION_USERNAME).apply()
    }

    private fun readUsers(): JSONArray {
        val savedUsers = preferences.getString(KEY_USERS, null) ?: return JSONArray()
        return JSONArray(savedUsers)
    }

    companion object {
        private const val PREFERENCES_NAME = "latihan_kotlin_preferences"
        private const val KEY_USERS = "users"
        private const val KEY_SESSION_USERNAME = "session_username"
        private const val KEY_NAME = "nama"
        private const val KEY_USERNAME = "username"
        private const val KEY_PASSWORD = "password"
    }
}
