package com.example.calculator

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.lang.NumberFormatException

enum class Operator {
    Dodawanie, Odejmowanie, Mnozenie, Dzielenie, Rownasie, Puste
}

class MainActivity : AppCompatActivity() {
    private lateinit var resultEditText: TextInputEditText
    private var currentNumber: Double? = null

    private var oczekujacaOperacja = Operator.Puste

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultEditText = findViewById(R.id.resultEditText)

        findViewById<View>(R.id.addButton).setOnClickListener { wstrzymanaOperacja(Operator.Dodawanie) }
        findViewById<View>(R.id.subtractButton).setOnClickListener { wstrzymanaOperacja(Operator.Odejmowanie) }
        findViewById<View>(R.id.multiplyButton).setOnClickListener { wstrzymanaOperacja(Operator.Mnozenie) }
        findViewById<View>(R.id.divideButton).setOnClickListener { wstrzymanaOperacja(Operator.Dzielenie) }
        findViewById<View>(R.id.equalButton).setOnClickListener { wstrzymanaOperacja(Operator.Rownasie) }
        findViewById<View>(R.id.clearButton).setOnClickListener { clearCalculator() }

    }

    private fun wstrzymanaOperacja(operator: Operator) {
        val userInput = resultEditText.text.toString()

        if (userInput.isNotEmpty()) {
            try {
                val userInputAsNumber = userInput.toDouble()
                operacja(userInputAsNumber, operator)
            } catch (e: NumberFormatException) {
                resultEditText.error = "Nieprawidłowa liczba"
            }
        }
        oczekujacaOperacja = operator
    }

    private fun operacja(number: Double, operator: Operator) {
        currentNumber = if (currentNumber == null) {
            number
        } else {
            when (oczekujacaOperacja) {
                Operator.Dodawanie -> (currentNumber ?: 0.0) + number
                Operator.Odejmowanie -> (currentNumber ?: 0.0) - number
                Operator.Mnozenie -> (currentNumber ?: 0.0) * number
                Operator.Dzielenie -> if (number != 0.0) (currentNumber ?: 0.0) / number else Double.NaN

                Operator.Rownasie -> number
                Operator.Puste -> return
            }
        }

        if (operator == Operator.Rownasie) {
            resultEditText.setText(currentNumber.toString())
            currentNumber = null
            oczekujacaOperacja = Operator.Puste
        } else {
            resultEditText.setText("")
        }
    }
    private fun clearCalculator() {
        currentNumber = null
        oczekujacaOperacja = Operator.Puste
        resultEditText.setText("")
    }
}
