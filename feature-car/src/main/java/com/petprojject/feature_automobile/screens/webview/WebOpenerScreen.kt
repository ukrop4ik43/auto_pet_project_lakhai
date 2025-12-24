package com.petprojject.feature_automobile.screens.webview

import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.petprojject.common_ui.theme.AutoPetProjectLakhaiTheme

@Composable
fun WebOpenerScreen(
    state: WebOpenerContract.UiState
) {
    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(), factory = {
            WebView(it).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true

                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                this.webChromeClient = WebChromeClient()
                this.webViewClient = WebViewClient()
            }
        }, update = {
            it.loadUrl(state.url)
        })
}

@Composable
@Preview
private fun WebOpenerScreenPreview() {
    AutoPetProjectLakhaiTheme {
        WebOpenerScreen(state = WebOpenerContract.UiState())
    }
}