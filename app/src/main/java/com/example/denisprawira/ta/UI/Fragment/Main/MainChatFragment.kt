package com.example.denisprawira.ta.UI.Fragment.Main

import android.content.Intent
import com.google.firebase.firestore.FirebaseFirestore
import com.example.denisprawira.ta.SessionManager.UserSessionManager
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.denisprawira.ta.UI.ActivityResult.PmiActivityResult
import com.example.denisprawira.ta.UI.ActivityResult.UserChat
import com.example.denisprawira.ta.UI.Chat.ChatRoomActivity
import com.example.denisprawira.ta.databinding.FragmentMainChatBinding

import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.Filters
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.ui.channel.list.header.viewmodel.ChannelListHeaderViewModel
import io.getstream.chat.android.ui.channel.list.header.viewmodel.bindView
import io.getstream.chat.android.ui.channel.list.viewmodel.ChannelListViewModel
import io.getstream.chat.android.ui.channel.list.viewmodel.bindView
import io.getstream.chat.android.ui.channel.list.viewmodel.factory.ChannelListViewModelFactory


import java.util.*

class MainChatFragment : Fragment() {
    var usersCollection = FirebaseFirestore.getInstance().collection("Users")
    var client = ChatClient.instance()
    var userSessionManager: UserSessionManager? = null
    var extraData: MutableMap<String, String> = HashMap()
    var user = User()
    private var _binding: FragmentMainChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainChatBinding.inflate(
            layoutInflater
        )
        //addUser();
        userSessionManager = UserSessionManager(context)

        binding.channelListHeaderView.setOnUserAvatarClickListener{
            Toast.makeText(context,"avatarS",Toast.LENGTH_SHORT).show();
        }

        binding.channelListHeaderView.setOnActionButtonClickListener{
            val intent = Intent(context,UserChat::class.java)
            startActivity(intent)
        }

        binding.channelsView.setChannelItemClickListener(){channel->
            val intent = Intent(context, ChatRoomActivity::class.java);
            intent.putExtra("cid",channel.cid)
            startActivity(intent)
        }

        if (client.getCurrentUser() == null) {
            usersCollection.whereEqualTo("userId", userSessionManager!!.id).get()
                .addOnSuccessListener { queryDocumentSnapshots ->
                    user.id = queryDocumentSnapshots.documents[0].id
                    extraData["name"] = queryDocumentSnapshots.documents[0]["userName"].toString()
                    extraData["image"] = queryDocumentSnapshots.documents[0]["userPhoto"].toString()
                    user.extraData = Collections.unmodifiableMap(extraData)
                    val token = client.devToken(user.id)
                    client.connectUser(user, token).enqueue{result->
                        if(result.isSuccess){
                            Toast.makeText(context,"connect user success",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context,"connect user failed",Toast.LENGTH_SHORT).show();
                        }
                    };
                    setupChannels(user)
                }
        } else {
            setupChannels(client.getCurrentUser()!!)
        }
        return binding.root
    }

    private fun setupChannels(user: User) {
        val filters = Filters.and(
            Filters.eq("type", "messaging"),
            Filters.`in`("members", listOf(user.id))
        )
        val viewModelFactory = ChannelListViewModelFactory(
            filters,
            ChannelListViewModel.DEFAULT_SORT
        )
        val listViewModel: ChannelListViewModel by viewModels { viewModelFactory }
        val listHeaderViewModel: ChannelListHeaderViewModel by viewModels()

        listHeaderViewModel.bindView(binding.channelListHeaderView, viewLifecycleOwner)
        listViewModel.bindView(binding.channelsView, viewLifecycleOwner)
    }

    //        extraData.put("name",user)
    //        User user = new User();
    //        user.setId("tutorial-droid");
    //        user.setExtraData();
    //    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}