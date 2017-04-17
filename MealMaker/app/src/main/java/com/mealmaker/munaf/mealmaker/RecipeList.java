package com.mealmaker.munaf.mealmaker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class RecipeList extends AppCompatActivity {

    public static final String TAG = "RecipeList";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "'Mail Recipe to a Friend' MIGHT be implemented soon ;)", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        TextView tv = (TextView) findViewById(R.id.tv_Text);
        String recipe =  getIntent().getExtras().getString("recipe_json");
        tv.setText("");

        try {
            JSONObject jObject = new JSONObject(recipe);
            Iterator<?> keys = jObject.keys();

            while(keys.hasNext()) {
                String key = (String)keys.next();
                if(!key.equals("_id")) {
                    tv.append(key + "\n");
                    tv.append(jObject.get(key).toString() + "\n\n");
                }
//                if ( jObject.get(key) instanceof JSONObject ) {
//                      IF JSON INSIDE JSON - MIGHT BE USEFUL IN THE FUTURE!
//                }
            }
        }catch (JSONException je){
            tv.append("Error! Am i connected to the internet?");
            Log.e(TAG, "JSON Parsing error.");
        }
    }
}
