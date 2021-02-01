/* Assignment: 1
Campus: Ashdod
Author: Lidor Zaguri, ID: 205622814
Author: Liat Golber, ID: 313301129
*/

package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class FormToInitiate extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase; DatabaseReference myRef;
    FirebaseAuth firebaseAuth;
    TextView et_email,et_phone,description;
    String mail,remember;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_to_initiate);
        et_email = findViewById(R.id.etEmail);
        description = findViewById(R.id.textbox);
        et_phone = findViewById(R.id.etPhone);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        myRef=firebaseDatabase.getReference("Users");
        FirebaseUser myUser = firebaseAuth.getCurrentUser();
        Intent intent =getIntent();
        mail = intent.getExtras().getString("email");
        submit = findViewById(R.id.btnSend);
        SharedPreferences sharedPref = getSharedPreferences("remember",MODE_PRIVATE);
        remember = sharedPref.getString("checkBox","");
        if(remember.equals("true")){
            SharedPreferences sharedPref2 = getSharedPreferences("mail", MODE_PRIVATE);
            mail = sharedPref2.getString("mail", "");
        }
        else
        {
            et_email.setText("");
        }
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    if (singleSnapshot.child("email").getValue().toString().equals(mail))
                        et_email.setText(singleSnapshot.child("email").getValue().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FormToInitiate.this, ""+databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @SuppressLint("IntentReset")
    public void submit(View v)
    {
        String email = et_email.getText().toString();
        if(isValidate())
        {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setType("text/plain");
            intent.setData(Uri.parse("mailto:lidorza50@gmail.com"));
            intent.putExtra(Intent.EXTRA_EMAIL,email );
            intent.putExtra(Intent.EXTRA_SUBJECT, "Contact us");
            intent.putExtra(Intent.EXTRA_TEXT, "phone number:"+et_phone.getText().toString()+" "+description.getText().toString());
            startActivity(Intent.createChooser(intent, "Send Email"));
            Toast.makeText(this,"Sending..."+ email ,Toast.LENGTH_LONG).show();
        }
    }
    public boolean isValidate()
    {
        if (!et_phone.getText().toString().matches("[0-9]+") || et_phone.getText().toString().length() < 9 )
        {
            et_phone.setError("Phone number please.");
            et_phone.setFocusable(true); return false;
        }
        return true;
    }
}
