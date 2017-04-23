import json
import re
initial_words = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '7/8',
			'1/2', '1/3', '1/4', '1/8', '3/4', '2/3', 'ADVERTISEMENT',
			'cup', 'cups', 'teaspoon', 'teaspoons', 'tablespoons',
			'tablespoon', 'pint', 'pints', 'pound', 'pounds', 'ounce',
			'ounces', 'halved', 'full', 'sliced', 'diced', 'half',
			'quartered', 'chopped', 'melted', 'pinch','ounce','package',
			'frozen','thawed']

import csv
stop_words=[]
with open('stopwords.csv', 'r') as csvfile:
	stop_words_list = csv.reader(csvfile, delimiter='\n')
	for row in stop_words_list:
		stop_words.append(row[0])
	csvfile.close()
for s_word in initial_words:
	stop_words.append(s_word)

with open('recipes3.json', 'r') as pls:
	data = json.loads(pls.read())
	for i in data:
		cleaned_ing = []
		for key,value in i.items():
			if key == 'ingredients':
				for ingredient in value:
					cleaned_ing.append(ingredient.replace('ADVERTISEMENT','').strip())
		i['ingredients'] = cleaned_ing

	cookbook = []
	# allWords = []
	for i in data:
		recipe = {}
		for key,value in i.items():
			recipe[key]=value
			if key == 'ingredients':
				query_keys = []
				for ingredient in value:
					for word in ingredient.split():
						w = re.sub('[^a-z]+','',word.lower().strip())
						if w not in stop_words and len(w)>0:
							query_keys.append(w)
							# allWords.append(re.sub('[^a-z]+','',word.lower().strip()))
				recipe['search_terms'] = query_keys
				recipe['diet_rest'] = 'vegetarian'
				for v in recipe['search_terms']:
					if v in ['meat', 'chicken', 'lamb', 'veal', 'ham', 'pork','shrimp']:
						recipe['diet_rest'] = 'non_vegetarian'
		cookbook.append(recipe)
pls.close()


# with open('cookbook3.json', 'w') as fp:
#     json.dump(cookbook, fp,sort_keys=True, indent=4)

ingredient_list=[]
with open('items.csv', 'r') as itemfile:
	ingredients = csv.reader(itemfile, delimiter='\n')
	for row in ingredients:
		ingredient_list.append("{}".format(row[0]))
	itemfile.close()
print(ingredient_list)



		# FOR COUNTING DIFFERENT WORDS (allWords) IN THE INGREDIENT LIST
	# AND WRITING THEM TO CSV
	# FOR FURTHER PROCESSING
	# from collections import Counter
	# counts = Counter(allWords)
	#
	# import csv
	# f = open('ingredients.csv','w')
	# # w = csv.DictWriter(f,counts.keys())
	# w = csv.writer(f,dialect='excel')
	# w.writerows(counts.items())
	# f.close()
	#
	# print(counts)
	# print(allWords)


	# FOR CREATING A DICTIONARY IN FORMAT
	# [<TITLE>:{
	# 	TIME:<>
	# 	INGREDIENTS:<>
	# 	DIET_REST:<>
	# 	SEARCH_TERMS:<>
	# 	INSTRUCTIONS:<>
	# 	},
	# ...
	# TITLE:{<>}
	# ]
    #
#dict_cookbook = {}
	# for i in data:
	# 	for key,value in i.items():
	# 		if key == 'title':
	# 			dict_cookbook[value] = {}
    #
	# for key, value in dict_cookbook.items():
	# 	for i in data:
	# 		if key == i['title']:
	# 			for eachItemK, eachItemV in i.items():
	# 				if eachItemK not in ['title']:
	# 					dict_cookbook[key][eachItemK]=eachItemV
	# 					if eachItemK == 'ingredients':
	# 						query_keys = []
	# 						for ingredient in eachItemV:
	# 							for word in ingredient.split():
	# 								if word not in stop_words:
	# 									query_keys.append(word)
	# 						dict_cookbook[key]['search_terms'] = query_keys
	# 						dict_cookbook[key]['diet_rest'] = 'vegetarian'
	# 						for value in dict_cookbook[key]['search_terms']:
	# 							if value in ['meat', 'chicken', 'lamb', 'veal', 'ham','pork']:
	# 								dict_cookbook[key]['diet_rest'] = 'non_vegetarian'
	# 			break