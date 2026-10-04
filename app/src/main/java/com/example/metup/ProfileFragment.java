package com.example.metup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;
public class ProfileFragment extends Fragment {
    public ProfileFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_profile, container, false);

        TextInputEditText profile_name,profile_gender,profile_birthdate,profile_city,profile_mobno;
        TextView adminName,profile_email;
        MaterialButton profile_update,profile_btn;

        adminName= v.findViewById(R.id.adminName);
        profile_email= v.findViewById(R.id.profile_email);
        profile_btn=v.findViewById(R.id.profile_btn);

        profile_name= v.findViewById(R.id.profile_name);
        profile_gender= v.findViewById(R.id.profile_gender);
        profile_birthdate= v.findViewById(R.id.profile_birthdate);
        profile_city= v.findViewById(R.id.profile_city);
        profile_mobno= v.findViewById(R.id.profile_mobno);
        profile_update=v.findViewById(R.id.profile_update);
        TextView aboutprofile =v.findViewById(R.id.aboutprofile);
   ImageView img_changeabout=v.findViewById(R.id.img_changeabout);

        String currentuser= FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profile_name.setText(snapshot.child("Name").getValue().toString());
                profile_gender.setText(snapshot.child("Gender").getValue().toString());
                profile_mobno.setText(snapshot.child("MobileNo").getValue().toString());
                profile_email.setText(snapshot.child("EmailID").getValue().toString());
                adminName.setText(snapshot.child("Name").getValue().toString());

                if(snapshot.child("About").getValue()!=null)
                {
                   aboutprofile.setText(snapshot.child("About").getValue().toString());
                }
                if(snapshot.child("city").getValue()!=null)
                {
                    profile_city.setText(snapshot.child("city").getValue().toString());
                }
                if(snapshot.child("birthdate").getValue()!=null)
                {
                    profile_birthdate.setText(snapshot.child("birthdate").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
       profile_update.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              String Name = profile_name.getText().toString();
              String MobileNo = profile_mobno.getText().toString();
              String Gender= profile_gender.getText().toString();
              String city=profile_city.getText().toString();
              String birthdate = profile_birthdate.getText().toString();
               HashMap<String, Object> hm= new HashMap<>();
               hm.put("Name",Name);
               hm.put("MobileNo",MobileNo);
               hm.put("Gender",Gender);
               hm.put("city",city);
               hm.put("birthdate",birthdate);
               FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).updateChildren(hm);
               Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();

           }
       });
       //change about of user on click edit about imageview
        img_changeabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit = new EditText(getContext());
                AlertDialog.Builder alertbox =new AlertDialog.Builder(getContext());
                alertbox.setTitle("What's your mood today");
                alertbox.setMessage("Enter about yourself");
                alertbox.setView(edit);
                alertbox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HashMap<String,Object> hm =new HashMap<>();
                        hm.put("About",edit.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).updateChildren(hm);
                    }
                });
                alertbox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                alertbox.show();
            }
        });
        return v;
    }
}