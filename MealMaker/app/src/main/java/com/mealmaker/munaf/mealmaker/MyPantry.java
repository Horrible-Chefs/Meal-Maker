package com.mealmaker.munaf.mealmaker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

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

        final View actionB = findViewById(R.id.action_addmore);
        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Should access add more stub");
                Intent intent_AddMore = new Intent(getApplicationContext(), AddMore.class);
                int resultCode=200;
                startActivityForResult(intent_AddMore, resultCode);
            }
        });

        FloatingActionButton actionC = new FloatingActionButton(getBaseContext());
        actionC.setIcon(R.drawable.trash_icon);
        actionC.setTitle("Delete All");
        actionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteAll();
                list.clear();
                refresh();
                String message = "pushed everything down the drain!";
                Toast.makeText(MyPantry.this,message,Toast.LENGTH_LONG).show();
            }
        });

        final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        menuMultipleActions.addButton(actionC);

        final FloatingActionButton actionA = (FloatingActionButton) findViewById(R.id.action_imhungry);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"Should access im hungry stub");
                Intent intent_imhungry = new Intent(getApplicationContext(), ImHungry.class);
                intent_imhungry.putExtra("pantry_data", allItems);
                startActivity(intent_imhungry);
            }
        });

        tv = (TextView) findViewById(R.id.tv_empty);

        //Fill DB for testing
        Log.i(TAG,"Inserting/Deleting items");
        Log.i(TAG,"Inserted/Deleted items");

        refresh();
        registerClickCallback();
//        changeStatusBarColor();
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

    //    private void changeStatusBarColor() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.BLACK);
//        }
//    }
}