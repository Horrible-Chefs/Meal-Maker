'''''''''
SCRIPT TO POPULATE MONGO SERVER WITH JSON RECIPES
@AUTHOR: MUNAF A QAZI
@DATE: 04/21/2017
'''''''''

'''''''''
NOTES:
- Please make sure MongoServer is running.
- Please make sure all required packages have been pip installed.
- Please make sure you have the correct username, password and dbname.
'''''''''

import pymongo
import pprint
import json

username = 'developer1'
password = 'mealmaker1'
dbname = 'mealmakerdata'

client = pymongo.MongoClient('mongodb://'+username+':'+password+'@ds147520.mlab.com:47520/'+dbname)

db = client.mealmakerdata
collection = db.cookbook

if __name__ == "__main__":

   with open('cookbook_new.json') as json_data:
        recipes = json.load(json_data)

   for recipe in recipes:
        collection.insert_one(recipe)

   try:
        pprint.pprint(collection.find_one({'title':'Chicken Fettuccine with Roasted Red Pepper Sauce'}))

   finally:
        client.close()