from socket import *
import sys
import numpy as np
import pandas as pd
from sklearn.externals import joblib


def createServer():
    serversocket = socket(AF_INET, SOCK_STREAM)
    serversocket.bind(('localhost',9000))
    serversocket.listen(5)
    while(1):
        (clientsocket, address) = serversocket.accept()
        clientsocket.send("HTTP/1.1 200 OK\n"
         +"Content-Type: text/json\n"
         +"\n" # Important!
         +'{"data":%s}\n' % Predict())
        clientsocket.shutdown(SHUT_WR)
        clientsocket.close()

    serversocket.close()

createServer()

def formParams():
    features = pd.DataFrame().append(pd.Series(np.zeros(22)), ignore_index=True)
    features = set_color(features)
    features = set_month(features)
    features = set_department(features)
    return features

def set_color(features):
    color = sys.argv[1]
    features.at[0,color] = 1
    return features

def set_month(features):
    month = sys.argv[2]
    features.at[0,month] = 1
    return features

def set_department(features):
    department = sys.argv[3]
    features.at[0,department] = 1
    return features

def Predict():
    model = joblib.load('model.pkl')
    features = formParams()
    preds = model.predict(features)
    return preds
