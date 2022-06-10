package com.example.denisprawira.ta.UIPMI.Fragment.Main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.denisprawira.ta.R
import com.example.denisprawira.ta.UIPMI.MainPmiActivity
import com.example.denisprawira.ta.UIPMI.RequestedEventActivity
import com.example.denisprawira.ta.databinding.FragmentHomePmiBinding
import com.example.denisprawira.ta.databinding.FragmentMainChatBinding

class HomePmiFragment : Fragment() {

    private var _binding: FragmentHomePmiBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomePmiBinding.inflate(layoutInflater)

        binding.pmiRequestedEvent.setOnClickListener {
            val intent = Intent(context, RequestedEventActivity::class.java)
            startActivity(intent)
        }
        return binding.root


    }
}