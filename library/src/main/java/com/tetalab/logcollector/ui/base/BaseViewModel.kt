package com.tetalab.logcollector.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.tetalab.logcollector.coroutine.AppCoroutinesConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlin.coroutines.CoroutineContext

open class BaseViewModel constructor(
    application: Application,
    private val coroutineScope: CoroutineScope
) : AndroidViewModel(application), CoroutineScope by coroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = AppCoroutinesConfiguration.uiDispatcher + job

    override fun onCleared() {
        coroutineScope.coroutineContext.cancelChildren()
        super.onCleared()
    }
}