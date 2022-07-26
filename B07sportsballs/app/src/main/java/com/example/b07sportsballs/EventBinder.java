package com.example.b07sportsballs;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class EventBinder {

    private Event parent; //the parent the binder is attached to
    private ValueEventListener listener;

    /**
     * Prevents EventBinder from being initialised without a event to attach it to
     */
    private EventBinder()
    {

    }

    /**
     * Initialises an EventBinder
     * @param event the event to which this reader is attached
     */
    public EventBinder(Event event)
    {
        parent = event;
    }

    /**
     * Binds the parent event to a reference in the database. Does not check if the event is there
     * beforehand, that is the calling function's responsibility
     * @param ref the reference to the event
     * @param onUpdate a void() function that the user implements. It is called every time event is
     *                updated
     */
    public void bind(DatabaseReference ref, Updater onUpdate)
    {

        listener = ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //Null checking?
                Event event = snapshot.getValue(Event.class);
                if(event != null)
                {
                    parent.setData(event);
                }
                else
                {
                    Log.i("Event Binder Error", "Tried to bind to empty node at " + ref.toString());
                }
                onUpdate.onUpdate();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("EventBinder Warning:", "error while binding to " + parent.getName(), error.toException());
            }
        });

    }

    /**
     * Update the onUpdate function. Assumes bind has been called previously
     *
     * @param ref the reference to the event
     * @param onUpdate a void() function that the user implements. It is called every time event is
     *                updated
     */
    public void update(DatabaseReference ref, Updater onUpdate)
    {

        ref.removeEventListener(this.listener);
        this.bind(ref, onUpdate);

    }

}