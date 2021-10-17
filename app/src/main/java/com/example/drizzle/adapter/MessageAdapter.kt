package com.example.drizzle.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drizzle.R
import com.example.drizzle.model.MessageModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.received_message.view.*
import kotlinx.android.synthetic.main.sent_message.view.*

class MessageAdapter(val list: ArrayList<MessageModel>) :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2

    override fun getItemViewType(position: Int): Int {

        val currentMessage = list[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SENT
        }else{
            return ITEM_RECEIVE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == 1){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.received_message, parent, false)
            return ReceivedViewHolder(view)
        }
        else{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.sent_message, parent, false)
            return SentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val data = list[position]
        if (holder.javaClass == SentViewHolder::class.java){

            //do stuff for sentViewHolder
            val viewholder = holder as SentViewHolder
            holder.itemView.text_sentMessage.text = data.message
        }
        else{

            //do stuff for receivedViewHolder

            val viewolder = holder as ReceivedViewHolder
            holder.itemView.text_receivedMessage.text = data.message
        }
    }

    override fun getItemCount(): Int {
       return list.size
    }

    inner class SentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    inner class ReceivedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}