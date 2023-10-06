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

public class SelectedCategory_adapter extends RecyclerView.Adapter<SelectedCategory_adapter.My_viewHolder>  {

    Context context;
    ArrayList<Items_model> selectedCatList;
    itemCartClickListener clickListener;

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(ArrayList<Items_model> categories){
        this.selectedCatList = categories;
        notifyDataSetChanged();
    }

    public SelectedCategory_adapter(Context context, ArrayList<Items_model> List, itemCartClickListener clickListener) {
        this.context=context;
        this.selectedCatList=List;
        this.clickListener=clickListener;
    }

    @NonNull
    @Override
    public SelectedCategory_adapter.My_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewitem,parent,false);
        return new My_viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedCategory_adapter.My_viewHolder holder, int position) {
        holder.binding.itemImage.setClipToOutline(true);
        Items_model categoryModel=selectedCatList.get(position);

//     Loading photo to imageview

        Glide.with(context).load(categoryModel.getImageUrl()).listener(new RequestListener<Drawable>() {
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

        holder.binding.itemName.setText(categoryModel.getItemName());
        holder.binding.itemPrice.setText("Rs: "+categoryModel.getItemPrice()+".00");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Activity_viewItem.class);
                intent.putExtra("itemImageUrl",categoryModel.getImageUrl())
                        .putExtra("itemName",categoryModel.getItemName())
                        .putExtra("itemPrice",categoryModel.getItemPrice());
                context.startActivity(intent);
            }
        });


           holder.binding.manuItemCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.binding.manuItemCart.getTag() == null){
                    holder.binding.manuItemCart.setTag("ADDED");
                    holder.binding.manuItemCart.setImageDrawable(context.getDrawable(R.drawable.ic_cart_added));
                    clickListener.addToCart(categoryModel);

                }else {
                    holder.binding.manuItemCart.setTag(null);
                    holder.binding.manuItemCart.setImageDrawable(context.getDrawable(R.drawable.ic_cart_removed));
                    clickListener.removeFromCart(categoryModel);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return selectedCatList.size();
    }

    public class My_viewHolder extends RecyclerView.ViewHolder {
        ViewitemBinding binding;
        public My_viewHolder(@NonNull View itemView) {
            super(itemView);
            binding= ViewitemBinding.bind(itemView);
        }
    }
}
