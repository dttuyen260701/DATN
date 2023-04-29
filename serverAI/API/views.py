import json
from django.shortcuts import render
from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
import pickle
import joblib
import pandas as pd

def readData(name):
    with open(name, 'r') as f:
        links = []
        for line in f.readlines():
            links.append(float(line))
    return links

GLOBAL_BDSModel = pickle.load(open('API/models/BDSModel.sav', 'rb'))
GLOBAL_Cluster =  joblib.load('API/models/KMeansCluster.save')
GLOBAL_Scaler =  joblib.load('API/models/scaler.save')
GLOBAL_MaxPrice = readData('API/models/MaxValuePrice.txt')

def scaleMinMax(data):
    global GLOBAL_Scaler
    data['PricePerMet'] = 0
    return pd.DataFrame(GLOBAL_Scaler.fit_transform(data),columns=data.columns).drop(columns=["PricePerMet"])

def getCluster(point):
    global GLOBAL_Cluster
    return GLOBAL_Cluster.predict([point])

def decodeClusters(data):
    data_temp = data.copy()
    for i in range(0,20):
        colums = 'Clusters_' + str(i)
        data_temp[colums] = (data_temp['Clusters'] == i)*1
    return data_temp.drop(columns=["Clusters"])

def getPriceTraining(x):
    global GLOBAL_BDSModel
    result = GLOBAL_BDSModel.predict(x)
    return round(float(result[0]) * GLOBAL_MaxPrice[0], 3)

# Create your views here.
@csrf_exempt
def getPrice(request):
    method = request.method
    if method == "POST":
        try:
            body_unicode = request.body.decode('utf-8')
            data = json.loads(body_unicode)
            data = pd.DataFrame.from_dict([data])
            data = scaleMinMax(data)
            data['Clusters'] = -1
            clusters = getCluster(data.iloc[0])
            data.iloc[0]['Clusters'] = clusters[0]
            data = decodeClusters(data)
            return JsonResponse({"Result": getPriceTraining(data), "Cluster": str(clusters[0])})
        except Exception as e:
            return JsonResponse({"Error": e})
    else:
        return JsonResponse({"invalid":"Not good data"})
    
