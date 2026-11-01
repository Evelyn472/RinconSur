
package com.example.rinconsur.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.rinconsur.data.UsuarioRepository
import com.example.rinconsur.model.Usuario

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UsuarioRepository(application)

    fun registrarUsuario(
        nombreUsuario: String,
        contraseña: String,
        confirmarContraseña: String
    ): String {
        val nombre = nombreUsuario.trim()

        if (nombre.isBlank()) {
            return "Ingresa un nombre de usuario"
        }

        if (contraseña.isBlank()) {
            return "Ingresa una contraseña"
        }

        if (contraseña != confirmarContraseña) {
            return "Las contraseñas no coinciden"
        }

        val usuario = Usuario(
            nombreUsuario = nombre,
            contraseña = contraseña
        )

        return if (repository.registrar(usuario)) {
            "Cuenta creada exitosamente"
        } else {
            "El nombre de usuario ya existe"
        }
    }

    fun iniciarSesion(
        nombreUsuario: String,
        contraseña: String
    ): Boolean {
        val usuario = repository.buscarPorNombreUsuario(nombreUsuario)

        return usuario != null && usuario.contraseña == contraseña
    }
}
