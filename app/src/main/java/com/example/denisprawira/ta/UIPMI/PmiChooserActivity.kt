package com.example.denisprawira.ta.UIPMI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.denisprawira.ta.R
import com.example.denisprawira.ta.UI.HomeActivity
import com.example.denisprawira.ta.databinding.ActivityMainPmiBinding
import com.example.denisprawira.ta.databinding.PmiChooserActivityLayoutBinding

class PmiChooserActivity : AppCompatActivity() {
    lateinit var email : String
    lateinit var token : String
    private lateinit var binding : PmiChooserActivityLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PmiChooserActivityLayoutBinding.inflate(layoutInflater)
        email = intent.extras?.get("email").toString()
        token = intent.extras?.get("token").toString()

        binding.choosePmi.setOnClickListener{
            val intent = Intent(this@PmiChooserActivity, MainPmiActivity::class.java)
            intent.putExtra("email", email)
            intent.putExtra("token", token)
            startActivity(intent)
        }
        binding.chooseUser.setOnClickListener{
            val intent = Intent(this@PmiChooserActivity, HomeActivity::class.java)
            intent.putExtra("email", email)
            intent.putExtra("token", token)
            startActivity(intent)
        }

        setContentView(binding.root)
    }
}