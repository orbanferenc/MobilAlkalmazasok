package com.example.trackerapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.trackerapp.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity" // Tag for logging
    private lateinit var adapter: ExpenseAdapter
    private var expenses = mutableListOf<Expense>()

    private fun calculateTotalExpenses(expenses: List<Expense>): Float {
        return expenses.sumOf { it.amount.toDouble() }.toFloat()
    }

    private fun updateTotalExpenses(expenses: List<Expense>) {
        val totalExpensesTextView: TextView = findViewById(R.id.totalExpensesTextView)
        val total = calculateTotalExpenses(expenses)
        totalExpensesTextView.text = "Total Expenses: $${"%.2f".format(total)}"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "calling super.onCreate(savedInstanceState)")
        super.onCreate(savedInstanceState)
        Log.d(TAG, "calling setContentView(R.layout.activity_main)")
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate called")

        val expenseListView: ListView = findViewById(R.id.expenseListView)

        // Load expenses from SharedPreferences
        expenses = loadExpensesFromSharedPreferences(this).toMutableList()
        Log.d(TAG, "Loaded expenses: $expenses")

        // Initialize the adapter and set it to the ListView
        adapter = ExpenseAdapter(this, expenses)
        expenseListView.adapter = adapter

        updateTotalExpenses(expenses)
    }

    // Inflate the menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d(TAG, "onCreateOptionsMenu called")
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Handle menu item selection
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected called for item: ${item.itemId}")
        when (item.itemId) {
            R.id.action_add_expense -> {
                // Add a random expense and update SharedPreferences
                val randomExpense = generateRandomExpense()
                Log.d(TAG, "Generated random expense: $randomExpense")

                expenses.add(randomExpense)
                saveExpensesToSharedPreferences(expenses, this)

                // Notify the adapter of the data change
                adapter.notifyDataSetChanged()
                Log.d(TAG, "Updated expenses list and adapter notified")
            }
            R.id.action_clear_all -> {
                // Clear all expenses from SharedPreferences
                clearAllExpenses(this)

                expenses.clear()
                saveExpensesToSharedPreferences(expenses, this)

                // Notify the adapter of the data change
                adapter.notifyDataSetChanged()
                Log.d(TAG, "Cleared all expenses and updated the list")

                // Update the total
                updateTotalExpenses(emptyList())
            }
        }
        val currentExpenses = loadExpensesFromSharedPreferences(this).toMutableList()
        // Update the total
        updateTotalExpenses(currentExpenses)
        return super.onOptionsItemSelected(item)
    }

    // Generate a random expense
    private fun generateRandomExpense(): Expense {
        val descriptions = listOf("Groceries", "Utilities", "Transportation", "Entertainment", "Clothing")
        val categories = listOf("Food", "Bills", "Transportation", "Entertainment", "Shopping")
        val randomDescription = descriptions.random()
        val randomAmount = (10..100).random().toFloat()
        val randomCategory = categories.random()
        Log.d(TAG, "Generated random expense with description: $randomDescription, amount: $randomAmount, category: $randomCategory")
        return Expense(randomDescription, randomAmount, randomCategory)
    }

    // Clear all expenses from SharedPreferences
    private fun clearAllExpenses(context: Context) {
        Log.d(TAG, "Clearing all expenses from SharedPreferences")
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("TrackerAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("expenses")  // Remove the expenses data from SharedPreferences
        editor.apply()
    }
}

// Function to save expenses to SharedPreferences
fun saveExpensesToSharedPreferences(expenses: List<Expense>, context: Context) {
    Log.d("MainActivity", "Saving expenses to SharedPreferences: $expenses")
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("TrackerAppPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    // Convert the expenses list to a JSON string using Gson
    val gson = Gson()
    val json = gson.toJson(expenses)

    // Save the JSON string to SharedPreferences
    editor.putString("expenses", json)
    editor.apply()
}

// Function to load expenses from SharedPreferences
fun loadExpensesFromSharedPreferences(context: Context): List<Expense> {
    Log.d("MainActivity", "Loading expenses from SharedPreferences")
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("TrackerAppPrefs", Context.MODE_PRIVATE)
    val json = sharedPreferences.getString("expenses", null)

    // If no expenses found, load default sample expenses
    if (json == null) {
        Log.d("MainActivity", "No expenses found in SharedPreferences, loading default expenses")
        val defaultExpenses = listOf(
            Expense("Gaaroceries", 50.0f, "Food"),
            Expense("Utilities", 100.0f, "Bills"),
            Expense("Transportation", 30.0f, "Transportation"),
            Expense("Entertainment", 20.0f, "Entertainment"),
            Expense("Clothing", 40.0f, "Shopping")
        )
        // Save the default expenses to SharedPreferences
        saveExpensesToSharedPreferences(defaultExpenses, context)
        return defaultExpenses
    }

    val gson = Gson()
    val type = object : TypeToken<List<Expense>>() {}.type
    val expenses = gson.fromJson<List<Expense>>(json, type)
    Log.d("MainActivity", "Loaded expenses from SharedPreferences: $expenses")
    return expenses
}
