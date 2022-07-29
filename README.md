# StockPrice
> The purpose of this thesis project: to write applications that allow the user to view stock market stocks. Project Requirements: 
+ The use of a list of
+ Multiple screens
+ Settings screen
+ Using the API
+ Using a data base

# Used stack:
+ MVVM
+ Navigation Component
+ Coroutines 
+ LiveData
+ DI (Koin)
+ Room Database
+ Retrofit2
+ View Binding
+ Material Design

# Screen
## Splash Activity 
![image](https://user-images.githubusercontent.com/95710980/181835638-58f8bf6e-2ae1-4c3b-b4d8-68b8fce2e324.png)
> The loading screen required for Visual smoothing application startup.

## List stock Fragment
![image](https://user-images.githubusercontent.com/95710980/181835861-79701e81-9c19-4480-8a9b-803abd9b71d8.png)
> Home screen displaying the stock list, screen names, search icon, and menu.

## Details stock Fragment 
![image](https://user-images.githubusercontent.com/95710980/181836837-f117174d-9f73-417c-9dc9-60cc6db25f6e.png)
> The screen displays the symbol code of the stock, the name of the company, the exchange on which the stock is listed, the currency in which the stock is traded, the date of the last trade and the closing price of the day.

## Settings Activity and Fragment 
![image](https://user-images.githubusercontent.com/95710980/181837021-17759ef2-2350-49ca-a9ec-8750da6d0c25.png) ![image](https://user-images.githubusercontent.com/95710980/181837054-11178cb2-5f38-4fa7-91c1-cbb1d002322b.png)
> On the settings screen, there is a change of theme, a change of language. There is also a link to the GItHub repository of the application.

# Difficulties encountered
> The DataSource class received zero initial load parameter. There was only one query, and no data came to the user's screen.
> Using default DataSource representation, pagination did not stop the request when filling the screen with items, and loaded the data until they were all loaded. In this case, if the user went to another fragment during loading, then when returning to the initial screen, the list was not displayed or was displayed after some time.
> Selecting APIs 

# Using API 
> https://twelvedata.com/
