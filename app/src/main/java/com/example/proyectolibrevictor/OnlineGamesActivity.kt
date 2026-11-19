package com.example.proyectolibrevictor

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class OnlineGamesActivity : AppCompatActivity() {

    private lateinit var pbCargando: ProgressBar
    private lateinit var tvError: TextView
    private lateinit var rvOfertas: RecyclerView
    private lateinit var adapter: JuegoOfertaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online_games)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        pbCargando = findViewById(R.id.pbCargandoOnline)
        tvError = findViewById(R.id.tvEstadoErrorOnline)
        rvOfertas = findViewById(R.id.rvOfertasOnline)
        val btnVolver = findViewById<Button>(R.id.btnVolverOnline)

        adapter = JuegoOfertaAdapter(emptyList()) { juegoSeleccionado ->
            Toast.makeText(
                this,
                "${juegoSeleccionado.title} - $${juegoSeleccionado.salePrice}",
                Toast.LENGTH_SHORT
            ).show()
        }

        rvOfertas.layoutManager = LinearLayoutManager(this)
        rvOfertas.adapter = adapter

        btnVolver.setOnClickListener {
            finish()
        }

        cargarOfertasDesdeApi()
    }

    private fun cargarOfertasDesdeApi() {
        pbCargando.visibility = View.VISIBLE
        tvError.visibility = View.GONE
        rvOfertas.visibility = View.GONE

        lifecycleScope.launch {
            try {
                val resultado = RetrofitClient.apiService.obtenerOfertas()
                pbCargando.visibility = View.GONE

                if (resultado.isNotEmpty()) {
                    adapter.actualizarLista(resultado)
                    rvOfertas.visibility = View.VISIBLE
                } else {
                    tvError.text = "No se encontraron ofertas disponibles en este momento."
                    tvError.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                android.util.Log.e("ONLINE_ERROR", "Fallo en consulta CheapShark", e)
                pbCargando.visibility = View.GONE
                tvError.text = "No se han podido cargar los datos. Comprueba tu conexión a Internet."
                tvError.visibility = View.VISIBLE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}