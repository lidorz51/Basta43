/* Assignment: 1
Campus: Ashdod
Author: Lidor Zaguri, ID: 205622814
Author: Liat Golber, ID: 313301129
*/


package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity  {
    ImageButton community;
    ImageButton photos;
    Button signin,signup,sigout;
    TextView login;
    String id,mail;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Basta43");
        community = findViewById(R.id.btncommunity);
        photos = findViewById(R.id.btnimage);
        signin = findViewById(R.id.bthLogin);
        login = findViewById(R.id.tlogin);
        signup = findViewById(R.id.bthSignup);
        try {
            Intent intent = getIntent();
            id = intent.getExtras().getString("username");
            login.setVisibility(View.VISIBLE);
            signin.setVisibility(View.GONE);
            mail = intent.getExtras().getString("email");
            login.setText(id);
            signup.setText("Exit");
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logOut();
                }
            });
            SharedPreferences sharedPref = getSharedPreferences("mail",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("mail",mail);
            editor.apply();
        }catch (Exception ignored){}
    }

    //Login
    public void logIn(View v)
    {
        Intent intent = new Intent(MainActivity.this,Login.class);
        startActivity(intent);
    }

    //Logout
    public void logOut()
    {
        FirebaseAuth Auth = FirebaseAuth.getInstance();
        Auth.signOut();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        SharedPreferences sharedPref = getSharedPreferences("remember",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("checkBox","false");
        editor.apply();
    }

    //Settings
    public void settings(View v)
    {
        if(mail == null){
            Intent intent = new Intent(this, SiginAndSignout.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(MainActivity.this, Setting.class);
            intent.putExtra("email", mail);
            startActivity(intent);
        }
    }


    //Rgister
    public void signUp(View v)
    {
        Intent intent = new Intent(MainActivity.this, Register.class);
        startActivity(intent);
    }

    //show the community page.
    public void viewCommunity(View v)
    {
        Intent intent = new Intent(this, Community.class);
        intent.putExtra("username",id);
        intent.putExtra("email",mail);
        startActivity(intent);
    }

    //show the Image page.
    public void viewImage (View v)
    {
        Intent intent = new Intent(this, Gallery.class);
        intent.putExtra("email",mail);
        startActivity(intent);
    }

}