// Layar utama setelah login yang menampilkan sapaan, ringkasan dummy, dan logout.
package com.example.latihankotlin.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DashboardScreen(
    fullName: String,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Halo, $fullName",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Ringkasan aktivitas belajar Anda",
            style = MaterialTheme.typography.bodyLarge
        )

        SummaryCard(title = "Tugas", value = "5 tugas aktif")
        SummaryCard(title = "Jadwal", value = "2 kelas hari ini")
        SummaryCard(title = "Nilai rata-rata", value = "86,5")
        SummaryCard(title = "Kehadiran", value = "92%")

        Button(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Logout")
        }
    }
}

@Composable
private fun SummaryCard(
    title: String,
    value: String
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 6.dp)
            )
        }
    }
}
