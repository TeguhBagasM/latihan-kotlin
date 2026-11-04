// Titik masuk aplikasi yang menyiapkan penyimpanan user dan navigasi Compose.
package com.example.latihankotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.latihankotlin.data.UserStorage
import com.example.latihankotlin.navigation.AppNavigation
import com.example.latihankotlin.ui.theme.LatihanKotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // UserStorage dibuat sekali di Activity dan dipakai oleh semua layar.
        val userStorage = UserStorage(applicationContext)

        setContent {
            LatihanKotlinTheme {
                AppNavigation(userStorage = userStorage)
            }
        }
    }
}
