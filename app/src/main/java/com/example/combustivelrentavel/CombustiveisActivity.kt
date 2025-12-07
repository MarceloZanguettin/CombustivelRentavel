package com.example.combustivelrentavel

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ArrayAdapter
import android.widget.AdapterView

class CombustiveisActivity : AppCompatActivity() {

    private lateinit var lvcombustiveis: ListView

    private var EXTRA_RETURN_KEY: String = ""

    private val CONSUMO_MEDIO = mapOf(
        0 to 13.0,
        1 to 8.5
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_combustiveis)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lvcombustiveis = findViewById(R.id.fuel_list)
        val combustiveisArray: Array<String> = arrayOf("Gasolina", "Etanol")

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_activated_1,
            combustiveisArray
        )
        lvcombustiveis.adapter = adapter

        EXTRA_RETURN_KEY = intent.getStringExtra("RETURN_KEY") ?: ""
        if (EXTRA_RETURN_KEY.isEmpty()) {
           Toast.makeText(this, "Erro: Chave de retorno ausente.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        lvcombustiveis.setOnItemClickListener {
            parent, view, position, id ->

            val consumo = CONSUMO_MEDIO[position]
            if (consumo != null) {

                val returnIntent = Intent()


                returnIntent.putExtra(EXTRA_RETURN_KEY, consumo)

                setResult(RESULT_OK, returnIntent)
                finish()
            } else {
                Toast.makeText(this, "Erro: Consumo não mapeado.", Toast.LENGTH_SHORT).show()
            }

        }

    }
}