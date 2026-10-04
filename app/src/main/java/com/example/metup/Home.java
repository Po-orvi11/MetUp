package com.example.metup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#507687")));
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'><b>MetUp</b></font>"));
        getSupportActionBar().setSubtitle(Html.fromHtml("<font color='#afb1b3'><small>Welcome Poorvi</small></font>"));
        getSupportActionBar().setIcon(R.drawable.icon);
        TabLayout tabs = findViewById(R.id.tabLayoutHome);
        //set adapter to viewpager
        ViewPager2 viewpager1=findViewById(R.id.viewpagerhome);
        ViewPagerAdapter adapter= new ViewPagerAdapter(getSupportFragmentManager(),getLifecycle());
        viewpager1.setAdapter(adapter);

     tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
         @Override
         public void onTabSelected(TabLayout.Tab tab) {
             viewpager1.postDelayed(new Runnable() {
                 @Override
                 public void run() {
                     viewpager1.setCurrentItem(tab.getPosition());
                 }
             }, 30);
         }

         @Override
         public void onTabUnselected(TabLayout.Tab tab) {

         }

         @Override
         public void onTabReselected(TabLayout.Tab tab) {

         }
     });

        viewpager1.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                tabs.selectTab(tabs.getTabAt(position));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logout)
        {
            //statemente executes on click of logout optionmenu
            FirebaseAuth.getInstance().signOut();
            Intent in =new Intent(this,MainActivity.class);
            startActivity(in);
        }
        else if (item.getItemId()==R.id.setting) {
            //statemente executes on click of setting optionmenu

        }
        return super.onOptionsItemSelected(item);
    }
}

//CREATE AN ADAPTER FOR VIEWPAGER
class ViewPagerAdapter extends FragmentStateAdapter
{

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
      if(position==0)
          return new ChatFragment();
      else if (position==1)
          return new StoryFragment();
      else
          return new ProfileFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}


