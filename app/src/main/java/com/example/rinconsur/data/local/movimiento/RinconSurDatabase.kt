
package com.example.rinconsur.data.local.movimiento

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [MovimientoEntity::class],
    version = 2,
    exportSchema = false
)
abstract class RinconSurDatabase : RoomDatabase() {

    abstract fun movimientoDao(): MovimientoDao

    companion object {
        @Volatile
        private var INSTANCE: RinconSurDatabase? = null

        fun obtenerInstancia(context: Context): RinconSurDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    RinconSurDatabase::class.java,
                    "rincon_sur_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
