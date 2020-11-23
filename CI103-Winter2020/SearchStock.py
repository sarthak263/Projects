import requests
from datetime import date
import time
import json

def User_input():
    print("Enter the stock name")
    stock_name = input()
    return stock_name

def callAPI():
    today = date.today()
    d1 = today.strftime("%Y-%m-%d")
    print(d1)
    symbol = User_input()
    if symbol == 'exit':
        return 1
    key = "4U2TIXIE2ETZYTRE"
    name_url = "https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords="+symbol+"&apikey="+key
    get_name_data = requests.get(name_url)
    name_data = get_name_data.json()

    for data in name_data['bestMatches']:
        print('StockSymbol: ' + data['1. symbol'])
        print('StockName: ' + data['2. name'])
        '''
        price_url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + data['1. symbol'] + "&apikey=" + key
        get_price_data = requests.get(price_url)
        price_data = get_price_data.json()
        if len(price_data) == 0:
            print("SYMBOL PRICE NOT FOUND")
            continue
        #print(get_price_data.text)
        print("Price of" + data['1. symbol'] + " is " + price_data["Time Series (Daily)"]['2019-11-21']['1. open'])
        '''
        print("\n")

    return 0

def main():
    while(True):
        if callAPI():
            break

main()

