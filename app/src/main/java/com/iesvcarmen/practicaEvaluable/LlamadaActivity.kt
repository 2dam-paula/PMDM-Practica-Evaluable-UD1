package com.iesvcarmen.practicaEvaluable

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class LlamadaActivity : AppCompatActivity() {
    private val CODIGO_PERMISO = 1
    private var telefonoDestino = ""
    private var nombreDestino = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_llamada)

        nombreDestino = intent.getStringExtra("NOMBRE_A_LLAMAR") ?: "Sin nombre"
        telefonoDestino = intent.getStringExtra("TELEFONO_A_LLAMAR") ?: "Sin número"

        val btnLLamar = findViewById<ImageButton>(R.id.coger_llamada)
        val btnVolver = findViewById<ImageButton>(R.id.colgar_volver)
        val tvNombre = findViewById<TextView>(R.id.nombre)
        val tvTelefono = findViewById<TextView>(R.id.num_telefono)

        tvNombre.text = nombreDestino
        tvTelefono.text = telefonoDestino

        btnLLamar.setOnClickListener {
            realizarLlamada()
        }

        btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun realizarLlamada() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), CODIGO_PERMISO)
            else
                lanzarIntentLlamada()
        } else
            lanzarIntentLlamada()
    }

    private fun lanzarIntentLlamada() {
        val intentLLamada = Intent(Intent.ACTION_DIAL)
        intentLLamada.data = Uri.parse("tel:$telefonoDestino")
        startActivity(intentLLamada)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CODIGO_PERMISO){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                lanzarIntentLlamada()
            else
                Toast.makeText(this, "Permiso denegado para llamar", Toast.LENGTH_SHORT).show()
        }
    }
}