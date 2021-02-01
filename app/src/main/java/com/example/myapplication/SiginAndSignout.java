/* Assignment: 1
Campus: Ashdod
Author: Lidor Zaguri, ID: 205622814
Author: Liat Golber, ID: 313301129
*/

package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageButton;

public class SiginAndSignout extends AppCompatActivity {

    ImageButton signin_image;
    ImageButton signup_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigin_signout);
        signin_image = findViewById(R.id.signinimage);
        signup_image = findViewById(R.id.signupimage);
    }

    //Rgister
    public void signUp1(View v)
    {
        Intent intent = new Intent(SiginAndSignout.this, Register.class);
        startActivity(intent);
    }
    //Login
    public void signIn1(View v)
    {
        Intent intent = new Intent(SiginAndSignout.this,Login.class);
        startActivity(intent);
    }
}