package com.example.feladat1  // Ensure this is the correct package

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        var totalClicks = intent.getIntExtra("TOTAL_CLICKS",0)
        var textViewClicks = findViewById<TextView>(R.id.textViewClicks)
        textViewClicks.text = "Kattintások száma: $totalClicks"

        // Initialize buttons
        val buttonOk: Button = findViewById(R.id.buttonOk)
        val buttonCancel: Button = findViewById(R.id.buttonCancel)

        // Set OnClickListener for the OK button
        buttonOk.setOnClickListener {
            // You can add your desired action here, for example, finish the activity and return a result
            setResult(RESULT_OK) // Return RESULT_OK to indicate success
            finish() // Close the activity
        }

        // Set OnClickListener for the Cancel button
        buttonCancel.setOnClickListener {
            // You can add your desired action here, for example, finish the activity with RESULT_CANCELED
            setResult(RESULT_CANCELED) // Return RESULT_CANCELED to indicate cancellation
            finish() // Close the activity
        }
    }
}
