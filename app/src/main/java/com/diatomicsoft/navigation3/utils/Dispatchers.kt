package com.diatomicsoft.navigation3.utils

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(
    val dispatcher: AppDispatchers,
)

enum class AppDispatchers { IO, DEFAULT, MAIN, MAIN_IMMEDIATE }
