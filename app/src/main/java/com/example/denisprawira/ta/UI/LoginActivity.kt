package com.example.denisprawira.ta.UI

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import com.example.denisprawira.ta.Api.UserService
import android.os.Bundle
import com.example.denisprawira.ta.R
import com.example.denisprawira.ta.Api.ApiUtils
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.denisprawira.ta.Api.Model.Response
import com.example.denisprawira.ta.Api.Status
import com.example.denisprawira.ta.UI.HomeActivity
import com.example.denisprawira.ta.UIPMI.MainPmiActivity
import com.example.denisprawira.ta.UIPMI.PmiChooserActivity
import com.example.denisprawira.ta.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding;
    var email: String? = null
    var password: String? = null
    var userService: UserService? =  ApiUtils.getUserService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener(View.OnClickListener {
            login(
                binding.loginEmail.getText().toString(),
                binding.loginPassword.getText().toString()
            )
        })
    }

    fun login(email: String?, password: String?) {
        userService!!.login(email, password).enqueue(object : Callback<Response?> {
            override fun onResponse(call: Call<Response?>, response: retrofit2.Response<Response?>){
                assert(response.body() != null)
                if (response.body()!!.status == Status.success) {
                    if(response.body()!!.getUserStatus().equals("user")){
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        intent.putExtra("email", email)
                        intent.putExtra("token", response.body()!!.getToken())
                        startActivity(intent)
                        Log.e("result","user Status: user")
                    }else if(response.body()!!.getUserStatus().equals("pmi")){
                        Log.e("result","userstatus: pmi")
                        val intent = Intent(this@LoginActivity, PmiChooserActivity::class.java)
                        intent.putExtra("email", email)
                        intent.putExtra("token", response.body()!!.getToken())
                        startActivity(intent)
                    }
                }else{
                    Log.e("result", response.body()!!.message)
                }
            }
            override fun onFailure(call: Call<Response?>, t: Throwable) {
                Log.e("result",t.message.toString())
            }
        })
    }
}