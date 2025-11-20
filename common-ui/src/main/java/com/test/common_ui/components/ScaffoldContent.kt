package com.test.common_ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ScaffoldContent(
    isLoading: Boolean,
    error: String? = null,
    paddingValues: PaddingValues,
    content: @Composable() () -> Unit,
    onTryAgain: () -> Unit ={}
) {
    if (isLoading) {
        Box(
            Modifier
                .padding(paddingValues)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        when (error) {
            null -> {
                content()
            }

            else -> {
                TryAgainSection(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    error = error,
                    onClick = { onTryAgain() })
            }
        }
    }
}