
package com.example.rinconsur.data.local.movimiento

import kotlinx.coroutines.flow.Flow

class MovimientoRepository(
    private val movimientoDao: MovimientoDao
) {

    // READ: obtener todos los registros
    fun obtenerTodos(): Flow<List<MovimientoEntity>> {
        return movimientoDao.obtenerTodos()
    }

    // READ: obtener un registro por ID
    suspend fun obtenerPorId(id: Int): MovimientoEntity? {
        return movimientoDao.obtenerPorId(id)
    }

    // CREATE: insertar registro
    suspend fun insertar(movimiento: MovimientoEntity): Long {
        return movimientoDao.insertar(movimiento)
    }

    // UPDATE: actualizar registro
    suspend fun actualizar(movimiento: MovimientoEntity) {
        movimientoDao.actualizar(movimiento)
    }

    // DELETE: eliminar registro
    suspend fun eliminar(movimiento: MovimientoEntity) {
        movimientoDao.eliminar(movimiento)
    }
}
