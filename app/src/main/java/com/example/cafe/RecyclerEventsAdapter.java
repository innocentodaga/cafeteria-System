package com.example.cafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerEventsAdapter extends RecyclerView.Adapter<RecyclerEventsAdapter.EventsHolder> {
    Context context;
    List<EventsModel> eventsModelList;

    public RecyclerEventsAdapter(Context context, List<EventsModel> eventsModelList){
        this.context = context;
        this.eventsModelList = eventsModelList;
    }

    @NonNull
    @Override
    public RecyclerEventsAdapter.EventsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View events = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_card,parent, false);
        return new EventsHolder(events);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerEventsAdapter.EventsHolder holder, int position) {
        EventsModel eventsModel = eventsModelList.get(position);
        holder.eventname.setText(eventsModel.getName());
        holder.eventdesc.setText(eventsModel.getDescription());
        holder.eventdate.setText(eventsModel.getDate());

    }

    @Override
    public int getItemCount() {
        return eventsModelList.size();
    }
    public class EventsHolder extends RecyclerView.ViewHolder{
        TextView eventdesc, eventname, eventdate;

        public EventsHolder(@NonNull View itemView) {
            super(itemView);
            eventname = itemView.findViewById(R.id.event_title);
            eventdate = itemView.findViewById(R.id.eventdate);
            eventdesc = itemView.findViewById(R.id.eventdescription);

        }
    }
}
