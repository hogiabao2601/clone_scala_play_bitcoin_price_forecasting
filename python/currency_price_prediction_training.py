import time
import pandas as pd
import numpy as np

from keras.models import Sequential
from keras.layers import Activation, Dense
from keras.layers import LSTM
from sklearn.preprocessing import MinMaxScaler
from sklearn.externals import joblib


def get_market_data(market):
    url = "https://coinmarketcap.com/currencies/" + market + "/historical-data/?start=20130428&end=" + time.strftime(
        "%Y%m%d")
    market_data = pd.read_html(url, flavor='html5lib')[0]
    market_data = market_data.assign(Date=pd.to_datetime(market_data['Date']))
    market_data['Volume'] = (pd.to_numeric(market_data['Volume'], errors='coerce').fillna(0))
    return market_data


def build_model(training_set, num_units=4, activation_function='sigmoid', optimizer='adam',
                loss_function='mean_squared_error', batch_size=5, num_epochs=100):
    x_train = training_set[0:len(training_set) - 1]
    y_train = training_set[1:len(training_set)]
    x_train = np.reshape(x_train, (len(x_train), 1, 1))

    # Initialize the RNN
    model = Sequential()
    # Adding the input layer and the LSTM layer
    lstm = LSTM(units=num_units, activation=activation_function, input_shape=(None, 1))
    model.add(lstm)

    # Adding the output layer
    model.add(Dense(units=1))

    # Compiling the RNN
    model.compile(optimizer=optimizer, loss=loss_function)

    # Using the training set to train the model
    model.fit(x_train, y_train, batch_size=batch_size, epochs=num_epochs)

    return model


def prepare_data(df):
    df = df[['Close**']]
    df_train = df
    training_set = df_train.values
    return training_set


def save_model(model, currency="bitcoin"):
    model_json = model.to_json()
    with open('{}_model.json'.format(currency), "w") as json_file:
        json_file.write(model_json)
    # serialize weights to HDF5
    model.save_weights("{}_model.h5".format(currency))
    print("Saved model to disk")


def save_min_max_scaler(scaler, currency):
    joblib.dump(scaler, "{}_scaler.save".format(currency))


def main():
    min_max_scaler = MinMaxScaler()
    currency = "bitcoin"
    data = get_market_data(currency)
    training_set = prepare_data(data)
    training_set = min_max_scaler.fit_transform(training_set)
    model = build_model(training_set)
    save_min_max_scaler(min_max_scaler, currency)
    save_model(model, currency)


if __name__ == '__main__':
    main()
