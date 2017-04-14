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
    private DBHandler db;
    private TextView tv;
    private ArrayList<PantryItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypantrylayout);

        db = new DBHandler(this);
        tv = (TextView) findViewById(R.id.tv_pantrylist);
        Button addMore = (Button) findViewById(R.id.btn_addmore);
        Button imHungry = (Button) findViewById(R.id.btn_imhungry);
        Button reset = (Button) findViewById(R.id.btn_reset);

        //Fill DB for testing
        Log.i(TAG,"Inserting/Deleting items");
        Log.i(TAG,"Inserted/Deleted items");

        refresh();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteAll();
                refresh();
            }
        });

        addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Should access add more stub");
                Intent intent_AddMore = new Intent(getApplicationContext(), AddMore.class);
                int resultCode=200;
                startActivityForResult(intent_AddMore, resultCode);
            }
        });

        imHungry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Should access im hungry stub");
                Intent intent_imhungry = new Intent(getApplicationContext(), ImHungry.class);
                intent_imhungry.putExtra("pantry_data", list);
                startActivity(intent_imhungry);
            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode==200){
            refresh();
        }
    }

    private void refresh(){
        Log.i("MyPantry: ", "Reading all items..");
        tv.setText("");
        list = (ArrayList) db.getAllItems();
        if(list.size()==0){
            tv.setText("Nothing in your Pantry!");
        }
        for (PantryItem item : list) {
            tv.append(item.getName()+"\n");
        }
    }
}
