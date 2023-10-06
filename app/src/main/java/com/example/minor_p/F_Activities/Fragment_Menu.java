package com.example.minor_p.F_Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.minor_p.Activities.Activity_Cart;
import com.example.minor_p.Activities.itemCartClickListener;
import com.example.minor_p.Adapter_Activities.Menu_adapter;

import com.example.minor_p.Model_Activities.Items_model;

import com.example.minor_p.databinding.FragmentMenuBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;


public class Fragment_Menu extends Fragment implements itemCartClickListener {
    FragmentMenuBinding binding;
    private DatabaseReference mDatabase;
    ArrayList<Items_model> menuList;
    ArrayList<CarouselItem> slideList;
    Menu_adapter menu_adapter;
    ArrayList<Items_model> cartList;

    Boolean isAllFlotVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentMenuBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cartList=new ArrayList<>();
        setFlotBtn();
        initSlider();
        initMenuItem();
        initFloatingBtn();
    }

    //Initialize array/menuList/adapter in this method:
    void initMenuItem() {
        slideList=new ArrayList<>();
        menuList=new ArrayList<>();
        menu_adapter=new Menu_adapter(getContext(),menuList,this);

        getMenuItem();
        GridLayoutManager layoutManager =new GridLayoutManager(getContext(),3);
        binding.menuRvList.setLayoutManager(layoutManager);
        binding.menuRvList .setAdapter(menu_adapter);
    }

    //Getting data from FireBase using method:
    private void getMenuItem() {
        mDatabase=FirebaseDatabase.getInstance().getReference("MENU_ITEMS").child("MENU_ITEMS");

        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot=task.getResult();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Log.d((String) ds.child("image").getValue(),"dc");
                        Items_model items_model =new Items_model(
                                ds.child("image").getValue().toString(),
                                ds.child("name").exists()?
                                        ds.child("name").getValue().toString() : ds.child("name ").getValue().toString(),
                                ds.child("price").getValue().toString());
                        menuList.add(items_model);
                    }
                    menu_adapter.updateList(menuList);

                }else {
                    Toast.makeText(getContext(), "SOMETHING ERROR OCCURRED", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //Getting data from FireBase using method:
    void initSlider(){
        mDatabase=FirebaseDatabase.getInstance().getReference("SLIDER");
        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot=task.getResult();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        CarouselItem carouselItem=new CarouselItem(ds.child("image").getValue().toString());
                        slideList.add(carouselItem);
                        }
                    binding.carousel.addData(slideList);
                }else {
                    Toast.makeText(getContext(), "SOMETHING ERROR OCCURRED", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Floating Button setting

    public  void setFlotBtn(){
        binding.AddCartBtn.setVisibility(View.GONE);
        binding.AddCartTv.setVisibility(View.GONE);
        binding.AddFavBtn.setVisibility(View.GONE);
        binding.AddFavTv.setVisibility(View.GONE);

        isAllFlotVisible=false;

        binding.AddFlotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isAllFlotVisible){
                    binding.AddCartBtn.show();
                    binding.AddFavBtn.show();
                    binding.AddCartTv.setVisibility(View.VISIBLE);
                    binding.AddFavTv.setVisibility(View.VISIBLE);


                    isAllFlotVisible=true;
                }else {
                    binding.AddCartBtn.hide();
                    binding.AddFavBtn.hide();;
                    binding.AddFavTv.setVisibility(View.GONE);
                    binding.AddCartTv.setVisibility(View.GONE);

                    isAllFlotVisible=false;
                }
            }
        });
    }
    public void initFloatingBtn(){
        binding.AddFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getContext(),"FAV OPEN", Toast.LENGTH_SHORT).show();

            }
        });
        binding.AddCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cartList.size()==0) {
                    Toast.makeText(getContext(),"No Item Is Added To Cart",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getContext(), Activity_Cart.class);
                    intent.putExtra("List", cartList);
                    startActivity(intent);
                    // Log.d("123",e.getMessage());
                }

            }
        });
    }

    @Override
    public void addToCart(Items_model items_model) {
        cartList.add(items_model);

    }

    @Override
    public void removeFromCart(Items_model items_model) {
        cartList.remove(items_model);
    }

}