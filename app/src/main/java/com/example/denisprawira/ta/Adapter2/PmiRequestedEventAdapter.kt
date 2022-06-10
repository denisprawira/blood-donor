package com.example.denisprawira.ta.Adapter2

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.denisprawira.ta.Api.Model.Event
import com.example.denisprawira.ta.Helper.TimeConverter
import com.example.denisprawira.ta.R
import com.example.denisprawira.ta.UIPMI.RequestedDetailEventActivity
import com.example.denisprawira.ta.UIPMI.RequestedEventActivity
import com.example.denisprawira.ta.Values.GlobalValues
import com.example.denisprawira.ta.databinding.LayoutCardRequestEventBinding


class PmiRequestedEventAdapter: RecyclerView.Adapter<PmiRequestedEventAdapter.ViewHolder>() {

    private var eventList = emptyList<Event>()

    class ViewHolder(val binding: LayoutCardRequestEventBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutCardRequestEventBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPosition  = eventList.get(position)
        holder.binding.eventRequestTittle.text = currentPosition.title
        holder.binding.eventRequestAddress.text = currentPosition.address
        holder.binding.eventRequestStatus.text = currentPosition.status
        holder.binding.eventRequestDate.text = TimeConverter.convertDateTimeToDay(currentPosition.dateStart) + " " + TimeConverter.convertDateTimeToMonth(currentPosition.dateStart)
        holder.binding.eventRequestStartTime.text = TimeConverter.convertDateTimeToTime(currentPosition.dateStart)
        if (eventList[position].dateEnd == null) {
           holder.binding.eventRequestEndTime.setText("--:--")
        } else {
            holder.binding.eventRequestEndTime.setText(
                TimeConverter.convertDateTimeToTime(
                    currentPosition.dateEnd
                )
            )
        }
        if(currentPosition.status.equals(GlobalValues.EVENT_PENDING)){
            holder.binding.eventRequestStatus.setBackground(getDrawable(holder.itemView.context, R.drawable.theme_status_background_pending));
            holder.binding.eventRequestStatus.setTextColor(getColor(holder.itemView.context,R.color.colorStatusPending));
        }else if(currentPosition.status.equals(GlobalValues.EVENT_DISAPPROVED)){
            holder.binding.eventRequestStatus.setBackground(getDrawable(holder.itemView.context, R.drawable.theme_status_background_unapproved));
            holder.binding.eventRequestStatus.setTextColor(getColor(holder.itemView.context,R.color.colorStatusDisapproved));
        }else if(currentPosition.status.equals(GlobalValues.EVENT_APPROVED)){
            holder.binding.eventRequestStatus.setBackground(getDrawable(holder.itemView.context, R.drawable.theme_status_background_approved));
            holder.binding.eventRequestStatus.setTextColor(getColor(holder.itemView.context,R.color.colorStatusApproved));
        }
        holder.binding.root.setOnClickListener{
           val intent = Intent(holder.itemView.context,RequestedDetailEventActivity::class.java)
            intent.putExtra("event",currentPosition)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    fun setData(newList: List<Event>) {
        eventList = newList
        notifyDataSetChanged()
    }



}