package com.zachallegretti.fenceandroid

interface BoutView {

    fun updateLeftScore(newScore: Int)

    fun updateRightScore(newScore: Int)

    fun updateBoutType(boutType: Bout.BoutType)

    fun updateTimerText(millisRemaining: Long)

    fun updateStartStopText(start: Boolean)

    fun setDoubleTouchVisibility(show: Boolean)

    fun useTimerTapMode(timerTapEnabled: Boolean)

}