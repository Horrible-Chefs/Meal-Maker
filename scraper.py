from recipe_scrapers import scrap_me
import pandas as pd

# give the url as a string, it can be url from any site listed below
# numbers, tablespoons, teaspoons, cups

# cols = ['title','time','ingredients','instructions']
# list = pd.DataFrame(columns=cols)
#
# for i in range(1000,11000,1):
#     try:
#         s = scrap_me('http://allrecipes.com/recipe/'+str(i))
#         list.loc[len(list)]= [s.title(), s.total_time(), s.ingredients(), s.instructions()]
#         if len(list) >= 1000:
#             break
#     except Exception:
#         print("not found recipe: {}".format(i))
#
# list.to_json('recipes3.json',orient='records')


import pymongo
import pprint
import json

# username = ''
# password = ''
# dbname = ''
#
# client = pymongo.MongoClient('mongodb://'+username+':'+password+'@ds147520.mlab.com:47520/'+dbname)
#
# db = client.mealmakerdata
# collection = db.test

if __name__ == "__main__":

   with open('recipes3.json') as json_data:
        recipes = json.load(json_data)
        print(len(recipes))
        print(recipes[0]['ingredients'])

   recipes_df = pd.read_json('recipes3.json',orient='records')
   print(recipes_df.head(5))
   recipes_df['ingredients']


   # for recipe in recipes['recipes']:
   #      collection.insert_one(recipe)

   # try:
   #      pprint.pprint(collection.find_one({'title':'Cajun Chicken Pasta'}))

   # finally:
   #      client.close()