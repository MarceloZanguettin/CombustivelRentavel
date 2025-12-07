package com.example.combustivelrentavel

import com.example.combustivelrentavel.util.CustoRentabilidade
import com.example.combustivelrentavel.util.RentavelUtil

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RentavelActivity : AppCompatActivity() {

    private lateinit var etConsumoCombustivel1: EditText
    private lateinit var etConsumoCombustivel2: EditText
    private lateinit var etPrecoComb1: EditText
    private lateinit var etPrecoComb2: EditText
    private lateinit var etResult: EditText

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_rentavel)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        etConsumoCombustivel1 = findViewById(R.id.etConsumoCombustivel1)
        etConsumoCombustivel2 = findViewById(R.id.etConsumoCombustivel2)
        etPrecoComb1 = findViewById(R.id.etPrecoComb1)
        etPrecoComb2 = findViewById(R.id.etPrecoComb2)
        etResult = findViewById(R.id.etResult)

        etConsumoCombustivel1.setOnClickListener {
            iniciarBusca(1)
        }

        etConsumoCombustivel2.setOnClickListener {
            iniciarBusca(2)
        }
    }

    private fun iniciarBusca(combustivelNumero: Int) {
        val intent = Intent(this, CombustiveisActivity::class.java)

        val returnKey = when (combustivelNumero) {
            1 -> "consumoCombustivel1"
            2 -> "consumoCombustivel2"
            else -> return
        }

        intent.putExtra("RETURN_KEY", returnKey)

        if (combustivelNumero == 1) {
            getResult1.launch(intent)
        } else {
            getResult2.launch(intent)
        }
    }


    private val getResult1 = registerForActivityResult(

        ActivityResultContracts.StartActivityForResult() ) {it ->

        if (it.resultCode == RESULT_OK) {

            val consumo1 = it.data?.getDoubleExtra("consumoCombustivel1", 0.0)

            etConsumoCombustivel1.setText(consumo1.toString())

        }

    }

    private val getResult2 = registerForActivityResult(

        ActivityResultContracts.StartActivityForResult() ) { it ->

        if (it.resultCode == RESULT_OK) {

            val consumo2 = it.data?.getDoubleExtra("consumoCombustivel2", 0.0)

            etConsumoCombustivel2.setText(consumo2.toString())

        }
    }

    fun calcularRentabilidadeOnClick(view: View) {

        val consumoComb1Str = etConsumoCombustivel1.text.toString()
        val consumoComb2Str = etConsumoCombustivel2.text.toString()
        val precoComb1Str = etPrecoComb1.text.toString()
        val precoComb2Str = etPrecoComb2.text.toString()


        try {
            val consumoComb1 = consumoComb1Str.toDouble()
            val consumoComb2 = consumoComb2Str.toDouble()
            val precoComb1 = precoComb1Str.toDouble()
            val precoComb2 = precoComb2Str.toDouble()


            val resultado: CustoRentabilidade = RentavelUtil(
                consumoComb1,
                consumoComb2,
                precoComb1,
                precoComb2
            )


            exibirResultado(resultado)

        } catch (e: NumberFormatException) {

            Toast.makeText(this, "Por favor, preencha todos os campos com valores válidos.", Toast.LENGTH_LONG).show()
            etResult.setText("")
        }
    }


    private fun exibirResultado(resultado: CustoRentabilidade) {
        val custoKm1 = resultado.custoPorKmComb1
        val custoKm2 = resultado.custoPorKmComb2


        if (custoKm1.isInfinite() || custoKm2.isInfinite()) {
            etResult.setText("Erro: Consumo não pode ser zero.")
            return
        }


        val custo1Formatado = "R$ %.2f".format(custoKm1)
        val custo2Formatado = "R$ %.2f".format(custoKm2)

        val mensagem: String

        if (custoKm1 < custoKm2) {

            mensagem = "Combustível 1 é mais rentável!\n(Custo por Km: $custo1Formatado)"
        } else if (custoKm2 < custoKm1) {

            mensagem = "Combustível 2 é mais rentável!\n(Custo por Km: $custo2Formatado)"
        } else {

            mensagem = "Ambos os combustíveis têm o mesmo custo por Km ($custo1Formatado)."
        }

        etResult.setText(mensagem)
    }

}