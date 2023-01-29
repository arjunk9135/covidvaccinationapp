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

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText usertxt;
    EditText pwdtxt;
    Button loginbutton;
    TextView signupLabel;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usertxt = findViewById(R.id.usertxt);
        pwdtxt = findViewById(R.id.pwdtxt);
        loginbutton = findViewById(R.id.addslots);
        signupLabel=findViewById(R.id.signupLabel);


        //read from database
      loginbutton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String phno = usertxt.getText().toString().trim();
              String pwd = pwdtxt.getText().toString().trim();
              //SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
              //String currdate = sdf.format(new Date());


             myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot snapshot) {

                     try{



                         if(snapshot.hasChild(phno)){ //snapshot
                              String  getpwd = snapshot.child(phno).child("Password").getValue(String.class);
                              if(getpwd.equals("admin")) //admincase
                              {


                                      startActivity(new Intent(MainActivity.this, adminactivity.class));
                                      Toast.makeText(MainActivity.this, "Welcome Admin!!", Toast.LENGTH_SHORT).show();

                              }
                              else{                         //admin case
                                  if (getpwd.equals(pwd)) {     //normal user

                                      myRef.child("LOGIN ATTEMPT").child("id").setValue(phno);
                                      myRef.child("LOGIN ATTEMPT").child("email").setValue(pwd);

                                      Toast.makeText(MainActivity.this, "Succesfully Signed in", Toast.LENGTH_SHORT).show();
                                      startActivity(new Intent(MainActivity.this, Home.class));
                                  }
                                  else{

                                      Toast.makeText(MainActivity.this, "Wrong password!!", Toast.LENGTH_SHORT).show();
                                  }

                                        }


                         }
                         else       //snapshot
                         {
                             Toast.makeText(MainActivity.this, "no user found", Toast.LENGTH_SHORT).show();
                             myRef.child("LOGIN ATTEMPT").child("id").setValue(phno);
                         }

                     }catch (Exception e)
                     {
                         Toast.makeText(MainActivity.this, "Exception !!!", Toast.LENGTH_SHORT).show();
                     }



             }

                 @Override
                 public void onCancelled(@NonNull DatabaseError error) {
                     Toast.makeText(MainActivity.this, "Db error", Toast.LENGTH_SHORT).show();
                 };
          });
      };

    });


      signupLabel.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(MainActivity.this, signup.class));
          }
      });
}
}