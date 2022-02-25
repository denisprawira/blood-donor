package com.example.denisprawira.ta.User.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.R;

import com.example.denisprawira.ta.User.UI.Fragment.Request.DonorFragment;
import com.example.denisprawira.ta.User.UI.Fragment.Request.EventFragment;
import com.example.denisprawira.ta.User.UI.HomeActivity;
import com.example.denisprawira.ta.User.UI.RequestEventDonorActivity;
import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

import java.util.Calendar;


public class RequestEventDonorMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {




    Calendar calendar = Calendar.getInstance();
    int hour        = calendar.get(Calendar.HOUR);
    int minute      = calendar.get(Calendar.MINUTE);
    int day         = calendar.get(Calendar.DAY_OF_MONTH);
    int month       = calendar.get(Calendar.MONTH);
    int year        = calendar.get(Calendar.YEAR);

    private RecyclerView.LayoutManager mLayoutManager;


    static final int ITEM_UPPER      = 0;
    static final int ITEM_BOTTOM     = 1;

    private Context context;

    FragmentManager fragmentManager;

    public RequestEventDonorMainAdapter(Context context){
        this.context = context;
    }

    public RequestEventDonorMainAdapter(FragmentManager fragmentManager, Context context) {
        this.fragmentManager = fragmentManager;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view;
        RecyclerView.ViewHolder vh = null;
        switch(viewType){
            case ITEM_UPPER:
                view = layoutInflater.inflate(R.layout.layout_event_donor_request,parent,false);
                vh =  new UpperViewHolder(view);
                break;
            case ITEM_BOTTOM:
               view = layoutInflater.inflate(R.layout.layout_event_donor_request_bottom,parent,false);
                vh = new BottomViewHolder(view);
                break;
            default:
                view = layoutInflater.inflate(R.layout.layout_event_donor_request_bottom,parent,false);
                vh = new BottomViewHolder(view);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder.getItemViewType()==ITEM_UPPER){
            upperView((UpperViewHolder) viewHolder);
        }
        else if(viewHolder.getItemViewType()==ITEM_BOTTOM){
            bottomView((BottomViewHolder) viewHolder);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(position ==ITEM_UPPER)
            return ITEM_UPPER;
        if(position ==ITEM_BOTTOM)
            return ITEM_BOTTOM;
        return -1;
    }

    public class UpperViewHolder extends RecyclerView.ViewHolder{
        ImageButton  backRequestEventDonor;
        TextView eventRequestLabelDate, eventRequestLabelDay;
        UpperViewHolder( View itemView) {
            super(itemView);
            backRequestEventDonor = itemView.findViewById(R.id.backRequestEventDonor);
            eventRequestLabelDate = itemView.findViewById(R.id.eventRequestLabelDate);
            eventRequestLabelDay = itemView.findViewById(R.id.eventRequestLabelDay);
        }
    }

    public class BottomViewHolder extends RecyclerView.ViewHolder{
        BubbleNavigationConstraintView bubbleNavigationConstraintView;
        BottomViewHolder( View itemView){
            super(itemView);
            bubbleNavigationConstraintView = itemView.findViewById(R.id.floating_event_donor_);
        }
    }


    public void upperView(final UpperViewHolder holder){
        holder.backRequestEventDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,HomeActivity.class));
            }
        });
        holder.eventRequestLabelDate.setText(GlobalHelper.convertDayMonth(year+"-"+(month+1)+"-"+day)+" ,"+GlobalHelper.convertToYear(year+"-"+(month+1)+"-"+day));
        holder.eventRequestLabelDay.setText(GlobalHelper.convertToDayOfWeek(year+"-"+(month+1)+"-"+day));
    }

    public void bottomView(final BottomViewHolder holder){
        ((RequestEventDonorActivity)context).getSupportFragmentManager().beginTransaction().add(R.id.eventDonorContainerFrame,new com.example.denisprawira.ta.User.UI.Fragment.Request.EventFragment()).commit();

        holder.bubbleNavigationConstraintView.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                if(position==0){
                    ((RequestEventDonorActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.eventDonorContainerFrame,new EventFragment()).commit();
                }else if(position==1){
                    ((RequestEventDonorActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.eventDonorContainerFrame,new DonorFragment()).commit();
                }
            }
        });
    }
}
