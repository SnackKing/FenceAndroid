package com.zachallegretti.fenceandroid

import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import android.os.CombinedVibration
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import com.zachallegretti.fenceandroid.FencingUtils.getMaxScoreForBoutType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivityPresenter constructor(val view: BoutView, val context: Context) {
    private lateinit var bout: Bout
    private lateinit var boutPreferencesDataStore: BoutPreferencesDataStore
    private lateinit var boutSettings: BoutSettings
    private var countDownTimer: CountDownTimer? = null

    private var timerRunning = false

    fun startTimer() {
        countDownTimer =  object :CountDownTimer(bout.millisRemaining, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                view.updateTimerText(millisUntilFinished)
                bout.millisRemaining = millisUntilFinished
            }

            override fun onFinish() {
                maybeVibrateDevice()
                maybePlayAlarm()
            }
        }.start()
        view.updateStartStopText(false)
    }

    fun stopTimer() {
        view.updateStartStopText(true)
        countDownTimer?.cancel()
    }

    fun toggleTimer() {
        if (timerRunning) {
            countDownTimer?.cancel()
            timerRunning = false
            view.updateStartStopText(true)
        } else {
            countDownTimer = object : CountDownTimer(bout.millisRemaining, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timerRunning = true
                    view.updateTimerText(millisUntilFinished)
                    bout.millisRemaining = millisUntilFinished
                }

                override fun onFinish() {
                    timerRunning = false
                    maybeVibrateDevice()
                    maybePlayAlarm()
                }
            }.start()
            view.updateStartStopText(false)
        }
    }

    private fun maybeVibrateDevice() {
        if (boutSettings.timerBuzzEnabled) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    val vibratorManager =
                        context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                    val vibrationPattern = longArrayOf(500L, 3000L, 5000L)
                    val vibrationEffect = VibrationEffect.createWaveform(vibrationPattern, 0)
                    vibratorManager.vibrate(CombinedVibration.createParallel(vibrationEffect))
                } else {
                    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    vibrator.vibrate(5000L)
                }
        }
    }

    private fun maybePlayAlarm() {
        if (boutSettings.timerSoundEnabled) {
            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val mp = MediaPlayer.create(context, alarmSound)
            mp.start()
        }
    }


    private fun boutUpdated() {
        (view as Activity).runOnUiThread {
            view.updateBoutType(bout.boutType)
            view.setDoubleTouchVisibility(bout.weaponType == Bout.WeaponType.EPEE)
            //TODO Don't always reset timer
            view.updateTimerText(bout.millisRemaining)
            view.useTimerTapMode(boutSettings.timerTapModeEnabled)
            resetScores()
        }

    }

    fun increaseLeftScore() {
        if (bout.leftScore < getMaxScoreForBoutType(bout.boutType)) {
            bout.leftScore++
            view.updateLeftScore(bout.leftScore)
        }
        if (bout.leftScore == getMaxScoreForBoutType(bout.boutType)) {
            view.showBoutEndDialog(bout.leftScore, bout.rightScore)
        }
    }

    fun decreaseLeftScore() {
        if (bout.leftScore > 0) {
            bout.leftScore--
            view.updateLeftScore(bout.leftScore)
        }
    }

    fun increaseRightScore() {
        if (bout.rightScore < getMaxScoreForBoutType(bout.boutType)) {
            bout.rightScore++
            view.updateRightScore(bout.rightScore)
        }
        if (bout.rightScore == getMaxScoreForBoutType(bout.boutType)) {
            view.showBoutEndDialog(bout.leftScore, bout.rightScore)
        }
    }

    fun doubleTouch() {
        increaseLeftScore()
        increaseRightScore()
    }

    fun resetScores() {
        bout.leftScore = 0
        bout.rightScore = 0
        view.updateLeftScore(0)
        view.updateRightScore(0)
    }

    fun decreaseRightScore() {
        if (bout.rightScore > 0) {
            bout.rightScore--
            view.updateRightScore(bout.rightScore)
        }
    }

    fun newBout() {
        bout = Bout(
            millisRemaining = FencingUtils.getBoutLength(Bout.BoutType.fromInt(boutSettings.boutSettingsKey)),
            boutType = Bout.BoutType.fromInt(boutSettings.boutSettingsKey),
            weaponType = Bout.WeaponType.fromInt(boutSettings.weaponKey)
        )
        boutUpdated()
    }


    init {
        boutPreferencesDataStore = BoutPreferencesDataStore(context)
        CoroutineScope(Dispatchers.IO).launch {
            // Flow will publish new data when settings are updated.
            boutPreferencesDataStore.boutSettingsFlow.collect {
                boutSettings = it
                bout = Bout(
                    millisRemaining = FencingUtils.getBoutLength(Bout.BoutType.fromInt(boutSettings.boutSettingsKey)),
                    boutType = Bout.BoutType.fromInt(it.boutSettingsKey),
                    weaponType = Bout.WeaponType.fromInt(it.weaponKey)
                )
                boutUpdated()

            }
        }
        this.bout = Bout(millisRemaining =  FencingUtils.BOUT_PERIOD_LENGTH)
    }
}