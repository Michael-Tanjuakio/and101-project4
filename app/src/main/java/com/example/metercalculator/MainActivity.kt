package com.example.metercalculator

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import com.example.metercalculator.databinding.ActivityMainBinding
import kotlin.math.abs
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Variables
        var metric1 = 3 // meters originally
        var metric2 = 0 // kilometers originally

        // Binding Code

        // activity_main.xml -> ActivitySimpleBinding
        binding = ActivityMainBinding.inflate(layoutInflater)

        // layout of activity is stored in a special property called root
        val view = binding.root
        setContentView(view)

        // Listeners

        // Exit keyboard on enter (virtual keyboard)
        // Note: android:imeOptions="actionSend" exits keyboard on enter for emulated keyboard
        binding.inputNumber.setOnKeyListener { _, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    Log.d("BUTTONS", "exit keyboard")
                    val imm = getSystemService(InputMethodManager::class.java)
                    imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                }
                false
        }

        // Get metric name
        fun getMetricName(metric: Int): String {
            when (metric) {
                0 -> return "Kilometers"
                1 -> return "Hectometers"
                2 -> return "Decameters"
                3 -> return "Meters"
                4 -> return "Decimeters"
                5 -> return "Centimeters"
                6 -> return "Millimeters"
            }
            return "null"
        }

        // Smaller button, first metric
        binding.decedit1.setOnClickListener {
            if (metric1 != 6) // biggest metric limit
                metric1++
            binding.convert1.text = getMetricName(metric1)
        }

        // Bigger button, first metric
        binding.incedit1.setOnClickListener {
            if (metric1 != 0) // smallest metric limit
                metric1--
            binding.convert1.text = getMetricName(metric1)
        }

        // Smaller button, second metric
        binding.decedit2.setOnClickListener {
            if (metric2 != 6)
                metric2++
            binding.convert2.text = getMetricName(metric2)
        }

        // Bigger button, second metric
        binding.incedit2.setOnClickListener {
            if (metric2 != 0)
                metric2--
            binding.convert2.text = getMetricName(metric2)
        }

        // Convert button
        binding.button.setOnClickListener {
                val inputNum = binding.inputNumber.text.toString() // input value
                if (inputNum.isNotEmpty() && inputNum.isDigitsOnly()) { // if input is not empty and only digits

                    // Convert metric1 to metric2
                    var convertedNum = when (true) {
                        (metric1 > metric2) -> (inputNum.toDouble() / (10.0.pow(metric1 - metric2))).toString()
                        (metric1 < metric2) -> (inputNum.toDouble() * (10.0.pow(abs(metric1 - metric2)))).toString()
                        else -> inputNum
                    }

                    Log.d("BUTTONS", "Number inputted = $inputNum")
                    binding.ConvertedNumber.text = convertedNum + " " + getMetricName(metric2)
                }
            }
        }
    }