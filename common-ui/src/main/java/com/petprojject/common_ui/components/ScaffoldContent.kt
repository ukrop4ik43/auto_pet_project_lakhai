package com.petprojject.common_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.petprojject.common_ui.theme.AutoPetProjectLakhaiTheme

@Composable
fun ScaffoldContent(
    isLoading: Boolean,
    error: String? = null,
    paddingValues: PaddingValues,
    content: @Composable() () -> Unit,
    onTryAgain: () -> Unit = {}
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

@Composable
@Preview
private fun ScaffoldContentPreview() {
    AutoPetProjectLakhaiTheme {
        ScaffoldContent(
            isLoading = false,
            error = null,
            paddingValues = PaddingValues(1.dp),
            content = {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Blue),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Some text content")
                }
            })
    }
}
