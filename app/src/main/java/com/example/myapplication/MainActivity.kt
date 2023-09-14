package com.example.myapplication

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {
    private val tag = "MyActivity"
    var looping = false
    val colors = arrayOf(Color.WHITE, Color.BLUE, Color.GREEN, Color.RED)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val counter: TextView = findViewById(R.id.counter)
        val button: Button = findViewById(R.id.button)
        val box: View = findViewById(R.id.box)
        val timer = Timer()

        button.setOnClickListener{
            looping = !looping
            counter.text = getString(R.string._00_00)
            box.setBackgroundColor(colors[0])
            if(looping) {
                button.text = getString(R.string.stop)
            } else {
                button.text = getString(R.string.play)
            }
        }
        updateCounter(counter, box, timer)
    }

    private fun updateCounter(counter: TextView, box: View, timer: Timer) {
        var counterValue = 0
        var minutes = 0
        var seconds = 0
        var counterText = "00:00"
        timer.schedule(object : TimerTask() {
            override fun run() {
                this@MainActivity.runOnUiThread {
                    if (looping) {
                        counter.text = counterText
                        box.setBackgroundColor(colors[seconds%20/5])
                        counterValue += 1
                        seconds = counterValue % 60
                        if (seconds == 0 && counterValue > 0) {
                            minutes += 1
                        }

                        if (minutes < 10) {
                            counterText = "0$minutes:"
                        } else {
                            counterText = "$minutes:"
                        }

                        if (seconds < 10) {
                            counterText += "0$seconds"
                        } else {
                            counterText += "$seconds"
                        }
                    } else {
                        box.setBackgroundColor(colors[0])
                        counterValue = 0
                        minutes = 0
                        seconds = 0
                        counterText = "00:00"
                    }
                }
            }
        }, 1, 1000)
    }
}