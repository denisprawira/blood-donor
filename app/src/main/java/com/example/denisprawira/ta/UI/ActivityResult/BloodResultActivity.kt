package com.example.denisprawira.ta.UI.ActivityResult

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.denisprawira.ta.Api.ApiUtils
import com.example.denisprawira.ta.Api.Model.Blood
import com.example.denisprawira.ta.Api.UserService
import com.example.denisprawira.ta.databinding.ActivityBloodResultBinding
import com.example.denisprawira.ta.databinding.ActivityUserChatBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BloodResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBloodResultBinding
    val userService :UserService = ApiUtils.getUserService();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBloodResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showBloodType()
    }

    fun showBloodType(){
        userService.showBloodType().enqueue(object: Callback<List<Blood>> {
            override fun onResponse(call: Call<List<Blood>>, response: Response<List<Blood>>) {
               Toast.makeText(this@BloodResultActivity, response.body()?.get(0)?.bloodType,Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<List<Blood>>, t: Throwable) {
                Log.e("result",t.message.toString())
            }

        })
    }
}