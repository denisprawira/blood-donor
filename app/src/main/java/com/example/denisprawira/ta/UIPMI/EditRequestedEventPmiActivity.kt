package com.example.denisprawira.ta.UIPMI

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.denisprawira.ta.Api.ApiUtils
import com.example.denisprawira.ta.Api.Model.Event
import com.example.denisprawira.ta.Api.Model.Response
import com.example.denisprawira.ta.Api.UserService
import com.example.denisprawira.ta.Helper.TimeConverter
import com.example.denisprawira.ta.R
import com.example.denisprawira.ta.Values.GlobalValues
import com.example.denisprawira.ta.databinding.ActivityEditRequestedEventPmiBinding
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.plugins.places.picker.PlacePicker
import com.mapbox.mapboxsdk.plugins.places.picker.model.PlacePickerOptions
import retrofit2.Call
import retrofit2.Callback
import java.util.*

class EditRequestedEventPmiActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding : ActivityEditRequestedEventPmiBinding

    var activityLocationResultLauncher: ActivityResultLauncher<Intent>? = null

    var calendar = Calendar.getInstance()
    var day: Int = calendar.get(Calendar.DAY_OF_MONTH)
    var month: Int = calendar.get(Calendar.MONTH)
    var year: Int = calendar.get(Calendar.YEAR)


    lateinit var event :Event
    var eventResult: Event = Event()
    var date: String = ""

//    var eventId:String = ""
//    var eventTitle:String = ""
//    var eventInstitution:String = ""
//    var eventDescription:String = ""
//    var eventTarget:String = ""
//    var eventLat:String = ""
//    var eventLng:String = ""
//    var eventAddress:String = ""
//    var eventDateStart:String = ""
//    var  eventDateEnd:String = ""


    var TIME_START = 0
    var TIME_END = 1
    var hour = calendar[Calendar.HOUR]
    var minute = calendar[Calendar.MINUTE]

    var userService:UserService = ApiUtils.getUserService()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token))
        binding = ActivityEditRequestedEventPmiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.requestedEditEventPmiBack.setOnClickListener{
            onBackPressed()
        }


        if(intent.hasExtra("event")){
            event = intent.extras?.get("event") as Event
            binding.eventEditPmiTitle.setText(event.title)
            binding.eventEditPmiInstitution.setText(event.institution)
            binding.eventEditPmiDescription.setText(event.description)
            binding.eventEditPmiTarget.setText(event.target)
            binding.eventEditPmiDate.setText(TimeConverter.convertDateTimeToDate(event.dateStart))
            binding.eventEditPmiTimeStart.setText(TimeConverter.convertDateTimeToTime(event.dateStart))
            binding.eventEditPmiTimeEnd.setText(TimeConverter.convertDateTimeToTime(event.dateEnd))


            binding.eventEditPmiDate.setOnClickListener(this)
            binding.eventEditPmiTimeStart.setOnClickListener(this)
            binding.eventEditPmiTimeEnd.setOnClickListener(this)
            binding.eventEditPmiSubmit.setOnClickListener(this)

            binding.eventEditPmiDate.isFocusable = false;
            binding.eventEditPmiTimeStart.isFocusable = false;
            binding.eventEditPmiTimeEnd.isFocusable = false;

            binding.eventEditPmiLocation.setText(event.address)
            binding.eventEditPmiLocation.isClickable = true
            binding.eventEditPmiLocation.isFocusable = false

            if(!binding.eventEditPmiLocation.text.isEmpty()){
                binding.eventEditPmiContainerIcon.visibility = View.GONE
            }

            binding.eventEditPmiLocation.setOnClickListener{

                placePicker()
            }

            binding.eventEditPmiLocation.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, count: Int, i2: Int) {

                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, count: Int, i2: Int) {
                    if(charSequence.length>0){
                        binding.eventEditPmiContainerIcon.visibility = View.GONE
                    }else{
                        binding.eventEditPmiContainerIcon.visibility = View.VISIBLE
                    }
                }

                override fun afterTextChanged(editable: Editable) {

                }
            })
        }

        onLocationResult()


    }

    fun placePicker() {
        val intent = PlacePicker.IntentBuilder()
            .accessToken(getString(R.string.mapbox_access_token))
            .placeOptions(
                PlacePickerOptions.builder()
                    .statingCameraPosition(
                        CameraPosition.Builder()
                            .target(LatLng(-8.588641, 115.297787)).zoom(16.0).build()
                    )
                    .build()
            )
            .build(this)
        activityLocationResultLauncher!!.launch(intent)
    }

    fun onLocationResult() {
        activityLocationResultLauncher = registerForActivityResult<Intent, ActivityResult>(ActivityResultContracts.StartActivityForResult()) { result ->
            val carmenFeature = PlacePicker.getPlace(result.data)
            if (carmenFeature != null) {
                val point = carmenFeature.geometry()
                val latitude = Point.fromJson(point!!.toJson()).latitude()
                val longitude = Point.fromJson(point!!.toJson()).longitude()
                binding.eventEditPmiLocation.setText(carmenFeature.placeName())
                event.lat = latitude.toString()
                event.lng = longitude.toString()
                event.address = carmenFeature.placeName()
                Log.e("carmen", carmenFeature.placeName().toString())
                Log.e("lat",event.lat)
                Log.e("long",event.lng)
            }
            binding.eventEditPmiLocation.setText(carmenFeature?.placeName())
        }
    }
//
//    override fun onBackPressed() {
//
//        eventResult = Event()
//        eventResult.title         = event.title
//        eventResult.institution   = event.institution
//        eventResult.description   = event.description
//        eventResult.lat           = event.lat
//        eventResult.lng           = event.lng
//        eventResult.address       = event.address
//        eventResult.target        = event.target
//        eventResult.dateStart     = date+" "+event.dateStart
//        eventResult.dateEnd       = date+" "+event.dateEnd
//        val intent = Intent()
//        intent.putExtra("eventResult", eventResult)
//        setResult(RESULT_OK, intent)
//        super.onBackPressed()
//    }


    fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(this,
            { view, year, month, dayOfMonth ->
                val monthReal = month + 1
                date = "$year-$monthReal-$dayOfMonth"
                val dateTime = "$dayOfMonth/$monthReal/$year"
                binding.eventEditPmiDate.setText(dateTime)
            }, year, month, day
        )
        datePickerDialog.show()
    }

    fun showTimePickerDialog(code: Int) {
        val timePickerDialog = TimePickerDialog(this,
            { view, hourOfDay, minute ->
                    val lengthMinute = (Math.log10(minute.toDouble()) + 1).toInt()
                    val lengthHour = (Math.log10(hourOfDay.toDouble()) + 1).toInt()
                    if (lengthMinute < 2 && lengthHour < 2) {
                        if(code==TIME_START){
                            binding.eventEditPmiTimeStart.setText("0$hourOfDay:0$minute")
                        }else{
                            binding.eventEditPmiTimeEnd.setText("0$hourOfDay:0$minute")
                        }
                    } else if (lengthHour < 2) {
                        if(code==TIME_START){
                            binding.eventEditPmiTimeStart.setText("0$hourOfDay:$minute")
                        }else{
                            binding.eventEditPmiTimeEnd.setText("0$hourOfDay:$minute")
                        }
                    } else if (lengthMinute < 2) {
                        if(code==TIME_START){
                            binding.eventEditPmiTimeStart.setText("$hourOfDay:0$minute")
                        }else{
                            binding.eventEditPmiTimeEnd.setText("$hourOfDay:0$minute")
                        }
                    } else {
                        if(code==TIME_START){
                            binding.eventEditPmiTimeStart.setText("$hourOfDay:$minute")
                        }else{
                            binding.eventEditPmiTimeEnd.setText("$hourOfDay:$minute")
                        }
                    }

                    if(code==TIME_START){
                        event.dateStart= binding.eventEditPmiTimeStart.text.toString()
                    }else{
                        event.dateEnd = binding.eventEditPmiTimeEnd.text.toString()
                    }


            }, hour, minute, true
        )
        timePickerDialog.show()
    }

    override fun onClick(view: View?) {
        if(view==binding.eventEditPmiDate){
            showDatePickerDialog()
        }else if(view == binding.eventEditPmiTimeStart){
            showTimePickerDialog(TIME_START)
        }else if(view == binding.eventEditPmiTimeEnd){
            showTimePickerDialog(TIME_END)
        }else if(view == binding.eventEditPmiSubmit){
            if(date.isEmpty()){
               date = TimeConverter.convertDateMonthToDate(binding.eventEditPmiDate.text.toString())
            }
            event.dateStart = binding.eventEditPmiTimeStart.text.toString()
            event.dateEnd = binding.eventEditPmiTimeEnd.text.toString()

           event.id = event.id
           event.title = binding.eventEditPmiTitle.text.toString()
           event.institution = binding.eventEditPmiInstitution.text.toString()
           event.description = binding.eventEditPmiDescription.text.toString()
           event.target      = binding.eventEditPmiTarget.text.toString()
           event.dateStart  = date+" "+event.dateStart
           event.dateEnd   = date+" "+event.dateEnd

//           Log.e("result",event.id)
//           Log.e("result",event.title)
//           Log.e("result",event.institution)
//           Log.e("result",event.description)
//           Log.e("result",event.lat)
//           Log.e("result",event.lng)
//           Log.e("result",event.address)
//           Log.e("result",event.target)
//           Log.e("result",event.dateStart)
//           Log.e("result",event.dateEnd)
           updateEvent(event.id,event.title,event.institution,event.description,event.lat,event.lng,event.address,event.target,event.dateStart,event.dateEnd)

        }
    }

    fun updateEvent(eventId:String,
                    eventTitle:String,
                    eventInstitution:String,
                    eventDescription:String,
                    eventLat:String,
                    eventLng: String,
                    eventAddress:String,
                    eventTarget:String,
                    eventDateStart:String,
                    eventDateEnd:String){
        userService.updateEventPmi(eventId,eventTitle,eventInstitution,eventDescription,eventLat,eventLng,eventAddress,eventTarget,eventDateStart,eventDateEnd).enqueue(object: Callback<Response>{
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                eventResult = Event()
                eventResult.title         = eventTitle
                eventResult.institution   = eventInstitution
                eventResult.description   = eventDescription
                eventResult.lat           = eventLat
                eventResult.lng           = eventLng
                eventResult.address       = eventAddress
                eventResult.target        = eventTarget
                eventResult.dateStart     = eventDateStart
                eventResult.dateEnd       = eventDateEnd
                Toast.makeText(this@EditRequestedEventPmiActivity, response.body()?.message.toString(),Toast.LENGTH_SHORT).show()
                val intent = Intent()
                intent.putExtra("eventResult", eventResult)
                setResult(RESULT_OK, intent)
                finish()

            }
            override fun onFailure(call: Call<Response>, t: Throwable) {
                Log.e("result",t.message.toString())
            }

        })
    }


}