package com.zachallegretti.fenceandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity(), BoutView {

    private lateinit var presenter: MainActivityPresenter

    private lateinit var leftScoreIncreaseButton: Button
    private lateinit var leftScoreDecreaseButton: Button
    private lateinit var leftScoreView: TextView

    private lateinit var rightScoreIncreaseButton: Button
    private lateinit var rightScoreDecreaseButton: Button
    private lateinit var rightScoreView: TextView

    private lateinit var boutTypeView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainActivityPresenter(this)

        leftScoreIncreaseButton = findViewById(R.id.left_increase_button)
        leftScoreDecreaseButton = findViewById(R.id.left_decrease_button)
        leftScoreView = findViewById(R.id.left_score)

        rightScoreIncreaseButton = findViewById(R.id.right_increase_button)
        rightScoreDecreaseButton = findViewById(R.id.right_decrease_button)
        rightScoreView = findViewById(R.id.right_score)

        boutTypeView = findViewById(R.id.mode_indicator)

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
}