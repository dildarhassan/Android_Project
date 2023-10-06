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
import com.example.minor_p.Activities.Activity_selectedCategory;
import com.example.minor_p.Model_Activities.Items_model;
import com.example.minor_p.R;
import com.example.minor_p.databinding.ViewcategoryBinding;

import java.util.ArrayList;

public class Category_adapter extends RecyclerView.Adapter<Category_adapter.My_ViewHolder> {
    Context context;
    ArrayList<Items_model> categories;


    @SuppressLint("NotifyDataSetChanged")
    public void updateList(ArrayList<Items_model> categories){
        this.categories = categories;
        notifyDataSetChanged();
    }


    public Category_adapter(Context context, ArrayList<Items_model> categories) {
        this.context=context;
        this.categories=categories;
    }

    @NonNull
    @Override
    public My_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.viewcategory,parent,false);
        return new My_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull My_ViewHolder holder, int position) {
        holder.binding.image.setClipToOutline(true);
        Items_model category=categories.get(position);
//        Picasso.with(context).load(category.getImageUrl()).into(holder.binding.image, new Callback() {
//            @Override
//            public void onSuccess() {
//                holder.binding.pbImageLoader.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onError() {
//
//            }

//        });
       // Glide.with(context).load(category.getImageUrl()).into(holder.binding.image);
        //holder.binding.label.setText(category.getName);
        Glide.with(context).load(category.getImageUrl()).listener(new RequestListener<Drawable>() {
            @SuppressLint("CheckResult")
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

        holder.binding.label.setText(category.getItemName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context.getApplicationContext(), Activity_selectedCategory.class);
                //intent.putExtra("CatName",category.getName());
                intent.putExtra("catName",category.getItemName());
               // Toast.makeText(context, category.getName(), Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    public class My_ViewHolder extends RecyclerView.ViewHolder {
        ViewcategoryBinding binding;
        public My_ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding= ViewcategoryBinding.bind(itemView);
        }
    }

}














