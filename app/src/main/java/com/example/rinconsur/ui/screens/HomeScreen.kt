
package com.example.rinconsur.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val VerdeRincon = Color(0xFF075B46)
private val VerdeClaro = Color(0xFFDDF5E9)
private val AzulRegistros = Color(0xFF2479E8)
private val AmarilloResumen = Color(0xFFF5B52E)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onRegistrarClick: () -> Unit,
    onRegistrosClick: () -> Unit,
    onResumenClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "CENTRO COMERCIAL",
                            fontSize = 10.sp
                        )
                        Text(
                            text = "RINCÓN SUR",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = VerdeRincon
                        )
                    }
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text("⌂  Inicio", color = VerdeRincon)
                Text("▤  Registros", color = Color.Gray)
                Text("▥  Resumen", color = Color.Gray)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8FAF9))
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "¡Bienvenido!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Registra y consulta los movimientos de nuestra tienda.",
                fontSize = 16.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(28.dp))

            TarjetaMenu(
                titulo = "Registrar movimiento",
                colorFondo = VerdeClaro,
                colorTexto = VerdeRincon,
                onClick = onRegistrarClick
            )

            Spacer(modifier = Modifier.height(14.dp))

            TarjetaMenu(
                titulo = "Ver registros",
                colorFondo = Color(0xFFE5EFFF),
                colorTexto = AzulRegistros,
                onClick = onRegistrosClick
            )

            Spacer(modifier = Modifier.height(14.dp))

            TarjetaMenu(
                titulo = "Resumen",
                colorFondo = Color(0xFFFFF2D5),
                colorTexto = AmarilloResumen,
                onClick = onResumenClick
            )
        }
    }
}

@Composable
private fun TarjetaMenu(
    titulo: String,
    colorFondo: Color,
    colorTexto: Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorFondo
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = titulo,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorTexto
            )
            Text(
                text = "›",
                fontSize = 32.sp,
                color = colorTexto
            )
        }
    }
}
