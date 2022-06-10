package com.example.denisprawira.ta.UI.Event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.denisprawira.ta.R
import com.example.denisprawira.ta.Adapter2.ViewPagerAdapter.UserRequestViewPagerAdapter
import com.example.denisprawira.ta.databinding.ActivityRequestUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class RequestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRequestUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPagerRequestedEvent.adapter = UserRequestViewPagerAdapter(this)
        var tabLayoutMediator = TabLayoutMediator(binding.tabLayoutRequestedEvent,binding.viewPagerRequestedEvent,
            object: TabLayoutMediator.TabConfigurationStrategy{
                override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                    when(position){
                        0->{
                            tab.setText("Acara")
                            tab.setIcon(R.drawable.ic_event_request)
                        }
                        1->{
                            tab.text = "Pendonor"
                            tab.setIcon(R.drawable.ic_add_event)
                        }

                    }
                }
            }
        )
        tabLayoutMediator.attach()
    }
}