package com.mealmaker.munaf.mealmaker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.MyViewHolder> {

    public static final String TAG = "MyCustomAdapter";
    private Context context;

    private ArrayList<Information> data;

    private LayoutInflater inflater;

    private int previousPosition = 0;

    private HashMap<String, String> queryResult;

    public MyCustomAdapter(Context context, ArrayList<Information> data, HashMap<String, String> queryResult) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
        this.queryResult = queryResult;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = inflater.inflate(R.layout.list_item_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {
        myViewHolder.textview.setText(data.get(position).title);
        myViewHolder.imageView.setImageResource(data.get(position).imageId);
        if(position > previousPosition){ // We are scrolling DOWN
            AnimationUtil.animate(myViewHolder, true);
        }else{ // We are scrolling UP
            AnimationUtil.animate(myViewHolder, false);
        }
        previousPosition = position;
        final int currentPosition = position;
        final Information infoData = data.get(position);
        myViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "OnClick Called at position " + position, Toast.LENGTH_SHORT).show();
                TextView tv2 = myViewHolder.textview; //(TextView) v;
                Intent intent_recipe = new Intent(context, RecipeList.class);
                intent_recipe.putExtra("recipe_json", queryResult.get(tv2.getText().toString()));
                context.startActivity(intent_recipe);
            }
        });

//        myViewHolder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Toast.makeText(context, "OnLongClick Called at position " + position, Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textview;
        ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textview = (TextView) itemView.findViewById(R.id.txv_row);
            imageView = (ImageView) itemView.findViewById(R.id.img_row);
        }
    }

    // This removes the data from our Dataset and Updates the Recycler View.
    private void removeItem(Information infoData) {
        int currPosition = data.indexOf(infoData);
        data.remove(currPosition);
        notifyItemRemoved(currPosition);
    }

    // This method adds(duplicates) a Object (item ) to our Data set as well as Recycler View.
    private void addItem(int position, Information infoData) {
        data.add(position, infoData);
        notifyItemInserted(position);
    }
}