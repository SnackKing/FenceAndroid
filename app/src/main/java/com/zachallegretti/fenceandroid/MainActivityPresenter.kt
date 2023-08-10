package com.zachallegretti.fenceandroid

import android.view.View
import com.zachallegretti.fenceandroid.FencingUtils.getMaxScoreForBoutType

class MainActivityPresenter constructor(val view: BoutView) {
    private lateinit var bout: Bout

    fun updateBoutType(boutType: Bout.BoutType) {
        bout.boutType = boutType
        view.updateBoutType(boutType)
    }

    fun increaseLeftScore() {
        bout.leftScore++
        view.updateLeftScore(bout.leftScore)
        if (bout.leftScore == getMaxScoreForBoutType(bout.boutType)) {
            // TODO"Figure out what to do when bout ends")
        }
    }

    fun decreaseLeftScore() {
        if (bout.leftScore > 0) {
            bout.leftScore--
            view.updateLeftScore(bout.leftScore)
        }
    }

    fun increaseRightScore() {
        bout.rightScore++
        view.updateRightScore(bout.rightScore)
        if (bout.rightScore == getMaxScoreForBoutType(bout.boutType)) {
            TODO("Figure out what to do when bout ends")
        }
    }

    fun decreaseRightScore() {
        if (bout.rightScore > 0) {
            bout.rightScore--
            view.updateRightScore(bout.rightScore)
        }
    }

    init {
        this.bout = Bout()
    }
}