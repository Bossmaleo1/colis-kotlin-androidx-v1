package com.Kcolis.android.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.Kcolis.android.appviews.Home
import com.Kcolis.android.connInscript.Connexion

class ThreadLauncher(val context: Context): Thread() {



    override fun run() {
            try {
                //Toast.makeText(context,"Test du boss",Toast.LENGTH_LONG).show()
                Thread.sleep(200)
                //Toast.makeText(context,"Test du boss",Toast.LENGTH_LONG).show()
                val intent = Intent(context, Connexion::class.java)
                context.startActivity(intent)
            }catch (e: InterruptedException){}
    }


}