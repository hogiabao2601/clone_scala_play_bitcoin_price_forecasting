import numpy as np
import os

from keras.models import model_from_json
from sklearn.externals import joblib

cwd = os.getcwd()

def load_model(currency="bitcoin"):
    # load json and create model
    json_file = open(cwd + '/python/{}_model.json'.format(currency), 'r')
    loaded_model_json = json_file.read()
    json_file.close()
    regressor = model_from_json(loaded_model_json)
    regressor.load_weights(cwd + "/python/{}_model.h5".format(currency))
    print("Loaded model from disk")
    return regressor
    # load weights into new model


def predict_price(last15DaysPrice, currency="bitcoin"):
    model = load_model(currency)
    min_max_scaler = joblib.load(cwd + "/python/{}_scaler.save".format(currency))
    df_test = last15DaysPrice
    price = df_test.split(" ")
    test_set = np.array(price).reshape(len(price), 1)
    inputs = np.reshape(test_set, (len(test_set), 1))
    inputs = min_max_scaler.transform(inputs)
    inputs = np.reshape(inputs, (len(inputs), 1, 1))
    predicted_price = model.predict(inputs)
    predicted_price = min_max_scaler.inverse_transform(predicted_price)
    return predicted_price.flatten()
