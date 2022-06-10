package com.example.denisprawira.ta.UI.Chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels

import com.example.denisprawira.ta.R
import com.example.denisprawira.ta.databinding.ActivityChatRoomBinding
import com.example.denisprawira.ta.databinding.ActivityUserChatBinding
import com.getstream.sdk.chat.viewmodel.MessageInputViewModel
import com.getstream.sdk.chat.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.ui.message.input.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.header.viewmodel.MessageListHeaderViewModel
import io.getstream.chat.android.ui.message.list.header.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.viewmodel.factory.MessageListViewModelFactory

class ChatRoomActivity : AppCompatActivity() {


    private lateinit var binding: ActivityChatRoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatRoomBinding.inflate(layoutInflater)
//        if(intent.extras?.get("cid")!==null){
//            var cid:String = intent.extras?.get("cid").toString()
//            Toast
//            val fragmentChatRoom  = ChatRoomFragment.newInstance(cid)
//            val transaction = supportFragmentManager.beginTransaction()
//            transaction.add(binding.fragmentContainerChat.id,fragmentChatRoom)
//            transaction.commit()
//        }

        setContentView(binding.root)
    }



}