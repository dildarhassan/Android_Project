package com.example.minor_p.F_Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.minor_p.Adapter_Activities.Category_adapter;
import com.example.minor_p.Model_Activities.Items_model;
import com.example.minor_p.databinding.FragmentCategoryBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Fragment_Category extends Fragment  {
    FragmentCategoryBinding binding;
    private DatabaseReference mDatabase;
    ArrayList<Items_model>categories;
    Category_adapter category_adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentCategoryBinding.inflate(getLayoutInflater());
        initCategories();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    void initCategories() {
        categories=new ArrayList<>();
        category_adapter=new Category_adapter(getContext(),categories);
        getCategories();

        GridLayoutManager layoutManager =new GridLayoutManager(getContext(),2);
        binding.categoriesList.setLayoutManager(layoutManager);
        binding.categoriesList.setAdapter(category_adapter);
    }
    private void getCategories() {
        mDatabase=FirebaseDatabase.getInstance().getReference("CATEGORY");
        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot=task.getResult();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Items_model items_model =new Items_model(ds.child("image").getValue().toString(),ds.child("name").getValue().toString());
                        categories.add(items_model);
                    }
                    category_adapter.updateList(categories);
                }else {
                    Toast.makeText(getContext(), "SOMETHING ERROR OCCURRED", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}