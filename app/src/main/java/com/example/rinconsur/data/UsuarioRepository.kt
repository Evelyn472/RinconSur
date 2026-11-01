
package com.example.rinconsur.data

import android.content.Context
import com.example.rinconsur.model.Usuario

class UsuarioRepository(context: Context) {

    private val preferencias = context.getSharedPreferences(
        "usuarios_rincon_sur",
        Context.MODE_PRIVATE
    )

    fun registrar(usuario: Usuario): Boolean {
        if (buscarPorNombreUsuario(usuario.nombreUsuario) != null) {
            return false
        }

        preferencias.edit()
            .putString(
                usuario.nombreUsuario.trim().lowercase(),
                usuario.contraseña
            )
            .apply()

        return true
    }

    fun buscarPorNombreUsuario(nombreUsuario: String): Usuario? {
        val nombre = nombreUsuario.trim().lowercase()
        val contraseña = preferencias.getString(nombre, null)

        return if (contraseña != null) {
            Usuario(
                nombreUsuario = nombre,
                contraseña = contraseña
            )
        } else {
            null
        }
    }
}
