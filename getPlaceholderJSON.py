import json
import urllib.request

#api_key = "250f280d106ed8bda19362eeccc66da6"
searchLink = "http://food2fork.com/api/search?key=250f280d106ed8bda19362eeccc66da6&q="

searchTerms = ["chicken", "pasta", "beef", "chocolate", "ice%20cream", "fruit", "cake", "vegetables"]

def getJSONData(term):
    url = searchLink + term
    response = urllib.request.urlopen(url).read()
    jsonObject = str(response, "utf-8")

    return jsonObject


def createJSONFile():
    completeResults = ""
    for term in searchTerms:
        completeResults = completeResults + getJSONData(term)

    with open("placeholderJSON.json", "a") as outfile:
        outfile.write(completeResults)

createJSONFile()




