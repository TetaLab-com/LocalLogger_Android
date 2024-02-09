package com.tetalab.logcollector.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class AppCoroutineScope constructor() : CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = AppCoroutinesConfiguration.uiDispatcher + job
}