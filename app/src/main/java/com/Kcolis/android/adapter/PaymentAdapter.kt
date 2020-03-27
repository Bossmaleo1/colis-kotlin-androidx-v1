package com.Kcolis.android.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.Kcolis.android.R
import com.Kcolis.android.model.data.Payment
import kotlinx.android.synthetic.main.payment_adapter.view.*

class PaymentAdapter(var data: ArrayList<Payment>):
    RecyclerView.Adapter<PaymentAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PaymentAdapter.MyViewHolder(
            LayoutInflater.from(parent.context)!!.inflate(R.layout.payment_adapter, parent, false)
        )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: PaymentAdapter.MyViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class MyViewHolder (view : View) : RecyclerView.ViewHolder(view) {
        private val  title = view.title93
        private val icon = view.icon

        fun bind(paiement : Payment) {
            title.text = paiement.Libelle
            icon.setImageResource(paiement.ID)
        }
    }
}