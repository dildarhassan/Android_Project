package com.example.minor_p.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.minor_p.R;
import com.example.minor_p.databinding.ActivityViewItemBinding;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class Activity_viewItem extends AppCompatActivity {
    ActivityViewItemBinding binding;
    String itemImageUrl,itemName,itemPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_view_item);
        itemImageUrl=getIntent().getStringExtra("itemImageUrl");
        itemName=getIntent().getStringExtra("itemName");
        itemPrice=getIntent().getStringExtra("itemPrice");

        if (itemImageUrl==null && itemName==null && itemPrice==null){
            Toast.makeText(this, "Something Error Occurred", Toast.LENGTH_SHORT).show();
        }else {
            initGetItem(itemImageUrl,itemName,itemPrice);

        }
    }

    private void initGetItem(String itemImageUrl, String itemName, String itemPrice) {
        binding.itemName.setText(itemName);
        binding.itemDesc.setText("Rs: "+ itemPrice);
       // binding.itemPrice.setText(itemImageUrl);
//        Picasso.with(this).load(itemImageUrl).into(binding.menuImage, new Callback() {
//            @Override
//            public void onSuccess() {
//                binding.pbImageLoader.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onError() {
//
//            }
//        });
        Glide.with(this).load(itemImageUrl).listener(new RequestListener<Drawable>() {
            @SuppressLint("CheckResult")
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }
            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                binding.pbImageLoader.setVisibility(View.GONE);
                return false;
            }
        }).into(binding.menuImage);

}
}