package org.d3if3063.miniproject02.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pasien")
data class Pasien(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val namaHewan: String,
    val jenisHewan: String,
    val jenisKelaminHewan: String,
    val umurHewan: String,
    val namaPemilik: String,
    val noTelp: String,
    val alamat: String,
    val tanggal: String
)
