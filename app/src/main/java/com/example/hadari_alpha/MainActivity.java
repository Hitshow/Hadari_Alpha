package com.example.hadari_alpha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText Email,Password;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Email = (EditText) findViewById(R.id.Email);
        Password = (EditText) findViewById(R.id.Password);
        fAuth = FirebaseAuth.getInstance();
    }

    public void SignUp(View view) {
        Intent si = new Intent(this , Register.class);
        startActivity(si);
    }

    public void Back(View view) {
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if(email.isEmpty()){
            Email.setError("Email is required");
            Email.requestFocus();
            return;
        }
        if(password.isEmpty()){
            Password.setError("Email is required");
            Password.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Please enter a valid email!");
            Email.requestFocus();
            return;
        }
        if(password.length()<6){
            Password.setError("Min password length is 6 characters!");
            Password.requestFocus();
            return;
        }
        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Start in the homepage application
                }
                else{
                    Toast.makeText(MainActivity.this, "Failed to login! please check your credentials", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
