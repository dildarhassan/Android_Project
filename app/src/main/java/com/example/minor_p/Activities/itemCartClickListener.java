package com.example.minor_p.Activities;

import com.example.minor_p.Model_Activities.Items_model;


public interface itemCartClickListener {

    void addToCart(Items_model items_model);
    void removeFromCart(Items_model items_model);
  //  int add(Items_model items_model);
    abstract int add();

}
