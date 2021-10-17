package com.example.drizzle.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drizzle.R
import com.example.drizzle.adapter.MessageAdapter
import com.example.drizzle.model.MessageModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {


    private lateinit var adapter: MessageAdapter
    private lateinit var list: ArrayList<MessageModel>
    private lateinit var mDBRef: DatabaseReference

    var receiverRoom: String? = null
    var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        mDBRef = FirebaseDatabase.getInstance().getReference()

        val name =  intent.getStringExtra("name")
        val receivedUid =  intent.getStringExtra("uid")

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        senderRoom = receiverRoom + senderUid
        receiverRoom = senderRoom + receivedUid

        //logic for entering data to database
        mDBRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    for(postSnapshot in snapshot.children){

                        val message = postSnapshot.getValue(MessageModel::class.java)
                        list.add(message!!)
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

        //adding message to database
        chatButton.setOnClickListener {
            val message = messageBox.text.toString()
            val messageObject = MessageModel(message,senderUid)

            mDBRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnCompleteListener {

                    mDBRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            messageBox.setText("")
        }

        list = ArrayList()
        supportActionBar?.title = name
        adapter = MessageAdapter(list)
        chatRecyclerView.adapter = adapter
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}