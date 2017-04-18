import json
ing_1 = raw_input("Enter 1st ingredient :")
ing_2 = raw_input("Enter 2nd ingredient :")
with open('recipes3.json', 'r') as pls:
	data = json.loads(pls.read())
	for i in data:
		#if i['ingredients'][0].find("cheese") >0:
		for j in i['ingredients']:
				if (ing_1 in j and ing_2 in j):
					print(i['title'])
					print(i['instructions'])
					break
		#else:
			#print("nahi milla")
			

pls.close()		



