package com.mealmaker.munaf.mealmaker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddMore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_more);
        final DBHandler db = new DBHandler(this);
        Button Enter_your_Items = (Button) findViewById(R.id.btn);


        Enter_your_Items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText Item = (EditText) findViewById(R.id.editText1);
                EditText Quant = (EditText) findViewById(R.id.editText2);
                String Item_S = Item.getText().toString();
               // String Quant_S = Quant.getText().toString();
               db.addItem(new PantryItem(Item_S));


            }
        });




    }
}
