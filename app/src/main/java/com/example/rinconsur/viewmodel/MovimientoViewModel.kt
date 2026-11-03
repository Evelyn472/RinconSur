
package com.example.rinconsur.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.rinconsur.data.local.movimiento.MovimientoEntity
import com.example.rinconsur.data.local.movimiento.MovimientoRepository
import com.example.rinconsur.data.local.movimiento.RinconSurDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch

class MovimientoViewModel(application: Application) : AndroidViewModel(application) {

    private val database = RinconSurDatabase.obtenerInstancia(application)
    private val repository = MovimientoRepository(database.movimientoDao())

    val movimientos: StateFlow<List<MovimientoEntity>> =
        repository.obtenerTodos().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    var ultimoMovimiento by mutableStateOf<MovimientoEntity?>(null)
        private set

    suspend fun obtenerMovimiento(id: Int): MovimientoEntity? {
        return withContext(Dispatchers.IO) {
            repository.obtenerPorId(id)
        }
    }

    suspend fun guardarMovimiento(
        movimiento: MovimientoEntity
    ): MovimientoEntity {

        val movimientoGuardado = withContext(Dispatchers.IO) {
            val id = repository.insertar(movimiento)
            movimiento.copy(id = id.toInt())
        }

        ultimoMovimiento = movimientoGuardado

        return movimientoGuardado
    }

    fun actualizarMovimiento(movimiento: MovimientoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.actualizar(movimiento)
        }
    }

    fun eliminarMovimiento(movimiento: MovimientoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.eliminar(movimiento)
        }
    }
}
