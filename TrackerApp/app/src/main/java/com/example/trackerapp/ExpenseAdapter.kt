package com.example.trackerapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ExpenseAdapter(private val context: Context, private val expenses: List<Expense>) : BaseAdapter() {

    override fun getCount(): Int {
        return expenses.size
    }

    override fun getItem(position: Int): Any {
        return expenses[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_expense, parent, false)

        val expense = getItem(position) as Expense

        val descriptionTextView: TextView = view.findViewById(R.id.descriptionTextView)
        val amountTextView: TextView = view.findViewById(R.id.amountTextView)
        val categoryTextView: TextView = view.findViewById(R.id.categoryTextView)

        descriptionTextView.text = expense.description
        amountTextView.text = expense.amount.toString()
        categoryTextView.text = expense.category

        return view
    }
}
