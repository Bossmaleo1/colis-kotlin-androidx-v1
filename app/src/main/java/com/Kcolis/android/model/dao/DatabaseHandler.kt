package com.Kcolis.android.model.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.Kcolis.android.model.Const
import com.Kcolis.android.model.data.User


class DatabaseHandler(context:Context,
                      factory: SQLiteDatabase.CursorFactory?):
      SQLiteOpenHelper(context,Const.DATABASE_NAME,
          factory,Const.DATABASE_VESION) {

        override fun onCreate(db:SQLiteDatabase) {
            val CREATE_USER = "CREATE TABLE " + Const.TABLE_USER + "(" + Const.KEY_ID_DATABASE + " INTEGER PRIMARY KEY," + Const.KEY_NOM + " TEXT," + Const.KEY_PRENOM + " TEXT," + Const.KEY_PHOTO + " TEXT," +
                    Const.KEY_KEYPUSH+ " TEXT," + Const.KEY_SEXE + " TEXT," + Const.KEY_EMAIL + " TEXT," + Const.KEY_ETAT + " TEXT," +
                    Const.KEY_VILLE + " TEXT," + Const.KEY_DATE_DE_NAISSANCE + " TEXT," + Const.KEY_LANGUE + " TEXT," + Const.KEY_TELEPHONE + " TEXT," + Const.KEY_PAYS + " TEXT);"

            val CREATE_DARK_MODE = "CREATE TABLE " + Const.TABLE_DARK_MODE + "("+ Const.KEY_ID_DATABASE + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + Const.KEY_DARK_MODE + " TEXT)"

            db.execSQL(CREATE_DARK_MODE)
            db.execSQL(CREATE_USER)
        }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS "+Const.TABLE_USER)
        onCreate(db)
    }

     fun addUSER(profil : User) {
        val values = ContentValues()
        values.put(Const.KEY_NOM,profil.NOM)
        values.put(Const.KEY_PRENOM,profil.PRENOM)
        values.put(Const.KEY_EMAIL,profil.EMAIL)
        values.put(Const.KEY_PHOTO,profil.PHOTO)
        values.put(Const.KEY_ID_DATABASE,profil.ID)
        values.put(Const.KEY_KEYPUSH,profil.KEYPUSH)
        values.put(Const.KEY_SEXE,profil.SEXE)
        values.put(Const.KEY_VILLE,profil.VILLE)
        values.put(Const.KEY_DATE_DE_NAISSANCE,profil.DATE_DE_NAISSANCE)
        values.put(Const.KEY_ETAT,profil.ETAT)
        values.put(Const.KEY_LANGUE,profil.LANGUE)
        values.put(Const.KEY_TELEPHONE,profil.TELEPHONE)
        values.put(Const.KEY_PAYS,profil.PAYS)
        val db = this.writableDatabase
        db.insert(Const.TABLE_USER,null,values)
        db.close()
    }

     fun getUser(id: Int) : User {
        val db = this.readableDatabase
        var cursor: Cursor? = null
        cursor = db.rawQuery("SELECT * FROM "+Const.TABLE_USER+" WHERE "+Const.KEY_ID_DATABASE+" = "+id,null)
        if(cursor != null)
            cursor.moveToFirst()
        val user = User(cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(9),
            cursor.getString(5),
            cursor.getString(6),
            cursor.getString(3),
            cursor.getString(4),
            cursor.getString(11),
            cursor.getString(10),
            cursor.getString(7),
            cursor.getString(12),
            cursor.getString(8))

        return user
    }

    fun UpdatePhoto(IDUSER: Int,Photo: String) {
        val values = ContentValues()
        val db = this.writableDatabase
        values.put(Const.KEY_PHOTO,Photo)
        db.update(Const.TABLE_USER,values,Const.KEY_ID_DATABASE+"="+IDUSER,null)
    }

    fun UpdateKeyPush(IDUSER: Int,Keypush: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(Const.KEY_KEYPUSH,Keypush)
        db.update(Const.TABLE_USER,values,Const.KEY_ID_DATABASE+"="+IDUSER,null)
    }

    fun addDARKMODE(dark:String) {
        val values = ContentValues()
        val db = this.writableDatabase
        values.put(Const.KEY_DARK_MODE,dark)

        db.insert(Const.TABLE_DARK_MODE,null,values)
        db.close()
    }

    fun getDARKMODE() : String {
        val selectQuery = "SELECT * FROM "+Const.TABLE_DARK_MODE+" WHERE "+Const.KEY_ID_DATABASE+" = 1"
        val db = this.writableDatabase
        var dark_mode = ""
        var cursor: Cursor? = null
        cursor = db.rawQuery(selectQuery,null)
        if(cursor.moveToFirst())
        {
            do{
                dark_mode = cursor.getString(1)
            }while (cursor.moveToNext());
        }
        return dark_mode
    }

    fun updateDARKMODE(value:String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(Const.KEY_DARK_MODE,value)
        db.update(Const.TABLE_DARK_MODE,values,Const.KEY_ID_DATABASE+"=1",null)
    }


}