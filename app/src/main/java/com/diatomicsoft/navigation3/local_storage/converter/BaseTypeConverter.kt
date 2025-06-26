package com.diatomicsoft.navigation3.local_storage.converter

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

abstract class BaseTypeConverter {
    protected val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
}