package org.d3if3063.miniproject02.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if3063.miniproject02.model.Pasien

@Database(entities = [Pasien::class], version = 1, exportSchema = false)
abstract class PasienDb : RoomDatabase() {
    abstract val dao: PasienDao

    companion object {
        @Volatile
        private var INSTANCE: PasienDb? = null

        fun getInstance(context: Context): PasienDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PasienDb::class.java,
                        "pasien.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}