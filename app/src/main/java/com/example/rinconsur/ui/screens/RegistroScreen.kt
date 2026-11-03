
package com.example.rinconsur.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rinconsur.data.local.movimiento.MovimientoEntity
import com.example.rinconsur.viewmodel.MovimientoViewModel
import kotlinx.coroutines.launch

private val VerdeRincon = Color(0xFF075B46)
private val VerdeClaro = Color(0xFFE2F4EA)
private val RojoSalida = Color(0xFFCC3030)
private val RojoClaro = Color(0xFFFCE9E9)
private val Azul = Color(0xFF2874CE)
private val AzulClaro = Color(0xFFE5F0FF)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    movimientoViewModel: MovimientoViewModel,
    onGuardarClick: () -> Unit
) {
    var accesoEntrada by remember { mutableIntStateOf(0) }
    var accesoSalida by remember { mutableIntStateOf(0) }
    var sexo by remember { mutableStateOf("Femenino") }
    var edad by remember { mutableStateOf("6–17") }
    var tipoFlujo by remember { mutableStateOf("Compra") }
    var acompanantes by remember { mutableIntStateOf(0) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "CENTRO COMERCIAL",
                            fontSize = 10.sp
                        )
                        Text(
                            "RINCÓN SUR",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Column {
                Text(
                    "Registrar movimiento",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Completa los datos de la persona.",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }

            Text(
                "1. Movimientos y accesos",
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(
                        containerColor = VerdeClaro
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("↪", color = VerdeRincon, fontSize = 30.sp)
                        Text(
                            "Entrada",
                            color = VerdeRincon,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text("Selecciona acceso", fontSize = 11.sp)

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            (1..4).forEach { numero ->
                                FilterChip(
                                    selected = accesoEntrada == numero,
                                    onClick = { accesoEntrada = numero },
                                    label = { Text("$numero") },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = VerdeRincon,
                                        selectedLabelColor = Color.White
                                    )
                                )
                            }
                        }
                    }
                }

                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(
                        containerColor = RojoClaro
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("⇥", color = RojoSalida, fontSize = 30.sp)
                        Text(
                            "Salida",
                            color = RojoSalida,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text("Selecciona acceso", fontSize = 11.sp)

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            (1..4).forEach { numero ->
                                FilterChip(
                                    selected = accesoSalida == numero,
                                    onClick = { accesoSalida = numero },
                                    label = { Text("$numero") },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = RojoSalida,
                                        selectedLabelColor = Color.White
                                    )
                                )
                            }
                        }
                    }
                }
            }

            Text("2. Sexo", fontWeight = FontWeight.Bold)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Femenino", "Masculino", "Otro").forEach { opcion ->
                    val color = when (opcion) {
                        "Femenino" -> RojoSalida
                        "Masculino" -> Azul
                        else -> Color.DarkGray
                    }

                    val fondo = when (opcion) {
                        "Femenino" -> RojoClaro
                        "Masculino" -> AzulClaro
                        else -> Color.LightGray
                    }

                    FilterChip(
                        selected = sexo == opcion,
                        onClick = { sexo = opcion },
                        label = { Text(opcion) },
                        modifier = Modifier.weight(1f),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = fondo,
                            selectedLabelColor = color
                        )
                    )
                }
            }

            Text("3. Rango de edad", fontWeight = FontWeight.Bold)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                listOf("6–17", "18–30", "31–50", "51+").forEach { rango ->
                    FilterChip(
                        selected = edad == rango,
                        onClick = { edad = rango },
                        label = {
                            Text(
                                when (rango) {
                                    "6–17" -> "6–17"
                                    "18–30" -> "18–30"
                                    "31–50" -> "31–50"
                                    else -> "51+"
                                },
                                fontSize = 11.sp
                            )
                        },
                        modifier = Modifier.weight(1f),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = VerdeRincon,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            Text("4. Tipo de flujo", fontWeight = FontWeight.Bold)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                FilterChip(
                    selected = tipoFlujo == "Compra",
                    onClick = { tipoFlujo = "Compra" },
                    label = { Text("🛒  Compra") },
                    modifier = Modifier.weight(1f),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = VerdeClaro,
                        selectedLabelColor = VerdeRincon
                    )
                )

                FilterChip(
                    selected = tipoFlujo == "Tránsito",
                    onClick = { tipoFlujo = "Tránsito" },
                    label = { Text("🚶  Tránsito") },
                    modifier = Modifier.weight(1f),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = VerdeClaro,
                        selectedLabelColor = VerdeRincon
                    )
                )
            }

            Text("5. Acompañantes", fontWeight = FontWeight.Bold)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = {
                        if (acompanantes > 0) acompanantes--
                    }
                ) {
                    Text("−", fontSize = 22.sp)
                }

                Text(
                    "$acompanantes",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    onClick = { acompanantes++ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VerdeRincon
                    )
                ) {
                    Text("+", fontSize = 22.sp)
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = {
                    if (accesoEntrada == 0 || accesoSalida == 0) {
                        Toast.makeText(
                            context,
                            "Debes seleccionar entrada y salida",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val nuevoMovimiento = MovimientoEntity(
                            accesoEntrada = accesoEntrada,
                            accesoSalida = accesoSalida,
                            sexo = sexo,
                            rangoEdad = edad,
                            tipoFlujo = tipoFlujo,
                            acompanantes = acompanantes,
                            fechaHora = System.currentTimeMillis()
                        )

                        scope.launch {
                            try {
                                movimientoViewModel.guardarMovimiento(nuevoMovimiento)

                                Toast.makeText(
                                    context,
                                    "Movimiento guardado correctamente",
                                    Toast.LENGTH_SHORT
                                ).show()

                                onGuardarClick()

                            } catch (e: Exception) {
                                Toast.makeText(
                                    context,
                                    "Error al guardar el movimiento: ${e.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = VerdeRincon
                )
            ) {
                Text(
                    "Guardar movimiento",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
