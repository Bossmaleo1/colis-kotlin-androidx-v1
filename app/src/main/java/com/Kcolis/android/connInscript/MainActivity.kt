package com.Kcolis.android.connInscript

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.Kcolis.android.R
import com.Kcolis.android.utils.ThreadLauncher

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val launch = ThreadLauncher(this)
        launch.start()
    }

}
