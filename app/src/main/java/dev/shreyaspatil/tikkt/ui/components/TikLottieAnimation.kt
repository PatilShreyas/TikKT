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
