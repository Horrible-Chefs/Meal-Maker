package com.mealmaker.munaf.mealmaker;

import android.util.Log;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static com.mongodb.client.model.Filters.all;
import static com.mongodb.client.model.Filters.in;

public class SearchEngine {

    private static final String username = "developer1";
    private static final String password = "mealmaker1";
    private static final String dbName = "mealmakerdata";
    private static final String TAG = "SearchEngine";

    static MongoClient mongoClient;
    static MongoDatabase database;
    static MongoCollection<Document> collection;
    static MongoClientOptions.Builder options = MongoClientOptions.builder().socketKeepAlive(true);
    static final MongoClientURI uri = new MongoClientURI(
            "mongodb://"+username+":"+password+"@ds147520.mlab.com:47520/"+dbName, options.socketKeepAlive(true));

    public SearchEngine(){
        mongoClient = new MongoClient(uri);
        database = mongoClient.getDatabase(dbName);
        collection = database.getCollection("cookbook");
    }

    public Iterator<Document> findExact(ArrayList<PantryItem> pantry){
        MongoCursor<Document> allResults = findAll(pantry);
        ArrayList<String> pantryItems = convertPListToTitleList(pantry);
        ArrayList<Document> exactResults = new ArrayList<>();
        while (allResults.hasNext()){
            Document doc = allResults.next();
            Integer found = 0;
            ArrayList<String> searchTerms = (ArrayList<String>) doc.get("search_terms");
            for (String term : searchTerms){
                if(pantryItems.contains(term)){
                    found++;
                }
            }
            if(found==searchTerms.size()){
                Log.i(TAG,"FOUND: "+doc.toJson());
                exactResults.add(doc);
            }
        }
        return exactResults.iterator();
    }

    public MongoCursor<Document> findAll(ArrayList<PantryItem> pantry){
        Log.i(TAG,convertList(pantry));
        return collection.find(in("search_terms", convertPListToTitleList(pantry))).iterator();
    }

    public MongoCursor<Document> findPublisher(ArrayList<PantryItem> pantry){
        Document findQuery = new Document("title", convertList(pantry));
        return collection.find(findQuery).iterator();
    }

    public String convertList(ArrayList<PantryItem> pantry){
        String s = "";
        for (PantryItem p : pantry){
            s = s+p.getName().trim().toLowerCase()+" ";
        }
        Log.i(TAG,s);
        return s.trim();
    }

    public ArrayList<String> convertPListToTitleList(ArrayList<PantryItem> pantry){
        ArrayList<String> s = new ArrayList<>();
        for (PantryItem p : pantry){
            s.add(p.getName().trim().toLowerCase());
        }
        Log.i(TAG,s.toString());
        return s;
    }

    public void disconnectService(){
        mongoClient.close();
    }
}
