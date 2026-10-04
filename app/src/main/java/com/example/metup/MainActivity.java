package com.example.metup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    TextView text_goToRegister;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hide action bar
        getSupportActionBar().hide();

        TextInputEditText txt_loginemail,txt_loginpassword;
        txt_loginemail=findViewById(R.id.txt_loginemail);
        txt_loginpassword=findViewById(R.id.txt_loginpassword);

        text_goToRegister=findViewById(R.id.text_goToRegister);
        text_goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent class is use to redirect from activity to onother activity
                Intent in =new Intent(MainActivity.this,Registration.class);
                startActivity(in);
            }
        });

        btn= findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String email,password;
               email=txt_loginemail.getText().toString();
               password=txt_loginpassword.getText().toString();
               if(email.isEmpty() || password.isEmpty())
               {
                   Toast.makeText(MainActivity.this, "Please Input email and password", Toast.LENGTH_SHORT).show();
               }
               else{
                   FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful()){
                               Toast.makeText(MainActivity.this, "Welcome to MetUp", Toast.LENGTH_SHORT).show();
                               Intent in=new Intent(MainActivity.this,Home.class);
                               startActivity(in);
                           }
                           else{
                               Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();

                           }
                       }
                   });
               }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            Intent in =new Intent(this,Home.class);
            startActivity(in);
        }
    }
}