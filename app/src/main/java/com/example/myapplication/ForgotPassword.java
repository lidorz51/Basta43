/* Assignment: 1
Campus: Ashdod
Author: Lidor Zaguri, ID: 205622814
Author: Liat Golber, ID: 313301129
*/

package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgotPassword extends AppCompatActivity {
    EditText resetEmail;
    Button reset;
    ImageButton back;
    String mail;
    String message;
    FirebaseDatabase firebaseDatabase; DatabaseReference myRef;
    FirebaseAuth firebaseAuth;
    private static final String TAG = "ForgotPassword";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        resetEmail = findViewById(R.id.reset_email);
        reset = findViewById(R.id.btn_forgot);
        back =findViewById(R.id.btn_back);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        myRef=firebaseDatabase.getReference("Users");
        FirebaseUser myUser = firebaseAuth.getCurrentUser();

    }

    public void forgotPassword(View v)
    {
        mail = resetEmail.getText().toString();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    try {
                        if (singleSnapshot.child("email").getValue().toString().equals(mail)) {

                            firebaseAuth.sendPasswordResetEmail(mail)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "Email sent.");
                                            }
                                        }
                                    });
                            openDialog();
                            resetEmail.setText("");
                            break;
                        }
                    }catch (Exception ignored)
                    {}
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ForgotPassword.this, ""+databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Information")
                .setMessage("A reset request has been sent to you by email.\n\nThank you!")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        builder.show();
    }

    public void back(View v)
    {
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }

}