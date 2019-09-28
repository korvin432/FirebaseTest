package com.mindyapps.android.firebasetest;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "qwwe";
    private DatabaseReference mDatabase, getDataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDataRef = FirebaseDatabase.getInstance().getReference().child("donations");
        getDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "" + postSnapshot.child("name").getValue());
                    Log.d(TAG, "" + postSnapshot.child("message").getValue());
                    Log.d(TAG, "" + postSnapshot.child("value").getValue());
                    Log.d(TAG, "======================");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "The read failed: " + databaseError.getCode());
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();
        writeNewDonations("Kek", "Gsdgsdg", 118);
    }

    private void writeNewDonations(String name, String message, int value) {
        Donation donation = new Donation(name, message, value);

        mDatabase.child("donations").push().setValue(donation).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "onComplete: " + task.isComplete());
            }
        });
    }
}

@IgnoreExtraProperties
class Donation {

    public String name;
    public String message;
    public int value;

    public Donation(){

    }

    public Donation(String name, String message, int value) {
        this.name = name;
        this.message = message;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public int getValue() {
        return value;
    }
}