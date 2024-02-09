package com.tetalab.logcollector.sample

import android.app.Application
import com.tetalab.logcollector.ui.base.BaseLogViewModel
import kotlinx.coroutines.CoroutineScope

class SampleViewModel constructor(
    application: Application,
    coroutineScope: CoroutineScope
) : BaseLogViewModel(application, coroutineScope) {

    init {
        createNewLogSession()
    }
}