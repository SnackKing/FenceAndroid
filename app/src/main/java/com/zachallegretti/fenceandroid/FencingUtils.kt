package com.zachallegretti.fenceandroid

object FencingUtils {

    fun getMaxScoreForBoutType(boutType: Bout.BoutType): Int {
        return when (boutType) {
            Bout.BoutType.PRACTICE, Bout.BoutType.POOL -> 5
            Bout.BoutType.VET_DE, Bout.BoutType.YOUTH_DE -> 10
            Bout.BoutType.DE -> 15
            Bout.BoutType.TEAM -> 45
        }
    }

    const val BOUT_PERIOD_LENGTH = 180000L
}