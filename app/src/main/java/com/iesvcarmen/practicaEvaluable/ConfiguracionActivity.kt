package com.iesvcarmen.practicaEvaluable

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ConfiguracionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracion)

        val etNombre = findViewById<EditText>(R.id.et_nombre)
        val etTelefono = findViewById<EditText>(R.id.et_telefono)
        val etUrl = findViewById<EditText>(R.id.et_url)
        val etEmail = findViewById<EditText>(R.id.et_email)
        val btnGuardar = findViewById<Button>(R.id.btn_guardar_entrar)

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val telefono = etTelefono.text.toString().trim()
            val url = etUrl.text.toString().trim()
            val email = etEmail.text.toString().trim()

            if (nombre.isEmpty() || telefono.isEmpty() || url.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show()
            }

            else if (telefono.length != 9 || !telefono.all { it.isDigit() }) {
                Toast.makeText(this, "El teléfono debe tener 9 números válidos", Toast.LENGTH_SHORT).show()
            }

            else {
                val sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                editor.putString("NOMBRE_GUARDADO", nombre)
                editor.putString("TELEFONO_GUARDADO", telefono)
                editor.putString("URL_GUARDADA", url)
                editor.putString("EMAIL_GUARDADO", email)
                editor.apply()

                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        }
    }
}