package com.example.metup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import com.example.metup.model.*;
import com.example.metup.adapter.*;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
public class Message extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Intent i=getIntent();
        String name=i.getStringExtra("name");
        String recieveruid=i.getStringExtra("uid");
        //Toast.makeText(this,name+uid, Toast.LENGTH_LONG).show();

        TextView txt_msgusername =findViewById(R.id.txt_msgusername);
        CircleImageView msg_send =findViewById(R.id.msg_send);
        EditText txt_msgbox=findViewById(R.id.txt_msgbox);
        ImageView msgBack=findViewById(R.id.msgBack);

        msgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message.this.finish();
            }
        });

        txt_msgusername.setText(name);
        String senderuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        msg_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg= txt_msgbox.getText().toString();
                if(msg.isEmpty()){
                    Toast.makeText(Message.this, "Input your message", Toast.LENGTH_SHORT).show();
                }
                else{
                    SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");
                    String date = formatter.format(new Date());
                    SimpleDateFormat format1= new SimpleDateFormat("HH:mm aa");
                    String time=format1.format(new Date());

                    HashMap<String,String> hm= new HashMap<>();
                    hm.put("chat",msg);
                    hm.put("date",date);
                    hm.put("time",time);
                    hm.put("sender",senderuid);
                    FirebaseDatabase.getInstance().getReference().child("message").child(senderuid+recieveruid).push().setValue(hm)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        FirebaseDatabase.getInstance().getReference().child("message").child(recieveruid+senderuid).push().setValue(hm)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        txt_msgbox.setText("");
                                                    }
                                                });
                                    }
                                    else{
                                        Toast.makeText(Message.this, "Server error! Try again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }           }
        });

        //select all data form message table and bind into arraylist
        ArrayList<msgmodel> list = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("message").
                child(senderuid+recieveruid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                     //   Toast.makeText(Message.this, snapshot.getChildrenCount()+"", Toast.LENGTH_SHORT).show();
                        for(DataSnapshot msg:snapshot.getChildren()){
                            msgmodel m1=new msgmodel();
                            m1.msgid= msg.getKey();
                            m1.message=msg.child("chat").getValue().toString();
                            m1.date=msg.child("date").getValue().toString();
                            m1.time=msg.child("time").getValue().toString();
                            m1.sender=msg.child("sender").getValue().toString();
                            list.add(m1);
                        }
                        RecyclerView recycler =findViewById(R.id.msg_recycler);
                        messageadapter adapter= new messageadapter(Message.this,list);
                        recycler.setAdapter(adapter);
                        recycler.setLayoutManager(new LinearLayoutManager(Message.this));
                        if(list.size()>1) {
                            recycler.scrollToPosition(list.size()-1);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Message.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }                });
    }}