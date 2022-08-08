package com.example.b07sportsballs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

public class EventRecyclerviewConfig {

    private Context context;
    private EventAdapter adapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<Event> events)
    {
         this.context = context;
         adapter = new EventAdapter(events);

         recyclerView.setLayoutManager(new LinearLayoutManager(context));
         recyclerView.setAdapter(adapter);

    }

    class EventView extends RecyclerView.ViewHolder
    {
        private TextView name;
        private TextView host;
        private TextView venue;
        private TextView capacity;
        private TextView duration;

        public EventView(ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.event_list_item, parent, false));

            name = (TextView) itemView.findViewById(R.id.textEventName);
            host = (TextView) itemView.findViewById(R.id.textEventHost);
            venue = (TextView) itemView.findViewById(R.id.textEventVenue);
            capacity = (TextView) itemView.findViewById(R.id.textEventCapacity);
            duration = (TextView) itemView.findViewById(R.id.textEventDuration);

        }

        public void bind(Event event, String name)
        {
            this.name.setText(event.getName());
            host.setText(event.getHost());
            venue.setText(event.getLocation());
            capacity.setText("Current Capacity: " + event.getCurrPlayers() + "/" + event.getMaxPlayers());
            duration.setText("Duration: " + new SimpleDateFormat("HH:mm").format(event.getStartTime()) + " - " + new SimpleDateFormat("HH:mm").format(event.getEndTime()));
        }

    }

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
