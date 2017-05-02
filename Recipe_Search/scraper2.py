
# Execute the following line in your terminal, use python 3+:
# pip install git+git://github.com/hhursev/recipe-scraper.git
#
# then run the following script

from recipe_scrapers import scrap_me
import pandas as pd
import time
# give the url as a string, it can be url from any site listed below
'''
Ranges scraped:
            10000 to 110000 - munaf [blocked]
            150000 to 160000

            lower  - upper
            150000 - 152000 - niha
            152000 - 154000 - farhan
            154000 - 156000 - jay
            156000 - 158000 - sid
'''
#Change these
lower = 158000
upper = 160000

cols = ['title','time','ingredients','instructions']
list = pd.DataFrame(columns=cols)
recipeNames = []
for i in range(lower,upper,1):
    try:
        s = scrap_me('http://allrecipes.com/recipe/'+str(i))
        print(i)
        if (s.title() not in recipeNames):
            time.sleep(1)
            recipeNames.append(s.title())
            list.loc[len(list)]= [s.title(), s.total_time(), s.ingredients(), s.instructions()]
            print(s.title())
        if len(list) >= 5000:
            break
    except Exception:
        print("not found recipe: {}".format(i))
    finally:
        list.to_json('recipes4.json',orient='records')


# import pymongo
# import pprint
# import json

# username = ''
# password = ''
# dbname = ''
#
# client = pymongo.MongoClient('mongodb://'+username+':'+password+'@ds147520.mlab.com:47520/'+dbname)
#
# db = client.mealmakerdata
# collection = db.test

# if __name__ == "__main__":
#
#    with open('recipes3.json') as json_data:
#         recipes = json.load(json_data)
#         print(len(recipes))
#         print(recipes[0]['ingredients'])
#
#    recipes_df = pd.read_json('recipes3.json',orient='records')
#    print(recipes_df.head(5))
#    recipes_df['ingredients']


   # for recipe in recipes['recipes']:
   #      collection.insert_one(recipe)

   # try:
   #      pprint.pprint(collection.find_one({'title':'Cajun Chicken Pasta'}))

# finally:
   #      client.close()