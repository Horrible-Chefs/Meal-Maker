package com.mealmaker.munaf.mealmaker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MyPantry extends AppCompatActivity{

    private final static String TAG = "MyPantry";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypantrylayout);


        DBHandler db = new DBHandler(this);
        TextView tv = (TextView) findViewById(R.id.tv_pantrylist);
        Button addMore = (Button) findViewById(R.id.btn_addmore);
        Button imHungry = (Button) findViewById(R.id.btn_imhungry);

        //Fill DB for testing
        Log.i(TAG,"Inserting/Deleting items");
//db.addItem(new PantryItem("Broccoli Cheese Soup"));//DOCUMENT'S KEY SHOULD BE SET AS 'TITLE' (NO CAPS) IN SEARCHENGINE.FINDEXACT
        db.deleteItem("Broccoli Cheese Soup");
//        db.addItem(new PantryItem("The Pioneer Woman")); //DOCUMENT'S KEY SHOULD BE SET AS 'PUBLISHER' (NO CAPS) IN SEARCHENGINE.FINDEXACT
//        db.deleteItem("The Pioneer Woman");
        Log.i(TAG,"Inserted/Deleted items");
        db.deleteItem("Milk");
        db.deleteItem("Eggs");
        db.deleteItem("Meat");
        //db.deleteItem("");

        Log.i("MyPantry: ", "Reading all items..");
        final ArrayList<PantryItem> list = (ArrayList) db.getAllItems();

        if(list.size()==0){
            tv.append("Nothing in your Pantry!");
        }
        for (PantryItem item : list) {
            Log.i(TAG, item.getName());
            tv.append(item.getName()+"\n");
        }

        addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Should access add more stub");
                Intent intent_AddMore = new Intent(getApplicationContext(),AddMore.class);

                startActivity(intent_AddMore);

            }
        });

        imHungry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Should access im hungry stub");
                Intent intent_imhungry = new Intent(getApplicationContext(),ImHungry.class);
                intent_imhungry.putExtra("pantry_data",list);
                startActivity(intent_imhungry);
            }


        });

    }


}
