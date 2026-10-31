package com.iesvcarmen.practicaEvaluable

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE)
        val nombreGuardado = sharedPreferences.getString("NOMBRE_GUARDADO", "Sin nombre")
        val telefonoGuardado = sharedPreferences.getString("TELEFONO_GUARDADO", "Sin número")
        val urlGuardada = sharedPreferences.getString("URL_GUARDADA", "https://www.google.com")
        val emailGuardado = sharedPreferences.getString("EMAIL_GUARDADO", "Sin email")

        val btnLlamada = findViewById<ImageButton>(R.id.btn_llamada)
        val btnNavegador = findViewById<ImageButton>(R.id.btn_navegador)
        val btnAlarma = findViewById<ImageButton>(R.id.btn_alarma)
        val btnLibre = findViewById<ImageButton>(R.id.btn_libre)
        val btnVolver = findViewById<Button>(R.id.btn_volver_config)

        // Botón 1: Llamada (Intent Explícito pasando el nombre y teléfono)
        btnLlamada.setOnClickListener {
            val intent = Intent(this, LlamadaActivity::class.java)
            intent.putExtra("NOMBRE_A_LLAMAR", nombreGuardado)
            intent.putExtra("TELEFONO_A_LLAMAR", telefonoGuardado)
            startActivity(intent)
        }

        // Botón 2: Navegador Web (Intent Implícito ACTION_VIEW)
        btnNavegador.setOnClickListener {
            var urlFinal = urlGuardada ?: "https://www.google.com"
            if (!urlFinal.startsWith("http://") && !urlFinal.startsWith("https://")) {
                urlFinal = "http://$urlFinal"
            }

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(urlFinal)
            startActivity(intent)
        }

        // Botón 3: Alarma (Intent Implícito ACTION_SET_ALARM)
        btnAlarma.setOnClickListener {
            val calendario = Calendar.getInstance()
            calendario.add(Calendar.MINUTE, 2)

            val horaCalculada = calendario.get(Calendar.HOUR_OF_DAY)
            val minutoActual = calendario.get(Calendar.MINUTE)

            val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
                putExtra(AlarmClock.EXTRA_MESSAGE, "Alarma +2 minutos")
                putExtra(AlarmClock.EXTRA_HOUR, horaCalculada)
                putExtra(AlarmClock.EXTRA_MINUTES, minutoActual)
                putExtra(AlarmClock.EXTRA_SKIP_UI, false)
            }
            startActivity(intent)
        }

        // Botón 4: Acción Libre - Enviar Email (Intent Implícito ACTION_SENDTO)
        btnLibre.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(emailGuardado))
                putExtra(Intent.EXTRA_SUBJECT, "Práctica Tema 1 - Intent Libre")
                putExtra(Intent.EXTRA_TEXT, "Hola, esto es una prueba del botón libre.")
            }
            startActivity(intent)
        }

        // Botón 5: Volver a Configuración (Intent Explícito)
        btnVolver.setOnClickListener {
            val intent = Intent(this, ConfiguracionActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}