package com.example.b07sportsballs;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public interface Updater {
    public void onStart();
    public void onSuccess(int returnCode);
    public void onFailure(DatabaseError error);
}
