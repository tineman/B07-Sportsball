package com.example.b07sportsballs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AdminUpcomingEventAdapter
        extends RecyclerView.Adapter<AdminUpcomingEventAdapter.EventHolder>
        implements Filterable {
    private Context context;
    private ArrayList<Event> events;
    private ArrayList<Event> eventsAll;

    public AdminUpcomingEventAdapter(Context context, ArrayList<Event> events) {
        this.context = context;
        this.events = events;
        this.eventsAll = new ArrayList<>(events);
    }

    @NonNull
    @Override
    public AdminUpcomingEventAdapter.EventHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                    int viewType) {
        View view = LayoutInflater.from(context).inflate
                (R.layout.admin_upcoming_event_layout_item, parent, false);
        return new AdminUpcomingEventAdapter.EventHolder(view);
    }

    @Override
    public void onBindViewHolder
            (@NonNull AdminUpcomingEventAdapter.EventHolder holder, int position) {
        Event event = events.get(position);
        holder.setDetails(event);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class EventHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtOwner, txtVenue, txtTime, txtNumPlayers;

        public EventHolder(@NonNull View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            txtName = (TextView) view.findViewById(R.id.txtName);
            txtOwner = (TextView) view.findViewById(R.id.txtOwner);
            txtVenue = (TextView) view.findViewById(R.id.txtVenue);
            txtTime = (TextView) view.findViewById(R.id.txtTime);
            txtNumPlayers = (TextView) view.findViewById(R.id.txtNumPlayers);
        }

        void setDetails(Event event) {
            SimpleDateFormat timeFormat = new SimpleDateFormat("d MMM yyyy HH:mm a");
            txtName.setText(event.getName());
            txtOwner.setText(String.format("Created by: %s", event.getHost()));
            txtVenue.setText(String.format("Location: %s", event.getLocation()));
            txtTime.setText(String.format("%s - %s", timeFormat.format(event.getStartTime()),
                    timeFormat.format(event.getEndTime())));
            txtNumPlayers.setText(String.format("Players: %d/%d",
                    event.getCurrPlayers(), event.getMaxPlayers()));
        }
    }

    // Implements filter by venue.
    @Override
    public Filter getFilter() {
        return eventsFilter;
    }

    private Filter eventsFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Event> filteredEvents = new ArrayList<Event>();
            if (charSequence.equals("All Venues")) {
                filteredEvents.addAll(eventsAll);
            }
            else {
                String filteringVenue = charSequence.toString();
                for (Event event:eventsAll) {
                    if (event.getLocation().equals(filteringVenue)) filteredEvents.add(event);
                }
            }
            FilterResults result = new FilterResults();
            result.values = filteredEvents;
            return result;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            events.clear();
            events.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };
}
