package com.example.denisprawira.ta.UIPMI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.denisprawira.ta.Api.ApiUtils
import com.example.denisprawira.ta.Api.Model.Pmi
import com.example.denisprawira.ta.Api.Model.Pmi2
import com.example.denisprawira.ta.Api.Model.User
import com.example.denisprawira.ta.Api.Model.UserFirestore
import com.example.denisprawira.ta.Api.UserService
import com.example.denisprawira.ta.R
import com.example.denisprawira.ta.SessionManager.UserSessionManager
import com.example.denisprawira.ta.UI.MainActivity
import com.example.denisprawira.ta.UIPMI.Fragment.Main.HomePmiFragment
import com.example.denisprawira.ta.UIPMI.Fragment.Main.MainPmiChatFragment
import com.example.denisprawira.ta.UIPMI.Fragment.Main.MainPmiNotificationFragment
import com.example.denisprawira.ta.Values.GlobalValues
import com.example.denisprawira.ta.databinding.ActivityMainPmiBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import io.getstream.chat.android.client.ChatClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainPmiActivity : AppCompatActivity() {

    private lateinit var binding :ActivityMainPmiBinding

    lateinit var pmiMainFragment: Fragment
    lateinit var userService : UserService
    var db = FirebaseFirestore.getInstance()
    val chatClient = ChatClient.instance();
    lateinit var userSessionManager: UserSessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPmiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userSessionManager = UserSessionManager(this)
        pmiMainFragment = Fragment()
        userService = ApiUtils.getUserService()
        binding.BottomNavigationViewPmi.setOnNavigationItemSelectedListener(bottomNavigationPmi)
        if(!userSessionManager.logStatus){
            val email: String? = intent.extras?.get("email").toString()
            val token : String? = intent.extras?.get("token").toString()
            loadCurrentUser(email, token)
        }
//        if(!pmiSessionManager.idFirebase.isEmpty()){
//            Toast.makeText(this,pmiSessionManager.idFirebase,Toast.LENGTH_SHORT).show()
//        }
//        if(){
//
//        }
        checkSession()
        //loadCurerntUser(intent.extras?.get("email").toString(),intent.extras?.get("token").toString());
        //Toast.makeText(this@MainPmiActivity,"email: "+intent.extras?.get("email").toString(),Toast.LENGTH_SHORT).show()
        //Toast.makeText(this@MainPmiActivity,"token: "+intent.extras?.get("token").toString(),Toast.LENGTH_SHORT).show()
    }

    private val bottomNavigationPmi = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.homePmi -> {
                pmiMainFragment = HomePmiFragment()
            }
            R.id.chatPmi -> {
                pmiMainFragment = MainPmiChatFragment()
            }
            R.id.notificationPmi -> {
                pmiMainFragment = MainPmiNotificationFragment()
            }
            R.id.settingsPmi -> {
                userSessionManager.clearSession()
                chatClient.disconnect()
                val intent = Intent(this@MainPmiActivity,MainActivity::class.java)
                startActivity(intent)
            }
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.fragmentContainerView2.id,pmiMainFragment).commit()
        true

    }

    private fun loadCurrentUser(email: String?, token: String?){
        GlobalValues.setTokenApi(token)
        userService.showCurrentUser(token, email).enqueue(object: Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                userSessionManager.setSession(response.body())
                userSessionManager.setTokenApi(GlobalValues.getTokenApi())
                loadCurrentPmi(userSessionManager.id)
                //checkDataExist(userSessionManager)
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("result",t.message.toString())
            }
        })
    }

    private fun loadCurrentPmi(userId:String?){
        userService.showCurrentPmi(userId).enqueue(object:Callback<Pmi2>{
            override fun onResponse(call: Call<Pmi2>, response: Response<Pmi2>) {
                userSessionManager.setSessionPmi(response.body())
                Log.e("result pmi name", userSessionManager.name)
                Log.e("result pmi name", userSessionManager.email)
                Log.e("result pmi name", userSessionManager.bloodTypeName)
                Log.e("result pmi name", userSessionManager.role)
                Log.e("result pmi name", userSessionManager.pmiName)
                Log.e("result pmi name", userSessionManager.pmiAddress)
            }

            override fun onFailure(call: Call<Pmi2>, t: Throwable) {
                Log.e("result",t.message.toString())
            }
        })
    }


    private fun loadPmiData(){

    }

    //###############################//
    //        CUSTOMIZE FUNCTION     //
    //###############################//
    private fun checkSession() {
        if (userSessionManager.getLogStatus()) {
//            Glide.with(HomeActivity.this).load(sessionManager.getPhoto()).into(header_image);
//            header_nama.setText(sessionManager.getName());
//            header_email.setText(sessionManager.getEmail());
//            updateToken(sessionManager.getChatId());
            //checkDataExist(userSessionManager)
        } else {
            if (intent.extras != null) {
                val currentUserEmail = Objects.requireNonNull(intent.extras)
                    ?.getString("email")
                val currentUserToken = Objects.requireNonNull(intent.extras)
                    ?.getString("token")
                loadCurrentUser(currentUserEmail, currentUserToken)
            }
        }
    }


    fun checkDataExist(userSessionManager: UserSessionManager) {
        val user: CollectionReference = db.collection("Users")
        user.whereEqualTo("userId", userSessionManager.id).whereEqualTo("userStatus","pmi").get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                if (!queryDocumentSnapshots.isEmpty) {
                    Log.e("result","query is empty")
                    //Toast.makeText(this@MainPmiActivity, "test", Toast.LENGTH_SHORT).show()
                    if (queryDocumentSnapshots.documents.size != 0) {
                        userSessionManager.idFirebase = queryDocumentSnapshots.getDocuments().get(0).getId()
                    }
                } else {
                   // Toast.makeText(this@MainPmiActivity, "document empty", Toast.LENGTH_SHORT).show()
                    addUser(userSessionManager)
                }
            }.addOnFailureListener { e ->
                Toast.makeText(this@MainPmiActivity, e.message, Toast.LENGTH_SHORT).show()
            }
    }

    fun addUser(userSessionManager: UserSessionManager) {
        val notifs = db.collection("Users")
        val userFirestore = UserFirestore()
        userFirestore.userId = userSessionManager.id
        userFirestore.userName = userSessionManager.name
        userFirestore.userPhoto = userSessionManager.img
        userFirestore.userStatus = "pmi"
        notifs.add(userFirestore).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val docRef = task.result
                val key = docRef.id
                userSessionManager.idFirebase = key
                notifs.document(key).update("userUID", key).addOnCompleteListener {
                    Log.e(
                        "insert to firebase",
                        "berhasil gan"
                    )
                }
            }
        }
    }


}

