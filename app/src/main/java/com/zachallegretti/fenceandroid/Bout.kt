package com.zachallegretti.fenceandroid

data class Bout(
    var leftScore: Int = 0,
    var rightScore: Int = 0,
    var millisRemaining: Long = 0L,
    var boutType: BoutType = BoutType.PRACTICE,
    val weaponType: WeaponType = WeaponType.EPEE,
    var finished: Boolean = false
) {
    enum class BoutType(val value: Int) {
        PRACTICE(0),
        POOL(1),
        TEAM(2),
        DE(3),
        VET_DE(4),
        YOUTH_DE(5);

        companion object {
            fun fromInt(value: Int) = BoutType.values().first { it.value == value }
        }
    }


    enum class WeaponType(val value: Int) {
        EPEE(0),
        FOIL(1),
        SABRE(2);
        companion object {
            fun fromInt(value: Int) = WeaponType.values().first { it.value == value }
        }
    }


}