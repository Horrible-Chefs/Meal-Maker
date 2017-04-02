package com.mealmaker.munaf.mealmaker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;


public class SQLActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);


        DBHandler db = new DBHandler(this);
        TextView tv = (TextView) findViewById(R.id.tv_pantry);

        // Inserting/Deleting Items/Rows
//        Log.d("Insert: ", "Inserting ..");
//        db.addItem(new PantryItem(1, "Milk", 1f));
//        db.addItem(new PantryItem(2, "Eggs", 5f));
//        db.addItem(new PantryItem(3, "Meat", 2.3f));
//        db.deleteItem("Carrots");
//        db.deleteItem("Milk");
//        db.deleteItem("Bread");
//        db.deleteItem("Meat");

        // Reading all items
        Log.d("Reading: ", "Reading all shops..");
        List<PantryItem> list = db.getAllItems();

        for (PantryItem item : list) {
            String log = "Id: " + item.getId() + " , Name: " + item.getName() + " , Quantity: " + item.getQuantity()+"\n";
            // Writing to log
            Log.d("Item : ", log);
            tv.append(log);
        }
    }
}


