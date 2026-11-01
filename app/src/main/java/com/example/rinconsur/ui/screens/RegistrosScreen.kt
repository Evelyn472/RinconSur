
package com.example.rinconsur.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.rinconsur.data.local.movimiento.MovimientoEntity
import com.example.rinconsur.viewmodel.MovimientoViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val VerdeEntrada = Color(0xFF075B46)
private val RojoSalida = Color(0xFFCC3D3D)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrosScreen(
    movimientoViewModel: MovimientoViewModel,
    onVolverClick: () -> Unit
) {
    val movimientos by movimientoViewModel.movimientos.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registros") },
                navigationIcon = {
                    TextButton(onClick = onVolverClick) {
                        Text("Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (movimientos.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text("Todavía no hay visitas registradas.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = movimientos,
                    key = { it.id }
                ) { movimiento ->
                    TarjetaMovimiento(
                        movimiento = movimiento,
                        onActualizar = { movimientoViewModel.actualizarMovimiento(it) },
                        onEliminar = { movimientoViewModel.eliminarMovimiento(it) }
                    )
                }
            }
        }
    }
}

@Composable
private fun TarjetaMovimiento(
    movimiento: MovimientoEntity,
    onActualizar: (MovimientoEntity) -> Unit,
    onEliminar: (MovimientoEntity) -> Unit
) {
    var mostrarEdicion by remember { mutableStateOf(false) }
    var confirmarEliminar by remember { mutableStateOf(false) }

    val fecha = SimpleDateFormat(
        "dd/MM/yyyy HH:mm",
        Locale.getDefault()
    ).format(Date(movimiento.fechaHora))

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray.copy(alpha = 0.12f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Visita N° ${movimiento.id}")

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(
                        containerColor = VerdeEntrada.copy(alpha = 0.12f)
                    )
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text("↑ Entrada", color = VerdeEntrada)
                        Text("Puerta ${movimiento.accesoEntrada}")
                    }
                }

                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(
                        containerColor = RojoSalida.copy(alpha = 0.12f)
                    )
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text("↓ Salida", color = RojoSalida)
                        Text("Puerta ${movimiento.accesoSalida}")
                    }
                }
            }

            Text("Sexo: ${movimiento.sexo}")
            Text("Rango de edad: ${movimiento.rangoEdad}")
            Text("Tipo de flujo: ${movimiento.tipoFlujo}")
            Text("Acompañantes: ${movimiento.acompanantes}")
            Text("Fecha y hora: $fecha")

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { mostrarEdicion = true },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Editar")
                }

                Button(
                    onClick = { confirmarEliminar = true },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Eliminar")
                }
            }
        }
    }

    if (mostrarEdicion) {
        DialogoEditarMovimiento(
            movimiento = movimiento,
            onCerrar = { mostrarEdicion = false },
            onGuardar = {
                onActualizar(it)
                mostrarEdicion = false
            }
        )
    }

    if (confirmarEliminar) {
        AlertDialog(
            onDismissRequest = { confirmarEliminar = false },
            title = { Text("Eliminar visita") },
            text = { Text("¿Estás seguro de eliminar este registro?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onEliminar(movimiento)
                        confirmarEliminar = false
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { confirmarEliminar = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun DialogoEditarMovimiento(
    movimiento: MovimientoEntity,
    onCerrar: () -> Unit,
    onGuardar: (MovimientoEntity) -> Unit
) {
    var entrada by remember(movimiento.id) {
        mutableIntStateOf(movimiento.accesoEntrada)
    }
    var salida by remember(movimiento.id) {
        mutableIntStateOf(movimiento.accesoSalida)
    }
    var sexo by remember(movimiento.id) {
        mutableStateOf(movimiento.sexo)
    }
    var edad by remember(movimiento.id) {
        mutableStateOf(movimiento.rangoEdad)
    }
    var flujo by remember(movimiento.id) {
        mutableStateOf(movimiento.tipoFlujo)
    }
    var acompanantes by remember(movimiento.id) {
        mutableIntStateOf(movimiento.acompanantes)
    }

    AlertDialog(
        onDismissRequest = onCerrar,
        title = { Text("Editar visita N° ${movimiento.id}") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("Puerta de entrada")
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    (1..4).forEach { numero ->
                        FilterChip(
                            selected = entrada == numero,
                            onClick = { entrada = numero },
                            label = { Text("$numero") }
                        )
                    }
                }

                Text("Puerta de salida")
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    (1..4).forEach { numero ->
                        FilterChip(
                            selected = salida == numero,
                            onClick = { salida = numero },
                            label = { Text("$numero") }
                        )
                    }
                }

                Text("Sexo")
                Column {
                    listOf("Femenino", "Masculino", "Otro").forEach { opcion ->
                        FilterChip(
                            selected = sexo == opcion,
                            onClick = { sexo = opcion },
                            label = { Text(opcion) }
                        )
                    }
                }

                Text("Rango de edad")
                Column {
                    listOf("6–17", "18–30", "31–50", "51+").forEach { opcion ->
                        FilterChip(
                            selected = edad == opcion,
                            onClick = { edad = opcion },
                            label = { Text(opcion) }
                        )
                    }
                }

                Text("Tipo de flujo")
                Row {
                    listOf("Compra", "Tránsito").forEach { opcion ->
                        FilterChip(
                            selected = flujo == opcion,
                            onClick = { flujo = opcion },
                            label = { Text(opcion) }
                        )
                    }
                }

                Text("Acompañantes: $acompanantes")
                Row {
                    TextButton(
                        onClick = {
                            if (acompanantes > 0) acompanantes--
                        }
                    ) {
                        Text("−")
                    }

                    TextButton(
                        onClick = { acompanantes++ }
                    ) {
                        Text("+")
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onGuardar(
                        movimiento.copy(
                            accesoEntrada = entrada,
                            accesoSalida = salida,
                            sexo = sexo,
                            rangoEdad = edad,
                            tipoFlujo = flujo,
                            acompanantes = acompanantes
                        )
                    )
                }
            ) {
                Text("Guardar cambios")
            }
        },
        dismissButton = {
            TextButton(onClick = onCerrar) {
                Text("Cancelar")
            }
        }
    )
}
