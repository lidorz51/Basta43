/* Assignment: 1
Campus: Ashdod
Author: Lidor Zaguri, ID: 205622814
Author: Liat Golber, ID: 313301129
*/

package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class Setting extends AppCompatActivity {

    Button delete;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        delete =findViewById(R.id.delete);
        Intent intent =getIntent();
        SharedPreferences sharedPref2 = getSharedPreferences("mail",MODE_PRIVATE);
        mail =  sharedPref2.getString("mail","");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            if (postSnapshot.child("email").getValue().toString().equals(mail)) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Setting.this);
                                builder.setTitle("Are you sure?");
                                builder.setMessage("Deleting this account will result in completely removing your " +
                                        "account from the system and you won't be to able to access the app");
                                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(Setting.this, "Acount Deleted", Toast.LENGTH_LONG).show();
                                                        FirebaseAuth Auth = FirebaseAuth.getInstance();
                                                        Auth.signOut();
                                                        SharedPreferences sharedPref = getSharedPreferences("remember", MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = sharedPref.edit();
                                                        editor.putString("checkBox", "false");
                                                        editor.apply();
                                                        Intent intent = new Intent(Setting.this, MainActivity.class);
                                                        startActivity(intent);
                                                    } else {
                                                        Toast.makeText(Setting.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                        }catch (Exception e)
                                        {
                                            SharedPreferences sharedPref = getSharedPreferences("remember", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPref.edit();
                                            editor.putString("checkBox", "false");
                                            editor.apply();
                                            Intent intent = new Intent(Setting.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                });
                                builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(Setting.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
            }
        });
    }

    public void back(View view) {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
    }
}