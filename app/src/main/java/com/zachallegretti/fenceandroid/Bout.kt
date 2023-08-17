package com.zachallegretti.fenceandroid

data class Bout(
    var leftScore: Int = 0,
    var rightScore: Int = 0,
    var millisRemaining: Long = 0L,
    var boutType: BoutType = BoutType.PRACTICE,
    var finished: Boolean = false
) {
    enum class BoutType(val value: Int) {
        PRACTICE(1),
        POOL(2),
        TEAM(3),
        DE(4),
        VET_DE(5),
        YOUTH_DE(6)
    }
}