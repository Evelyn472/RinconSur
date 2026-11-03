
package com.example.rinconsur.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.rinconsur.viewmodel.MovimientoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumenScreen(
    movimientoViewModel: MovimientoViewModel,
    onVolverClick: () -> Unit
) {
    val movimientos by movimientoViewModel.movimientos.collectAsState()
    val context = LocalContext.current

    val total = movimientos.size
    val compras = movimientos.count { it.tipoFlujo == "Compra" }
    val transito = movimientos.count { it.tipoFlujo == "Tránsito" }
    val acompanantes = movimientos.sumOf { it.acompanantes }

    val textoResumen = buildString {
        appendLine("RINCÓN SUR")
        appendLine("Resumen de movimientos")
        appendLine("----------------------")
        appendLine("Total de visitas: $total")
        appendLine("Visitas de compra: $compras")
        appendLine("Visitas de tránsito: $transito")
        appendLine("Total de acompañantes: $acompanantes")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Resumen")
                },
                navigationIcon = {
                    TextButton(onClick = onVolverClick) {
                        Text("Volver")
                    }
                }
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
            Text(
                text = "Resumen de actividad",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFDDF5E9)
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text("Total de visitas")
                    Text(
                        text = "$total",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE5EFFF)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Compras")
                        Text(
                            text = "$compras",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFF2D5)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Tránsito")
                        Text(
                            text = "$transito",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text("Total de acompañantes")
                    Text(
                        text = "$acompanantes",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(
                            Intent.EXTRA_SUBJECT,
                            "Resumen de movimientos - Rincón Sur"
                        )
                        putExtra(
                            Intent.EXTRA_TEXT,
                            textoResumen
                        )
                    }

                    context.startActivity(
                        Intent.createChooser(
                            intent,
                            "Compartir resumen"
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = total > 0
            ) {
                Text("Compartir resumen")
            }
        }
    }
}
