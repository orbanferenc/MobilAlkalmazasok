package com.example.feladat1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment

class BlankFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_blank, container, false)

        // Set up a ListView
        val listView = rootView.findViewById<ListView>(R.id.listView)
        val items = listOf("Hetfo", "Hedd","Szerda", "Csutortok", "Pentek", "Szobmat", "Vasarnap")  // Sample list
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)
        listView.adapter = adapter

        // Item click listener
        listView.setOnItemClickListener { parent, view, position, id ->
            // Handle item click: Display item text in a TextView in the activity
            val selectedItem = items[position]
            val textView = activity?.findViewById<TextView>(R.id.textViewSelectedItem)
            textView?.text = selectedItem
        }

        return rootView
    }
}
