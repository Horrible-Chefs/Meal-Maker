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

public class SearchEngine {

    private static final String username = "";
    private static final String password = "";
    private static final String dbName = "";
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
        collection = database.getCollection("test");
    }

    public MongoCursor<Document> findExact(ArrayList<PantryItem> pantry){
        //need to make the searching more elegant. This is just a place holder function atm, need to know the
        //exact structure of ingredients on the server to implement thiss properly.
        //CHANGE TITLE TO INGREDIENTS OR WHATEVER THE KEY IS ON THE SERVER
        Document findQuery = new Document("title", convertList(pantry));
        return collection.find(findQuery).iterator();
    }

    public String convertList(ArrayList<PantryItem> pantry){
        String s = "";
        for (PantryItem p : pantry){
            s = s+p.getName()+" ";
        }
        Log.i(TAG,s);
        return s.trim();
    }
}
