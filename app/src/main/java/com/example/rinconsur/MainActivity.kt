
package com.example.rinconsur

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rinconsur.ui.screens.LoginScreen
import com.example.rinconsur.ui.screens.HomeScreen
import com.example.rinconsur.ui.screens.RegistroScreen
import com.example.rinconsur.ui.screens.RegistroUsuarioScreen
import com.example.rinconsur.viewmodel.UsuarioViewModel
import com.example.rinconsur.viewmodel.MovimientoViewModel
import com.example.rinconsur.ui.screens.ConfirmacionScreen
import com.example.rinconsur.ui.screens.RegistrosScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var pantallaActual by remember { mutableStateOf("login") }
            val usuarioViewModel: UsuarioViewModel = viewModel()
            val movimientoViewModel: MovimientoViewModel = viewModel()

            MaterialTheme {
                Surface {
                    when (pantallaActual) {
                        "login" -> LoginScreen(
                            onLoginClick = {
                                pantallaActual = "home"
                            },
                            onRegistroClick = {
                                pantallaActual = "registroUsuario"
                            },
                            usuarioViewModel = usuarioViewModel
                        )

                        "registroUsuario" -> RegistroUsuarioScreen(
                            onCrearCuentaClick = {
                                pantallaActual = "login"
                            },
                            onIniciarSesionClick = {
                                pantallaActual = "login"
                            },
                            usuarioViewModel = usuarioViewModel
                        )

                        "home" -> HomeScreen(
                            onRegistrarClick = {
                                pantallaActual = "registro"
                            },
                            onRegistrosClick = {
                                pantallaActual = "registros"
                            },
                            onResumenClick = {
                                pantallaActual = "resumen"
                            }
                        )




                        "registro" -> RegistroScreen(
                            movimientoViewModel = movimientoViewModel,
                            onGuardarClick = {
                                pantallaActual = "confirmacion"
                            }
                        )

                        "confirmacion" -> {
                            val movimiento = movimientoViewModel.ultimoMovimiento

                            if (movimiento != null) {
                                ConfirmacionScreen(
                                    movimiento = movimiento,
                                    onRegistrarOtroClick = {
                                        pantallaActual = "registro"
                                    },
                                    onInicioClick = {
                                        pantallaActual = "home"
                                    }
                                )
                            } else {
                                pantallaActual = "home"
                            }
                        }

                        "registros" -> RegistrosScreen(
                            movimientoViewModel = movimientoViewModel,
                            onVolverClick = {
                                pantallaActual = "home"
                            }
                        )


                    }
                }
            }
        }
    }
}
