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
