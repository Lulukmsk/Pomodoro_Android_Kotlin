package com.example.pomodoratest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.text.InputFilter
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editHoursText = findViewById<EditText>(R.id.editHoursNumber)
        editHoursText.filters = arrayOf<InputFilter>(MinMaxFilter("0", "24"))
        val editMinutesText = findViewById<EditText>(R.id.editMinutesNumber)
        editMinutesText.filters = arrayOf<InputFilter>(MinMaxFilter("0", "60"))
        val editSecondsText = findViewById<EditText>(R.id.editSecondsNumber)
        editSecondsText.filters = arrayOf<InputFilter>(MinMaxFilter("0", "60"))
    }

    fun startRegularPomodora(view: View)
    {
        startPomodora(view, 0, 25,0)
    }

    fun startDoublePomodora(view: View)
    {
        startPomodora(view, 0, 50,0)
    }

    fun startQuadroPomodora(view: View)
    {
        startPomodora(view, 1, 40,0)
    }

    fun startCustomPomodora(view: View)
    {
        val editHoursText = findViewById<EditText>(R.id.editHoursNumber)
        val editMinutesText = findViewById<EditText>(R.id.editMinutesNumber)
        val editSecondsText = findViewById<EditText>(R.id.editSecondsNumber)
        startPomodora(view,  if (!editHoursText.text.toString().isNullOrEmpty()) editHoursText.text.toString().toShort() else 0, if (!editMinutesText.text.toString().isNullOrEmpty()) editMinutesText.text.toString().toShort() else 0, if (!editSecondsText.text.toString().isNullOrEmpty()) editSecondsText.text.toString().toShort() else 0)
    }

    fun startPomodora(view: View, hours: Short, minutes: Short, seconds: Short)
    {
        val intent = Intent(this, PomodoraActivity::class.java).apply {
            putExtra(EXTRA_Hours, hours)
            putExtra(EXTRA_Minutes.toString(), minutes)
            putExtra(EXTRA_Seconds.toString(), seconds)
        }
        startActivity(intent)
    }
}