package com.example.denisprawira.ta.UIPMI.Fragment.Main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.denisprawira.ta.SessionManager.PmiSessionManager
import com.example.denisprawira.ta.UI.ActivityResult.UserChat
import com.example.denisprawira.ta.UI.Chat.ChatRoomActivity
import com.example.denisprawira.ta.databinding.FragmentMainPmiChatBinding
import com.google.firebase.firestore.FirebaseFirestore
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.Filters
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.ui.channel.list.header.viewmodel.ChannelListHeaderViewModel
import io.getstream.chat.android.ui.channel.list.header.viewmodel.bindView
import io.getstream.chat.android.ui.channel.list.viewmodel.ChannelListViewModel
import io.getstream.chat.android.ui.channel.list.viewmodel.bindView
import io.getstream.chat.android.ui.channel.list.viewmodel.factory.ChannelListViewModelFactory
import java.util.*

class MainPmiChatFragment : Fragment() {
    var usersCollection = FirebaseFirestore.getInstance().collection("Users")
    var client = ChatClient.instance()
    var pmiSessionManager: PmiSessionManager? = null
    var extraData: MutableMap<String, String> = HashMap()
    var user = User()
    private var _binding: FragmentMainPmiChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        _binding = FragmentMainPmiChatBinding.inflate(layoutInflater)
        pmiSessionManager = PmiSessionManager(context)

        if (client.getCurrentUser() == null) {
            usersCollection.whereEqualTo("userId", pmiSessionManager!!.id).whereEqualTo("userStatus","pmi").get()
                .addOnSuccessListener { queryDocumentSnapshots ->
                   // Toast.makeText(context,queryDocumentSnapshots.documents[0]["userName"].toString(),Toast.LENGTH_SHORT).show()
                    user.id = queryDocumentSnapshots.documents[0].id
                    extraData["name"] = queryDocumentSnapshots.documents[0]["userName"].toString()
                    extraData["image"] = queryDocumentSnapshots.documents[0]["userPhoto"].toString()
                    user.extraData = Collections.unmodifiableMap(extraData)
                    val token = client.devToken(user.id)
                    client.connectUser(user, token).enqueue{result->
                        if(result.isSuccess){
                            Toast.makeText(context,"connect user success", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context,"connect user failed", Toast.LENGTH_SHORT).show();
                        }
                    };
                    setupChannels(user)
                }
        } else {
            setupChannels(client.getCurrentUser()!!)
        }

        binding.channelListHeaderPmi.setOnUserAvatarClickListener{
            Toast.makeText(context,"avatarS",Toast.LENGTH_SHORT).show();
        }

        binding.channelListHeaderPmi.setOnActionButtonClickListener{
            val intent = Intent(context, UserChat::class.java)
            startActivity(intent)
        }

        binding.channelsViewPmi.setChannelItemClickListener(){channel->
            val intent = Intent(context, ChatRoomActivity::class.java);
            intent.putExtra("cid",channel.cid)
            startActivity(intent)
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

        listHeaderViewModel.bindView(binding.channelListHeaderPmi, viewLifecycleOwner)
        listViewModel.bindView(binding.channelsViewPmi, viewLifecycleOwner)
    }



}