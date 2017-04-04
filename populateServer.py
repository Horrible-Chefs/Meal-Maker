'''''''''
SCRIPT TO POPULATE MONGO SERVER WITH JSON RECIPES
@AUTHOR: MUNAF A QAZI
@DATE: 04/01/2017
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

username = ''
password = ''
dbname = ''

client = pymongo.MongoClient('mongodb://'+username+':'+password+'@ds147520.mlab.com:47520/'+dbname)

db = client.mealmakerdata
collection = db.test

if __name__ == "__main__":

   with open('placeholderJSON.json') as json_data:
        recipes = json.load(json_data)

   for recipe in recipes['recipes']:
        collection.insert_one(recipe)

   try:
        pprint.pprint(collection.find_one({'title':'Cajun Chicken Pasta'}))

   finally:
        client.close()