package com.mealmaker.munaf.servertester;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class MainActivity extends AppCompatActivity {

    private static final String username = "";
    private static final String password = "";
    private static final String dbName = "";

    static MongoClient mongoClient;
    static MongoDatabase database;
    static MongoCollection<Document> collection;
    static MongoClientOptions.Builder options = MongoClientOptions.builder().socketKeepAlive(true);
    static final MongoClientURI uri = new MongoClientURI(
            "mongodb://"+username+":"+password+"@ds147520.mlab.com:47520/"+dbName, options.socketKeepAlive(true));



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final Button button_connect = (Button) findViewById(R.id.btn_connect);
        final Button button_disconnect = (Button) findViewById(R.id.btn_disconnect);
        final Button button_query = (Button) findViewById(R.id.btn_query);
        final TextView tv = (TextView) findViewById(R.id.tv_results);

        button_disconnect.setEnabled(false);
        button_query.setEnabled(false);

        button_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_connect.setEnabled(false);
                button_disconnect.setEnabled(true);
                button_query.setEnabled(true);
                Clicked();
                tv.append("connected!\n");
            }
        });


        button_disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_disconnect.setEnabled(false);
                button_connect.setEnabled(true);
                button_query.setEnabled(false);
                mongoClient.close();
                tv.append("disconnected.\n");
            }
        });


        button_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Trying: ","Going in!");
                Document findQuery = new Document("title", "Cajun Chicken Pasta");
                MongoCursor<Document> cursor = collection.find(findQuery).iterator();
                try {
                    while (cursor.hasNext()) {
                        Document doc = cursor.next();
                        Log.i("RECEIVED: ",doc.toJson());
                        String s = "Querying the server for Cajun Chicken Pasta...\n" + doc.get("title") + ", can be found at: " + doc.get("source_url") +
                                ".\nIf you're seeing this the demo is working. Feels great right?\n";
                        tv.append(s);
                    }
                } finally {
                    cursor.close();
                }
            }
        });
    }

    public static void Clicked(){
        mongoClient = new MongoClient(uri);
        database = mongoClient.getDatabase(dbName);
        collection = database.getCollection("test");
    }
}
