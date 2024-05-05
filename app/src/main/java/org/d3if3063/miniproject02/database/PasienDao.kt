package org.d3if3063.miniproject02.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if3063.miniproject02.model.Pasien

@Dao
interface PasienDao {
    @Insert
    suspend fun insert(pasien: Pasien)
    @Update
    suspend fun update(pasien: Pasien)
    @Query("SELECT * FROM pasien ORDER BY tanggal ASC")
    fun getPasien(): Flow<List<Pasien>>
    @Query("SELECT * FROM pasien WHERE id = :id")
    suspend fun getPasienByID(id: Long): Pasien?
    @Query("DELETE FROM pasien WHERE id = :id")
    suspend fun deleteById(id: Long)
}