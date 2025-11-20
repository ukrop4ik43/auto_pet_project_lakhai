package com.test.feature_automobile.screens.models

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.common_ui.R
import com.test.common_ui.components.ScaffoldContent
import com.test.common_ui.modifiers.clickableNoIndication

@Composable
fun ModelsScreen(
    uiState: ModelsContract.UiState,
    onAction: (ModelsContract.UiAction) -> Unit,
) {
    Scaffold(
        Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .systemBarsPadding(),
        topBar = {
            Box(Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier

                        .align(Alignment.Center),
                    text = "Choose model:",
                    style = TextStyle(fontSize = 32.sp)
                )
                Icon(
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.CenterStart)
                        .clickableNoIndication {
                            onAction(ModelsContract.UiAction.OnBackClick)
                        },
                    painter = painterResource(R.drawable.arrow_back),
                    tint = Color.Black,
                    contentDescription = null
                )
            }
        }
    ) { padding ->
        ScaffoldContent(
            isLoading = uiState.isLoading, error =
                uiState.error, paddingValues = padding, onTryAgain = {
                onAction(ModelsContract.UiAction.TryAgain)
            }, content = {
                LazyColumn(
                    modifier = Modifier.padding(padding),
                ) {
                    item {
                        Card(
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .padding(vertical = 6.dp)
                                .fillMaxWidth(),
                            border = BorderStroke(2.dp, Color.Gray),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
                        ) {
                            BasicText(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                text = "Manufacturer: ${uiState.manufacturer.second}",
                                autoSize = TextAutoSize.StepBased(maxFontSize = 20.sp),
                                style = TextStyle(fontSize = 20.sp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                    item {
                        TextField(
                            value = uiState.searchText,
                            onValueChange = { onAction(ModelsContract.UiAction.OnSearchTextChange(it)) },
                            label = { Text("Enter model...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp)
                                .padding(vertical = 6.dp),
                            singleLine = true,
                        )
                    }
                    items(uiState.modelsMapForShow.toList()) { item ->
                        Card(
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .padding(vertical = 6.dp)
                                .fillMaxWidth()
                                .clickableNoIndication {
                                    onAction(ModelsContract.UiAction.OnModelClick(item))
                                },
                            border = BorderStroke(1.dp, Color.Gray),
                            colors = CardDefaults.cardColors(containerColor = Color.LightGray),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .padding(vertical = 12.dp),
                                text = item.second
                            )
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            })
    }
}

@Composable
@Preview
private fun ModelsScreenPreview() {
    ModelsScreen(
        onAction = {}, uiState = ModelsContract.UiState(
            originalModelsMap = emptyMap(),
            manufacturer = "" to "",
            modelsMapForShow = emptyMap()
        )
    )
}