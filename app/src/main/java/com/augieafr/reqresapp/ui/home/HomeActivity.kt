package com.augieafr.reqresapp.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.augieafr.reqresapp.R
import com.augieafr.reqresapp.data.utils.ReqresException
import com.augieafr.reqresapp.databinding.ActivityHomeBinding
import com.augieafr.reqresapp.ui.login.LoginActivity
import com.augieafr.reqresapp.ui.utils.Alert
import com.augieafr.reqresapp.ui.utils.AlertType
import com.augieafr.reqresapp.ui.utils.isVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var pagingAdapter: ListUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()
        initObserver()
    }

    private fun initUi() {
        binding.imgLogout.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        initAdapter()
    }

    private fun initAdapter() = with(binding) {
        pagingAdapter = ListUserAdapter()
        pagingAdapter.addLoadStateListener {
            when (val refreshState = it.refresh) {
                is LoadState.Error -> Alert.showAlert(
                    this@HomeActivity,
                    AlertType.ERROR,
                    refreshState.error.getMessageByType(this@HomeActivity)
                )

                LoadState.Loading -> progressBar.isVisible(true)
                is LoadState.NotLoading -> progressBar.isVisible(false)
            }
        }

        rvUser.layoutManager = LinearLayoutManager(this@HomeActivity)
        rvUser.adapter = pagingAdapter
    }

    private fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getUsers().collect {
                    pagingAdapter.submitData(lifecycle, it)
                }
            }
        }
    }
}

private fun Throwable.getMessageByType(context: Context) = when (this) {
    is ReqresException.EmptyResultException -> context.getString(R.string.no_user_found)
    is ReqresException.UnexpectedError -> context.getString(R.string.something_wrong)
    else -> this.message ?: context.getString(R.string.something_wrong)
}

