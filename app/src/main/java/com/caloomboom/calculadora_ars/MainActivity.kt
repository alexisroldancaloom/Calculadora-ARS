package com.caloomboom.calculadora_ars

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.caloomboom.calculadora_ars.databinding.ActivityMainBinding
import android.content.Intent
import android.view.View
import android.widget.AdapterView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var formulaSeleccionada: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Adaptador para el Spinner con las opciones
        val opciones = resources.getStringArray(R.array.formulas)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        // Agregar un Listener al Spinner para detectar selecciones
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                formulaSeleccionada = opciones[position]
                updateFormulaImage(formulaSeleccionada)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //
            }
        }
        //Comentario Prueba

        // Agregar un OnClickListener al botón
        binding.button.setOnClickListener {

            if (formulaSeleccionada != getString(R.string.selec)) {
                val intent = Intent(this@MainActivity, FormulaActivity::class.java)
                intent.putExtra("formula", formulaSeleccionada)
                startActivity(intent)
            } else {
                // El usuario no ha seleccionado una opción válida en el Spinner
                Toast.makeText(this@MainActivity, getString(R.string.selecOV), Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun updateFormulaImage(selectedFormula: String) {
        val context=this
        when (selectedFormula) {
            context.getString(R.string.formCuadra) -> {
                binding.formulaImagen.setImageResource(R.drawable.cuadratica)
            }

            context.getString(R.string.formPita) -> {
                binding.formulaImagen.setImageResource(R.drawable.pitagoras)
            }

            context.getString(R.string.formEPC) -> {
                binding.formulaImagen.setImageResource(R.drawable.energiapotencialgravitatoria)
            }
            else -> {
                // Puedes manejar otros casos o dejar la imagen como está
                //binding.formulaImagen.setImageResource(R.drawable.default_image)
            }
        }
    }
}
