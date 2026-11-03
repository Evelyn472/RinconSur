
package com.example.rinconsur.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.rinconsur.viewmodel.UsuarioViewModel

private val VerdeRincon = Color(0xFF075B46)
private val VerdeClaro = Color(0xFFDDF5E9)
private val FondoRincon = Color(0xFFF8FAF9)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    onAdminRegistroClick: () -> Unit,
    usuarioViewModel: UsuarioViewModel
) {
    var usuario by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var tipoAcceso by remember { mutableStateOf("trabajador") }

    Scaffold(
        containerColor = FondoRincon,
        topBar = {
            TopAppBar(
                title = {
                    Text("RINCÓN SUR", color = VerdeRincon)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(FondoRincon)
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Inicio de sesión",
                color = VerdeRincon,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    tipoAcceso = "trabajador"
                    mensaje = ""
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (tipoAcceso == "trabajador") VerdeRincon else VerdeClaro,
                    contentColor = if (tipoAcceso == "trabajador") Color.White else VerdeRincon
                )
            ) {
                Text(
                    if (tipoAcceso == "trabajador") "✓ Ingresar como trabajador"
                    else "Ingresar como trabajador"
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = {
                    tipoAcceso = "administrador"
                    mensaje = ""
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (tipoAcceso == "administrador") VerdeClaro else Color.Transparent,
                    contentColor = VerdeRincon
                )
            ) {
                Text(
                    if (tipoAcceso == "administrador") "✓ Ingresar como administrador"
                    else "Ingresar como administrador"
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = usuario,
                onValueChange = {
                    if (it.length <= 30 && it.matches(Regex("^[a-zA-Z0-9_.]*$"))) {
                        usuario = it
                        mensaje = ""
                    }
                },
                label = { Text("Usuario") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VerdeRincon,
                    focusedLabelColor = VerdeRincon,
                    cursorColor = VerdeRincon
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = contraseña,
                onValueChange = {
                    if (it.matches(Regex("^[0-9]{0,4}$"))) {
                        contraseña = it
                        mensaje = ""
                    }
                },
                label = { Text("PIN de 4 dígitos") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VerdeRincon,
                    focusedLabelColor = VerdeRincon,
                    cursorColor = VerdeRincon
                )
            )

            if (mensaje.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(mensaje, color = Color.Red)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (usuarioViewModel.iniciarSesion(usuario, contraseña)) {
                        if (usuarioViewModel.rolActual == tipoAcceso) {
                            onLoginClick()
                        } else {
                            usuarioViewModel.cerrarSesion()
                            mensaje = "Esta cuenta no corresponde al tipo de acceso seleccionado"
                        }
                    } else {
                        mensaje = "Usuario o contraseña incorrectos"
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = usuario.isNotBlank() && contraseña.length == 4,
                colors = ButtonDefaults.buttonColors(
                    containerColor = VerdeRincon
                )
            ) {
                Text("Ingresar")
            }

            if (tipoAcceso == "administrador") {
                TextButton(onClick = onAdminRegistroClick) {
                    Text("Crear cuenta de administrador", color = VerdeRincon)
                }
            }
        }
    }
}
