/* Assignment: 1
Campus: Ashdod
Author: Lidor Zaguri, ID: 205622814
Author: Liat Golber, ID: 313301129
*/

package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity   {
    EditText pass, email;
    Button login,forgot_password,signup1;
    ImageButton back;
    TextView wrong_user;
    User user;
    String password;
    String mail,id;
    String remember;
    CheckBox checkBox;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.bthLogin);
        email = findViewById(R.id.etEmail);
        pass = findViewById(R.id.etPassword);
        wrong_user = findViewById(R.id.wronguser);
        forgot_password = findViewById(R.id.Forgot);
        user =new User();
        signup1 = findViewById(R.id.signup1);
        checkBox = findViewById(R.id.checkbox);
        back = findViewById(R.id.btn_back_login);
        SharedPreferences sharedPref = getSharedPreferences("remember",MODE_PRIVATE);
        remember = sharedPref.getString("checkBox","");
        SharedPreferences sharedPref2 = getSharedPreferences("mail", MODE_PRIVATE);
        mail = sharedPref2.getString("mail", "");
        stayLogin();
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(buttonView.isChecked())
                    {
                            SharedPreferences sharedPref = getSharedPreferences("remember", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("checkBox", "true");
                            editor.apply();
                            Toast.makeText(Login.this, "Check", Toast.LENGTH_SHORT).show();
                    }else if(!checkBox.isChecked()){
                        SharedPreferences sharedPref = getSharedPreferences("remember",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("checkBox","false");
                        editor.apply();
                        Toast.makeText(Login.this,"Uncheck",Toast.LENGTH_SHORT).show();
                    }
                }
        });

    }


   //Sign in
    @SuppressLint("SetTextI18n")
    public void logIn(View v) {
        try {
            password = pass.getText().toString();
            mail = email.getText().toString();
            pd = ProgressDialog.show(this, "Connecting ...", "Please wait ", true);
            pd.show();
            final FirebaseAuth Auth = FirebaseAuth.getInstance();
            Auth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    String[] parts = mail.split("@");
                    if (task.isSuccessful()) {
                        if (Auth.getCurrentUser().isEmailVerified()) {
                            Toast.makeText(Login.this, parts[0] + " " + "sign in", Toast.LENGTH_LONG).show();
                            pd.dismiss();
                            user.setEmail(mail);
                            Intent in = new Intent(Login.this, MainActivity.class);
                            in.putExtra("email", mail);
                            in.putExtra("username", "Hello" + " " + parts[0] + "    ");
                            startActivity(in); //go to next page
                        } else {
                            pd.dismiss();
                            Toast.makeText(Login.this, "Please verified your mail", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onFailure(@NonNull Exception e) {
                    email.setText("");
                    pass.setText("");
                    wrong_user.setText(e.getMessage());
                    pd.dismiss();
                }
            });
    }catch (Exception e){
            pd.dismiss();
            wrong_user.setText(e.getMessage());
        }
 }

 //stay login
    public void stayLogin()
    {
        if (remember.equals("true"))
        {
            try {
                user.setEmail(mail);
                String[] parts = mail.split("@");
                Intent in=new Intent(Login.this, MainActivity.class );
                in.putExtra("email",mail);
                in.putExtra("username","Hello"+" "+ parts[0]);
                startActivity(in); //go to next page

                Toast.makeText(Login.this,"Remember",Toast.LENGTH_LONG).show();
            }catch (Exception ignored){}
        }
        else
        {
            Toast.makeText(Login.this,"login please",Toast.LENGTH_LONG).show();
        }
    }

    //forgotPassword
    public void  forgotPassword(View v)
    {
        Intent intent = new Intent(this,ForgotPassword.class);
        startActivity(intent);
    }

//back
    public void  back(View v)
    {
        checkBox.setChecked(false);
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

//Sign up
    public void signup1(View view) {
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }
}
