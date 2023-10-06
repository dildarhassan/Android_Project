package com.example.minor_p.Adapter_Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.minor_p.Activities.Activity_Cart;
import com.example.minor_p.Activities.itemCartClickListener;
import com.example.minor_p.Model_Activities.Items_model;
import com.example.minor_p.R;
import com.example.minor_p.databinding.ViewcartBinding;

import java.util.ArrayList;

public class Cart_Adapter extends RecyclerView.Adapter<Cart_Adapter.MyAdapter_viewHolder> {
    Context context;
    ArrayList<Items_model> cartList;
    itemCartClickListener listener;
    int totalPrice=0;

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(ArrayList<Items_model> cartList) {
        this.cartList=cartList;
        notifyDataSetChanged();
    }

    public Cart_Adapter(Activity_Cart context, ArrayList<Items_model> cartList,itemCartClickListener listener) {
        this.context = context;
        this.cartList = cartList;
        this.listener=listener;
    }

    @NonNull
    @Override
    public MyAdapter_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.viewcart,parent,false);
        return new Cart_Adapter.MyAdapter_viewHolder(view);
    }

    @SuppressLint({"CheckResult", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull MyAdapter_viewHolder holder,int position) {
        Items_model data_model=cartList.get(position);
        holder.binding.name.setText(data_model.getItemName());
        holder.binding.price.setText("Rs "+data_model.getItemPrice()+".00");


        Glide.with(context).load(data_model.getImageUrl()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;

            }
            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.binding.pbImageLoader.setVisibility(View.GONE);
                return false;

            }
        }).into(holder.binding.image);

        //Remove From Cart
        holder.binding.image.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                    for(int i = cartList.size()-1; i >= 0; i--) {
                        if(cartList.get(i)==data_model) {
                            cartList.remove(i);
                        }
                    }
                notifyDataSetChanged();
            }
        });
//        totalPrice= Integer.parseInt(totalPrice + cartList.get(position).getItemPrice());
//        Intent intent=new Intent("Total_Price");
//        intent.putExtra("totalPrice",totalPrice);
//        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyAdapter_viewHolder extends RecyclerView.ViewHolder {
        ViewcartBinding binding;
        public MyAdapter_viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=ViewcartBinding.bind(itemView);

        }
    }
}
