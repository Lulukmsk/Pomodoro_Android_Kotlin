package com.example.pomodoratest

import android.animation.ValueAnimator
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_run_pomodora.*
import java.time.Duration


val EXTRA_Hours = "com.example.pomodoratest.EXTRA_Hours"
val EXTRA_Minutes = "com.example.pomodoratest.EXTRA_Minutes"
val EXTRA_Seconds = "com.example.pomodoratest.EXTRA_Seconds"

class PomodoraActivity : AppCompatActivity() {

    private lateinit var counter : CountDownTimer
    private var pause = false
    private var milliSeconds : Long = 0
    val secondsInMilli: Long = 1000
    val minutesInMilli = secondsInMilli * 60
    val hoursInMilli = minutesInMilli * 60
    val daysInMilli = hoursInMilli * 24

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run_pomodora)

        val hours = intent.getShortExtra(EXTRA_Hours, 0)
        val minutes = intent.getShortExtra(EXTRA_Minutes, 25)
        val seconds = intent.getShortExtra(EXTRA_Seconds, 0)


        milliSeconds = hours * hoursInMilli + minutes * minutesInMilli + seconds * secondsInMilli


        ValueAnimator.ofFloat(1f,0f).apply{
            duration = milliSeconds
            addUpdateListener {
                updateAnimation -> roundProgressBar.alpha = updateAnimation.animatedValue as Float
            }
            start()
        }

        counter = object : CountDownTimer(milliSeconds, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    var diff = millisUntilFinished
                    val elapsedHours = diff / hoursInMilli
                    diff %= hoursInMilli
                    val elapsedMinutes = diff / minutesInMilli
                    diff %= minutesInMilli
                    val elapsedSeconds = diff / secondsInMilli

                    val progress = (millisUntilFinished.toDouble() / milliSeconds.toDouble() * 100).toInt()
                    SetProgress(progress)
                    CountDownView.text =
                        "${elapsedHours.toString().padStart(2,'0')}:${elapsedMinutes.toString().padStart(2,'0')}:${elapsedSeconds.toString().padStart(2,'0')}"
                }

                override fun onFinish() {
                    SetProgress(100)
                    StartPlayPomodora()
                }
            }

        if (milliSeconds > 0)
            counter.start()
        else
            StartPlayPomodora()
    }

    override fun onDestroy() {
        super.onDestroy()
        counter.cancel()
    }

    fun SetProgress(progress : Int)
    {
        roundProgressBar.setProgress(progress, true)
        slideProgressBar.setProgress(progress, true)
    }

    fun StartPlayPomodora()
    {
        roundProgressBar.visibility = View.GONE
        slideProgressBar.visibility = View.GONE
        CountDownView.text = "POMODORA!!!"
        val mAudioManager = this.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val current_volume = mAudioManager.getStreamVolume(AudioManager.STREAM_ALARM)
        val mediaPlayer = MediaPlayer.create(this, R.raw.alarm)
        mediaPlayer.setVolume(1.0f, 1.0f)
        mediaPlayer.start()
    }
}