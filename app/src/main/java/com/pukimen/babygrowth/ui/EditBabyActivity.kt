package com.pukimen.babygrowth.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pukimen.babygrowth.R
import com.pukimen.babygrowth.databinding.ActivityEditBabyBinding

class EditBabyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBabyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBabyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi toolbar
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.topAppBar.setNavigationOnClickListener { onBackPressed() }

        // Set listener untuk tombol Edit
        binding.editButton.setOnClickListener {
            editBabyDetails()
        }
    }

    private fun editBabyDetails() {
        val name = binding.nameInput.text.toString().trim()
        val weight = binding.weightInput.text.toString().trim()
        val height = binding.heightInput.text.toString().trim()

        if (name.isEmpty() || weight.isEmpty() || height.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Lakukan operasi penyimpanan data disini
        // Contoh:
        // saveBabyDetails(name, weight, height)

        Toast.makeText(this, "Details saved successfully", Toast.LENGTH_SHORT).show()
    }

    // Metode ini dapat diimplementasikan untuk menyimpan data ke database atau shared preferences
    private fun saveBabyDetails(name: String, weight: String, height: String) {
        // Implementasi penyimpanan data
    }
}