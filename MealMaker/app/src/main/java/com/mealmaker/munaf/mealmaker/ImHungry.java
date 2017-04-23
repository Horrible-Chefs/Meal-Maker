package com.mealmaker.munaf.mealmaker;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.mongodb.client.MongoCursor;
import com.wang.avi.AVLoadingIndicatorView;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

public class ImHungry extends AppCompatActivity {

    private final static String TAG = "ImHungry";
    private ArrayList<Information> recipeNames = new ArrayList<>();
    private HashMap<String, String> queryResult = new HashMap<>();
    private ArrayList<Information> vegRecipeNames;
    private HashMap<String, String> vegResults;
    private ArrayList<Information> nVegRecipeNames;
    private HashMap<String, String> nonVegResults;
    private RecyclerView recyclerView;
    private MyCustomAdapter myCustomAdapter;
    private MyCustomAdapter myVegAdapter;
    private MyCustomAdapter myNonVegAdapter;
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Starting Im Hungry");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imhungrylayout);
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        tv = (TextView) findViewById(R.id.tv_recipeResults);

        final FloatingActionButton actionVeg = (FloatingActionButton) findViewById(R.id.action_veg);
        actionVeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Fitlering Veg only");
//                    if current adapter is not veg
                vegRecipeNames = new ArrayList<>();
                vegResults = new HashMap<>();
                for (Map.Entry<String, String> eachResult : queryResult.entrySet()) {
                    try {
                        JSONObject jObject = new JSONObject(eachResult.getValue());
                        if (jObject.get("diet_rest").equals("vegetarian")) {
                            vegRecipeNames.add(new Information(R.drawable.recipe_logo, eachResult.getKey()));
                            vegResults.put(eachResult.getKey(), eachResult.getValue());
                        }
                    } catch (JSONException je) {
                        Log.e(TAG, "JSON Parsing error.");
                    }
                }
                myVegAdapter = new MyCustomAdapter(ImHungry.this, vegRecipeNames, vegResults);
                recyclerView.setAdapter(myVegAdapter);
                GridLayoutManager staggeredGridLayoutManager = new GridLayoutManager(ImHungry.this, 2);
                recyclerView.setLayoutManager(staggeredGridLayoutManager);
            }
        });

        final FloatingActionButton actionNonVeg = (FloatingActionButton) findViewById(R.id.action_nonveg);
        actionNonVeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Fitlering Non Veg only");
//                    if current adapter is not veg
                nVegRecipeNames = new ArrayList<>();
                nonVegResults = new HashMap<>();
                for (Map.Entry<String, String> eachResult : queryResult.entrySet()) {
                    try {
                        JSONObject jObject = new JSONObject(eachResult.getValue());
                        if (jObject.get("diet_rest").equals("non_vegetarian")) {
                            nVegRecipeNames.add(new Information(R.drawable.recipe_logo, eachResult.getKey()));
                            nonVegResults.put(eachResult.getKey(), eachResult.getValue());
                        }
                    } catch (JSONException je) {
                        Log.e(TAG, "JSON Parsing error.");
                    }
                }
                myNonVegAdapter = new MyCustomAdapter(ImHungry.this, nVegRecipeNames, nonVegResults);
                recyclerView.setAdapter(myNonVegAdapter);
                GridLayoutManager staggeredGridLayoutManager = new GridLayoutManager(ImHungry.this, 2);
                recyclerView.setLayoutManager(staggeredGridLayoutManager);
            }
        });

        final FloatingActionButton actionAll = (FloatingActionButton) findViewById(R.id.action_all);
        actionAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Showing everything");
                recyclerView.setAdapter(myCustomAdapter);
                GridLayoutManager staggeredGridLayoutManager = new GridLayoutManager(ImHungry.this, 2);
                recyclerView.setLayoutManager(staggeredGridLayoutManager);
            }
        });

        final ArrayList<PantryItem> pantryData = (ArrayList<PantryItem>) getIntent().getSerializableExtra("pantry_data");

        final FloatingActionButton action_whatIf = (FloatingActionButton) findViewById(R.id.action_whatif);
        action_whatIf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"Should access what if stub");
                Intent intent_whatIf = new Intent(getApplicationContext(), WhatIf.class);
                intent_whatIf.putExtra("pantry_data", pantryData);
                startActivity(intent_whatIf);
            }
        });

         final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        new AsyncServerAccess().execute(pantryData);
    }

    private class AsyncServerAccess extends AsyncTask <ArrayList<PantryItem>,Void,Void>{
        private String TAG = "Async";
        private AVLoadingIndicatorView avi;
        private final Dialog dialog = new Dialog(ImHungry.this);

        @Override
        protected void onPreExecute() {
            dialog.setContentView(R.layout.custom_dialog);
            dialog.setTitle("Loading...");
            avi = (AVLoadingIndicatorView) dialog.findViewById(R.id.avi);
            startAnim();
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(ArrayList<PantryItem>...pantryData) {
            final SearchEngine searchEngine = new SearchEngine();
            Iterator<Document> results = searchEngine.findExact(pantryData[0]);
            while (results.hasNext()) {
                Document doc = results.next();
                Log.i(TAG, "RECEIVED: " + doc.toJson());
                recipeNames.add(new Information(R.drawable.recipe_logo, doc.get("title").toString()));
                queryResult.put(doc.get("title").toString(), doc.toJson());
            }
            myCustomAdapter = new MyCustomAdapter(ImHungry.this, recipeNames, queryResult);
            searchEngine.disconnectService();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            recyclerView.setAdapter(myCustomAdapter);
            GridLayoutManager staggeredGridLayoutManager = new GridLayoutManager(ImHungry.this, 2);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
            stopAnim();
            dialog.dismiss();
            super.onPostExecute(result);
        }

        void startAnim() {
            avi.smoothToShow();
        }

        void stopAnim() {
            avi.smoothToHide();
        }


    }
}


