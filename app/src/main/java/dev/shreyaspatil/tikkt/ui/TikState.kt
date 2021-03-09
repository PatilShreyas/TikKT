package dev.shreyaspatil.tikkt.ui

sealed class TikState {
    object Idle : TikState()
    class Ticking(val millisRemaining: Long) : TikState()
    object Finished : TikState()
}
