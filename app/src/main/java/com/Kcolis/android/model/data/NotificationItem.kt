package com.Kcolis.android.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class NotificationItem (var id: Int,
                        var description: String,
                        var date_validation: String,
                        var statut_validation : String,
                        var nombre_kilo: String,
                        var id_emetteur: String,
                        var id_annonce: String,
                        var nom_emmetteur: String,
                        var prenom_emmetteur: String,
                        var photo_emmetteur: String,
                        var telephone: String,
                        var keypush: String,
                        var Id_type: Int): Parcelable