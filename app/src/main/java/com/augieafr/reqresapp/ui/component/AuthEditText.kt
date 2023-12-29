package com.augieafr.reqresapp.ui.component

import android.content.Context
import android.util.AttributeSet
import android.util.Patterns
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import com.augieafr.reqresapp.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthEditText(context: Context, attributeSet: AttributeSet) :
    TextInputLayout(context, attributeSet) {

    private var authInputType = 0
    private val editText = TextInputEditText(this.context)
    var text: String
        get() = editText.text.toString()
        set(value) {
            editText.setText(value)
        }

    private val _errorState = MutableStateFlow(true)
    val errorState: StateFlow<Boolean>
        get() = _errorState

    init {
        initAttrs(attributeSet)
    }

    private fun initAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.AuthEditText
        )

        try {
            authInputType = typedArray.getInt(R.styleable.AuthEditText_auth_input_type, EMAIL_TYPE)
        } finally {
            typedArray.recycle()
        }

        setup()
    }

    private fun setup() {
        addView(editText)
        when (authInputType) {
            PASSWORD_TYPE -> {
                hint = context.getString(R.string.password)
                with(editText) {
                    inputType =
                        EditorInfo.TYPE_TEXT_VARIATION_PASSWORD or EditorInfo.TYPE_CLASS_TEXT
                    addTextChangedListener(afterTextChanged = {
                        error = if (it.toString().isEmpty()) {
                            _errorState.value = true
                            context.getString(R.string.password_can_not_be_empty)
                        } else {
                            _errorState.value = false
                            null
                        }
                    })
                }
            }

            EMAIL_TYPE -> {
                hint = context.getString(R.string.email)
                with(editText) {
                    inputType = EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                    addTextChangedListener(afterTextChanged = {
                        error =
                            if (!Patterns.EMAIL_ADDRESS.matcher(it.toString())
                                    .matches()
                            ) {
                                _errorState.value = true
                                context.getString(R.string.email_not_valid)
                            } else {
                                _errorState.value = false
                                null
                            }
                    })
                }
            }
        }
    }

    companion object {
        const val EMAIL_TYPE = 1
        const val PASSWORD_TYPE = 2
    }
}