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
         +'{"data":%s}\n' % Predict(readArgs))
        clientsocket.shutdown(SHUT_WR)
        clientsocket.close()

    serversocket.close()

createServer()

def readArgs(socket):
    line = ""
    while True:
        part = socket.recv(1)
        if part != "\n":
            line+=part
        elif part == "\n":
            break
    args = line.split(',')
    return (args[0], args[1], args[2])

def formParams(args):
    features = pd.DataFrame().append(pd.Series(np.zeros(22)), ignore_index=True)
    features = set_color(features, args[0])
    features = set_month(features, args[1])
    features = set_department(features, args[2])
    return features

def set_color(features, color):
    features.at[0,color] = 1
    return features

def set_month(features, month):
    features.at[0,month] = 1
    return features

def set_department(features, dep):
    features.at[0,dep] = 1
    return features

def Predict(args):
    model = joblib.load('model.pkl')
    features = formParams(args)
    preds = model.predict(features)
    return preds
