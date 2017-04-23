package com.mealmaker.munaf.mealmaker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class RecipeList extends AppCompatActivity {

    public static final String TAG = "RecipeList";
    private String subject = "";
    private String body = "";

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
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });
        TextView tv = (TextView) findViewById(R.id.tv_Text);
        String recipe =  getIntent().getExtras().getString("recipe_json");
        tv.setText("");
        String title = "";
        String inst = "";
        Integer dr = 0;


        try {
            JSONObject jObject = new JSONObject(recipe);
            Iterator<?> keys = jObject.keys();

            while(keys.hasNext()) {
                String key = (String)keys.next();
//                if(!key.equals("_id")) {
//                    tv.append(key + "\n");
//                    tv.append(jObject.get(key).toString() + "\n\n");
//                }

                if(key.equals("title")){
                    subject="Shopping List for "+jObject.get(key);
                    title = jObject.getString(key)+"\n";
                }

                String list_str = "";
                if(key.equals("ingredients")){
                    ArrayList<String> list = new ArrayList<String>();
                    JSONArray jsonArray = (JSONArray)jObject.get(key);
                    if (jsonArray != null) {
                        int len = jsonArray.length();
                        for (int i=0;i<len;i++){
                            list.add(jsonArray.get(i).toString());
                        }
                    }
                    for (String s : list){
                        list_str = list_str + s + "\n";
                    }
                    body="Ingredients:\n"+list_str;
                }

                if(key.equals("instructions")){
                    inst="Instructions:\n"+jObject.get(key);
                }

                if(key.equals("diet_rest")){
                    if (jObject.get(key).equals("vegetarian")){
                            dr = R.drawable.leaf_icon;
                    }else{
                            dr = R.drawable.meat_icon;
                    }
                }
//                if ( jObject.get(key) instanceof JSONObject ) {
//                      IF JSON INSIDE JSON - MIGHT BE USEFUL IN THE FUTURE!
//                }
            }
        }catch (JSONException je){
            tv.append("Error! Am i connected to the internet?");
            Log.e(TAG, "JSON Parsing error.");
        }
        tv.append(title+"\n"); //change activity 'title' to recipe name (where it says 'Recipe' rn)
        tv.append(body+"\n");
        tv.append(inst+"\n");
        tv.append(dr.toString()); //use image view for veg/nonveg icon
    }
}
