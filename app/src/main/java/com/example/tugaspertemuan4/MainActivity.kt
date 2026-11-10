package com.example.tugaspertemuan4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Biru = Color(0xFF162660)
private val Hijau = Color(0xFF2E7D32)
private val Merah = Color(0xFFC62828)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme { ProfilMahasiswaScreen() }
        }
    }
}

@Composable
fun ProfilMahasiswaScreen() {

    var nama by remember { mutableStateOf("") }
    var nim by remember { mutableStateOf("") }
    var prodi by remember { mutableStateOf("Rekayasa Perangkat Lunak") }
    var semester by remember { mutableStateOf("") }

    var tersimpan by remember { mutableStateOf<List<Pair<String, String>>?>(null) }
    var pesan by remember { mutableStateOf("Silakan isi data profil lalu tekan Simpan.") }
    var pesanSukses by remember { mutableStateOf<Boolean?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCFCFD))
            .verticalScroll(rememberScrollState())
    ) {
        // ===== HEADER dengan logo / foto profil (Box) =====
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Biru)
                .padding(top = 48.dp, bottom = 28.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.unmerpas),
                    contentDescription = "Logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(104.dp)
                        .clip(CircleShape)
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = tersimpan?.let { "Halo, ${it[0].second}!" } ?: "Profil Mahasiswa RPL",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Universitas Merdeka Pasuruan",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 14.sp
                )
            }
        }

        // ===== FORM =====
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Data Mahasiswa", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Biru)
                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = nama, onValueChange = { nama = it },
                    label = { Text("Nama Lengkap") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = nim, onValueChange = { nim = it },
                    label = { Text("NIM") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = prodi, onValueChange = { prodi = it },
                    label = { Text("Program Studi") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = semester, onValueChange = { semester = it.filter(Char::isDigit) },
                    label = { Text("Semester") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(16.dp))

                // ===== TOMBOL (Row) =====
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            val smt = semester.toIntOrNull()
                            when {
                                nama.isBlank() || nim.isBlank() || prodi.isBlank() || semester.isBlank() -> {
                                    pesan = "Gagal: semua kolom wajib diisi!"
                                    pesanSukses = false
                                }
                                smt == null || smt !in 1..14 -> {
                                    pesan = "Gagal: semester harus berupa angka 1–14!"
                                    pesanSukses = false
                                }
                                else -> {
                                    tersimpan = listOf(
                                        "Nama" to nama.trim(),
                                        "NIM" to nim.trim(),
                                        "Program Studi" to prodi.trim(),
                                        "Semester" to semester
                                    )
                                    pesan = "Berhasil: profil ${nama.trim()} telah disimpan."
                                    pesanSukses = true
                                }
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Biru)
                    ) { Text("Simpan") }

                    OutlinedButton(
                        onClick = {
                            nama = ""; nim = ""; semester = ""
                            prodi = "Rekayasa Perangkat Lunak"
                            tersimpan = null
                            pesan = "Form telah direset."
                            pesanSukses = null
                        },
                        modifier = Modifier.weight(1f)
                    ) { Text("Reset") }
                }
            }
        }

        // ===== PESAN STATUS (berubah setelah tombol ditekan) =====
        val warnaPesan = when (pesanSukses) {
            true -> Hijau
            false -> Merah
            null -> Color.DarkGray
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(warnaPesan.copy(alpha = 0.12f))
                .padding(14.dp)
        ) {
            Text(
                pesan,
                color = warnaPesan,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // ===== KARTU HASIL SIMPAN =====
        tersimpan?.let { data ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Profil Tersimpan", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Biru)
                    Spacer(Modifier.height(8.dp))
                    data.forEach { (label, isi) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Text(label, modifier = Modifier.weight(0.4f), color = Color.Gray)
                            Text(": $isi", modifier = Modifier.weight(0.6f), fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfil() {
    MaterialTheme { ProfilMahasiswaScreen() }
}