package com.mealmaker.munaf.mealmaker;

import java.io.Serializable;


public class PantryItem implements Serializable {

    public  final Integer id;
    public static final Float DEFAULT_QUANTITY = 0f;
    public static final Integer DEFAULT_ID = 0;
    private final String name;
    private final Float quantity;

    public PantryItem(String name){
        this(name,DEFAULT_QUANTITY);
    }

    public PantryItem(String name, Float quantity){
        this(DEFAULT_ID, name, quantity);
    }

    public PantryItem(Integer id, String name, Float quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }



    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Float getQuantity() {
        return quantity;
    }
}
