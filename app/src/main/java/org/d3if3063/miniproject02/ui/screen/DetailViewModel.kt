package org.d3if3063.miniproject02.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3063.miniproject02.database.PasienDao
import org.d3if3063.miniproject02.model.Pasien
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailViewModel(private val dao: PasienDao): ViewModel() {
    private val formatter = SimpleDateFormat("yyy-MM-dd HH:mm:ss", Locale.US)

    fun insert(namaHewan: String, jenisHewan:String, jenisKelamin: String, umurHewan: String, namaPemilik: String, noTelp: String, alamat: String) {
        val pasien = Pasien(
            namaHewan = namaHewan,
            jenisHewan = jenisHewan,
            jenisKelaminHewan = jenisKelamin,
            umurHewan = umurHewan,
            namaPemilik = namaPemilik,
            noTelp = noTelp,
            alamat = alamat,
            tanggal = formatter.format(Date())
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(pasien)
        }
    }

    suspend fun getPasien(id: Long): Pasien? {
        return dao.getPasienByID(id)
    }

    fun update(id: Long, namaHewan: String, jenisHewan:String, jenisKelamin: String, umurHewan: String, namaPemilik: String, noTelp: String, alamat: String) {
        val pasien = Pasien(
            id = id,
            namaHewan = namaHewan,
            jenisHewan = jenisHewan,
            jenisKelaminHewan = jenisKelamin,
            umurHewan = umurHewan,
            namaPemilik = namaPemilik,
            noTelp = noTelp,
            alamat = alamat,
            tanggal = formatter.format(Date())
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(pasien)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}