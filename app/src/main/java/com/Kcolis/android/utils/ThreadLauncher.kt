package com.Kcolis.android.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.Kcolis.android.appviews.Home
import com.Kcolis.android.connInscript.Connexion
import com.Kcolis.android.model.Const
import com.Kcolis.android.model.cache.SessionManager

class ThreadLauncher(val context: Context): Thread() {



    override fun run() {
            try {

                Thread.sleep(200)
                val session = SessionManager(context)
                //Toast.makeText(context,"Test du boss : "+session.IsLoggedIn().toString(), Toast.LENGTH_LONG).show()
                //Toast.makeText(context,"Hello Wordld : "+session.IsLoggedIn().toString(),Toast.LENGTH_LONG).show()
                if(!session.IsLoggedIn()) {
                    val intent = Intent(context, Connexion::class.java)
                    context.startActivity(intent)
                } else {
                    val intent = Intent(context,Home::class.java)
                    context.startActivity(intent)
                }


            }catch (e: InterruptedException){}
    }


}