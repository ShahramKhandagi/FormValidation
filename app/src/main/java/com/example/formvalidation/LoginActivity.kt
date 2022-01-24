package com.example.formvalidation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.formvalidation.databinding.ActivityLoginBinding
import com.example.formvalidation.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    var message = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        passwordFocusListener()
        usernameFocusListener()

        binding.btnLogin.setOnClickListener { submitForm() }

    }

    private fun submitForm() {
        binding.passwordContainer.helperText = validPassword()
        binding.usernameContainer.helperText = validUsername()

        val validPassword = binding.passwordContainer.helperText == null
        val validUsername = binding.usernameContainer.helperText == null

        if (validPassword && validUsername)
            resetForm()
        else
            invalidForm()
    }

    private fun invalidForm() {
        if (binding.passwordContainer.helperText != null)
            message += "\n\nPassword: " + binding.passwordContainer.helperText
        if (binding.usernameContainer.helperText != null)
            message += "\n\nUsername: " + binding.usernameContainer.helperText

        AlertDialog.Builder(this)
            .setTitle("Invalid Form")
            .setMessage(message)
            .setPositiveButton("Okay") { _, _ ->
            }
            .show()
    }

    private fun resetForm() {
        message += "\nPassword: " + binding.passwordEditText.text
        message += "\nUsername: " + binding.usernameEditText.text
        AlertDialog.Builder(this)
            .setTitle("Form submitted")
            .setMessage(message)
            .setPositiveButton("Okay") { _, _ ->
                binding.passwordEditText.text = null
                binding.usernameEditText.text = null

                binding.passwordContainer.helperText = getString(R.string.required)
                binding.usernameContainer.helperText = getString(R.string.required)
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
            .show()
    }


    private fun passwordFocusListener() {
        binding.passwordEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.passwordContainer.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): String? {
        val passwordText = binding.passwordEditText.text.toString()
        if (passwordText.length < 8) {
            return "حداقل رمز عبور 8 کاراکتری"
        }
        if (!passwordText.matches(".*[A-Z].*".toRegex())) {
            return "باید دارای 1 نویسه با حروف بزرگ باشد"
        }
        if (!passwordText.matches(".*[a-z].*".toRegex())) {
            return "باید دارای 1 نویسه با حروف کوچک باشد"
        }
        if (!passwordText.matches(".*[@#\$%^&+=].*".toRegex())) {
            return "باید دارای 1 نویسه خاص باشد (@#\$%^&+=)"
        }

        return null
    }

    private fun usernameFocusListener() {
        binding.usernameEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.usernameContainer.helperText = validUsername()
            }
        }
    }

    private fun validUsername(): String? {
        val usernameText = binding.usernameEditText.text.toString()
        if (usernameText.length < 3) {
            return "نام کاربری حداقل باید 3 کاراکتر باشد!"
        }
        return null
    }
}