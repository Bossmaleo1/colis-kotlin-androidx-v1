package com.Kcolis.android.appviews

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.Kcolis.android.R
import kotlinx.android.synthetic.main.connexion.*

class ListAvis : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listavis)

        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar!!.title =  "Les Avis"
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar!!.setDisplayShowHomeEnabled(true)
    }
}