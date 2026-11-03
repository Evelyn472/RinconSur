
package com.example.rinconsur.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.rinconsur.data.UsuarioRepository
import com.example.rinconsur.model.Usuario

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UsuarioRepository(application)

    var usuarioActual = ""
    var rolActual = ""

    private fun validarUsuario(nombre: String): String? {
        if (nombre.isBlank()) return "Ingresa un nombre de usuario"
        if (nombre.length < 4) return "El usuario debe tener al menos 4 caracteres"
        if (!nombre.matches(Regex("^[a-zA-Z0-9_.]+$"))) {
            return "Usa solo letras, números, punto o guion bajo"
        }
        return null
    }

    private fun validarContraseña(contraseña: String): String? {
        if (!contraseña.matches(Regex("^[0-9]{4}$"))) {
            return "La contraseña debe tener exactamente 4 números"
        }
        return null
    }

    fun registrarUsuario(
        nombreUsuario: String,
        contraseña: String,
        confirmarContraseña: String
    ): String {
        return registrarCuenta(nombreUsuario, contraseña, confirmarContraseña, "trabajador")
    }

    fun registrarAdministrador(
        nombreUsuario: String,
        contraseña: String,
        confirmarContraseña: String,
        codigo: String
    ): String {
        if (codigo != "RINCON2026") return "Código de autorización incorrecto"
        return registrarCuenta(nombreUsuario, contraseña, confirmarContraseña, "administrador")
    }

    private fun registrarCuenta(
        nombreUsuario: String,
        contraseña: String,
        confirmarContraseña: String,
        rol: String
    ): String {
        val nombre = nombreUsuario.trim()

        validarUsuario(nombre)?.let { return it }
        validarContraseña(contraseña)?.let { return it }

        if (contraseña != confirmarContraseña) {
            return "Las contraseñas no coinciden"
        }

        val usuario = Usuario(nombre, contraseña)

        return if (repository.registrar(usuario, rol)) {
            "Cuenta creada exitosamente"
        } else {
            "El nombre de usuario ya existe"
        }
    }

    fun iniciarSesion(nombreUsuario: String, contraseña: String): Boolean {
        val usuario = repository.buscarPorNombreUsuario(nombreUsuario)
        val correcto = usuario != null && usuario.contraseña == contraseña

        if (correcto) {
            usuarioActual = usuario!!.nombreUsuario
            rolActual = repository.obtenerRol(usuarioActual)
        }

        return correcto
    }

    fun esAdministrador(): Boolean = rolActual == "administrador"

    fun cerrarSesion() {
        usuarioActual = ""
        rolActual = ""
    }

    fun guardarFotoUsuario(nombreUsuario: String, ruta: String) {
        repository.guardarFotoUsuario(nombreUsuario, ruta)
    }

    fun obtenerFotoUsuario(nombreUsuario: String): String? =
        repository.obtenerFotoUsuario(nombreUsuario)

    fun obtenerUsuarios(): List<String> = repository.obtenerUsuarios()

    fun obtenerTrabajadores(): List<String> = repository.obtenerTrabajadores()

    fun eliminarUsuario(nombreUsuario: String): Boolean {
        if (!esAdministrador()) return false
        if (nombreUsuario.equals(usuarioActual, ignoreCase = true)) return false
        if (repository.obtenerRol(nombreUsuario) == "administrador") return false

        return repository.eliminarUsuario(nombreUsuario)
    }
}
