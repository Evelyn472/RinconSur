
package com.example.rinconsur.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.rinconsur.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroUsuarioScreen(
    onCrearCuentaClick: () -> Unit,
    onIniciarSesionClick: () -> Unit,
    usuarioViewModel: UsuarioViewModel
) {
    var usuario by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var confirmarContraseña by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    val contraseñasCoinciden =
        contraseña.isNotEmpty() && contraseña == confirmarContraseña

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Rincón Sur") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Crear cuenta")

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = usuario,
                onValueChange = {
                    usuario = it
                    mensaje = ""
                },
                label = { Text("Nombre de usuario") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = contraseña,
                onValueChange = {
                    contraseña = it
                    mensaje = ""
                },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmarContraseña,
                onValueChange = {
                    confirmarContraseña = it
                    mensaje = ""
                },
                label = { Text("Confirmar contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            if (
                confirmarContraseña.isNotEmpty() &&
                !contraseñasCoinciden
            ) {
                Text(
                    text = "Las contraseñas no coinciden",
                    color = Color.Red
                )
            }

            if (mensaje.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = mensaje,
                    color = if (mensaje == "Cuenta creada exitosamente") {
                        Color(0xFF075B46)
                    } else {
                        Color.Red
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    mensaje = usuarioViewModel.registrarUsuario(
                        nombreUsuario = usuario,
                        contraseña = contraseña,
                        confirmarContraseña = confirmarContraseña
                    )

                    if (mensaje == "Cuenta creada exitosamente") {
                        onCrearCuentaClick()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = usuario.isNotBlank() && contraseñasCoinciden
            ) {
                Text("Crear cuenta")
            }

            TextButton(
                onClick = onIniciarSesionClick
            ) {
                Text("Ya tengo una cuenta. Iniciar sesión")
            }
        }
    }
}
