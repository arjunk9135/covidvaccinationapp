package com.app.vaccination;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class signup extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EditText snametxt,susertxt,spwdtxt;
        Button signupbtn;
        TextView sample;
        setContentView(R.layout.activity_signup);
        sample=findViewById(R.id.sample);
        snametxt=(EditText) findViewById(R.id.snametxt);
        susertxt=(EditText) findViewById(R.id.susertxt);
        spwdtxt=(EditText) findViewById(R.id.spwdtxt);
        signupbtn=findViewById(R.id.signupbtn);


        //String NAME=name;
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=snametxt.getText().toString();
                String email=susertxt.getText().toString();
                String pwd=spwdtxt.getText().toString();
                String NAME=name;
                //sample.setText(NAME);
                //push to db
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    myRef.child(email).child("Name").setValue(name);
                    myRef.child(email).child("Password").setValue(pwd);
                    Toast.makeText(signup.this, "User Added!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(signup.this, MainActivity.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(signup.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });

            }
        });
    }
}