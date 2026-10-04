package com.example.metup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.metup.R;
import com.example.metup.model.*;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class messageadapter extends RecyclerView.Adapter{
        Context con;
        ArrayList<msgmodel> list;
        TextView txt_sendermsgtime,txt_sendermsgdate,txt_receivermsgtime,txt_receivermsgdate,txt_sendermsg,txt_receivermsg;

        public messageadapter(Context con,ArrayList<msgmodel> list)
    {
        this.con=con;
        this.list=list;
    }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if(viewType==1)
            {
             View v= LayoutInflater.from(con).inflate(R.layout.sendermsgsampledesign,parent,false);
             return new SenderViewHolder(v);
            }
            else{
                View v= LayoutInflater.from(con).inflate(R.layout.recivermsgsampledesign,parent,false);
                return new ReceiverViewHolder(v);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder.getClass()==SenderViewHolder.class){
                txt_sendermsg.setText(list.get(position).message);
                txt_sendermsgtime.setText(list.get(position).time);
                txt_sendermsgdate.setText(list.get(position).date);
            }
            else {
                txt_receivermsg.setText(list.get(position).message);
                txt_receivermsgtime.setText(list.get(position).time);
                txt_receivermsgdate.setText(list.get(position).date);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    @Override
    public int getItemViewType(int position) {
            if(list.get(position).sender.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                return 1;
            }
            else{
                return 2;
            }
    }

    class SenderViewHolder extends RecyclerView.ViewHolder{

            public SenderViewHolder(@NonNull View itemView) {
                super(itemView);
                txt_sendermsg=itemView.findViewById(R.id.txt_sendermsg);
                txt_sendermsgtime=itemView.findViewById(R.id.txt_sendermsgtime);
                txt_sendermsgdate=itemView.findViewById(R.id.txt_sendermsgdate);
            }
        }
        class ReceiverViewHolder extends RecyclerView.ViewHolder{

            public ReceiverViewHolder(@NonNull View itemView) {
                super(itemView);
                txt_receivermsg=itemView.findViewById(R.id.txt_receivermsg);
                txt_receivermsgtime=itemView.findViewById(R.id.txt_receivermsgtime);
                txt_receivermsgdate=itemView.findViewById(R.id.txt_receivermsgdate);
            }
        }
    }
