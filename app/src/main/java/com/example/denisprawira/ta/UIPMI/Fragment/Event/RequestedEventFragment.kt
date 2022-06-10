package com.example.denisprawira.ta.UIPMI.Fragment.Event

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.denisprawira.ta.Adapter2.PmiRequestedEventAdapter
import com.example.denisprawira.ta.Api.ApiUtils
import com.example.denisprawira.ta.Api.Model.Event
import com.example.denisprawira.ta.Api.Model.Pmi
import com.example.denisprawira.ta.Api.UserService
import com.example.denisprawira.ta.Model.User
import com.example.denisprawira.ta.R
import com.example.denisprawira.ta.SessionManager.PmiSessionManager
import com.example.denisprawira.ta.Values.GlobalValues
import com.example.denisprawira.ta.databinding.FragmentRequestEventBinding
import com.example.denisprawira.ta.databinding.FragmentRequestedEventBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestedEventFragment : Fragment() {

    private var _binding:FragmentRequestedEventBinding?= null
    private val binding get() = _binding!!

    val userService :UserService = ApiUtils.getUserService()
    lateinit var pmiSessionManager : PmiSessionManager
    private val requestEventAdapter by lazy { PmiRequestedEventAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRequestedEventBinding.inflate(layoutInflater)
        pmiSessionManager = PmiSessionManager(context)
        setupRecyclerView()
        loadRequestedEvent(pmiSessionManager.id,GlobalValues.EVENT_PENDING)

        return binding.root
    }

    private fun loadRequestedEvent(idPmi:String,status:String){
        userService.loadRequestedEvent(idPmi,status).enqueue(object : Callback<List<Event>>{
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                val events: List<Event> = response.body()!!;
                if(events.size==0){
                    binding.textViewEventRequestedRequest.visibility = View.VISIBLE
                }else{
                    binding.textViewEventRequestedRequest.visibility = View.GONE
                }
                requestEventAdapter.setData(events)
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                Log.e("result",t.message.toString())
            }

        })
    }

    private fun setupRecyclerView() {
        binding.eventRequestedRecycyerView.layoutManager = LinearLayoutManager(requireContext())
        binding.eventRequestedRecycyerView.adapter = requestEventAdapter
    }

    override fun onResume() {
        super.onResume()
        loadRequestedEvent(pmiSessionManager.id,GlobalValues.EVENT_PENDING)
    }
}