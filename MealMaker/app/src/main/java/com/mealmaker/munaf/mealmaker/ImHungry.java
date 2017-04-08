package com.mealmaker.munaf.mealmaker;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import com.mongodb.client.MongoCursor;

import org.bson.Document;

public class ImHungry extends AppCompatActivity{

    private final static String TAG = "ImHungry";

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
        MongoCursor<Document> results = searchEngine.findExact(pantryData);
        try {
            while (results.hasNext()) {
                Document doc = results.next();
                Log.i(TAG,"RECEIVED: "+doc.toJson());
                String s = "#####\nFound...\n" + doc.get("title") + ", can be found at: " + doc.get("source_url") +
                        ".\nIf you're seeing this the demo is working. Feels great right?\n#####\n";
                tv.append(s);
            }
        } finally {
            results.close();
        }
        Button back = (Button) findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();
            }
        });
    }

}


