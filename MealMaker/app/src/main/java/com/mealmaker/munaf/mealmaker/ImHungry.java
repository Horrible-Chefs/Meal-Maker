package com.mealmaker.munaf.mealmaker;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import com.mongodb.client.MongoCursor;

import org.bson.Document;

import static android.R.id.list;

public class ImHungry extends AppCompatActivity{

    private final static String TAG = "ImHungry";
    private ArrayAdapter<String> adapter;
    private ArrayList<String> recipeNames = new ArrayList<>();
    private HashMap<String,String> queryResult = new HashMap<>();
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG,"Starting Im Hungry");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imhungrylayout);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        TextView tv = (TextView) findViewById(R.id.tv_recipeResults);
        ArrayList<PantryItem> pantryData = (ArrayList<PantryItem>) getIntent().getSerializableExtra("pantry_data");
        SearchEngine searchEngine = new SearchEngine();
//      MongoCursor<Document> results = searchEngine.findExact(pantryData);
        MongoCursor<Document> results = searchEngine.findPublisher(pantryData);
        try {
            while (results.hasNext()) {
                Document doc = results.next();
                Log.i(TAG,"RECEIVED: "+doc.toJson());
                recipeNames.add(doc.get("title").toString());
                queryResult.put(doc.get("title").toString(),doc.toJson());
            }
            adapter = new ArrayAdapter<>(
                    this,
                    R.layout.each_item,
                    recipeNames);

            lv = (ListView) findViewById(R.id.listview_recipes);
            lv.setAdapter(adapter);
        } finally {
            results.close();
            if (recipeNames.isEmpty()){
                tv.append("Found Nothing, Sorry!");
            }
        }
        Button back = (Button) findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        registerClickCallback();
    }


    private void registerClickCallback(){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv2 = (TextView) view;
                Intent intent_recipe = new Intent(getApplicationContext(), RecipeList.class);
                intent_recipe.putExtra("recipe_json", queryResult.get(tv2.getText().toString()));
                startActivity(intent_recipe);
            }
        });
    }
}


