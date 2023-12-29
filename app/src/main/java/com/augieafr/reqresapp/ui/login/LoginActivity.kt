package com.augieafr.reqresapp.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.augieafr.reqresapp.databinding.ActivityLoginBinding
import com.augieafr.reqresapp.ui.utils.isVisible
import com.augieafr.reqresapp.ui.utils.launchCollectLatest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        initObserver()
    }

    private fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                observeFormErrorState()

                viewModel.loginUiState.collectLatest {
                    when (it) {
                        is LoginUiState.Success -> {
                            Toast.makeText(this@LoginActivity, "Login Success", Toast.LENGTH_SHORT)
                                .show()
                        }

                        is LoginUiState.Error -> {
                            Toast.makeText(this@LoginActivity, it.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                        is LoginUiState.Loading -> {
                            with(binding) {
                                progressBar.isVisible(it.isLoading)
                                btnSignIn.isEnabled = !it.isLoading
                            }
                        }
                    }
                }
            }
        }
    }

    private fun CoroutineScope.observeFormErrorState() {
        // StateFlow will never complete, we need to launch new coroutine to collect the state
        // so it doesn't cancel any other coroutine below it
        with(binding) {
            launchCollectLatest(edtEmail.errorState) {
                viewModel.setEmailErrorState(it)
            }
            launchCollectLatest(edtPassword.errorState) {
                viewModel.setPasswordErrorState(it)
            }
            launchCollectLatest(viewModel.formsErrorState) {
                btnSignIn.isEnabled = !(it.first || it.second)
            }
        }
    }

    private fun setupView() = with(binding) {
        btnSignIn.setOnClickListener {
            viewModel.login(edtEmail.text, edtPassword.text)
        }
    }
}
