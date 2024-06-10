package com.pukimen.babygrowth.ui


import android.R
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.jaredrummler.materialspinner.MaterialSpinner
import com.pukimen.babygrowth.databinding.ActivityInputBabyBinding
import java.util.Calendar

//
//class InputBabyActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityInputBabyBinding
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityInputBabyBinding.inflate(layoutInflater)
//        val view = binding.root
//        setContentView(view)
//
//
//        val genderOptions = arrayOf("Boy", "Girl")
//        val spinner: Spinner = findViewById(R.id.genderInput)
//        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spinner.adapter = adapter
//
//
//        binding.dateInput.setOnClickListener {
//            datePicker()
//        }
//
//        binding.inputButton.setOnClickListener {
//            getStarted()
//        }
//    }
//
//    private fun datePicker() {
//        val calendar = Calendar.getInstance()
//        val year = calendar.get(Calendar.YEAR)
//        val month = calendar.get(Calendar.MONTH)
//        val day = calendar.get(Calendar.DAY_OF_MONTH)
//
//        val datePicker = DatePickerDialog(
//            this,
//            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
//                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
//                binding.dateInput.setText(selectedDate)
//            },
//            year, month, day
//        )
//        datePicker.show()
//    }
//
//    private fun getStarted() {
//        val name = binding.nameInput.text.toString().trim()
//        val dateOfBirth = binding.dateInput.text.toString().trim()
//        val weight = binding.weightInput.text.toString().trim()
//        val height = binding.heightInput.text.toString().trim()
//        val gender = binding.genderInput.text.toString().trim()
//
//        if (name.isEmpty() || dateOfBirth.isEmpty() || weight.isEmpty() || height.isEmpty() || gender.isEmpty()) {
//            if (name.isEmpty()) {
//                binding.nameInput.error = "Name is required"
//                binding.nameInput.requestFocus()
//            }
//            if (dateOfBirth.isEmpty()) {
//                binding.dateInput.error = "Date of birth is required"
//                binding.dateInput.requestFocus()
//            }
//            if (weight.isEmpty()) {
//                binding.weightInput.error = "Weight is required"
//                binding.weightInput.requestFocus()
//            }
//            if (height.isEmpty()) {
//                binding.heightInput.error = "Height is required"
//                binding.heightInput.requestFocus()
//            }
//            if (gender.isEmpty()) {
//                binding.genderInput.error = "Gender is required"
//                binding.genderInput.requestFocus()
//            }
//            return
//        }
//
//
//        binding.progressBar.visibility = View.VISIBLE
//
//
//        binding.progressBar.visibility = View.GONE
//
//    }
//
//
//}


class InputBabyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputBabyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBabyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val genderOptions = arrayOf("Boy", "Girl")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.genderInput.adapter = adapter



        binding.dateInput.setOnClickListener {
            datePicker()
        }

        binding.inputButton.setOnClickListener {
            getStarted()
        }
    }

    private fun datePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(
            this,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.dateInput.setText(selectedDate)
            },
            year, month, day
        )
        datePicker.show()
    }

    private fun getStarted() {
        val name = binding.nameInput.text.toString().trim()
        val dateOfBirth = binding.dateInput.text.toString().trim()
        val weight = binding.weightInput.text.toString().trim()
        val height = binding.heightInput.text.toString().trim()
        val gender = binding.genderInput.selectedItem.toString().trim()

        if (name.isEmpty() || dateOfBirth.isEmpty() || weight.isEmpty() || height.isEmpty() || gender.isEmpty()) {
            if (name.isEmpty()) {
                Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show()
            }
            if (dateOfBirth.isEmpty()) {
                Toast.makeText(this, "Date of birth is required", Toast.LENGTH_SHORT).show()
            }
            if (weight.isEmpty()) {
                Toast.makeText(this, "Weight is required", Toast.LENGTH_SHORT).show()
            }
            if (height.isEmpty()) {
                Toast.makeText(this, "Height is required", Toast.LENGTH_SHORT).show()
            }
            if (gender.isEmpty()) {
                Toast.makeText(this, "Gender is required", Toast.LENGTH_SHORT).show()
            }
            return
        }

        binding.progressBar.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }
}