
package com.example.rinconsur.data.local.movimiento

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movimientos")
data class MovimientoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val accesoEntrada: Int,
    val accesoSalida: Int,
    val sexo: String,
    val rangoEdad: String,
    val tipoFlujo: String,
    val acompanantes: Int,
    val fechaHora: Long
)
