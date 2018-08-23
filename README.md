# Bitcoin Price Forecasting
-

This project is using Play framework (Scala) to create REST APIs and Python to create training model and predict price.

 - Api module: This module includes REST api:
   + get prices **/api/v1/prices/:currency/:from/:to/**
   - currency: name of currency, ex: bitcoin
   - from: start time want to query base on epoch time
   - to: end time want to query base on epoch time
    ex: http://localhost:9000/api/v1/prices/bitcoin/1420070640000/1421311441000/
    (This endpoint hasn't finished yet, I need to get id of currency from name)
    
    
   + price forecasting  **/api/v1/price_forecasting/:currency**
    - currency: name of currency, ex: bitcoin
    ex: http://localhost:9000/api/v1/price_forecasting/btc
 - Docker: This module includes docker-compose file to create environment
    cd ./Docker/Postgres/ && docker-compose up -d
    (I use postgres to store currency price)
    cd ./Docker/Redis/ && docker-compose up -d
    (I use redis to cache data, but I just a little bit)
 - Model: This module store all data model of the project, e.g. **Currency**, **CurrencyInfo**
 - scheduler: This module is used create crawls data job
 - spark: This module is used create training model offline for price forecasting
 - util: This module includes all utilities for other modules



### Prerequisites

I am using Mac OSX, so you need to the jep lib depend on you OS 
``https://github.com/ninia/jep/wiki/Getting-Started``
``https://github.com/sushant-hiray/scala-python-example``
```
pip install jep
pip3 show jep | grep Location
cp /usr/local/lib/python3.4/dist-packages/jep/{jep-3.6.3.jar,libjep.so} lib/
pip install pandas
pip install numpy
pip install tensorflow
pip install keras
pip install sklearn
pip install html5lib
pip install bs4
pip install psycopg2
pip install psycopg2-binary

cd ./Docker/Postgres/ && docker-compose up -d
```

You need to import data model from sql files from path

```
./conf/evolutions/currencies.sql
./conf/evolutions/currency_infos.sql
```

### Crawl data from coin market cap and import to Postgres

```
python3 ./python/crawl_prices.py
```


## Create model for price prediction

This model will be used to predict the bitcoin price
```
python3 ./python/currency_price_prediction_training.py
```

### Start to run api service

```
sbt run
```

### API Example

Use this endpoint to get the predicted prices for next 15 days

```
http://localhost:9000/api/v1/price_forecasting/bitcoin#
```
```
[
[
"2018-08-24",
"6378.7275"
],
[
"2018-08-25",
"6500.501"
],
[
"2018-08-26",
"6163.4907"
],
[
"2018-08-27",
"6371.8506"
],
[
"2018-08-28",
"6472.7983"
],
[
"2018-08-29",
"6040.4487"
],
[
"2018-08-30",
"6458.613 "
],
[
"2018-08-31",
"6425.9673"
],
[
"2018-09-01",
"6509.2793"
],
[
"2018-09-02",
"6518.291"
],
[
"2018-09-03",
"6404.209"
],
[
"2018-09-04",
"6503.043"
],
[
"2018-09-05",
"6503.173"
],
[
"2018-09-06",
"6683.777 "
],
[
"2018-09-07",
"6468.495"
]
]
```


Use this endpoint to get the prices from date to date (I am using Epoch time, so need to convert date to Long)

```
http://localhost:9000/api/v1/prices/bitcoin/1421188580000/1421311441000/
```
```
[
{
timestamp: 1421225041000,
currency_id: 1,
market_cap_by_available_supply: 2529312564,
price_usd: 184.322006,
volume: 74590000
},
{
timestamp: 1421229541000,
currency_id: 1,
market_cap_by_available_supply: 2851633026,
price_usd: 207.809006,
volume: 93162800
},
{
timestamp: 1421311441000,
currency_id: 1,
market_cap_by_available_supply: 2757337125,
price_usd: 200.889008,
volume: 98617000
}
]
```

Use this endpoint to get the average prices every day from date to date (I am using Epoch time, so need to convert date to Long)

```$xslt
http://localhost:9000/api/v1/average_prices/bitcoin/1420070640000/1421311441000/
```

```$xslt
[
[
"2015-01-01",
319.14149499999996
],
[
"2015-01-02",
313.639008
],
[
"2015-01-03",
314.8580015
],
[
"2015-01-04",
281.246002
],
[
"2015-01-05",
265.9035035
],
[
"2015-01-06",
274.3894955
],
[
"2015-01-07",
284.65750149999997
],
[
"2015-01-08",
293.94450400000005
],
[
"2015-01-09",
282.4449925
],
[
"2015-01-10",
279.9404905
],
[
"2015-01-11",
274.704987
],
[
"2015-01-12",
268.875
],
[
"2015-01-13",
244.565994
],
[
"2015-01-14",
196.065506
],
[
"2015-01-15",
200.889008
]
]
```
