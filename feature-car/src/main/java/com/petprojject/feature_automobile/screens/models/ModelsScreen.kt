package com.petprojject.feature_automobile.screens.models

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.petprojject.feature_automobile.R
import com.petprojject.common_ui.R as commonUiR
import com.petprojject.common_ui.components.ChoiceItem
import com.petprojject.common_ui.components.ScaffoldContent
import com.petprojject.common_ui.modifiers.clickableNoIndication
import com.petprojject.common_ui.theme.AutoPetProjectLakhaiTheme
import com.petprojject.common_ui.theme.CarTheme

@Composable
fun ModelsScreen(
    uiState: ModelsContract.UiState,
    onAction: (ModelsContract.UiAction) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        Modifier
            .fillMaxSize(),
        topBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .statusBarsPadding()
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = stringResource(R.string.choose_model) + ":",
                    style = TextStyle(fontSize = 24.sp),
                    color = CarTheme.customColors.textColor
                )
                Icon(
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.CenterStart)
                        .clickableNoIndication {
                            onAction(ModelsContract.UiAction.OnBackClick)
                        },
                    painter = painterResource(commonUiR.drawable.arrow_back),
                    tint = CarTheme.customColors.iconColor,
                    contentDescription = null
                )
            }
        },
        containerColor = CarTheme.customColors.backgroundColor,
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
                            border = BorderStroke(2.dp, CarTheme.customColors.cardBorderColor),
                            colors = CardDefaults.cardColors(containerColor = CarTheme.customColors.resultCardBackground),
                            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
                        ) {
                            BasicText(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                text = "${stringResource(R.string.manufacturer) + ":"} ${uiState.manufacturer.second}",
                                autoSize = TextAutoSize.StepBased(maxFontSize = 20.sp),
                                style = TextStyle(fontSize = 20.sp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                    item {
                        OutlinedTextField(
                            value = uiState.searchText,
                            onValueChange = { onAction(ModelsContract.UiAction.OnSearchTextChange(it)) },
                            label = { Text(stringResource(R.string.enter_model)) },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            singleLine = true,
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    tint = CarTheme.customColors.descriptionColor
                                )
                            },
                            trailingIcon = {
                                if (uiState.searchText.isNotEmpty()) {
                                    IconButton(onClick = {
                                        onAction(
                                            ModelsContract.UiAction.OnSearchTextChange(
                                                ""
                                            )
                                        )
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Clear,
                                            contentDescription = "Clear",
                                            tint = CarTheme.customColors.descriptionColor
                                        )
                                    }
                                }
                            },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = CarTheme.customColors.textColor,
                                unfocusedTextColor = CarTheme.customColors.textColor,
                                cursorColor = CarTheme.customColors.tryAgainButtonContainer,
                                focusedLabelColor = CarTheme.customColors.tryAgainButtonContainer,
                                unfocusedLabelColor = CarTheme.customColors.descriptionColor,
                                focusedBorderColor = CarTheme.customColors.tryAgainButtonContainer,
                                unfocusedBorderColor = CarTheme.customColors.cardBorderColor,
                                focusedContainerColor = CarTheme.customColors.backgroundColor,
                                unfocusedContainerColor = CarTheme.customColors.backgroundColor
                            )
                        )
                    }
                    items(uiState.modelsMapForShow.toList()) { item ->
                        ChoiceItem(
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .padding(vertical = 6.dp)
                                .fillMaxWidth(),
                            text = item.second,
                            onClick = {
                                onAction(ModelsContract.UiAction.OnModelClick(item))
                            }
                        )
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
    AutoPetProjectLakhaiTheme {
        ModelsScreen(
            onAction = {}, uiState = ModelsContract.UiState(
                originalModelsMap = mapOf(
                    "1" to "X3",
                    "2" to "X4",
                    "2" to "X5"
                ),
                manufacturer = "2" to "Audi",
                modelsMapForShow = mapOf(
                    "1" to "X3",
                    "2" to "X4",
                    "2" to "X5"
                )
            )
        )
    }
}