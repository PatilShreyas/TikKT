package dev.shreyaspatil.tikkt.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChangeCircle
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import dev.shreyaspatil.tikkt.timer.TikTimer
import dev.shreyaspatil.tikkt.ui.components.TikLottieAnimation
import dev.shreyaspatil.tikkt.ui.theme.IrisBlue

@ExperimentalAnimationApi
@Composable
fun TikScreen() {

    Scaffold(content = {
        var secondsTimeout by remember { mutableStateOf(10) }
        var tikTimer by remember { mutableStateOf(TikTimer(secondsTimeout * 1000L)) }

        val tikState by tikTimer.tikState.collectAsState()

        var play by remember { mutableStateOf(false) }
        var reset by remember { mutableStateOf(true) }

        var secondsRemaining by remember { mutableStateOf(10) }
        when (tikState) {
            is TikState.Ticking -> {
                val millisRemaining = (tikState as TikState.Ticking).millisRemaining
                secondsRemaining = (millisRemaining / 1000).toInt()
                play = true
                reset = false
            }
            TikState.Finished -> {
                secondsRemaining = 0
                play = false
            }
            TikState.Idle -> {
                secondsRemaining = secondsTimeout
                reset = true
                play = false
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.size(16.dp))

            Text(
                text = "$secondsRemaining",
                style = MaterialTheme.typography.h2,
                fontFamily = FontFamily.Serif
            )

            Spacer(modifier = Modifier.size(32.dp))

            TikLottieAnimation(
                modifier = Modifier.height(300.dp),
                play = play,
                duration = secondsTimeout,
                reset = reset
            )

            Spacer(modifier = Modifier.size(32.dp))

            TikControls(
                ticking = play,
                onStart = { tikTimer.start() },
                onStop = { tikTimer.cancel() }
            )

            Spacer(modifier = Modifier.size(32.dp))

            var changeTime by remember { mutableStateOf(false) }

            if (!play) {
                AnimatedVisibility(visible = !changeTime) {
                    Button(onClick = { changeTime = changeTime.not() }) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(Icons.Default.ChangeCircle, contentDescription = "Change?")
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(text = "Change time?")
                        }
                    }
                }

                AnimatedVisibility(visible = changeTime) {
                    LazyRow(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        for (seconds in 10..200 step 10) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(IrisBlue)
                                        .clickable {
                                            tikTimer = TikTimer(seconds * 1000L)
                                            secondsRemaining = seconds
                                            secondsTimeout = seconds
                                            changeTime = changeTime.not()
                                        }
                                ) {
                                    Row(modifier = Modifier.padding(8.dp)) {
                                        Text(
                                            "${seconds}s",
                                            style = MaterialTheme.typography.body2,
                                            color = Color.White,
                                            modifier = Modifier.padding(2.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.size(8.dp))
                            }
                        }
                    }
                }
            }
        }
    })
}

@Composable
fun TikControls(ticking: Boolean, onStart: () -> Unit, onStop: () -> Unit) {
    Row {
        Button(onClick = onStart, enabled = !ticking) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Default.PlayCircle, contentDescription = "Start")
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = "Start")
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        Button(onClick = onStop, enabled = ticking) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Default.StopCircle, contentDescription = "Stop")
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = "Stop")
            }
        }
    }
}