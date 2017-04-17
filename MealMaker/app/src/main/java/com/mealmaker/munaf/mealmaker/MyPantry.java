package com.mealmaker.munaf.mealmaker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyPantry extends AppCompatActivity{

    private final static String TAG = "MyPantry";
    private DBHandler db;
    private TextView tv;
    private ListView lv;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ArrayList<PantryItem> allItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypantrylayout);

        db = new DBHandler(this);
        tv = (TextView) findViewById(R.id.tv_empty);
        ImageButton addMore = (ImageButton) findViewById(R.id.btn_addmore);
        ImageButton imHungry = (ImageButton) findViewById(R.id.btn_imhungry);
        ImageButton reset = (ImageButton) findViewById(R.id.btn_reset);

        //Fill DB for testing
        Log.i(TAG,"Inserting/Deleting items");
        Log.i(TAG,"Inserted/Deleted items");

        refresh();
        registerClickCallback();


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteAll();
                list.clear();
                refresh();
                String message = "pushed everything down the drain!";
                Toast.makeText(MyPantry.this,message,Toast.LENGTH_LONG).show();

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
                intent_imhungry.putExtra("pantry_data", allItems);
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
    private void registerClickCallback(){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv2 = (TextView) view;
                String message = tv2.getText().toString()+" deleted.";
                Toast.makeText(MyPantry.this,message,Toast.LENGTH_LONG).show();
                db.deleteItem (((TextView) view).getText().toString());
                list.remove(tv2.getText().toString());
                refresh();
                Log.i(TAG,"Deleted something. listview updated");
            }
        });
    }

    private void refresh(){
        Log.i("MyPantry: ", "Reading all items..");
        allItems = (ArrayList) db.getAllItems();
        for (PantryItem item : allItems){
            if(!list.contains(item.getName())){
                list.add(item.getName());
            }
        }
        tv.setText("");
        if(list.isEmpty()){
            tv.setText("it's lonely in here..");
        }
        adapter = new ArrayAdapter<>(
                this,
                R.layout.each_item,
                list);

        lv = (ListView) findViewById(R.id.listview_pantry);
        lv.setAdapter(adapter);
    }
}
