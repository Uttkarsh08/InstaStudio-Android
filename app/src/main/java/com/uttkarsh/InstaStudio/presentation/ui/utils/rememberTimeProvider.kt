package com.uttkarsh.InstaStudio.presentation.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.uttkarsh.InstaStudio.di.TimeProviderEntryPoint
import dagger.hilt.android.EntryPointAccessors
import com.uttkarsh.InstaStudio.utils.time.TimeProvider

@Composable
fun rememberTimeProvider(): TimeProvider {
    val context = LocalContext.current
    return EntryPointAccessors.fromApplication(
        context.applicationContext,
        TimeProviderEntryPoint::class.java
    ).getTimeProvider()
}
