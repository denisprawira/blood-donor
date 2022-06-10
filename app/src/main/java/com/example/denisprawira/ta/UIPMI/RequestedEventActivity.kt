package com.example.denisprawira.ta.UIPMI

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.denisprawira.ta.Adapter2.PmiRequestedEventAdapter
import com.example.denisprawira.ta.Api.ApiUtils
import com.example.denisprawira.ta.Api.Model.Event
import com.example.denisprawira.ta.Api.UserService
import com.example.denisprawira.ta.R
import com.example.denisprawira.ta.SessionManager.UserSessionManager
import com.example.denisprawira.ta.Values.GlobalValues
import com.example.denisprawira.ta.databinding.ActivityRequestPmiBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestedEventActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRequestPmiBinding
    private var userService: UserService =  ApiUtils.getUserService()
    private lateinit var userSessionManager: UserSessionManager
    private val requestEventAdapter by lazy { PmiRequestedEventAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestPmiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userSessionManager = UserSessionManager(this);

        setupRecyclerView();

        binding.requestedEventPmiBack.setOnClickListener{
            onBackPressed()
        }

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.eventStatus,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.requestEventPmiSpinner.setAdapter(adapter)
        binding.requestEventPmiSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(p2==0){
                    loadRequestedEvent(userSessionManager.pmiId,GlobalValues.EVENT_APPROVED);
                }else if(p2==1){
                    loadRequestedEvent(userSessionManager.pmiId,GlobalValues.EVENT_PENDING);
                }else{
                    loadRequestedEvent(userSessionManager.pmiId,GlobalValues.EVENT_DISAPPROVED);
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun loadRequestedEvent(pmiId:String?,status:String?){
        userService.loadRequestedEvent(pmiId,status).enqueue(object: Callback<List<Event>>{
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                val events: List<Event> = response.body()!!;
                requestEventAdapter.setData(events)
                if (events.size == 0) {
                    binding.requestedEventPmiEmptyMessage.visibility = View.VISIBLE
                } else {
                    binding.requestedEventPmiEmptyMessage.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                Log.e("result",t.message.toString())
            }
        })
    }

    private fun setupRecyclerView() {
        binding.requestedEventPmiRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.requestedEventPmiRecyclerview.adapter = requestEventAdapter
    }
}