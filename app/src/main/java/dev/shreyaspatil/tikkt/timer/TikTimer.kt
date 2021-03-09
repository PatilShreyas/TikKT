package dev.shreyaspatil.tikkt.timer

import android.os.CountDownTimer
import dev.shreyaspatil.tikkt.ui.TikState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TikTimer(val millis: Long, interval: Long = 10L) {
    private val _tikState = MutableStateFlow<TikState>(TikState.Idle)
    val tikState: StateFlow<TikState> = _tikState

    private var countDownTimer = object : CountDownTimer(millis, interval) {
        override fun onTick(millisUntilFinished: Long) {
            _tikState.value = TikState.Ticking(millisUntilFinished)
        }

        override fun onFinish() {
            _tikState.value = TikState.Finished
        }

    }

    fun start() {
        countDownTimer.start()
    }

    fun cancel() {
        countDownTimer.onFinish()
        countDownTimer.cancel()
    }
}