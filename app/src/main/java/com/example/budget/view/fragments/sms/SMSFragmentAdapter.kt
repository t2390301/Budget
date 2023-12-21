package com.example.budget.view.fragments.sms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.R
import com.example.budget.model.database.entity.SmsDataEntity
import java.util.Date

class SMSFragmentAdapter(private var smslist: List<SmsDataEntity>?) : RecyclerView.Adapter<SMSFragmentAdapter.SMSViewHolder>(){
    class SMSViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val smsAddress = itemView.findViewById<TextView>(R.id.sms_sender)
        val smsBody = itemView.findViewById<TextView>(R.id.sms_text)
        val smsDate = itemView.findViewById<TextView>(R.id.sms_date_and_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SMSViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_sms_fragment, parent, false)
        return SMSViewHolder(itemView)

    }

    fun setList(list: List<SmsDataEntity>){
        this.smslist = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int =
        smslist?.size?:0


    override fun onBindViewHolder(holder: SMSViewHolder, position: Int) {
        holder.smsAddress.text = smslist?.get(position)?.sender
        holder.smsBody.text = smslist?.get(position)?.body
        holder.smsDate.text = Date(smslist?.get(position)?.date?:0).toString()
    }


}