package com.test.common_ui.modifiers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.clickableNoIndication(
    onClick: () -> Unit
): Modifier {
    return this.composed {
        this.clickable(
            enabled = true,
            onClickLabel = null,
            role = null,
            onClick = onClick,
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        )
    }
}