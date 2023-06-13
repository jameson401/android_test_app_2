package com.example.myapplication.assets.buttons

import android.os.CountDownTimer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun PressIconButton(
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource =
        remember { MutableInteractionSource() },
) {
    val isPressed by interactionSource.collectIsPressedAsState()

    var isPlaying by remember { mutableStateOf(false) }

    val timer = object: CountDownTimer(1000, 1000) {
        override fun onTick(millisUntilFinished: Long) {}

        override fun onFinish() {
            isPlaying = false
        }
    }

    if (isPressed) {
        isPlaying = true
        timer.start()
    }

    Button(onClick = onClick, modifier = modifier,
        interactionSource = interactionSource) {
        AnimatedVisibility(visible = isPlaying) {

            Row {
                icon()
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            }

        }
        text()
    }
}