package com.example.b07sportsballs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07sportsballs.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class CustomerUpcomingEventAdapter extends
        RecyclerView.Adapter<CustomerUpcomingEventAdapter.EventHolder>{
    private Context context;
    private ArrayList<Event> events;

    public CustomerUpcomingEventAdapter(Context context, ArrayList<Event> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate
                (R.layout.customer_upcoming_event_layout_item, parent, false);
        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder
            (@NonNull CustomerUpcomingEventAdapter.EventHolder holder, int position) {
        Event event = events.get(position);
        holder.setDetails(event);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class EventHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtOwner, txtVenue, txtTime, txtNumPlayers;
        private Button buttonJoin;

        public EventHolder(@NonNull View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            txtName = (TextView) view.findViewById(R.id.txtName);
            txtOwner = (TextView) view.findViewById(R.id.txtOwner);
            txtVenue = (TextView) view.findViewById(R.id.txtVenue);
            txtTime = (TextView) view.findViewById(R.id.txtTime);
            txtNumPlayers = (TextView) view.findViewById(R.id.txtNumPlayers);
            buttonJoin = (Button) view.findViewById(R.id.buttonJoin);
            buttonJoin.setOnClickListener(new View.OnClickListener() {
                //TODO: Modify to call method to join events once class Customer is finalized.
                public void onClick(View v) {
                }
            });
        }

        //TODO: Modify when class Event is finalized.
        void setDetails(Event event) {
            SimpleDateFormat timeFormat = new SimpleDateFormat("d MMM yyyy HH:mm a");
            txtName.setText(event.name);
            txtOwner.setText(String.format("Created by: %s", event.owner));
            txtVenue.setText(String.format("Location: %s", event.venue));
            txtTime.setText(String.format("%s - %s", timeFormat.format(event.startTime),
                    timeFormat.format(event.endTime)));
            txtNumPlayers.setText(String.format("Players: %d/%d",
                    event.currPlayers, event.maxPlayers));
        }
    }
}

