
package com.example.rinconsur.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.rinconsur.data.local.movimiento.MovimientoEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val VerdeEntrada = Color(0xFF075B46)
private val RojoSalida = Color(0xFFCC3D3D)

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun ConfirmacionScreen(
    movimiento: MovimientoEntity,
    onRegistrarOtroClick: () -> Unit,
    onInicioClick: () -> Unit
) {
    val fecha = SimpleDateFormat(
        "dd/MM/yyyy HH:mm",
        Locale.getDefault()
    ).format(Date(movimiento.fechaHora))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro confirmado") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("¡Visita registrada correctamente!")

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = VerdeEntrada.copy(alpha = 0.08f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("↑ ENTRADA", color = VerdeEntrada)
                    Text("Puerta ${movimiento.accesoEntrada}")
                }
            }

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = RojoSalida.copy(alpha = 0.08f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("↓ SALIDA", color = RojoSalida)
                    Text("Puerta ${movimiento.accesoSalida}")
                }
            }

            Text("Sexo: ${movimiento.sexo}")
            Text("Rango de edad: ${movimiento.rangoEdad}")
            Text("Tipo de flujo: ${movimiento.tipoFlujo}")
            Text("Acompañantes: ${movimiento.acompanantes}")
            Text("Fecha y hora: $fecha")

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onRegistrarOtroClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = VerdeEntrada
                )
            ) {
                Text("Registrar otra visita")
            }

            Button(
                onClick = onInicioClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al inicio")
            }
        }
    }
}
