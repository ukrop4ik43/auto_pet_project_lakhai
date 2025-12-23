package com.petprojject.feature_automobile.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.petprojject.common_ui.R
import com.petprojject.common_ui.modifiers.clickableNoIndication
import com.petprojject.common_ui.theme.AutoPetProjectLakhaiTheme
import com.petprojject.common_ui.theme.CarTheme
import com.petprojject.domain.car.model.CarHistoryItem

@Composable
fun CarHistoryItem(
    modifier: Modifier = Modifier,
    car: CarHistoryItem,
    onItemDelete: (CarHistoryItem) -> Unit
) {
    Row(
        modifier
            .border(1.dp, CarTheme.customColors.cardBorderColor, shape = RoundedCornerShape(8.dp))
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = car.manufacturer + " " + car.model + " ",
            color = CarTheme.customColors.textColor
        )
        Text(
            text = "("+car.year+")",
            color = CarTheme.customColors.descriptionColor
        )
        Spacer(Modifier.weight(1f))
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickableNoIndication {
                    onItemDelete(car)
                },
            painter = painterResource(R.drawable.close_24),
            tint = CarTheme.customColors.iconColor,
            contentDescription = null
        )
    }
}

@Composable
@Preview
private fun CarHistoryItemPreview() {
    AutoPetProjectLakhaiTheme {
        CarHistoryItem(
            modifier = Modifier.fillMaxWidth(),
            car = CarHistoryItem(
                id = 0,
                manufacturer = "AUDI",
                model = "X5",
                year = "2022"
            ), onItemDelete = {}
        )
    }
}