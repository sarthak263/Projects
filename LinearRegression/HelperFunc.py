import csv
import numpy as np
import pandas as pd

np.random.seed(0)

"""Simply gets the data"""
def getCSVData():
    dataSet = []
    with open("x06Simple.csv") as csvfile:
        reader = csv.reader(csvfile)
        for row in reader:
            dataSet.append(row)
    dataSet = dataSet[1:]
    dataSet = [map(int,i) for i in dataSet]
    df = pd.DataFrame(dataSet)

    dataSet = np.asarray(df)

    return dataSet

"""Randomize the data"""
def randomizeData(dataSet):
    """gets the data and randomly shuffles them"""

    np.random.shuffle(dataSet)
    trainingData = np.asarray(dataSet[: , 2:])
    trainingTarget = np.asarray(dataSet[: , 1:2])
    trainingData = np.insert(trainingData,0,1,axis=1)

    return trainingData, trainingTarget,len(dataSet)

"""Finds the theta with the given X and Y"""
def computeWeight(X,Y):
    XT = np.transpose(X)
    if (np.linalg.det(np.dot(XT,X))) == 0:
        return None
    res1 = np.linalg.inv(np.dot(XT,X))
    res2 = np.dot(res1,XT)
    theta = np.dot(res2,Y)

    return theta

"""Computes the Root Error Used for Part 3"""
def RootError(data,theta,target):
    test = np.dot(data, theta)
    target = np.asarray(target)
    SE = (target - test) ** 2
    MSE = np.sum(SE) / len(SE)
    RMSE = np.sqrt(MSE)

    return np.mean(SE),MSE, RMSE


