package com.example.denisprawira.ta.UIPMI.Fragment.Event

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.denisprawira.ta.Adapter2.PmiRequestedEventAdapter
import com.example.denisprawira.ta.Api.ApiUtils
import com.example.denisprawira.ta.Api.Model.Event
import com.example.denisprawira.ta.Api.UserService
import com.example.denisprawira.ta.R
import com.example.denisprawira.ta.SessionManager.PmiSessionManager
import com.example.denisprawira.ta.SessionManager.UserSessionManager
import com.example.denisprawira.ta.Values.GlobalValues
import com.example.denisprawira.ta.databinding.FragmentRejectedEventBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RejectedEventFragment : Fragment() {

    private var _binding:FragmentRejectedEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var pmiSessionManager: PmiSessionManager
    private val requestEventAdapter by lazy { PmiRequestedEventAdapter() }
    val userService : UserService = ApiUtils.getUserService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRejectedEventBinding.inflate(layoutInflater)
        pmiSessionManager = PmiSessionManager(context)
        setupRecyclerView()
        loadRequestedEvent(pmiSessionManager.id, GlobalValues.EVENT_DISAPPROVED)
        return binding.root
    }

    private fun loadRequestedEvent(idPmi:String,status:String){
        userService.loadRequestedEvent(idPmi,status).enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                val events: List<Event> = response.body()!!;
                if(events.size==0){
                    binding.textViewEventRequestedRejected.visibility = View.VISIBLE
                }else{
                    binding.textViewEventRequestedRejected.visibility = View.GONE
                }
                requestEventAdapter.setData(events)
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setupRecyclerView() {
        binding.eventRejectedRecycyerView.layoutManager = LinearLayoutManager(requireContext())
        binding.eventRejectedRecycyerView.adapter = requestEventAdapter
    }

    override fun onResume() {
        super.onResume()
        loadRequestedEvent(pmiSessionManager.id, GlobalValues.EVENT_DISAPPROVED)
    }
}