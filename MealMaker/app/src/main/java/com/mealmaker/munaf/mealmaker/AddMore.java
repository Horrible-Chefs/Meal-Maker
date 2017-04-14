package com.mealmaker.munaf.mealmaker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class AddMore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_more);
        final DBHandler db = new DBHandler(this);
        Button Enter_your_Items = (Button) findViewById(R.id.btn);
        Button back = (Button) findViewById(R.id.btn_back);
        final AutoCompleteTextView Item = (AutoCompleteTextView) findViewById(R.id.editText1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, Itemlist);
        Item.setAdapter(adapter);
        Item.setThreshold(1);
        final InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);


        Enter_your_Items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

                String Item_S = Item.getText().toString().trim();
                Boolean exists = db.itemExists(new PantryItem(Item_S));
                if(!exists && !Item_S.equals("")) {
                    db.addItem(new PantryItem(Item_S));
                    Snackbar.make(findViewById(R.id.snkbr_added), Item_S + " added to pantry!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Item.setText("");
                    setResult(200);
                }else{
                    Snackbar.make(findViewById(R.id.snkbr_added), "Error: Invalid Input or "+Item_S + " already exists in Pantry!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private static String[] Itemlist = new String[]{"Chicken", "Pasta", "Meat", "Carrots", "Milk", "Mango", "Bread", "Eggs"};
}
