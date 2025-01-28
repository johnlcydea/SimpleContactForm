package com.example.simplecontactform

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI components
        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPhone = findViewById<EditText>(R.id.editTextPhone)
        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)

        // Handle "Done" action on phone number field
        editTextPhone.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
                true
            } else {
                false
            }
        }

        // Handle form submission
        buttonSubmit.setOnClickListener {
            // Get user input
            val name = editTextName.text.toString().trim()
            val email = editTextEmail.text.toString().trim()
            val phone = editTextPhone.text.toString().trim()

            // Validate input
            if (!validateInputs(name, email, phone)) {
                return@setOnClickListener
            }

            // Hide keyboard when form is submitted
            hideKeyboard()

            // Show confirmation message
            Toast.makeText(this, "Submitted: $name, $email, $phone", Toast.LENGTH_LONG).show()
        }
    }

    // Function to validate inputs
    private fun validateInputs(name: String, email: String, phone: String): Boolean {
        return when {
            name.isEmpty() || email.isEmpty() || phone.isEmpty() -> {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
                false
            }
            phone.length < 10 || phone.length > 15 || !phone.all { it.isDigit() } -> {
                Toast.makeText(this, "Enter a valid phone number", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    // Function to hide the keyboard
    private fun hideKeyboard() {
        val view = currentFocus
        view?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
