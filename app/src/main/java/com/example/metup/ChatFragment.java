package com.example.metup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.metup.adapter.useradapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import com.example.metup.model.usermodel;

public class ChatFragment extends Fragment {
    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_chat, container, false);
        //coding to fetch all users from database
        ArrayList<usermodel> list = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot user : snapshot.getChildren()){
                    //Toast.makeText(getContext(),user.getKey(),Toast.LENGTH_LONG).show();
                   // Toast.makeText(getContext(), user.child("Name").getValue()+"", Toast.LENGTH_LONG).show();
                    usermodel u1 =new usermodel();
                    u1.uid = user.getKey().toString();
                    u1.Name=user.child("Name").getValue().toString();
                    u1.MobileNo=user.child("MobileNo").getValue().toString();
                    u1.EmailID=user.child("EmailID").getValue().toString();
                    u1.Password=user.child("Password").getValue().toString();
                    u1.Gender=user.child("Gender").getValue().toString();
                    if(!u1.uid.equals( FirebaseAuth.getInstance().getCurrentUser().getUid())) //for not adding current login user in user list in chat section
                    {
                        list.add(u1);
                    }
                    else {
                        ((Home)getActivity()).getSupportActionBar().setSubtitle("Welcome,"+ u1.Name);

                    }
                }
                //let's bind adapter to recyclerView
                RecyclerView userrecycler =v.findViewById(R.id.user_recycler);
                useradapter adapter =new useradapter(getContext(),list);
                userrecycler.setAdapter(adapter);
                userrecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;
    }
}