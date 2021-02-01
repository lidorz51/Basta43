/* Assignment: 1
Campus: Ashdod
Author: Lidor Zaguri, ID: 205622814
Author: Liat Golber, ID: 313301129
*/

package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference myref=firebaseDatabase.getReference("Users");
    Button submit;
    ImageButton back;
    EditText editTextName, editTextEmail, editTextPassword;
    ProgressDialog p;
    Security s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        editTextEmail = findViewById(R.id.editEmail);
        editTextPassword = findViewById(R.id.editPass);
        editTextName = findViewById(R.id.editName);
        firebaseAuth = FirebaseAuth.getInstance();
        submit=findViewById(R.id.buttonAcount);
        back =findViewById(R.id.btn_back_signup);
        s=new Security();
    }
    // signUp
    public void signUp(View v) throws Exception {
        p= new ProgressDialog(this);
        p.setMessage("Registration...");
        p.show();
        String password = s.encrypt(editTextPassword.getText().toString());
        if(isValidate())
        firebaseAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
        {
            @Override public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    firebaseAuth.getCurrentUser().sendEmailVerification()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            User u = new User(editTextName.getText().toString(), password, editTextEmail.getText().toString());
                            myref.push().setValue(u);
                            Toast.makeText(Register.this, "Register successfully. please check your email for verification !",Toast.LENGTH_LONG).show();
                            p.dismiss();
                            editTextEmail.setText("");
                            editTextName.setText("");
                            editTextPassword.setText("");
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override public void onFailure(@NonNull Exception e)
            {
                p.dismiss();
                editTextEmail.setText("");
                editTextName.setText("");
                editTextPassword.setText("");
                Toast.makeText(Register.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean isValidate()
    {
        p.dismiss();

        if (! Patterns.EMAIL_ADDRESS.matcher(editTextEmail.getText().toString()).matches()){
            editTextEmail.setError("Invalid email");
            editTextEmail.setFocusable(true);
            return false;
        } else
        if (editTextPassword.getText().toString().length()<6)
        {
            editTextPassword.setError("password length at least 6 characters");
            editTextPassword.setFocusable(true); return false;
        }

        if (editTextName.getText().toString().length()<2)
        {
            editTextName.setError("Please enter full name");
            editTextName.setFocusable(true); return false;
        }
        return true;
    }

    public void back(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void signin1(View view) {
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }
}


