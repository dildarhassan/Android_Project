package com.example.minor_p.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.minor_p.F_Activities.Fragment_Category;
import com.example.minor_p.F_Activities.Fragment_Delivery;
import com.example.minor_p.F_Activities.Fragment_Menu;
import com.example.minor_p.F_Activities.Fragment_Profile;
import com.example.minor_p.R;
import com.example.minor_p.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends FragmentActivity {
    ActivityMainBinding binding;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
  //    userId=getIntent().getStringExtra("userId");
        SharedPreferences sharedPreferences= getSharedPreferences("MY_FILE", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("user_Id", "NA");
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new Fragment_Menu()).commit();
        binding.btnNavigation.setBottom(R.id.rest_menu);

        binding.btnNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment temp=null;

                switch (item.getItemId()){
                    case R.id.delivery:temp = new Fragment_Delivery();
                    break;
                    case R.id.rest_menu:temp = new Fragment_Menu();
                    break;
                    case R.id.category:temp = new Fragment_Category();
                    break;
                    case R.id.user_profile:temp = new Fragment_Profile();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,temp).commit();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder builder= new  AlertDialog.Builder(this)
                .setTitle("Do You Really Want To Exist")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(true);
    }

    public String returnUserid(){
        return userId;
    }

}