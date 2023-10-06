package com.example.minor_p.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.minor_p.Adapter_Activities.SelectedCategory_adapter;
import com.example.minor_p.Model_Activities.Items_model;

import com.example.minor_p.R;
import com.example.minor_p.databinding.SelectedCategoryItemBinding;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.internal.NavigationMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;


public class Activity_selectedCategory extends AppCompatActivity implements itemCartClickListener {
    String itemSelected;
    String itemName;
    SelectedCategoryItemBinding binding;
    private DatabaseReference mDatabase;
    ArrayList <Items_model> selectedCategoryList;
    SelectedCategory_adapter selectedCategory_adapter;
    ArrayList<Items_model> cartList;
    Boolean isAllFlotVisible;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartList=new ArrayList<>();
//        sharedPreferences=getSharedPreferences("MY_FILE",MODE_PRIVATE);
//        SharedPreferences.Editor myFile=sharedPreferences.edit();
//        Gson gson=new Gson();
//        String json = gson.toJson(cartList);
//        myFile.putString("List",json);
//        myFile.apply();


        binding= DataBindingUtil.setContentView(this,R.layout.selected_category_item);

        itemName=getIntent().getStringExtra("catName");
        if (itemName==null){
            Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
        }else if (itemName.contains(" ")){
            itemSelected=itemName.replace(" ","_").toUpperCase();
        }else {
            itemSelected=itemName.toUpperCase();
        };

        initCategories();
        setFlotBtn();
        initFloatingBtn();
    }

    void initCategories() {
        selectedCategoryList = new ArrayList<>();
        selectedCategory_adapter = new SelectedCategory_adapter(Activity_selectedCategory.this, selectedCategoryList,this);
        getSelectedProduct(itemSelected);
        GridLayoutManager layoutManager = new GridLayoutManager(Activity_selectedCategory.this, 3);
        binding.selectedCategoryList.setLayoutManager(layoutManager);
        binding.selectedCategoryList.setAdapter(selectedCategory_adapter);
    }


    void getSelectedProduct(String productName){
        mDatabase= FirebaseDatabase.getInstance().getReference(productName);
        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot=task.getResult();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Items_model category_model=new Items_model(
                                (ds.child("image").getValue()).toString(),
                                ds.child("name").exists() ?
                                        (ds.child("name").getValue()).toString() : (ds.child("name ").getValue()).toString(),
                                (ds.child("price").getValue()).toString());
                        selectedCategoryList.add(category_model);
                    }
                    selectedCategory_adapter.updateList(selectedCategoryList);
                }else {
                    Toast.makeText(Activity_selectedCategory.this, "SOMETHING ERROR OCCURRED", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    //Floating Button setting
    public  void setFlotBtn() {
        binding.AddCartBtn.setVisibility(View.GONE);
        binding.AddCartTv.setVisibility(View.GONE);
        binding.AddFavBtn.setVisibility(View.GONE);
        binding.AddFavTv.setVisibility(View.GONE);

        isAllFlotVisible = false;

        binding.AddFlotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isAllFlotVisible) {
                    binding.AddCartBtn.show();
                    binding.AddFavBtn.show();
                    binding.AddCartTv.setVisibility(View.VISIBLE);
                    binding.AddFavTv.setVisibility(View.VISIBLE);


                    isAllFlotVisible = true;
                } else {
                    binding.AddCartBtn.hide();
                    binding.AddFavBtn.hide();
                    binding.AddFavTv.setVisibility(View.GONE);
                    binding.AddCartTv.setVisibility(View.GONE);

                    isAllFlotVisible = false;
                }
            }
        });
    }
    public void initFloatingBtn(){
        binding.AddFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(Activity_selectedCategory.this,"FAV OPEN", Toast.LENGTH_SHORT).show();

            }
        });
        binding.AddCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartList.size()==0) {
                    Toast.makeText(Activity_selectedCategory.this,"No Item Is Added To Cart",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(Activity_selectedCategory.this, Activity_Cart.class);
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
