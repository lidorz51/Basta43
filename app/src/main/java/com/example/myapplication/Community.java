/* Assignment: 1
Campus: Ashdod
Author: Lidor Zaguri, ID: 205622814
Author: Liat Golber, ID: 313301129
*/

package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;


public class Community extends AppCompatActivity {

    Button initiator,lectures,performances,initiate,login_button,logout,signup;
    Button all,day,week;
    TextView login;
    ImageView image,image1,image2,setting;
    String mail,id;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        setTitle("Basta43");
        initiator = findViewById(R.id.exhibition);
        initiate = findViewById(R.id.initiate);
        lectures = findViewById(R.id.lectures);
        performances = findViewById(R.id.performances);
        login_button = findViewById(R.id.Login);
        login = findViewById(R.id.Signin);
        signup = findViewById(R.id.Signup);
        all = findViewById(R.id.bthAll);
        day = findViewById(R.id.bthDay);
        week = findViewById(R.id.bthWeek);
        image = findViewById(R.id.image);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        setting = findViewById(R.id.settingC);
        try {
            Intent intent = getIntent();
            id = intent.getExtras().getString("username");
            if(id != null) {
                login.setVisibility(View.VISIBLE);
                login_button.setVisibility(View.GONE);
                signup.setText("Exit");
                signup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logOut1();
                    }
                });
                login.setText(id);
                mail = intent.getExtras().getString("email");
                SharedPreferences sharedPref = getSharedPreferences("mail",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("mail",mail);
                editor.apply();
            }

        } catch (Exception ignored){}
    }

    // show the big picture

    public void lecture(View v)
    {
        Dialog dialog = new Dialog(Community.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.lecture);
        dialog.getWindow().setBackgroundDrawable(null);

        dialog.show();
    }

    public void lecture1(View v)
    {
        Dialog dialog = new Dialog(Community.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.lecture1);
        dialog.getWindow().setBackgroundDrawable(null);

        dialog.show();
    }

    public void lecture2(View v)
    {
        Dialog dialog = new Dialog(Community.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.lecture2);
        dialog.getWindow().setBackgroundDrawable(null);

        dialog.show();
    }

    //Sort by subject
    public void lectures(View v)
    {
        image.setVisibility(View.VISIBLE);
        image1.setVisibility(View.VISIBLE);
        image2.setVisibility(View.VISIBLE);
        Toast.makeText(Community.this,"Lectures",Toast.LENGTH_LONG).show();
    }

    public void performances(View v)
    {
        image.setVisibility(View.GONE);
        image1.setVisibility(View.GONE);
        image2.setVisibility(View.GONE);
        Toast.makeText(Community.this,"Performances",Toast.LENGTH_LONG).show();
    }

    public void initiator(View v)
    {
        image.setVisibility(View.GONE);
        image1.setVisibility(View.GONE);
        image2.setVisibility(View.GONE);
        Toast.makeText(Community.this,"Initiator",Toast.LENGTH_LONG).show();
    }

    //Sort by All day and week
    public void viewAll(View v)
    {
        image.setVisibility(View.VISIBLE);
        image1.setVisibility(View.VISIBLE);
        image2.setVisibility(View.VISIBLE);
        Toast.makeText(Community.this,"All",Toast.LENGTH_LONG).show();
    }

    public void viewDay(View v)
    {
        image.setVisibility(View.VISIBLE);
        image1.setVisibility(View.GONE);
        image2.setVisibility(View.GONE);
        Toast.makeText(Community.this,"Day",Toast.LENGTH_LONG).show();
    }

    public void viewWeed(View v)
    {
        image.setVisibility(View.VISIBLE);
        image1.setVisibility(View.VISIBLE);
        image2.setVisibility(View.GONE);
        Toast.makeText(Community.this,"Week",Toast.LENGTH_LONG).show();
    }

    //Settings
    public void settings(View v)
    {
        if(mail == null){
            Intent intent = new Intent(this, SiginAndSignout.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(Community.this, Setting.class);
            intent.putExtra("email", mail);
            startActivity(intent);
        }
    }

    //Rgister
    public void signUp(View v)
    {
        Intent intent = new Intent(Community.this, Register.class);
        startActivity(intent);
    }

    //Login
    public void logIn(View v)
    {
        Intent intent = new Intent(Community.this,Login.class);
        startActivity(intent);
    }

    //Logout
    public void logOut1()
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

    //Initiate
    public void goToInitiate(View v)
    {
        if(mail != null){
            Intent intent = new Intent(this, FormToInitiate.class);
            intent.putExtra("email", mail);
            startActivity(intent);

        }
        else
        {
            Intent intent = new Intent(this, SiginAndSignout.class);
            startActivity(intent);
        }
    }

}