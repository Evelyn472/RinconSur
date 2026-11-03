
package com.example.rinconsur.data

import android.content.Context
import com.example.rinconsur.model.Usuario
import java.io.File

class UsuarioRepository(context: Context) {

    private val preferencias =
        context.getSharedPreferences("usuarios_rincon_sur", Context.MODE_PRIVATE)

    fun registrar(usuario: Usuario, rol: String = "trabajador"): Boolean {
        val nombre = usuario.nombreUsuario.trim().lowercase()

        if (buscarPorNombreUsuario(nombre) != null) return false

        preferencias.edit()
            .putString(nombre, usuario.contraseña)
            .putString("rol_$nombre", rol)
            .apply()

        return true
    }

    fun buscarPorNombreUsuario(nombreUsuario: String): Usuario? {
        val nombre = nombreUsuario.trim().lowercase()
        val contraseña = preferencias.getString(nombre, null)

        return if (contraseña != null) {
            Usuario(nombreUsuario = nombre, contraseña = contraseña)
        } else {
            null
        }
    }

    fun obtenerRol(nombreUsuario: String): String {
        return preferencias.getString(
            "rol_${nombreUsuario.trim().lowercase()}",
            "trabajador"
        ) ?: "trabajador"
    }

    fun guardarFotoUsuario(nombreUsuario: String, ruta: String) {
        preferencias.edit()
            .putString("foto_${nombreUsuario.trim().lowercase()}", ruta)
            .apply()
    }

    fun obtenerFotoUsuario(nombreUsuario: String): String? {
        return preferencias.getString(
            "foto_${nombreUsuario.trim().lowercase()}",
            null
        )
    }

    fun obtenerUsuarios(): List<String> {
        return preferencias.all.keys
            .filter { !it.startsWith("foto_") && !it.startsWith("rol_") }
            .filter { preferencias.getString(it, null) != null }
            .sorted()
    }

    fun obtenerTrabajadores(): List<String> {
        return obtenerUsuarios().filter {
            obtenerRol(it) == "trabajador"
        }
    }

    fun eliminarUsuario(nombreUsuario: String): Boolean {
        val nombre = nombreUsuario.trim().lowercase()
        val rutaFoto = obtenerFotoUsuario(nombre)

        val eliminado = preferencias.edit()
            .remove(nombre)
            .remove("rol_$nombre")
            .remove("foto_$nombre")
            .commit()

        if (eliminado && rutaFoto != null) {
            File(rutaFoto).delete()
        }

        return eliminado
    }
}
