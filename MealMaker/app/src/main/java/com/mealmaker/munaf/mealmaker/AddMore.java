package com.mealmaker.munaf.mealmaker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class AddMore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_more);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int w = dm.widthPixels;
        int h = dm.heightPixels;
        getWindow().setLayout((int) (w*.8), (int) (h*0.6));


        final DBHandler db = new DBHandler(this);
        Button Enter_your_Items = (Button) findViewById(R.id.btn);
        final AutoCompleteTextView Item = (AutoCompleteTextView) findViewById(R.id.editText1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, Itemlist);
        Item.setAdapter(adapter);
        Item.setThreshold(1);
        final InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        Enter_your_Items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

                String Item_S = Item.getText().toString().trim();
                Boolean exists = db.itemExists(new PantryItem(Item_S));
                if(!exists && !Item_S.equals("")) {
                    db.addItem(new PantryItem(Item_S));
                    Snackbar.make(findViewById(R.id.snkbr_added), Item_S + " added to pantry!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Item.setText("");
                    setResult(200);
                }else{
                    Snackbar.make(findViewById(R.id.snkbr_added), "Error: Invalid Input or "+Item_S + " already exists in Pantry!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }
    private static String[] Itemlist = new String[]{"pizza", "peppermint", "roll", "chili", "fudge", "onions", "grapes", "seedless", "liqueur", "worms", "beans", "green", "milk", "masa", "thyme", "ricotta", "citron", "glaze", "weed", "brandy", "prunes", "clove", "vitamin", "rice", "marrons", "coconut", "licorice", "toasts", "mint", "cake", "soy", "chai", "mangos", "strawberry", "cornmeal", "pineapple", "eggnog", "nutmeg", "bran", "puree", "tartar", "yolk", "pears", "sandies", "applesauce", "rosemary", "caramel", "beef", "sponge", "chocolate", "candies", "bouillon", "lemonlime", "colada", "wholekernel", "parsley", "oat", "cranberry", "lowfat", "oyster", "spice", "strawberries", "banana", "oil", "margarine", "nougat", "fennel", "ladyfingers", "raisins", "soda", "sourdough", "apricots", "yogurt", "anisette", "candy", "soup", "amaranth", "flaxseed", "fruit", "jello", "whey", "pecan", "chives", "oats", "wine", "buns", "chips", "caraway", "blackberry", "pudding", "macadamia", "cloves", "vegetable", "walnut", "candied", "dates", "graham", "kidney", "maple", "cracker", "lecithin", "apples", "potatoes", "mushrooms", "ovalette", "oranges", "jam", "tomato", "rosewater", "gingersnap", "mustard", "bean", "rhubarb", "cumin", "wafers", "cocoa", "crisp", "lard", "buttercream", "onion", "romano", "grain", "parmigiano", "gelatin", "cheddar", "cassava", "icing", "salted", "raspberries", "colaflavored", "granola", "orange", "tootsie", "wafer", "butternuts", "caramelpeanut", "dough", "broth", "cayenne", "biscuit", "vinegar", "sugar", "sprinkles", "cherries", "nugget", "provolone", "mints", "corn", "kernel", "cinnamon", "hazelnuts", "poppy", "tangerine", "zest", "chicken", "lime", "beverage", "zucchinis", "marnier", "sauce", "kalonji", "barley", "gumdrops", "arrowroot", "rolls", "olives", "squares", "cheese", "tea", "carrots", "pepperjack", "wheat", "rye", "marshmallow", "mocha", "olive", "canola", "rum", "vanilla", "grapenuts", "bread", "peanuts", "cream", "allspice", "caramels", "gum", "crackers", "sausage", "broccoli", "walnuts", "savory", "potato", "eggs", "galliano", "cookie", "sesame", "oregano", "butter", "buttermilk", "heavy", "plum", "sherbet", "shrimp", "anise", "cardamom", "prosciutto", "tomatoes", "reggiano", "spinach", "syrup", "molasses", "carrot", "seasoning", "cider", "citrus", "celery", "starch", "flax", "ground", "blueberries", "creme", "peppers", "macaroon", "yeast", "egg", "seeds", "nectar", "beer", "currants", "pumpkin", "apple", "chestnut", "vodka", "sweetener", "germ", "jalapeno", "chestnuts", "shortening", "toffee", "jelly", "cocktail", "pickle", "popcorn", "pecans", "mascarpone", "cranberries", "bananas", "zucchini", "basil", "ladyfinger", "yolks", "mango", "peanut", "mayonnaise", "paprika", "garlic", "lentils", "virgin", "honey", "cookies", "pie", "cakes", "ghee", "ripe", "peaches", "pork", "pastry", "pistachio", "amaretto", "mozzarella", "matzo", "nuts", "ginger", "apricot", "crumbs", "almonds", "cherry", "maraschino", "cornstarch", "coffee", "parmesan", "lemon", "tapioca", "lemonade", "butternut", "frosting", "cornbread", "raspberry", "mincemeat", "shallots", "marshmallows", "grape", "halfandhalf", "asiago", "pepper", "cheesecake", "cereal", "persimmon", "nut", "paste", "juice", "salt", "flour", "butterscotch", "raisin", "muesli", "berries", "pimento", "almond", "pepperoni"};


}
