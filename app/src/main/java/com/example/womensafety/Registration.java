package com.example.womensafety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.womensafety.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.StringTokenizer;
import java.util.function.LongFunction;

public class Registration extends AppCompatActivity {
    Button register;
    EditText editText_full_name,editText_email,editText_password,editText_confirm_password;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        register=(Button)findViewById(R.id.reg_button);
        editText_full_name=(EditText)findViewById(R.id.reg_full_name);
        editText_email=(EditText)findViewById(R.id.reg_email);
        editText_password=(EditText)findViewById(R.id.reg_password);
        editText_confirm_password=(EditText)findViewById(R.id.reg_confirm_password);
        progressBar=(ProgressBar)findViewById(R.id.reg_progress_bar);


        mAuth = FirebaseAuth.getInstance();
        register.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String full_name,email,password,confirm_password;
                        full_name=editText_full_name.getText().toString();
                        email=editText_email.getText().toString();
                        password=editText_password.getText().toString();
                        confirm_password=editText_confirm_password.getText().toString();
                        if(isValid()){
                            register.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.VISIBLE);
                            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                            mAuth.createUserWithEmailAndPassword(email,password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()){
                                                User user= new User(full_name,email);
                                                FirebaseDatabase.getInstance().getReference("users")
                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                        .setValue(user)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                progressBar.setVisibility(View.GONE);
                                                                if (task.isSuccessful()){
                                                                    Toast.makeText(Registration.this, "Registration Done!", Toast.LENGTH_SHORT).show();
                                                                }else{
                                                                    Toast.makeText(Registration.this, "Some Error Occured!", Toast.LENGTH_SHORT).show();

                                                                }
                                                            }
                                                        });
                                            }else{
                                                progressBar.setVisibility(View.GONE);
                                                Toast.makeText(Registration.this, "Email Already Exits!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            register.setVisibility(View.VISIBLE);

                        }

                    }
                }
        );
    }

    Boolean isValid(){
        String full_name=editText_full_name.getText().toString();
        String email=editText_email.getText().toString().trim();
        String password= editText_password.getText().toString();
        String confirm_password=editText_confirm_password.getText().toString();

        if(full_name.isEmpty()){
            editText_full_name.setError("Fullname is Required");
            editText_full_name.requestFocus();
            return false ;
        }
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

        if(confirm_password.isEmpty()){
            editText_confirm_password.setError("Password is Requried!");
            editText_confirm_password.requestFocus();
            return false;
        }
        if(confirm_password.length()<6){
            editText_confirm_password.setError("Password must be 6 characters long! ");
            editText_confirm_password.requestFocus();
            return false;
        }
//        Log.i("confirm pass",confirm_password);
//        Log.i("password",password);

//        if (confirm_password!=password){
//            editText_password.setError("Password not matches");
//            editText_password.requestFocus();
//            return false;
//        }
        return  true;
    }
}