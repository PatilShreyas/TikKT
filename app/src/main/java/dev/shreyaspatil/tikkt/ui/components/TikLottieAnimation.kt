/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.shreyaspatil.tikkt.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.LottieAnimationView
import dev.shreyaspatil.tikkt.R

@Composable
fun TikLottieAnimation(modifier: Modifier, play: Boolean, duration: Int, reset: Boolean) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            LottieAnimationView(context).apply {
                setAnimation(R.raw.sand_timer)
            }
        },
        update = { view ->
            if (play) {
                view.speed = (1 / (duration / 4.0f))
                view.playAnimation()
            } else {
                view.speed = 8.0f
            }

            if (reset) view.progress = 1.0f
        }
    )
}
