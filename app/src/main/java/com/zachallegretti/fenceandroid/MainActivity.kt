package com.zachallegretti.fenceandroid

import android.os.Bundle
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_UP
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), BoutView {

    private lateinit var presenter: MainActivityPresenter

    private lateinit var leftScoreIncreaseButton: Button
    private lateinit var leftScoreDecreaseButton: Button
    private lateinit var leftScoreView: TextView

    private lateinit var rightScoreIncreaseButton: Button
    private lateinit var rightScoreDecreaseButton: Button
    private lateinit var rightScoreView: TextView

    private lateinit var doubleTouchView: TextView

    private lateinit var boutTypeView: TextView

    private lateinit var timer: TextView

    private lateinit var startFrame: FrameLayout
    private lateinit var startView: TextView

    private lateinit var settingsView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainActivityPresenter(this, applicationContext)

        leftScoreIncreaseButton = findViewById(R.id.left_increase_button)
        leftScoreDecreaseButton = findViewById(R.id.left_decrease_button)
        leftScoreView = findViewById(R.id.left_score)

        rightScoreIncreaseButton = findViewById(R.id.right_increase_button)
        rightScoreDecreaseButton = findViewById(R.id.right_decrease_button)
        rightScoreView = findViewById(R.id.right_score)

        doubleTouchView = findViewById(R.id.double_touch)

        boutTypeView = findViewById(R.id.mode_indicator)

        timer = findViewById(R.id.timer)

        startFrame = findViewById(R.id.start_frame)
        startView = findViewById(R.id.start)

        settingsView = findViewById(R.id.settings)

        setClickListeners()

    }

    private fun setClickListeners() {
        leftScoreIncreaseButton.setOnClickListener {
            presenter.increaseLeftScore()
        }

        leftScoreDecreaseButton.setOnClickListener {
            presenter.decreaseLeftScore()
        }

        rightScoreIncreaseButton.setOnClickListener {
            presenter.increaseRightScore()
        }

        rightScoreDecreaseButton.setOnClickListener {
            presenter.decreaseRightScore()
        }

        doubleTouchView.setOnClickListener {
            presenter.doubleTouch()
        }

        startFrame.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == ACTION_DOWN) {
                presenter.startTimer()

            } else if (motionEvent.action == ACTION_UP) {
                presenter.stopTimer()
            }
            true
        }

        settingsView.setOnClickListener {
            val modalBottomSheet = SettingsBottomSheet()
            modalBottomSheet.show(supportFragmentManager, SettingsBottomSheet.TAG)
        }
    }

    override fun updateLeftScore(newScore: Int) {
        leftScoreView.text = newScore.toString()
    }

    override fun updateRightScore(newScore: Int) {
        rightScoreView.text = newScore.toString()
    }

    override fun updateBoutType(boutType: Bout.BoutType) {
        boutTypeView.text = boutType.name
    }

    override fun updateStartStopText(started: Boolean) {
        if (started) {
            startView.text = applicationContext.resources.getString(R.string.start)
        } else {
            startView.text = applicationContext.resources.getString(R.string.stop)
        }
    }

    override fun setDoubleTouchVisibility(show: Boolean) {
        doubleTouchView.visibility = if(show) View.VISIBLE else View.GONE
    }


    override fun useTimerTapMode(timerTapEnabled: Boolean) {
        if (timerTapEnabled) {
            startFrame.setOnTouchListener { view, motionEvent ->
                if (motionEvent.action == ACTION_UP) {
                    presenter.toggleTimer()
                }
                true
            }
        } else {
            startFrame.setOnTouchListener { view, motionEvent ->
                if (motionEvent.action == ACTION_DOWN) {
                    presenter.startTimer()

                } else if (motionEvent.action == ACTION_UP) {
                    presenter.stopTimer()
                }
                true
            }
        }
    }

    override fun updateTimerText(millisRemaining: Long) {
        timer.text = convertMillisToString(millisRemaining)
    }

    private fun convertMillisToString(millis: Long) = String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(millis),
            TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1)
        )
}