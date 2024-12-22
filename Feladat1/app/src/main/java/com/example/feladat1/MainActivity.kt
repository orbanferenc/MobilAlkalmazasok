package com.example.feladat1  // Ensure this is the correct package

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem

class MainActivity : AppCompatActivity() {
    private var redClickCount = 0
    private var greenClickCount = 0
    private lateinit var editTextRed: EditText
    private lateinit var editTextGreen: EditText
    private lateinit var buttonIncreaseRed: Button
    private lateinit var buttonIncreaseGreen: Button
    private lateinit var buttonSecondActivity: Button
    private lateinit var buttonThirdActivity: Button

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ensure the ActionBar is visible
        supportActionBar?.show()  // Explicitly show the ActionBar (it should be visible by default)

        // Optionally, you can set the title of the ActionBar
        supportActionBar?.title = "Main Activity"

        // Optionally, show the "up" button (back arrow) in the ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Ensuring we have a Toolbar if it's not custom
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize views
        editTextRed = findViewById(R.id.editTextRed)
        editTextGreen = findViewById(R.id.editTextGreen)
        buttonIncreaseRed = findViewById(R.id.buttonIncreaseRed)
        buttonIncreaseGreen = findViewById(R.id.buttonIncreaseGreen)
        buttonSecondActivity = findViewById(R.id.buttonSecondActivity)
        buttonThirdActivity = findViewById(R.id.buttonThirdActivity)

        // Set colors for text (red and green)
        editTextRed.setTextColor(resources.getColor(R.color.red))
        editTextGreen.setTextColor(resources.getColor(R.color.green))

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

        // Load saved values from SharedPreferences
        loadValues()

        // Button click listeners for increment
        buttonIncreaseRed.setOnClickListener {
            redClickCount++
            incrementValue(editTextRed)
        }

        buttonIncreaseGreen.setOnClickListener {
            greenClickCount++
            incrementValue(editTextGreen)
        }

        // Button for launching SecondActivity
        buttonSecondActivity.setOnClickListener {
            var totalClicks = redClickCount + greenClickCount
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("TOTAL_CLICKS", totalClicks)
            startActivityForResult(intent, REQUEST_CODE_SECOND_ACTIVITY)
        }
        buttonThirdActivity.setOnClickListener{
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }
    }

    // Helper method to increment value in the EditText
    private fun incrementValue(editText: EditText) {
        val currentValue = editText.text.toString().toIntOrNull() ?: 0
        editText.setText((currentValue + 1).toString())
    }

    // Load values from SharedPreferences
    private fun loadValues() {
        val redValue = sharedPreferences.getInt("redValue", 0)
        val greenValue = sharedPreferences.getInt("greenValue", 0)

        editTextRed.setText(redValue.toString())
        editTextGreen.setText(greenValue.toString())
    }

    // Save values to SharedPreferences
    private fun saveValues() {
        val editor = sharedPreferences.edit()
        editor.putInt("redValue", editTextRed.text.toString().toInt())
        editor.putInt("greenValue", editTextGreen.text.toString().toInt())
        editor.apply()
    }

    // Reset values in both EditTexts and SharedPreferences
    private fun resetValues() {
        editTextRed.setText("0")
        editTextGreen.setText("0")

        val editor = sharedPreferences.edit()
        editor.clear()  // Clear SharedPreferences
        editor.apply()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu from the XML
        menuInflater.inflate(R.menu.main_menu, menu)
        return true // return true to indicate that the menu should be shown
    }

    // Override onOptionsItemSelected to handle Save and Reset menu actions
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuSave -> {
                saveValues()
                return true
            }
            R.id.menuReset -> {
                resetValues()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val REQUEST_CODE_SECOND_ACTIVITY = 1
    }

    // Inner class for handling the first button click event
    inner class InnerClassButtonHandler : () -> Unit {
        override fun invoke() {
            // Handle first button click event here
            println("Gomb 1 clicked!")
        }
    }
}
