package com.mealmaker.munaf.mealmaker;

import android.app.Dialog;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.mongodb.client.MongoCursor;
import com.wang.avi.AVLoadingIndicatorView;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class WhatIf extends AppCompatActivity {

    private final static String TAG = "WhatIf";
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
    private ArrayList<PantryItem> pantryData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Starting What If");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_if);
        recyclerView = (RecyclerView) findViewById(R.id.recycleView_whatif);
        pantryData = (ArrayList<PantryItem>) getIntent().getSerializableExtra("pantry_data");

        final FloatingActionButton actionVeg = (FloatingActionButton) findViewById(R.id.action_veg_whatif);
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
                myVegAdapter = new MyCustomAdapter(WhatIf.this, vegRecipeNames, vegResults,pantryData);
                recyclerView.setAdapter(myVegAdapter);
                GridLayoutManager staggeredGridLayoutManager = new GridLayoutManager(WhatIf.this, 2);
                recyclerView.setLayoutManager(staggeredGridLayoutManager);
            }
        });

        final FloatingActionButton actionNonVeg = (FloatingActionButton) findViewById(R.id.action_nonveg_whatif);
        actionNonVeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Fitlering Non Veg only");
//                    if current adapter is not nonveg
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
                myNonVegAdapter = new MyCustomAdapter(WhatIf.this, nVegRecipeNames, nonVegResults,pantryData);
                recyclerView.setAdapter(myNonVegAdapter);
                GridLayoutManager staggeredGridLayoutManager = new GridLayoutManager(WhatIf.this, 2);
                recyclerView.setLayoutManager(staggeredGridLayoutManager);
            }
        });

        final FloatingActionButton actionAll = (FloatingActionButton) findViewById(R.id.action_all_whatif);
        actionAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Showing everything");
                recyclerView.setAdapter(myCustomAdapter);
                GridLayoutManager staggeredGridLayoutManager = new GridLayoutManager(WhatIf.this, 2);
                recyclerView.setLayoutManager(staggeredGridLayoutManager);
            }
        });

        final FloatingActionsMenu menuMultipleFiltering = (FloatingActionsMenu) findViewById(R.id.multiple_filtering_whatif);
        Log.i(TAG,"getting bundles");

        new WhatIf.AsyncServerAccess().execute(pantryData);
    }

    private class AsyncServerAccess extends AsyncTask<ArrayList<PantryItem>,Void,Void> {
        private String TAG = "Async";
        private AVLoadingIndicatorView avi;
        private final Dialog dialog = new Dialog(WhatIf.this);

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
        protected Void doInBackground(ArrayList<PantryItem>...pantryDataItems) {
            final SearchEngine searchEngine = new SearchEngine();
            MongoCursor<Document> results = searchEngine.findAll(pantryDataItems[0]);
            while (results.hasNext()) {
                Document doc = results.next();
                Log.i(TAG, "RECEIVED: " + doc.toJson());
                recipeNames.add(new Information(R.drawable.recipe_logo, doc.get("title").toString()));
                queryResult.put(doc.get("title").toString(), doc.toJson());
            }
            myCustomAdapter = new MyCustomAdapter(WhatIf.this, recipeNames, queryResult,pantryData);
            searchEngine.disconnectService();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            recyclerView.setAdapter(myCustomAdapter);
            GridLayoutManager staggeredGridLayoutManager = new GridLayoutManager(WhatIf.this, 2);
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


