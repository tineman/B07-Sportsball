package com.example.b07sportsballs;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

public class EventRecyclerviewConfig {

    private Context context;
    private EventAdapter adapter;

    /**
     * A Facade for the recycler configuration
     * @param recyclerView the recyclerview to be setup
     * @param context the context the recycler is in
     * @param events the list of events to display
     */
    public void setConfig(RecyclerView recyclerView, Context context, List<Event> events)
    {
         this.context = context;
         adapter = new EventAdapter(events);

         recyclerView.setLayoutManager(new LinearLayoutManager(context));
         recyclerView.setAdapter(adapter);

    }

    /**
     * Creates a template for each event. The recyclerview contains a list of EventViews
     */
    class EventView extends RecyclerView.ViewHolder
    {
        private TextView name;
        private TextView host;
        private TextView venue;
        private TextView capacity;
        private TextView start;
        private TextView end;

        public EventView(ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.event_list_item, parent, false));

            name = (TextView) itemView.findViewById(R.id.textEventName);
            host = (TextView) itemView.findViewById(R.id.textEventHost);
            venue = (TextView) itemView.findViewById(R.id.textEventVenue);
            capacity = (TextView) itemView.findViewById(R.id.textEventCapacity);
            start = (TextView) itemView.findViewById(R.id.textEventStart);
            end = (TextView) itemView.findViewById(R.id.textEventEnd);

        }

        /**
         * Updates the EventView with the Event information
         */
        public void bind(Event event, String name)
        {
            try {
                this.name.setText(event.getName());
                host.setText("Host: " + event.getHost());
                venue.setText("Venue: " + event.getLocation());
                capacity.setText("Current Capacity: " + event.getCurrPlayers() + "/" + event.getMaxPlayers());
                start.setText("Start: " + new SimpleDateFormat("d, MMM yyyy HH:mm").format(event.getStartTime()));
                end.setText("End: " + new SimpleDateFormat("d, MMM yyyy HH:mm").format(event.getEndTime()));
            }
            catch(NullPointerException e)
            {
                //Toast.makeText(venue.getContext(), "Error loading events. Please check your connection", Toast.LENGTH_LONG).show();
            }
        }

    }

    /**
     * An Adapter between Events and RecyclerView
     */
    class EventAdapter extends RecyclerView.Adapter<EventView>
    {
          private List<Event> events;

          public EventAdapter(List<Event> events)
          {
              this.events = events;
          }

        @NonNull
        @Override
        public EventView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new EventView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull EventView holder, int position) {
            holder.bind(events.get(position), events.get(position).getName());


        }

        @Override
        public int getItemCount() {
            return events.size();
        }
    }

}
