package com.petprojject.feature_ai.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.petprojject.common_ui.R
import com.petprojject.common_ui.modifiers.clickableNoIndication
import com.petprojject.common_ui.theme.AutoPetProjectLakhaiTheme
import com.petprojject.common_ui.theme.CarTheme
import com.petprojject.domain.car.model.CarHistoryItem

@Composable
fun CarChooseItemComponent(
    modifier: Modifier = Modifier,
    car: CarHistoryItem,
    isChosen: Boolean,
    onItemClick: (CarHistoryItem) -> Unit,
) {
    Row(
        modifier
            .fillMaxWidth()
            .background(CarTheme.customColors.backgroundColor)
            .animateContentSize()
            .padding(vertical = 4.dp)
            .clickableNoIndication {
                onItemClick(car)
            }, verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = car.manufacturer + " " + car.model + " ", color = CarTheme.customColors.textColor
        )
        Text(
            text = "(" + car.year + ")", color = CarTheme.customColors.descriptionColor
        )
        Spacer(Modifier.weight(1f))
        if (isChosen) {
            Icon(
                imageVector = Icons.Default.Check,
                tint = CarTheme.customColors.iconColor,
                contentDescription = null
            )
        }
        Spacer(Modifier.width(8.dp))
    }
}

@Composable
@Preview
private fun CarChooseItemComponentPreview() {
    AutoPetProjectLakhaiTheme {
        CarChooseItemComponent(
            modifier = Modifier.fillMaxWidth(), car = CarHistoryItem(
                id = 0, manufacturer = "AUDI", model = "X5", year = "2022"
            ), onItemClick = {}, isChosen = false
        )

        Spacer(Modifier.height(20.dp))
        CarChooseItemComponent(
            modifier = Modifier.fillMaxWidth(), car = CarHistoryItem(
                id = 0, manufacturer = "AUDI", model = "X5", year = "2022"
            ), onItemClick = {}, isChosen = true
        )
    }
}