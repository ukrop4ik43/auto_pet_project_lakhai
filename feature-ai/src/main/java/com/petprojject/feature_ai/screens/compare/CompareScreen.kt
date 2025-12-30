package com.petprojject.feature_ai.screens.compare

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.petprojject.common_ui.components.BackgroundImage
import com.petprojject.common_ui.components.ScaffoldContent
import com.petprojject.common_ui.modifiers.clickableNoIndication
import com.petprojject.common_ui.theme.AutoPetProjectLakhaiTheme
import com.petprojject.common_ui.theme.CarTheme
import com.petprojject.feature_ai.R
import com.petprojject.feature_ai.components.AiResponseContainer
import com.petprojject.feature_ai.components.CarChooseItemComponent
import dev.chrisbanes.haze.rememberHazeState
import com.petprojject.common_ui.R as commonUiR

@Composable
fun CompareScreen(
    uiState: CompareContract.UiState, onAction: (CompareContract.UiAction) -> Unit
) {
    val hazeState = rememberHazeState(true)
    Scaffold(
        Modifier
            .fillMaxSize(),
        topBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .statusBarsPadding(),
            ) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.powered_by) + " ",
                            style = TextStyle(fontSize = 12.sp),
                            color = CarTheme.customColors.textColor
                        )
                        Image(
                            painter = painterResource(R.drawable.gemini),
                            modifier = Modifier.height(12.dp),
                            contentDescription = null
                        )
                    }
                    Text(
                        text = stringResource(R.string.choose_two_items) + " ",
                        style = TextStyle(fontSize = 12.sp),
                        color = CarTheme.customColors.textColor
                    )
                }
                Icon(
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.CenterStart)
                        .clickableNoIndication {
                            onAction(CompareContract.UiAction.OnBackClick)
                        },
                    painter = painterResource(commonUiR.drawable.arrow_back),
                    tint = CarTheme.customColors.iconColor,
                    contentDescription = null
                )
            }
        }
    ) { padding ->
        ScaffoldContent(
            isLoading = uiState.isLoading,
            error = uiState.error,
            paddingValues = padding,
            onTryAgain = {
                onAction(CompareContract.UiAction.TryAgain)
            },
            content = {
                BackgroundImage(hazeState = hazeState) {
                    if (uiState.listOfIndexesChosenItems.size != 2) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize().padding(padding),
                        ) {
                            itemsIndexed(uiState.listOfHistory.toList()) { index, item ->
                                CarChooseItemComponent(
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .fillMaxWidth(),
                                    car = item,
                                    onItemClick = {
                                        onAction(CompareContract.UiAction.OnItemClick(index))
                                    },
                                    isChosen = uiState.listOfIndexesChosenItems.contains(index)
                                )
                                HorizontalDivider(color = CarTheme.customColors.cardBorderColor)
                            }
                            item {
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    } else {
                        Column(
                            Modifier
                                .padding(padding)
                                .fillMaxSize()
                        ) {
                            AiResponseContainer(
                                modifier = Modifier.weight(1f),
                                hazeState = hazeState,
                                content = {
                                    if (uiState.isLoading) {
                                        CircularProgressIndicator()
                                    } else {
                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 8.dp)
                                                .padding(vertical = 12.dp),
                                            text = uiState.response,
                                            textAlign = TextAlign.Center,
                                            fontSize = 20.sp,
                                            color = CarTheme.customColors.descriptionColor
                                        )
                                    }
                                })
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp)
                                    .padding(vertical = 12.dp),
                                text = stringResource(R.string.ai_can_be_wrong),
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp,
                                color = CarTheme.customColors.descriptionColor
                            )
                        }
                    }
                }
            })
    }
}

@Composable
@Preview
private fun CompareScreenPreview() {
    AutoPetProjectLakhaiTheme {
        CompareScreen(onAction = {}, uiState = CompareContract.UiState())
    }
}