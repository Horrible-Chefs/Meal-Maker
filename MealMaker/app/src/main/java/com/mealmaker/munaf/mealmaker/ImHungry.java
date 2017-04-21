package com.mealmaker.munaf.mealmaker;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import com.mongodb.client.MongoCursor;

import org.bson.Document;

public class ImHungry extends AppCompatActivity{

    private final static String TAG = "ImHungry";
    private ArrayList<Information> recipeNames = new ArrayList<>();
    private HashMap<String,String> queryResult = new HashMap<>();
    private RecyclerView recyclerView;
    private MyCustomAdapter myCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG,"Starting Im Hungry");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imhungrylayout);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        TextView tv = (TextView) findViewById(R.id.tv_recipeResults);
        ArrayList<PantryItem> pantryData = (ArrayList<PantryItem>) getIntent().getSerializableExtra("pantry_data");
        SearchEngine searchEngine = new SearchEngine();
//      MongoCursor<Document> results = searchEngine.findExact(pantryData);
        MongoCursor<Document> results = searchEngine.findPublisher(pantryData);
        try {
            while (results.hasNext()) {
                Document doc = results.next();
                Log.i(TAG,"RECEIVED: "+doc.toJson());
                recipeNames.add(new Information(R.drawable.recipe_logo,doc.get("title").toString()));
                queryResult.put(doc.get("title").toString(),doc.toJson());
            }
            myCustomAdapter = new MyCustomAdapter(this, recipeNames, queryResult);
            recyclerView.setAdapter(myCustomAdapter);
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
        } finally {
            results.close();
            if (recipeNames.isEmpty()){
                tv.append("Found Nothing, Sorry!");
            }
        }
    }
}


