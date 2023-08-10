package com.zachallegretti.fenceandroid

data class Bout(
    var leftScore: Int = 0,
    var rightScore: Int = 0,
    var boutType: BoutType = BoutType.PRACTICE,
    var finished: Boolean = false
) {
    enum class BoutType {
        PRACTICE,
        POOL,
        TEAM,
        DE,
        VET_DE,
        YOUTH_DE
    }
}