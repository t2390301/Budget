package com.example.budget.view.fragments.sms

import android.icu.text.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.R
import com.example.budget.model.constants.DEFAULT_BANK_IMAGE
import com.example.budget.model.domain.SmsData
import java.util.Locale

class SMSFragmentAdapter(private var smslist: List<SmsData>?) : RecyclerView.Adapter<SMSFragmentAdapter.SMSViewHolder>(){

    companion object{
        const val TAG ="SMSFragmentAdapter"

    }
    class SMSViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val bankImage = itemView.findViewById<ImageView>(R.id.img_bank)
        val smsBody = itemView.findViewById<TextView>(R.id.sms_text)
        val smsDate = itemView.findViewById<TextView>(R.id.sms_date_and_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SMSViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_sms_fragment, parent, false)
        return SMSViewHolder(itemView)

    }

    fun setList(list: List<SmsData>){

        this.smslist = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int =
        smslist?.size?:0


    override fun onBindViewHolder(holder: SMSViewHolder, position: Int) {
        holder.smsBody.text = smslist?.get(position)?.body
        holder.smsDate.text = formatToRusShortDate.format(smslist?.get(position)?.date?:0)
        val image = smslist?.get(position)?.bankImage
        if (image != null){
            holder.bankImage.setImageResource(image)
        } else {
            holder.bankImage.setImageResource(DEFAULT_BANK_IMAGE)
        }
    }


    private var formatToRusShortDate: DateFormat =
        DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale("ru"))
}