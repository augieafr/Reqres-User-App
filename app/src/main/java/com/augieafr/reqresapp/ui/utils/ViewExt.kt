package com.augieafr.reqresapp.ui.utils

import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun View.isVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun <T> CoroutineScope.launchCollectLatest(flow: Flow<T>, action: suspend (T) -> Unit) {
    launch {
        flow.collectLatest {
            action(it)
        }
    }
}