package com.petprojject.feature_ai.screens.alternatives

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
import androidx.compose.foundation.lazy.items
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
import com.petprojject.common_ui.R as commonUiR
import com.petprojject.feature_ai.components.CarChooseItemComponent

@Composable
fun AlternativesScreen(
    uiState: AlternativesContract.UiState, onAction: (AlternativesContract.UiAction) -> Unit
) {
    Scaffold(
        Modifier.fillMaxSize(),
        topBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .statusBarsPadding(),
            ) {
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.powered_by) + " ",
                        style = TextStyle(fontSize = 24.sp),
                        color = CarTheme.customColors.textColor
                    )
                    Image(
                        painter = painterResource(R.drawable.gemini),
                        modifier = Modifier.height(12.dp),
                        contentDescription = null
                    )
                }
                Icon(
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.CenterStart)
                        .clickableNoIndication {
                            onAction(AlternativesContract.UiAction.OnBackClick)
                        },
                    painter = painterResource(commonUiR.drawable.arrow_back),
                    tint = CarTheme.customColors.iconColor,
                    contentDescription = null
                )
            }
        },
    ) { padding ->
        ScaffoldContent(
            isLoading = uiState.isLoading,
            error = uiState.error,
            paddingValues = padding,
            onTryAgain = {
                onAction(AlternativesContract.UiAction.TryAgain)
            },
            content = {
                BackgroundImage {
                    if (uiState.response.isEmpty()) {
                        LazyColumn(
                            modifier = Modifier.padding(padding),
                        ) {
                            items(uiState.listOfHistory.toList()) { item ->
                                CarChooseItemComponent(
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .fillMaxWidth(),
                                    car = item,
                                    onItemClick = {
                                        onAction(AlternativesContract.UiAction.OnItemClick(item))
                                    },
                                    isChosen = false
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
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(8.dp)
                                    .background(
                                        CarTheme.customColors.cardBorderColor,
                                        RoundedCornerShape(4.dp)
                                    )
                                    .border(
                                        2.dp,
                                        CarTheme.customColors.cardBorderColor,
                                        RoundedCornerShape(4.dp)
                                    )
                                    .verticalScroll(rememberScrollState()),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
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
                            }
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
            }
        )
    }
}

@Composable
@Preview
private fun AlternativesScreenPreview() {
    AutoPetProjectLakhaiTheme {
        AlternativesScreen(onAction = {}, uiState = AlternativesContract.UiState())
    }
}