package com.example.hadari_alpha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText Name,Email,Password;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Email = (EditText) findViewById(R.id.Email);
        Password = (EditText) findViewById(R.id.Password);
        Name = (EditText) findViewById(R.id.Name);
        fAuth = FirebaseAuth.getInstance();
    }

    public void SignUp(View view) {
        registerUser();
    }

    private void registerUser() {
        String email = Email.getText().toString();
        String name = Name.getText().toString();
        String password =Password.getText().toString();
        if (name.isEmpty()) {
            Name.setError("Full name is required");
            Name.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            Email.setError("Email is required");
            Email.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            Password.setError("Password is required");
            Password.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Please provide valid email");
            Email.requestFocus();
            return;
        }

        if (password.length() < 6) {
            Password.setError("Password is should be 6 Characters!");
            Password.requestFocus();
            return;
        }

        fAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Users user = new Users(name, email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Register.this, "User has been registered successfully!", Toast.LENGTH_SHORT).show();
                                        //Move the user to the login activity
                                    } else {
                                        Toast.makeText(Register.this, "Failed to sign up", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        } else {
                            Toast.makeText(Register.this, "Failed to sign up", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void Back(View view) {
        finish();
    }
}