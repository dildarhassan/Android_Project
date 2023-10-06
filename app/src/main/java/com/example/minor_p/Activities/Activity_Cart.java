package com.example.minor_p.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.minor_p.Adapter_Activities.Cart_Adapter;
import com.example.minor_p.Model_Activities.Items_model;
import com.example.minor_p.R;
import com.example.minor_p.databinding.ActivityCartBinding;

import java.util.ArrayList;
import java.util.Random;

public class Activity_Cart extends AppCompatActivity implements itemCartClickListener{
    ActivityCartBinding binding;
    ArrayList<Items_model> cartList;


    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart);
        cartList = (ArrayList<Items_model>) getIntent().getSerializableExtra("List");
//        LocalBroadcastManager.getInstance(this)
//                .registerReceiver(mrcve,new IntentFilter("Total_Price"));


        Cart_Adapter cart_adapter = new Cart_Adapter(this, cartList,this);

        cart_adapter.updateList(cartList);
        binding.cartList.setLayoutManager(new LinearLayoutManager(this));
        binding.cartList.setAdapter(cart_adapter);
//        int sum = 0;
//        for (Items_model item : cartList) {
//            sum += Integer.parseInt(item.getItemPrice());
//        }
//        binding.subtotal.setText(sum + ".00");

        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String x=String.valueOf(Math.random());
               String orderId="OD"+x.substring(8).concat("FT");
                Toast.makeText(Activity_Cart.this, orderId, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void addToCart(Items_model items_model) {

    }

    @Override
    public void removeFromCart(Items_model items_model) {
        cartList.remove(items_model);
    }
//    BroadcastReceiver mrcve=new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            int totalPrice=intent.getIntExtra("totalPrice",0);
//            binding.subtotal.setText(String.valueOf(totalPrice));
//        }
//    };
}

