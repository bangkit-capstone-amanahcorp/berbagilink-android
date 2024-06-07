package com.example.ptamanah.view.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.widget.Toast
import com.example.ptamanah.R
import com.google.android.material.textfield.TextInputEditText

class EditTextEmail @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextInputEditText(context, attrs) {

    init {

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(s.toString().isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(s).matches()){
                    setError(resources.getString(R.string.error_email))
                }else{
                    error = null
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })
    }
}

class EditTextPassword @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextInputEditText(context, attrs) {

    init {

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s != null && s.length < 8) {
                    setError(resources.getString(R.string.error_password_8))
                } else if (!s.matches(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+\$"))) {
                    setError(resources.getString(R.string.error_password_requirements))
                } else {
                    error = null
                }
            }

            override fun afterTextChanged(s: Editable) {
                if (s.length >= 8 && s.matches(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+\$"))) {
                    // Tampilkan pesan bahwa password telah memenuhi kriteria
                    Toast.makeText(context, resources.getString(R.string.password_meets_criteria), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}

class EditTextPasswordConfirm @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextInputEditText(context, attrs) {

    var passwordField: EditTextPassword? = null

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s != null && s.length < 8) {
                    setError(resources.getString(R.string.error_password_8))
                } else if (!s.matches(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+\$"))) {
                    setError(resources.getString(R.string.error_password_requirements))
                } else {
                    error = null
                }
            }

            override fun afterTextChanged(s: Editable) {
                val password = passwordField?.text.toString()
                if (s.length >= 8 && s.matches(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+\$")) && s.toString() == password) {
                    // Tampilkan pesan bahwa password telah memenuhi kriteria
                    Toast.makeText(context, resources.getString(R.string.password_matches), Toast.LENGTH_SHORT).show()
                } else if (s.toString() != password) {
                    setError(resources.getString(R.string.error_password_mismatch))
                }
            }
        })
    }
}