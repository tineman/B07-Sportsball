package com.example.b07sportsballs;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        return new EventHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder
            (@NonNull CustomerUpcomingEventAdapter.EventHolder holder, int position) {
        holder.setDetails(position);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }


    public class EventHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtOwner, txtVenue, txtTime, txtNumPlayers;
        private Button buttonJoin;
        private CustomerUpcomingEventAdapter adapter;

        public EventHolder(@NonNull View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            txtName = (TextView) view.findViewById(R.id.txtName);
            txtOwner = (TextView) view.findViewById(R.id.txtOwner);
            txtVenue = (TextView) view.findViewById(R.id.txtVenue);
            txtTime = (TextView) view.findViewById(R.id.txtTime);
            txtNumPlayers = (TextView) view.findViewById(R.id.txtNumPlayers);
            buttonJoin = (Button) view.findViewById(R.id.buttonJoin);
        }

        void setDetails(int position) {
            Event event = adapter.events.get(position);
            SimpleDateFormat timeFormat = new SimpleDateFormat("d MMM yyyy HH:mm a");
            txtName.setText(event.getName());
            txtOwner.setText(String.format("Created by: %s", event.getHost()));
            txtVenue.setText(String.format("Location: %s", event.getLocation()));
            txtTime.setText(String.format("%s - %s", timeFormat.format(event.getStartTime()),
                    timeFormat.format(event.getEndTime())));
            txtNumPlayers.setText(String.format("Players: %d/%d",
                    event.getCurrPlayers(), event.getMaxPlayers()));
            buttonJoin.setOnClickListener(new View.OnClickListener() {
                //TODO: Delete event.
                public void onClick(View v) {
                    event.setWriter();
                    Customer.joinEvent(event);
                    adapter.events.remove(event);
                    adapter.notifyItemRemoved(position);
//                    notifyItemRangeChanged(position, adapter.events.size());
                    Toast.makeText(adapter.context, "Event joined.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(adapter.context, CustomerEventsJoinedScreen.class);
                    context.startActivity(intent);
                }
            });
        }

        public EventHolder linkAdapter(CustomerUpcomingEventAdapter adapter) {
            this.adapter = adapter;
            return this;
        }
    }
}

