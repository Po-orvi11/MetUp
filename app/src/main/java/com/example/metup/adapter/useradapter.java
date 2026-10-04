package com.example.metup.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.metup.Message;
import com.example.metup.model.usermodel;

import com.example.metup.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class useradapter extends RecyclerView.Adapter<useradapter.viewHolder> {

    Context con;
    ArrayList<usermodel> user;
    TextView Name,about,date,time;
    public useradapter(Context con,ArrayList<usermodel> user){
        this.con=con;
        this.user=user;
    }
    //this is first method to be execute when adapter is called (return the design name usersampledesign)
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
   View v= LayoutInflater.from(con).inflate(R.layout.sampleuserdesign,parent,false);
   return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
      Name.setText(user.get(position).Name);
      about.setText(user.get(position).EmailID);
      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent i= new Intent(con, Message.class);
              i.putExtra("name",user.get(position).Name);
              i.putExtra("uid",user.get(position).uid);
              con.startActivity(i);
          }
      });
    }

    @Override
    public int getItemCount() {
        return user.size();
    }

    //this is viewholder class that is use to get reference of view elements
    class viewHolder extends RecyclerView.ViewHolder{

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.text);
            about = itemView.findViewById(R.id.info);
            date=itemView.findViewById(R.id.msgdate);
            time=itemView.findViewById(R.id.msgtime);
        }
    }

}
