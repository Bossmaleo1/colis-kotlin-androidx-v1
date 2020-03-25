package com.Kcolis.android.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Annonce (var ID : Int,
               var ID_USER : Int,
               var PHOTO_USER : String,
               var NOM_USER : String,
               var PHONE_USER : String,
               var DATE_ANNONCE : String,
               var DATE_ANNONCE_VOYAGE : String,
               var PRIX: String,
               var LIEUX_RDV1: String,
               var LIEUX_RDV2: String,
               var VILLE_DEPART: String,
               var VILLE_ARRIVEE: String,
               var HEURE_DEPART: String,
               var HEURE_ARRIVEE: String,
               var NOMBRE_KILO: String,
               var DATE_ANNONCE_VOYAGE2: String,
               var KEY_PUSH: String)  : Parcelable