package com.example.combustivelrentavel

import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CombustiveisActivity : AppCompatActivity() {

    private lateinit var lvcombustiveis: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_combustiveis)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lvcombustiveis = findViewById(R.id.lvCombustiveis)

        lvcombustiveis.setOnClickListener {
            parent, view, position, id ->
            val cod: Int = position + 1
            intent.putExtra("cod", cod)
            setResult(RESULT_OK, intent)
            finish()
        }

    }
}