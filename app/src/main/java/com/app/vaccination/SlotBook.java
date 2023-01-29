package com.app.vaccination;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SlotBook extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("date");
EditText pnametxt;
EditText pagetxt;
TextView slots;
TextView seldate;
Button confirmdate;
Button bookbtn;
EditText aadhartxt,availslots;
int year,month,day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_book);
        seldate=findViewById(R.id.seldate);
        pnametxt=findViewById(R.id.pnametxt);
        bookbtn=findViewById(R.id.bookbtn);
        pagetxt=findViewById(R.id.pagetxt);
        Calendar cal=Calendar.getInstance();
        //slots=findViewById(R.id.slots);
        confirmdate=findViewById(R.id.confirmdate);
        aadhartxt=findViewById(R.id.aadhartxt);
        seldate.setText(" ");
        //availslots=findViewById(R.id.availslots);
        final String[] final_date = new String[1];
        confirmdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           year=cal.get(Calendar.YEAR);
           month=(cal.get(Calendar.MONTH)+1);
           day=cal.get(Calendar.DAY_OF_MONTH);
           //seldate.setText(" ");
                DatePickerDialog datePickerDialog=new DatePickerDialog(SlotBook.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String dd=Integer.toString(dayOfMonth);
                        String mm=Integer.toString(month+1);
                        String yy=Integer.toString(year);
                        String ddmmyy=dd.concat("-").concat(mm).concat("-");
                        String ddmmyy1=ddmmyy.concat(yy);
                        //final_date[0] =ddmmyy1;
                         //seldate.setText(ddmmyy1);
                         //availslots.setText(ddmmyy1);
                        seldate.setText(ddmmyy1);
                    }
                },year,month,day);

                datePickerDialog.show();

                //read slots


            }
        });

       //book slot

        bookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String patient_name=pnametxt.getText().toString().trim();
            String patient_age=pagetxt.getText().toString().trim();
            String datee=seldate.getText().toString().trim();
            String uidai=aadhartxt.getText().toString().trim();
            // add to database

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        myRef.child("Registrations").child(datee).child(uidai).child("Name").setValue(patient_name);
                        myRef.child("Registrations").child(datee).child(uidai).child("Age").setValue(patient_age);
                        Toast.makeText(SlotBook.this, "Slot Booked!!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SlotBook.this, Home.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}