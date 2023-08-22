package com.zachallegretti.fenceandroid

object FencingUtils {

    fun getMaxScoreForBoutType(boutType: Bout.BoutType): Int {
        return when (boutType) {
            Bout.BoutType.PRACTICE, Bout.BoutType.POOL, Bout.BoutType.TEST -> 5
            Bout.BoutType.VET_DE, Bout.BoutType.YOUTH_DE -> 10
            Bout.BoutType.DE -> 15
            Bout.BoutType.TEAM -> 45
        }
    }

    fun getBoutLength(boutType: Bout.BoutType): Long {
        if (boutType == Bout.BoutType.TEST) {
            return 5000L
        } else {
            return BOUT_PERIOD_LENGTH
        }
    }

    const val BOUT_PERIOD_LENGTH = 180000L
}