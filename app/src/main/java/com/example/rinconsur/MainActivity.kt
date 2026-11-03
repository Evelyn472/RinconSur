
package com.example.rinconsur

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rinconsur.ui.screens.AdministrarUsuariosScreen
import com.example.rinconsur.ui.screens.ConfirmacionScreen
import com.example.rinconsur.ui.screens.HomeScreen
import com.example.rinconsur.ui.screens.LoginScreen
import com.example.rinconsur.ui.screens.RegistroAdministradorScreen
import com.example.rinconsur.ui.screens.RegistroScreen
import com.example.rinconsur.ui.screens.RegistroUsuarioScreen
import com.example.rinconsur.ui.screens.RegistrosScreen
import com.example.rinconsur.ui.screens.ResumenScreen
import com.example.rinconsur.viewmodel.MovimientoViewModel
import com.example.rinconsur.viewmodel.UsuarioViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val usuarioViewModel: UsuarioViewModel = viewModel()
            val movimientoViewModel: MovimientoViewModel = viewModel()

            MaterialTheme {
                Surface {
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        enterTransition = {
                            fadeIn(tween(300)) +
                                    slideInHorizontally(tween(300)) { it / 8 }
                        },
                        exitTransition = {
                            fadeOut(tween(300)) +
                                    slideOutHorizontally(tween(300)) { -it / 8 }
                        },
                        popEnterTransition = {
                            fadeIn(tween(300)) +
                                    slideInHorizontally(tween(300)) { -it / 8 }
                        },
                        popExitTransition = {
                            fadeOut(tween(300)) +
                                    slideOutHorizontally(tween(300)) { it / 8 }
                        }
                    ) {

                        composable("login") {
                            LoginScreen(
                                onLoginClick = {
                                    navController.navigate("home") {
                                        popUpTo("login") {
                                            inclusive = true
                                        }
                                    }
                                },
                                onAdminRegistroClick = {
                                    navController.navigate("registroAdministrador")
                                },
                                usuarioViewModel = usuarioViewModel
                            )
                        }

                        composable("registroAdministrador") {
                            RegistroAdministradorScreen(
                                usuarioViewModel = usuarioViewModel,
                                onVolverClick = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable("home") {
                            HomeScreen(
                                onRegistrarClick = {
                                    navController.navigate("registro")
                                },
                                onRegistrosClick = {
                                    navController.navigate("registros")
                                },
                                onResumenClick = {
                                    navController.navigate("resumen")
                                },
                                onAdministrarUsuariosClick = {
                                    if (usuarioViewModel.esAdministrador()) {
                                        navController.navigate("administrarUsuarios")
                                    }
                                },
                                onCrearTrabajadorClick = {
                                    if (usuarioViewModel.esAdministrador()) {
                                        navController.navigate("registroUsuario")
                                    }
                                },
                                onCerrarSesionClick = {
                                    usuarioViewModel.cerrarSesion()
                                    navController.navigate("login") {
                                        popUpTo("home") {
                                            inclusive = true
                                        }
                                        launchSingleTop = true
                                    }
                                },
                                usuarioViewModel = usuarioViewModel
                            )
                        }

                        composable("registroUsuario") {
                            if (usuarioViewModel.esAdministrador()) {
                                RegistroUsuarioScreen(
                                    onCrearCuentaClick = {
                                        navController.popBackStack()
                                    },
                                    onIniciarSesionClick = {
                                        navController.popBackStack()
                                    },
                                    usuarioViewModel = usuarioViewModel
                                )
                            } else {
                                LaunchedEffect(Unit) {
                                    navController.navigate("login") {
                                        popUpTo(0)
                                    }
                                }
                            }
                        }

                        composable("administrarUsuarios") {
                            if (usuarioViewModel.esAdministrador()) {
                                AdministrarUsuariosScreen(
                                    usuarioViewModel = usuarioViewModel,
                                    onVolverClick = {
                                        navController.popBackStack()
                                    }
                                )
                            } else {
                                LaunchedEffect(Unit) {
                                    navController.navigate("login") {
                                        popUpTo(0)
                                    }
                                }
                            }
                        }

                        composable("registro") {
                            RegistroScreen(
                                movimientoViewModel = movimientoViewModel,
                                onGuardarClick = {
                                    navController.navigate("confirmacion")
                                }
                            )
                        }

                        composable("confirmacion") {
                            val movimiento = movimientoViewModel.ultimoMovimiento

                            if (movimiento != null) {
                                ConfirmacionScreen(
                                    movimiento = movimiento,
                                    onRegistrarOtroClick = {
                                        navController.navigate("registro") {
                                            popUpTo("registro") {
                                                inclusive = true
                                            }
                                        }
                                    },
                                    onInicioClick = {
                                        navController.navigate("home") {
                                            popUpTo("home") {
                                                inclusive = true
                                            }
                                        }
                                    }
                                )
                            } else {
                                LaunchedEffect(Unit) {
                                    navController.navigate("home") {
                                        popUpTo("home") {
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                        }

                        composable("registros") {
                            RegistrosScreen(
                                movimientoViewModel = movimientoViewModel,
                                onVolverClick = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable("resumen") {
                            ResumenScreen(
                                movimientoViewModel = movimientoViewModel,
                                onVolverClick = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
