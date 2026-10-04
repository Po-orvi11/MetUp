package com.example.metup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devlomi.circularstatusview.CircularStatusView;
import com.example.metup.Home;
import com.example.metup.R;
import com.example.metup.model.*;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import omari.hamza.storyview.StoryView;
import omari.hamza.storyview.callback.StoryClickListeners;
import omari.hamza.storyview.model.MyStory;

public class storyadapter extends RecyclerView.Adapter<storyadapter.ViewHolder> {
    Context con;

    ArrayList<storymodel> list;
    TextView txt_statususername,txt_statustime;
    CircleImageView img_status;
    CircularStatusView circular_status_view;

    public storyadapter(Context con, ArrayList<storymodel> list){
        this.con=con;
        this.list= list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(con).inflate(R.layout.samplestorydesign,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(list.get(position).userid.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
            txt_statususername.setText("Your Status");
        }
        else{
            txt_statususername.setText(list.get(position).name);

      }
txt_statustime.setText(list.get(position).time+" | "+ list.get(position).date);
circular_status_view.setPortionsCount(list.get(position).urls.size());
circular_status_view.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ArrayList<MyStory> myStories =new ArrayList<>();
        for(String s: list.get(position).urls){
                    myStories.add(
                    new MyStory(
                            s
            ));
        }
        new StoryView.Builder(((Home)con).getSupportFragmentManager())
                .setStoriesList(myStories) // Required
                .setStoryDuration(6000) // Default is 2000 Millis (2 Seconds)
                .setTitleText(list.get(position).name) // Default is Hidden
                .setSubtitleText(list.get(position).date+ " | " + list.get(position).time) // Default is Hidden
                .setStoryClickListeners(new StoryClickListeners() {
                    @Override
                    public void onDescriptionClickListener(int position) {
                        //your action
                    }

                    @Override
                    public void onTitleIconClickListener(int position) {
                        //your action
                    }
                }) // Optional Listeners
                .build() // Must be called before calling show method
                .show();

    }
});
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_statustime=itemView.findViewById(R.id.txt_statustime);
            txt_statususername=itemView.findViewById(R.id.txt_statususername);
            img_status=itemView.findViewById(R.id.img_status);
            circular_status_view=itemView.findViewById(R.id.circular_status_view);
        }
    }
}
