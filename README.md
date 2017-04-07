# Meal Maker
Turning Ideas Into Reality

## Problem Statement
* What customers can cook with the ingredients they already have?
* What could’ve they cooked with just one or two extra ingredients?

## Customers
* Mainly Students and professionals who live alone, use cookbooks and looking for a quick fix

## Horrible Chefs:
* Munaf Arshad Qazi
* Jayesh Kishor Mhatre
* Siddhartha Arora
* Niha Jasmin Habul
* Farhan Hasan

## Tech-Stuff & Citations
* The Recipe Knowledgebase has been deployed on an [mLab](https://mlab.com/) MongoDB instance. Credentials can be acquired from maqzi incase someone needs to access it.
* The Recipe Knowledgebase was populated using 1000 recipes scraped from [allrecipes.com](http://allrecipes.com). The scraper is a copyright of Hristo Harsev's work ([hhursev](https://github.com/hhursev/recipe-scraper)) and is under the [MIT License](https://github.com/hhursev/recipe-scraper/blob/master/LICENSE).
* Java-Mongo-Driver-3.4 (for android by matfur92) was used to connect to the server. It can be forked from [matfur92's github](https://github.com/matfur92/mongo-java-driver).
* [ServerTester 1.0](https://github.com/Horrible-Chefs/Meal-Maker/tree/master/ServerTester) can be downloaded, studied and run directly on Android Studio, I've uploaded the complete source code. You'll just need the username, password and database name and change query parameters for the 'query' button's on click action in [MainActivity](https://github.com/Horrible-Chefs/Meal-Maker/blob/master/ServerTester/app/src/main/java/com/mealmaker/munaf/servertester/MainActivity.java). 

## Testing & Troubleshooting
* Make sure you have entered the username, password and dbname in [SearchEngine.Class].
* Make sure you have something in the SQLite Database. You can use the commented stubs in [MyPantry.Class] to add/remove a test record until [AddMore.Class] is implemented.
