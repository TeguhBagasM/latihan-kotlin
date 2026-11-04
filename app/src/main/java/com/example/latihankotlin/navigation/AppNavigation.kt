// Konfigurasi route dan perpindahan antar layar aplikasi.
package com.example.latihankotlin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.latihankotlin.data.UserStorage
import com.example.latihankotlin.ui.DashboardScreen
import com.example.latihankotlin.ui.LoginScreen
import com.example.latihankotlin.ui.RegisterScreen

object AppRoutes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val DASHBOARD = "dashboard"
}

@Composable
fun AppNavigation(userStorage: UserStorage) {
    // navController adalah pengelola back stack, yaitu daftar layar yang bisa dikunjungi kembali.
    val navController = rememberNavController()
    val startDestination = if (userStorage.getSessionUser() != null) {
        AppRoutes.DASHBOARD
    } else {
        AppRoutes.LOGIN
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(AppRoutes.LOGIN) {
            val registrationMessage =
                navController.currentBackStackEntry?.savedStateHandle?.get<String>(
                    "registration_message"
                )

            LoginScreen(
                registrationMessage = registrationMessage,
                onLogin = { username, password ->
                    val user = userStorage.findUser(username)
                    if (user != null && user.password == password) {
                        userStorage.saveSession(user.username)
                        navController.navigate(AppRoutes.DASHBOARD) {
                            // popUpTo inclusive menghapus Login agar tombol Back di Dashboard
                            // tidak dapat kembali ke layar Login.
                            popUpTo(AppRoutes.LOGIN) { inclusive = true }
                        }
                        null
                    } else {
                        "Username atau password salah."
                    }
                },
                onOpenRegister = {
                    navController.navigate(AppRoutes.REGISTER)
                }
            )
        }

        composable(AppRoutes.REGISTER) {
            RegisterScreen(
                onRegister = { user ->
                    val isRegistered = userStorage.registerUser(user)
                    if (isRegistered) {
                        // Login masih berada di bawah Register, sehingga pesan dititipkan
                        // ke savedStateHandle milik entry Login sebelum kembali dengan Back.
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("registration_message", "Registrasi berhasil. Silakan login.")
                        navController.popBackStack()
                    }
                    isRegistered
                },
                onBackToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(AppRoutes.DASHBOARD) {
            val user = userStorage.getSessionUser()
            DashboardScreen(
                fullName = user?.nama ?: "Pengguna",
                onLogout = {
                    userStorage.clearSession()
                    navController.navigate(AppRoutes.LOGIN) {
                        // Menghapus Dashboard agar Back setelah logout tidak masuk lagi ke sesi lama.
                        popUpTo(AppRoutes.DASHBOARD) { inclusive = true }
                    }
                }
            )
        }
    }
}
