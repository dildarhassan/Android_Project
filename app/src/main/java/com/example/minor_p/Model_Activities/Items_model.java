package com.example.minor_p.Model_Activities;

import java.io.Serializable;

public class Items_model implements Serializable{
    private String imageUrl;
    private String itemName;
    private String itemPrice;
    private int menuItemId;

    public Items_model(String imageUrl, String itemName, String itemPrice, int menuItemId) {
        this.imageUrl = imageUrl;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.menuItemId = menuItemId;
    }

    public Items_model(String image, String name, String price) {
        this.imageUrl = image;
        this.itemName = name;
        this.itemPrice = price;
    }

    public Items_model(String image, String name) {
        this.imageUrl=image;
        this.itemName=name;
    }


    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    @Override
    public boolean equals(Object object)
    {
        boolean sameSame = false;

        if (object != null && object instanceof Items_model) {
            sameSame = this.menuItemId == ((Items_model) object).menuItemId;
        }
        return sameSame;
    }
}
