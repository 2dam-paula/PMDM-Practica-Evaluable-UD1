package com.iesvcarmen.practicaEvaluable

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class ConfiguracionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracion)

        val etTelefono = findViewById<EditText>(R.id.et_telefono)
        val btnGuardar = findViewById<Button>(R.id.btn_guardar_entrar)

        btnGuardar.setOnClickListener {
            val telefono = etTelefono.text.toString()

            val sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("TELEFONO_GUARDADO", telefono)
            editor.apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}