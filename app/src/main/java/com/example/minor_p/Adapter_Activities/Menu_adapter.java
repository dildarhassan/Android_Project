package com.example.minor_p.Adapter_Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.minor_p.Activities.Activity_viewItem;
import com.example.minor_p.Activities.itemCartClickListener;

import com.example.minor_p.Model_Activities.Items_model;
import com.example.minor_p.R;

import com.example.minor_p.databinding.ViewitemBinding;


import java.util.ArrayList;


public class  Menu_adapter extends RecyclerView.Adapter<Menu_adapter.My_ViewHolder> {

    Context context;
    ArrayList<Items_model> menuList;
    itemCartClickListener clickListener;

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(ArrayList<Items_model> menuList){
        this.menuList = menuList;
        notifyDataSetChanged();
    }


    public Menu_adapter(Context context, ArrayList<Items_model> menuList, itemCartClickListener clickListener) {
        this.context=context;
        this.menuList=menuList;
        this.clickListener=clickListener;
    }

    @NonNull
    @Override
    public Menu_adapter.My_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewitem,parent,false);
        return new Menu_adapter.My_ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "CheckResult"})
    @Override
    public void onBindViewHolder(@NonNull Menu_adapter.My_ViewHolder holder,int position) {
        holder.binding.itemImage.setClipToOutline(true);

        Items_model menu=menuList.get(position);
        Glide.with(context).load(menu.getImageUrl()).listener(new RequestListener<Drawable>() {
            @SuppressLint("CheckResult")
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }
            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.binding.pbItemImageLoader.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.binding.itemImage);


        holder.binding.itemName.setText(menu.getItemName());
        holder.binding.itemPrice.setText("Rs: " +menu.getItemPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Activity_viewItem.class);
                intent.putExtra("itemImageUrl",menu.getImageUrl())
                        .putExtra("itemName",menu.getItemName())
                        .putExtra("itemPrice",menu.getItemPrice());
                context.startActivity(intent);
            }
        });
        //Adding Item To Cart Function__________________
        holder.binding.manuItemCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.binding.manuItemCart.getTag() == null){
                    holder.binding.manuItemCart.setTag("ADDED");
                    holder.binding.manuItemCart.setImageDrawable(context.getDrawable(R.drawable.ic_cart_added));
                    clickListener.addToCart(menu);

                }else {
                    holder.binding.manuItemCart.setTag(null);
                    holder.binding.manuItemCart.setImageDrawable(context.getDrawable(R.drawable.ic_cart_removed));
                    clickListener.removeFromCart(menu);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class My_ViewHolder extends RecyclerView.ViewHolder {
        //ViewmenuBinding binding;
        ViewitemBinding binding;
        public My_ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding= ViewitemBinding.bind(itemView);
        }
    }

}
