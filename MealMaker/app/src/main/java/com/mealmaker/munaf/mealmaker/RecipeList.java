package com.mealmaker.munaf.mealmaker;

import android.content.Intent;
import android.nfc.Tag;
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
import java.util.StringTokenizer;

public class RecipeList extends AppCompatActivity {

    public static final String TAG = "RecipeList";
    private String subject = "";
    private String dontHaveString = "";
    private String inst = "Instructions:\n";
    private ArrayList<String> have = new ArrayList<>();
    private ArrayList<String> dontHave = new ArrayList<>();
    private ArrayList<String> itemNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String recipe =  getIntent().getExtras().getString("recipe_json");
        ArrayList<PantryItem> pd = (ArrayList<PantryItem>) getIntent().getSerializableExtra("current_items");

        for (PantryItem item:pd){
            itemNames.add(item.getName());
        }

        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        TextView tv_youHave = (TextView) findViewById(R.id.tv_youHave);
        TextView tv_youNeed = (TextView) findViewById(R.id.tv_youNeed);
        TextView tv_instruct = (TextView) findViewById(R.id.tv_instructions);
        TextView tv_dr = (TextView) findViewById(R.id.tv_dr);

        tv_title.setText("");
        String title = "";
        String dr = "";

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
                    subject="Shopping List and Instructions for "+jObject.get(key);
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
//                    for (String s : list){
//                        list_str = list_str + s + "\n";
//                    }
                    haveOrNot(list);
//                    body="Ingredients:\n"+list_str;
                }

                if(key.equals("instructions")){
                    String line=jObject.get(key).toString();
                    StringTokenizer stringTokenizer = new StringTokenizer(line,"\n");
                    int count = 1;
                    while (stringTokenizer.hasMoreTokens()){
                        inst = inst + count +"."+stringTokenizer.nextToken()+"\n\n";
                        count++;
                    }

                }

                if(key.equals("diet_rest")){
                    if (jObject.get(key).equals("vegetarian")){
                        dr = "Vegetarian";
                    }else{
                        dr = "Non-Vegetarian";
                    }
                }
//                if ( jObject.get(key) instanceof JSONObject ) {
//                      IF JSON INSIDE JSON - MIGHT BE USEFUL IN THE FUTURE!
//                }
            }
        }catch (JSONException je){
            tv_title.append("Error! Am i connected to the internet?");
            Log.e(TAG, "JSON Parsing error.");
        }


        tv_title.append(title+"\n"); //change activity 'title' to recipe name (where it says 'Recipe' rn)

        String haveString = "";
        for (String s : have){
//            Log.i(TAG,""+s);
            haveString = haveString + s + "\n";
        }
        Log.i(TAG,haveString);
        tv_youHave.append(haveString+"\n");

        for (String s : dontHave){
            dontHaveString = dontHaveString + s + "\n";
        }
        tv_youNeed.append(dontHaveString+"\n");


        tv_instruct.append(inst+"\n");
        tv_dr.append(dr); //use image view for veg/nonveg icon

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, dontHaveString+"\n\n"+inst);
                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

    }

    private void haveOrNot(ArrayList<String> list) {
        for(String s : list){
            Log.i(TAG,s);
            for (String i : itemNames){
                if(s.toLowerCase().trim().contains(i) && !have.contains(s)){
                    have.add(s);
                }
            }
            if(!have.contains(s)){
                dontHave.add(s);
            }
        }
        Log.i(TAG, "have: " + have.size() + " donthave: " + dontHave.size());
    }
}
