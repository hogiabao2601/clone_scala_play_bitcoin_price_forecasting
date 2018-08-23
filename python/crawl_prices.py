import urllib.request
from datetime import datetime
import json
import psycopg2


def connect_db():
    """
        host = "localhost"
        port = 5432
    """
    host = "localhost"
    port = 5432
    return psycopg2.connect(
        host=host,
        port=port,
        user="bao.ho",
        password="bao.ho",
        database="bao.ho"
    )


def call_coin_market_price_api(coin, from_time, to_time):
    from_time = int(from_time.timestamp()) * 1000
    to_time = int(to_time.timestamp()) * 1000
    url = 'https://graphs2.coinmarketcap.com/currencies/{0}/{1}/{2}'.format(coin, from_time, to_time)
    response = urllib.request.urlopen(url).read().decode("utf-8")
    return json.loads(response)


def parse_data(data):
    market_cap_by_available_supply = data['market_cap_by_available_supply']
    price_usd = data['price_usd']
    volume_usd = data['volume_usd']
    insert_query = []
    for supply, price, volume in zip(market_cap_by_available_supply, price_usd, volume_usd):
        insert_query.append(
            """INSERT INTO "public"."currency_infos" VALUES ({}, 1, {}, {}, {}) returning currency_infos;""".format(
                supply[0],
                supply[1],
                price[1],
                volume[1]))
    return insert_query


def import_data(cur):
    data = call_coin_market_price_api('bitcoin', datetime(2007, 1, 1), datetime.now())
    queries = parse_data(data)
    print("Total crawled data row: {}".format(len(queries)))
    timestamp = []
    for query in queries:
        try:
            cur.execute(query)
            timestamp.append(cur.fetchone()[0])
        except:
            print(query)

    print("Total inserted data row: {}".format(len(timestamp)))


conn = connect_db()
cur = conn.cursor()
import_data(cur)
conn.commit()
cur.close()
conn.close()
