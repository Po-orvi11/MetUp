package com.example.metup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.metup.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registration extends AppCompatActivity {
    TextView text_goToLogin;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().hide();

        text_goToLogin=findViewById(R.id.text_goToLogin);
        text_goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent class is use to redirect from activity to another activity
                Intent in =new Intent(Registration.this, MainActivity.class);
                startActivity(in);
            }
        });


        TextInputEditText txt_name, txt_mob, txt_email, txt_pwd;
        txt_name= findViewById(R.id.txt_name);
        txt_mob= findViewById(R.id.txt_mob);
        txt_email= findViewById(R.id.txt_email);
        txt_pwd= findViewById(R.id.txt_pwd);

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name, EmailID, MobileNo, Password;

                Name =txt_name.getText().toString();
                EmailID =txt_email.getText().toString();
                MobileNo=txt_mob.getText().toString();
                Password =txt_pwd.getText().toString();
                if(Name.isEmpty() || EmailID.isEmpty() || MobileNo.isEmpty() || Password.isEmpty()){
                    Toast.makeText(Registration.this, "PLEASE FILL ALL FIELDS PROPERLY", Toast.LENGTH_SHORT).show();
                }
                else {
                    //save data in database
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(EmailID,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                String uid = task.getResult().getUser().getUid();
                                HashMap<String, String> user = new HashMap<>();
                                user.put("Name", Name);
                                user.put("MobileNo",MobileNo);
                                user.put("EmailID", EmailID);
                                user.put("Password", Password);
                                user.put("Gender", "NOT KNOW");
                                FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(Registration.this, "REGISTRATION SUCCESSFUL", Toast.LENGTH_LONG).show();
                                                Intent in = new Intent(Registration.this, MainActivity.class);
                                                startActivity(in);
                                            }

                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(Registration.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }
                            else{
                                Toast.makeText(Registration.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Registration.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });
    }
}