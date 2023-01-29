package com.app.vaccination;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class adminactivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("date");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminactivity);
        //Button addslotbtn = findViewById(R.id.addslots);
        Button submit = findViewById(R.id.submitbtn);
         EditText slotnumbers = findViewById(R.id.slotnumbertxt);
        EditText datetxt=findViewById(R.id.datetxt);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adding to firebase



                //myRef.child("date");
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        String slots= String.valueOf(slotnumbers.getText());
                        String date=datetxt.getText().toString().trim();

                        try {
                            if (snapshot.hasChild(date)) {
                                Toast.makeText(adminactivity.this, "Date Already Registered", Toast.LENGTH_SHORT).show();
                            } else {

                                 System.out.print(date);
                                //send data to db

                                myRef.child("slots").child(date).setValue(slots);
                                //show success message

                                Toast.makeText(adminactivity.this, "Slot Added!", Toast.LENGTH_SHORT).show();
                                datetxt.setText("");
                                slotnumbers.setText("");
                                //startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                //finish();
                            }

                        } catch (Exception e) {
                            System.out.print(e);
                            Toast.makeText(adminactivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });
            }
        });
    }



}