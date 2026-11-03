
package com.example.rinconsur.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.rinconsur.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdministrarUsuariosScreen(
    usuarioViewModel: UsuarioViewModel,
    onVolverClick: () -> Unit
) {
    val trabajadores = remember {
        mutableStateListOf<String>().apply {
            addAll(usuarioViewModel.obtenerTrabajadores())
        }
    }

    var usuarioEliminar by remember { mutableStateOf<String?>(null) }
    var mensaje by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Administrar trabajadores") },
                navigationIcon = {
                    TextButton(onClick = onVolverClick) {
                        Text("Volver")
                    }
                }
            )
        },
        containerColor = Color(0xFFF8FAF9)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
        ) {
            Text("Trabajadores registrados")

            Spacer(modifier = Modifier.height(16.dp))

            if (trabajadores.isEmpty()) {
                Text("No hay trabajadores registrados.")
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(trabajadores) { trabajador ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color(0xFFDDF5E9),
                                RoundedCornerShape(12.dp)
                            )
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(trabajador)

                        TextButton(
                            onClick = {
                                usuarioEliminar = trabajador
                            }
                        ) {
                            Text("Eliminar", color = Color.Red)
                        }
                    }
                }
            }

            if (mensaje.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(mensaje)
            }
        }
    }

    if (usuarioEliminar != null) {
        AlertDialog(
            onDismissRequest = { usuarioEliminar = null },
            title = { Text("Eliminar trabajador") },
            text = {
                Text("¿Deseas eliminar la cuenta de $usuarioEliminar?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val nombre = usuarioEliminar!!
                        if (usuarioViewModel.eliminarUsuario(nombre)) {
                            trabajadores.remove(nombre)
                            mensaje = "Trabajador eliminado correctamente"
                        } else {
                            mensaje = "No se pudo eliminar el trabajador"
                        }
                        usuarioEliminar = null
                    }
                ) {
                    Text("Eliminar", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { usuarioEliminar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
