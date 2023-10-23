package com.caloomboom.calculadora_ars

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.caloomboom.calculadora_ars.databinding.ActivityFormulaBinding

class FormulaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormulaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recuperar la fórmula seleccionada del Intent
        val formulaSeleccionada = intent.getStringExtra("formula")

        configureUIForFormula(formulaSeleccionada)

        // Mostrar la fórmula seleccionada en el TextView usando ViewBinding
        binding.formulaTextView.text = formulaSeleccionada

        // Agregar lógica para calcular la fórmula según la selección
        binding.calculateButton.setOnClickListener {
            // Obtener valores ingresados por el usuario desde los EditText usando ViewBinding
            val value1 = binding.coefa.text.toString()
            val value2 = binding.coefb.text.toString()
            val value3 = binding.coefc.text.toString()

            // Verificar que los valores sean números válidos
            if (value1.isNotEmpty() && value2.isNotEmpty()) {
                val resultado = calcularFormula(formulaSeleccionada, value1.toDouble(), value2.toDouble(), value3.toDouble())

                if (resultado.isNotEmpty()) {
                    if (formulaSeleccionada==getString(R.string.formCuadra)){
                        binding.resultado.text = "${getString(R.string.raices)}: ${resultado[0]} y ${resultado[1]}"
                    }else{
                        binding.resultado.text = "${getString(R.string.res)}: ${resultado[0]}"
                    }

                } else {
                    if (formulaSeleccionada==getString(R.string.formCuadra)){
                        binding.resultado.text = getString(R.string.errorReales)
                    }else{
                        if (formulaSeleccionada==getString(R.string.formPita)){
                            binding.resultado.text = getString(R.string.errorPosit)
                        }else{
                            binding.resultado.text = getString(R.string.errorPosit)
                        }

                    }

                }
            } else {
                binding.resultado.text = getString(R.string.ingreseTC)
            }
        }

        binding.selec.setOnClickListener {
            val intent = Intent(this@FormulaActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private lateinit var binding: ActivityFormulaBinding

    private fun configureUIForFormula(formula: String?) {
        val context = this // Obtén el contexto de la actividad
        when (formula) {
            context.getString(R.string.formEPC), context.getString(R.string.formCuadra),context.getString(R.string.formPita)-> {
                binding.coefa.visibility = View.VISIBLE
                binding.coefb.visibility = View.VISIBLE
                binding.coefc.visibility = View.VISIBLE

                when (formula) {
                    context.getString(R.string.formCuadra) -> {
                        binding.valueOneLabel.text = resources.getString(R.string.coeficienteA)
                        binding.valueTwoLabel.text = resources.getString(R.string.coeficienteB)
                        binding.valueThreeLabel.text = resources.getString(R.string.coeficienteC)
                    }
                    context.getString(R.string.formPita) -> {
                        binding.valueOneLabel.text = resources.getString(R.string.catetoA)
                        binding.valueTwoLabel.text = resources.getString(R.string.catetoB)
                        binding.valueThreeLabel.visibility = View.INVISIBLE
                        binding.coefc.visibility = View.INVISIBLE
                    }
                    context.getString(R.string.formEPC) -> {
                        binding.valueOneLabel.text = resources.getString(R.string.masa)
                        binding.valueTwoLabel.text = resources.getString(R.string.gravedad)
                        binding.valueThreeLabel.text = resources.getString(R.string.altura)
                    }
                }
            }
            else -> {
                binding.coefa.visibility = View.INVISIBLE
                binding.coefb.visibility = View.INVISIBLE
                binding.coefc.visibility = View.INVISIBLE
            }
        }
    }

    private fun calcularFormula(formula: String?, value1: Double, value2: Double, value3: Double): List<Double> {
        val context = this // Obtén el contexto de la actividad
        when (formula) {
            context.getString(R.string.formCuadra) -> {
                val discriminante = value2 * value2 - 4 * value1 * value3
                if (discriminante >= 0) {
                    val raiz1 = (-value2 + Math.sqrt(discriminante)) / (2 * value1)
                    val raiz2 = (-value2 - Math.sqrt(discriminante)) / (2 * value1)
                    return listOf(raiz1, raiz2)
                } else {
                    return emptyList()
                }
            }
            context.getString(R.string.formPita) -> {
                if (value1 > 0 && value2 > 0) {
                    val hipotenusa = Math.sqrt(value1 * value1 + value2 * value2)
                    return listOf(hipotenusa)
                } else {
                    return emptyList()
                }
            }
            context.getString(R.string.formEPC) -> {
                if (value1 >= 0 && value2 >= 0 && value3 >= 0) {
                    val energiaPotencial = value1 * value2 * value3
                    return listOf(energiaPotencial)
                } else {
                    return emptyList()
                }
            }
            else -> {
                return emptyList()
            }
        }
    }
}
