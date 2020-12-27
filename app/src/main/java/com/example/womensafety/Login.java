package com.example.womensafety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button btn_login;
    EditText editText_email,editText_password;
    TextView textView_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth= FirebaseAuth.getInstance();
        btn_login = (Button)findViewById(R.id.login);
        editText_email = (EditText)findViewById(R.id.login_email);
        editText_password = (EditText)findViewById(R.id.login_password);
        textView_signup = (TextView)findViewById(R.id.sign_up);




        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=editText_email.getText().toString().trim();
                String password=editText_password.getText().toString();

                if(isValid()){

                    mAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                                    if(user.isEmailVerified()){
                                        Toast.makeText(Login.this, "Logged in", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(Login.this, "You are not verified yet, link has been send to you email.", Toast.LENGTH_SHORT).show();
                                        user.sendEmailVerification();
                                    }
                                }else {
                                    Toast.makeText(Login.this, "errr", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }
            }
        });

        textView_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Registration.class));
            }
        });
    }
    Boolean isValid(){
        String email= editText_email.getText().toString().trim();
        String password= editText_password.getText().toString();
        if(email.isEmpty()){
            editText_email.setError("Email is Requried!");
            editText_email.requestFocus();
            return false ;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editText_email.setError("Invalid Email Address!");
            editText_email.requestFocus();
            return false ;
        }

        if(password.isEmpty()){
            editText_password.setError("Password is Requried!");
            editText_password.requestFocus();
            return false;
        }
        if(password.length()<6){
            editText_password.setError("Password must be 6 characters long! ");
            editText_password.requestFocus();
            return false;
        }
        return true;
    }

}

