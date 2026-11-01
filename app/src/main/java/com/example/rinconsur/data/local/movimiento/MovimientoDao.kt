
package com.example.rinconsur.data.local.movimiento

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MovimientoDao {

    // READ: consultar todos los registros
    @Query("SELECT * FROM movimientos ORDER BY fechaHora DESC")
    fun obtenerTodos(): Flow<List<MovimientoEntity>>

    // READ: consultar un registro por su ID
    @Query("SELECT * FROM movimientos WHERE id = :id")
    suspend fun obtenerPorId(id: Int): MovimientoEntity?

    // CREATE: insertar un nuevo registro
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(movimiento: MovimientoEntity): Long

    // UPDATE: modificar un registro existente
    @Update
    suspend fun actualizar(movimiento: MovimientoEntity)

    // DELETE: eliminar un registro individual
    @Delete
    suspend fun eliminar(movimiento: MovimientoEntity)
}
