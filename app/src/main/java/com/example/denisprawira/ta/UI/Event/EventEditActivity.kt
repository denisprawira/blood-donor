package com.example.denisprawira.ta.UI.Event

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
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
import androidx.core.app.ActivityCompat
import com.example.denisprawira.ta.Api.ApiUrl
import com.example.denisprawira.ta.Api.Model.Event
import com.example.denisprawira.ta.Helper.FileHelper
import com.example.denisprawira.ta.Helper.RealPathUtil.getRealPath
import com.example.denisprawira.ta.Helper.TimeConverter
import com.example.denisprawira.ta.UI.PhotoViewerActivity
import com.example.denisprawira.ta.databinding.ActivityEventEditBinding
import java.io.File
import java.util.*

class EventEditActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var binding : ActivityEventEditBinding

    var selectedFileUri: Uri? = null
    var attachment: File? = null

    var calendar = Calendar.getInstance()
    var day: Int = calendar.get(Calendar.DAY_OF_MONTH)
    var month: Int = calendar.get(Calendar.MONTH)
    var year: Int = calendar.get(Calendar.YEAR)

    var TIME_START = 0
    var TIME_END = 1
    var hour = calendar[Calendar.HOUR]
    var minute = calendar[Calendar.MINUTE]
    var date: String = ""

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    var activityFileResultLauncher: ActivityResultLauncher<Intent>? = null
    lateinit var event: Event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.eventEditDate.setOnClickListener(this)
        binding.eventEditTimeStart.setOnClickListener(this)
        binding.eventEditTimeEnd.setOnClickListener(this)
        binding.eventEditDocument.setOnClickListener(this)
        binding.eventEditDocument.isFocusable = false
        binding.eventEditDate.isFocusable = false
        binding.eventEditTimeStart.isFocusable = false
        binding.eventEditTimeEnd.isFocusable = false
        binding.eventEditBack.setOnClickListener{
            onBackPressed()
        }
        binding.eventEditDocumentView.setOnClickListener(this)

        Toast.makeText(this@EventEditActivity,"this is intent",Toast.LENGTH_SHORT).show()
        event = intent.extras?.get("event") as Event
        binding.eventEditTitle.setText(event?.title.toString())
        binding.eventEditInstitution.setText(event?.institution.toString())
        binding.eventEditDescription.setText(event?.description.toString())
        binding.eventEditTarget.setText(event?.target.toString())
        binding.eventEditDate.setText(event?.dateStart.toString())
        binding.eventEditTimeStart.setText(event?.title.toString())
        binding.eventEditTimeEnd.setText(event?.title.toString())
        binding.eventEditLocation.setText(event?.address.toString())
        binding.eventEditDate.setText(TimeConverter.convertDateTimeToDate(event?.dateStart.toString()))
        binding.eventEditTimeStart.setText(TimeConverter.convertDateTimeToTime(event?.dateStart.toString()))
        binding.eventEditTimeEnd.setText(TimeConverter.convertDateTimeToTime(event?.dateEnd.toString()))
        binding.eventEditDocument.setText(event.img.toString())
        onFileResult()
        if(!binding.eventEditLocation.text.isEmpty()){
            binding.eventEditContainerIcon.visibility = View.GONE
        }
        binding.eventEditLocation.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, count: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, count: Int, i2: Int) {
                if(charSequence.length>0){
                    binding.eventEditContainerIcon.visibility = View.GONE
                }else{
                    binding.eventEditContainerIcon.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })

        binding.eventEditSubmit.setOnClickListener{
            val intent = Intent(this@EventEditActivity,PhotoViewerActivity::class.java)
            intent.putExtra("document",ApiUrl.IP+"/storage/documents/"+ event?.img.toString())
            startActivity(intent)
        }
    }

    fun filePicker() {
        if (ActivityCompat.checkSelfPermission(
                this@EventEditActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@EventEditActivity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
            val intent = Intent()
            //sets the select file to all types of files
            intent.type = "image/*"
            //allows to select data and return it
            intent.action = Intent.ACTION_GET_CONTENT
            activityFileResultLauncher!!.launch(intent)
        }
        val intent = Intent()
        //sets the select file to all types of files
        intent.type = "image/*"
        //allows to select data and return it
        intent.action = Intent.ACTION_GET_CONTENT
        activityFileResultLauncher!!.launch(intent)
    }

    fun onFileResult() {
        activityFileResultLauncher = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                selectedFileUri = result.data!!.data
                binding.eventEditDocument.setText(
                    FileHelper.getFileName(
                        this@EventEditActivity.getContentResolver(),
                        selectedFileUri
                    )
                )
                event.img = binding.eventEditDocument.getText().toString()
                val context: Context = this.getApplicationContext()
                val path = getRealPath(
                    context,
                    selectedFileUri!!
                )
                attachment = File(path)
            }
            // Toast.makeText(getActivity(), "file: "+attachment, Toast.LENGTH_SHORT).show();
        }
    }

    fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(this,
            { view, year, month, dayOfMonth ->
                val monthReal = month + 1
                date = "$year-$monthReal-$dayOfMonth"
                val dateTime = "$dayOfMonth/$monthReal/$year"
                binding.eventEditDate.setText(dateTime)
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
                        binding.eventEditTimeStart.setText("0$hourOfDay:0$minute")
                    }else{
                        binding.eventEditTimeEnd.setText("0$hourOfDay:0$minute")
                    }
                } else if (lengthHour < 2) {
                    if(code==TIME_START){
                        binding.eventEditTimeStart.setText("0$hourOfDay:$minute")
                    }else{
                        binding.eventEditTimeEnd.setText("0$hourOfDay:$minute")
                    }
                } else if (lengthMinute < 2) {
                    if(code==TIME_START){
                        binding.eventEditTimeStart.setText("$hourOfDay:0$minute")
                    }else{
                        binding.eventEditTimeEnd.setText("$hourOfDay:0$minute")
                    }
                } else {
                    if(code==TIME_START){
                        binding.eventEditTimeStart.setText("$hourOfDay:$minute")
                    }else{
                        binding.eventEditTimeEnd.setText("$hourOfDay:$minute")
                    }
                }

                if(code==TIME_START){
                    event.dateStart= binding.eventEditTimeStart.text.toString()
                }else{
                    event.dateEnd = binding.eventEditTimeEnd.text.toString()
                }


            }, hour, minute, true
        )
        timePickerDialog.show()
    }



    override fun onClick(v: View?) {
        if(v==binding.eventEditDate){
            showDatePickerDialog()
        }else if(v==binding.eventEditTimeStart){
            showTimePickerDialog(TIME_START)
        }else if(v==binding.eventEditTimeEnd){
            showTimePickerDialog(TIME_END)
        }else if(v==binding.eventEditDocumentView){
            val intent = Intent(this@EventEditActivity,PhotoViewerActivity::class.java)
            if(event.img.isEmpty()){
                Log.e("result",attachment.toString())
                Toast.makeText(this@EventEditActivity,"this is view document",Toast.LENGTH_SHORT).show()
                if(attachment!=null){

                    intent.putExtra("document",attachment.toString())
                    startActivity(intent)
                }
            }else{
                Toast.makeText(this@EventEditActivity,event.img,Toast.LENGTH_SHORT).show()
                intent.putExtra("document",ApiUrl.IP+"/storage/documents/"+event.img)
                startActivity(intent)
            }

        }else if(v==binding.eventEditDocument){
            filePicker()
        }
    }
}

