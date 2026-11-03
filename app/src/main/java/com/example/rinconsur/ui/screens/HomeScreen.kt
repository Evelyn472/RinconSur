
package com.example.rinconsur.ui.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rinconsur.viewmodel.UsuarioViewModel

private val VerdeRincon = Color(0xFF075B46)
private val VerdeClaro = Color(0xFFDDF5E9)
private val AzulRegistros = Color(0xFF2479E8)
private val AmarilloResumen = Color(0xFFF5B52E)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onRegistrarClick: () -> Unit,
    onRegistrosClick: () -> Unit,
    onResumenClick: () -> Unit,
    onAdministrarUsuariosClick: () -> Unit,
    onCrearTrabajadorClick: () -> Unit,
    onCerrarSesionClick: () -> Unit,
    usuarioViewModel: UsuarioViewModel
) {
    val nombreUsuario = usuarioViewModel.usuarioActual
    val esAdministrador = usuarioViewModel.esAdministrador()

    val fotoPath = remember(nombreUsuario) {
        if (nombreUsuario.isNotBlank()) usuarioViewModel.obtenerFotoUsuario(nombreUsuario) else null
    }

    val fotoBitmap = remember(fotoPath) {
        fotoPath?.let { BitmapFactory.decodeFile(it)?.asImageBitmap() }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Column {
                    Text("CENTRO COMERCIAL", fontSize = 10.sp)
                    Text(
                        "RINCÓN SUR",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = VerdeRincon
                    )
                }
            })
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
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (fotoBitmap != null) {
                    Image(
                        bitmap = fotoBitmap,
                        contentDescription = "Fotografía del usuario",
                        modifier = Modifier.size(64.dp).clip(CircleShape)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(VerdeClaro),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("RS", color = VerdeRincon, fontSize = 22.sp)
                    }
                }

                Column {
                    Text(
                        if (esAdministrador) "Administrador" else "Trabajador",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                    Text(
                        nombreUsuario,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "¡Bienvenido!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Registra y consulta los movimientos de nuestra tienda.",
                fontSize = 16.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(28.dp))

            TarjetaMenu("Registrar movimiento", VerdeClaro, VerdeRincon, onRegistrarClick)
            Spacer(modifier = Modifier.height(14.dp))

            TarjetaMenu("Ver registros", Color(0xFFE5EFFF), AzulRegistros, onRegistrosClick)
            Spacer(modifier = Modifier.height(14.dp))

            TarjetaMenu("Resumen", Color(0xFFFFF2D5), AmarilloResumen, onResumenClick)

            if (esAdministrador) {
                Spacer(modifier = Modifier.height(24.dp))
                Text("Administración", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(14.dp))

                TarjetaMenu(
                    "Administrar trabajadores",
                    Color(0xFFF0E8FF),
                    Color(0xFF7040A0),
                    onAdministrarUsuariosClick
                )

                Spacer(modifier = Modifier.height(14.dp))

                TarjetaMenu(
                    "Registrar trabajador",
                    Color(0xFFFFE8E8),
                    Color(0xFFB3261E),
                    onCrearTrabajadorClick
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            OutlinedButton(
                onClick = onCerrarSesionClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar sesión", color = Color(0xFFB3261E))
            }

            Spacer(modifier = Modifier.height(12.dp))
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
        modifier = Modifier.fillMaxWidth().height(88.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorFondo)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                titulo,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorTexto
            )
            Text("›", fontSize = 32.sp, color = colorTexto)
        }
    }
}
