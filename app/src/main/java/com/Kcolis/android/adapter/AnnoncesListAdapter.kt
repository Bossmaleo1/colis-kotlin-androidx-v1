package com.Kcolis.android.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.Kcolis.android.R
import com.Kcolis.android.model.Const
import com.Kcolis.android.model.data.Annonce
import kotlinx.android.synthetic.main.annonceslistadapter.view.*
import kotlinx.android.synthetic.main.searchadapter.view.title

class AnnoncesListAdapter(var data: ArrayList<Annonce>):
    RecyclerView.Adapter<AnnoncesListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AnnoncesListAdapter.MyViewHolder(
            LayoutInflater.from(parent.context)!!.inflate(R.layout.annonceslistadapter, parent, false)
        )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: AnnoncesListAdapter.MyViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class MyViewHolder (view : View) : RecyclerView.ViewHolder(view) {
        private val title = view.title
        private val title1 = view.title1
        private val depart = view.contenu
        private val arrivee = view.contenu_ville_arrivee_block
        private val dateannonce = view.contenu_heure_depart
        private val Prix = view.contenu_heure_arrivee
        private val picture = view.my_image_view

        fun bind(annonce : Annonce) {
            title.text = annonce.NOM_USER
            title1.text = annonce.DATE_ANNONCE
            if(annonce.PHOTO_USER != null) {
                val uri = Uri.parse(Const.dns+"/colis/uploads/photo_de_profil/"+annonce.PHOTO_USER)
                picture.setImageURI(uri)
            } else {
                picture.setImageResource(R.drawable.ic_profile_colorier)
            }

            depart.text = annonce.VILLE_DEPART
            arrivee.text = annonce.VILLE_ARRIVEE
            Prix.text = annonce.PRIX+" euros/Kg"
            dateannonce.text = annonce.DATE_ANNONCE_VOYAGE
        }
    }
}