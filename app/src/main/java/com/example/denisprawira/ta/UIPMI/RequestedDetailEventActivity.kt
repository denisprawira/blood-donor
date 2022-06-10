package com.example.denisprawira.ta.UIPMI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.denisprawira.ta.Api.ApiUtils
import com.example.denisprawira.ta.Api.Model.Event
import com.example.denisprawira.ta.Api.Model.Pmi
import com.example.denisprawira.ta.Api.Model.User
import com.example.denisprawira.ta.Api.UserService
import com.example.denisprawira.ta.Helper.TimeConverter
import com.example.denisprawira.ta.R
import com.example.denisprawira.ta.SessionManager.PmiSessionManager
import com.example.denisprawira.ta.Values.GlobalValues
import com.example.denisprawira.ta.databinding.ActivityRequestedDetailEventBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import io.getstream.chat.android.client.ChatClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestedDetailEventActivity :  AppCompatActivity(), OnMapReadyCallback{

    private lateinit var event: Event
    private lateinit var binding : ActivityRequestedDetailEventBinding
    private val userService : UserService = ApiUtils.getUserService()
    var usersCollection = FirebaseFirestore.getInstance().collection("Users")
    lateinit var userId :String
    val client = ChatClient.instance()
    var user = io.getstream.chat.android.client.models.User()
    lateinit  var userData :User
    lateinit var pmiSessionManager: PmiSessionManager
    var extraData: MutableMap<String, String> = HashMap()

    var activityEditEventResult: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // Mapbox.getInstance(this, getString(R.string.mapbox_access_token))
        binding = ActivityRequestedDetailEventBinding.inflate(layoutInflater)
        pmiSessionManager = PmiSessionManager(this)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.requestedDetailEventPmiMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        var view = View.inflate(this@RequestedDetailEventActivity,R.layout.dialog_approval,null)
        val builder = AlertDialog.Builder(this@RequestedDetailEventActivity)
        builder.setView(view)
        val dialog = builder.create()

        binding.requestedEventPmiEdit.setOnClickListener{
            val intent = Intent(this@RequestedDetailEventActivity,EditRequestedEventPmiActivity::class.java)
            intent.putExtra("event",event)
            activityEditEventResult?.launch(intent)
        }


        binding.requestedEventPmiConfirm.setOnClickListener{
            dialog.show()
        }
        val button = view.findViewById<View>(R.id.dialog_approval_close)
        button.setOnClickListener{
            dialog.dismiss()
        }

        val dialogApprove = view.findViewById<View>(R.id.dialog_approve).setOnClickListener{
            acceptEvent(event.id,GlobalValues.EVENT_APPROVED)
            it.setBackgroundResource(R.drawable.theme_button_solid_radius_disapprove)
            dialog.dismiss()
        }
        val dialogDisapprove = view.findViewById<View>(R.id.dialog_disapprove).setOnClickListener{
            acceptEvent(event.id,GlobalValues.EVENT_DISAPPROVED)
            it.setBackgroundResource(R.drawable.theme_button_solid_radius)
            dialog.dismiss()
        }

        activityEditEventResult = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val eventResult : Event? = result.data?.getParcelableExtra<Event>("eventResult")
                if(eventResult!=null){
                    binding.requestedEventPmiTitle.setText(eventResult.title)
                    binding.requestedEventPmiInstitution.setText(eventResult.institution)
                    binding.requestedEventPmiDescription.setText(eventResult.description)
                    binding.requestedEventPmiAddress.setText(eventResult.address)
                    binding.requestedEventPmiTarget.setText(eventResult.target)
                    binding.requestedEventPmiDateTime.setText(TimeConverter.convertDateTimeToDate(eventResult.dateStart+":00")+", "+ TimeConverter.convertDateTimeToTime(eventResult.dateStart+":00")+" - "+TimeConverter.convertDateTimeToTime(eventResult.dateEnd+":00"))
                    event.lat = eventResult.lat
                    event.lng = eventResult.lng
                    Log.e("event result",eventResult.dateStart +" "+eventResult.dateEnd)
                    Log.e("event",event.dateStart+" "+event.dateEnd)
                }
            }else{
                Toast.makeText(this@RequestedDetailEventActivity,"result not ok",Toast.LENGTH_SHORT).show()
            }
        }



        if(intent.hasExtra("event")){
            event = intent.extras?.get("event") as Event
            if(event.status.equals(GlobalValues.EVENT_DISAPPROVED)){
              // binding.eventRequestedDetailDisapprove.setText("Hapus Permintaan")
            }
            loadUserEvent(event.idUser);
//            loadUserFirebase(event.idUser)
            //connectUser()
            binding.requestedEventPmiTitle.text = event.title
            binding.requestedEventPmiInstitution.text = event.description
            binding.requestedEventPmiDescription.text = event.institution
            binding.requestedEventPmiTarget.text = event.target
            binding.requestedEventPmiDateTime.text  =  TimeConverter.convertDateTimeToDate(event.dateStart)+", "+ TimeConverter.convertDateTimeToTime(event.dateStart)+" - "+TimeConverter.convertDateTimeToTime(event.dateEnd)
            binding.requestedEventPmiAddress.text = event.address
//            binding.requestedEventPmiConfirmButton.setBackgroundResource(R.drawable.theme_button_solid_radius)
//            binding.requestedEventPmiConfirmButton.setOnClickListener{
//                binding.requestedEventPmiConfirmButton.setBackgroundResource(R.drawable.theme_button_solid_radius_disable)
//            }

//            binding.requestedEventPmiConfirm.setOnClickListener{
//                binding.requestedEventPmiConfirm.setBackgroundResource(R.drawable.theme_button_solid_radius_disable)
//            }

            binding.requestedEventPmiBack.setOnClickListener{
                onBackPressed()
            }


        }

//        binding.eventRequestedDetailApprove.setOnClickListener{
//            acceptEvent(event.id,GlobalValues.EVENT_APPROVED)
//            onBackPressed()
//        }
//
//        binding.eventRequestedDetailDisapprove.setOnClickListener{
//            if(event.status.equals(GlobalValues.EVENT_DISAPPROVED)){
//                acceptEvent(event.id,GlobalValues.EVENT_FINISHED)
//                onBackPressed()
//            }else{
//                acceptEvent(event.id,GlobalValues.EVENT_DISAPPROVED)
//                onBackPressed()
//            }
//
//        }


    }


    override fun onResume() {
        super.onResume()

    }

    private fun loadUserEvent(userId:String){
        userService.loadUserEvent(userId).enqueue(object: Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                userData = response.body()!!
                binding.requestedEventPmiUserName.setText(userData.name)
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("result",t.message.toString())
            }

        })
    }

    private fun loadUserFirebase(idUser: String){
        usersCollection.whereEqualTo("userId", idUser).whereEqualTo("userStatus","user").get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                userId = queryDocumentSnapshots.documents.get(0).id
            }
    }

//    private fun connectUser(){
//        user.id = pmiSessionManager.idFirebase
//        extraData["name"] = pmiSessionManager.name
//        extraData["image"] = pmiSessionManager.img
//        user.extraData = Collections.unmodifiableMap(extraData)
//        val token = client.devToken(user.id)
//        client.connectUser(user, token).enqueue{result->
//            if(result.isSuccess){
//                Log.e("result","Connect User Success")
//            }else{
//                Log.e("result","Connect User Failed")
//            }
//        };
//    }

    private fun acceptEvent(idEvent: String,status:String){
        userService.acceptEvent(idEvent,status).enqueue(object :Callback<com.example.denisprawira.ta.Api.Model.Response>{
            override fun onResponse(
                call: Call<com.example.denisprawira.ta.Api.Model.Response>,
                response: Response<com.example.denisprawira.ta.Api.Model.Response>
            ) {
                Toast.makeText(this@RequestedDetailEventActivity,response.body()?.getMessage(),Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(
                call: Call<com.example.denisprawira.ta.Api.Model.Response>,
                t: Throwable
            ) {
               Log.e("result",t.message.toString())
            }

        })
    }

    override fun onMapReady(map: GoogleMap) {
        map.uiSettings.setAllGesturesEnabled(false)
        var pos : LatLng
        if(event.lat ==null || event.lng == null){
            pos = LatLng(-8.588641, 115.297787)
        }else{
            pos = LatLng(event.lat.toDouble(), event.lng.toDouble())
        }
        map.addMarker(
            MarkerOptions()
                .position(pos)
                .title("Marker in Sydney")
        )
        map.setMinZoomPreference(19f)
        map.moveCamera(CameraUpdateFactory.newLatLng(pos))
    }
}
