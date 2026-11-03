
package com.example.rinconsur.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.rinconsur.viewmodel.UsuarioViewModel
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroUsuarioScreen(
    onCrearCuentaClick: () -> Unit,
    onIniciarSesionClick: () -> Unit,
    usuarioViewModel: UsuarioViewModel
) {
    val context = LocalContext.current

    var usuario by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var confirmarContraseña by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var foto by remember { mutableStateOf<Bitmap?>(null) }

    val launcherCamara = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            foto = bitmap
        }
    }

    val permisoCamara = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { autorizado ->
        if (autorizado) {
            launcherCamara.launch(null)
        } else {
            Toast.makeText(
                context,
                "No se autorizó el acceso a la cámara",
                Toast.LENGTH_LONG
            ).show()
        }
    }

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
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Crear cuenta")

            Spacer(modifier = Modifier.height(16.dp))

            if (foto != null) {
                Image(
                    bitmap = foto!!.asImageBitmap(),
                    contentDescription = "Fotografía del trabajador",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFDDF5E9)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Sin fotografía")
                }
            }

            TextButton(
                onClick = {
                    val tienePermiso = ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED

                    if (tienePermiso) {
                        launcherCamara.launch(null)
                    } else {
                        permisoCamara.launch(Manifest.permission.CAMERA)
                    }
                }
            ) {
                Text(
                    if (foto == null) {
                        "Tomar fotografía (opcional)"
                    } else {
                        "Cambiar fotografía"
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = usuario,
                onValueChange = {
                    if (
                        it.length <= 30 &&
                        it.matches(Regex("^[a-zA-Z0-9_.]*$"))
                    ) {
                        usuario = it
                        mensaje = ""
                    }
                },
                label = { Text("Nombre de usuario") },
                supportingText = {
                    Text("Mínimo 4 caracteres")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = contraseña,
                onValueChange = {
                    if (it.matches(Regex("^[0-9]{0,4}$"))) {
                        contraseña = it
                        mensaje = ""
                    }
                },
                label = { Text("PIN de 4 dígitos") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = confirmarContraseña,
                onValueChange = {
                    if (it.matches(Regex("^[0-9]{0,4}$"))) {
                        confirmarContraseña = it
                        mensaje = ""
                    }
                },
                label = { Text("Confirmar PIN") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            if (confirmarContraseña.isNotEmpty() && !contraseñasCoinciden) {
                Text(
                    text = "Los PIN no coinciden",
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

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    mensaje = usuarioViewModel.registrarUsuario(
                        usuario,
                        contraseña,
                        confirmarContraseña
                    )

                    if (mensaje == "Cuenta creada exitosamente") {
                        if (foto != null) {
                            try {
                                val original = foto!!
                                val lado = minOf(original.width, original.height)
                                val izquierda = (original.width - lado) / 2
                                val arriba = (original.height - lado) / 2

                                val fotoCuadrada = Bitmap.createBitmap(
                                    original,
                                    izquierda,
                                    arriba,
                                    lado,
                                    lado
                                )

                                val archivo = File(
                                    context.filesDir,
                                    "foto_${usuario.trim().lowercase().hashCode()}.jpg"
                                )

                                FileOutputStream(archivo).use {
                                    fotoCuadrada.compress(
                                        Bitmap.CompressFormat.JPEG,
                                        90,
                                        it
                                    )
                                }

                                usuarioViewModel.guardarFotoUsuario(
                                    usuario.trim(),
                                    archivo.absolutePath
                                )
                            } catch (e: Exception) {
                                Toast.makeText(
                                    context,
                                    "Cuenta creada, pero no se pudo guardar la foto",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }

                        onCrearCuentaClick()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = usuario.length >= 4 &&
                        contraseña.length == 4 &&
                        contraseñasCoinciden
            ) {
                Text("Crear cuenta")
            }

            TextButton(
                onClick = onIniciarSesionClick
            ) {
                Text("Volver")
            }
        }
    }
}
