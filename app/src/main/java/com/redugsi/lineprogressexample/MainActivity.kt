package com.redugsi.lineprogressexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.redugsi.lineprogress.LineProgress

class MainActivity : AppCompatActivity() {

    lateinit var runnable: Runnable
    var progressA = 0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val progress = findViewById<LineProgress>(R.id.progress)

        var handler = Handler()

        runnable = Runnable {
            progress.setProgressValue(progressA)
            progressA += 1
            handler.postDelayed(runnable, 100)
        }
        handler.postDelayed(runnable, 1000)


    }
}
