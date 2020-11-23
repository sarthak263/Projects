import cv2
import csv
import math
import random
import glob
import os

import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from sklearn.decomposition import PCA
from mpl_toolkits.mplot3d import Axes3D

prop_cycle = plt.rcParams['axes.prop_cycle']
colors = prop_cycle.by_key()['color']
SIZE = (640,480)

def getCSVData():
    dataSet = []
    with open("diabetes.csv") as csvfile:
        reader = csv.reader(csvfile)
        for row in reader:
            dataSet.append(row)
    dataSet = [list(map(float,i)) for i in dataSet]
    'dataset[0] = [-1.0, 6.0, 148.0, 72.0, 35.0, 0.0, 33.6, 0.627, 50.0]'
    return dataSet

def get_dataPoints(f):
    dataSet = getCSVData()
    df = pd.DataFrame(dataSet)
    df = np.asarray(df)
    df = df[:, 1:f+1]
    df = np.asarray(df)
    standardMatrix = get_standardMatrix(df)
    if f > 3:

        feature_3 = PCA(3)
        dataPoints = feature_3.fit_transform(standardMatrix)
        dataPoints = np.asarray(dataPoints).transpose()
        return dataPoints
    else:
        df =standardMatrix.transpose()
        return df

def plotData(x,y,z, k,name,f,refVec):
    name = "image_" + str(k) + "_" + str(name)
    fig = plt.figure()
    plt.clf()
    ax = fig.add_subplot(111, projection='3d')

    if f >= 3:
        mx = max(len(x[0]), len(y[0]), len(z[0]))
        for i in range(k):
            ax.scatter(x[i], y[i], z[i], c=colors[i], marker='x')
            ax.scatter(refVec[i][0],refVec[i][1],refVec[i][2],c=colors[i],marker='o')
    else:
        mx = max(len(x[0]), len(y[0]))
        for i in range(k - 1):
            mx1 = max(len(x[i + 1]), len(y[i + 1]))
            if mx < mx1:
                mx = mx1

        for i in range(k):
            ax.scatter(x[i], y[i], c=colors[i], marker='x')
            ax.scatter(refVec[i][0], refVec[i][1], c=colors[i], marker='o')
    plt.title("Purity="+str(round((mx/768),3)))
    plt.savefig(name)
    plt.close('all')
    CreateVideo(k,f)

def CreateVideo(k,f):
    name = "K_" + str(k) + "_F" + str(f) + ".avi"
    listPic = glob.glob('*.png')
    video = cv2.VideoWriter(name, cv2.VideoWriter_fourcc(*'DIVX'), 1, SIZE)

    for image in listPic:
        video.write(cv2.imread(image))

    cv2.destroyAllWindows()
    video.release()

"""Method to pick the first K points randomly and returns those points"""
def pickKPoints(k, dataLen,data):
    list_randomPoints = []
    random.seed(0)
    centroids = []
    for i in range(0,k):
        rand = random.randint(0,dataLen)
        list_randomPoints.append(rand)

    for i in list_randomPoints:
        centroids.append(get_points(data,i))

    return centroids

"""Helper method to return a Point in that Index"""
def get_points(data,index):
    'returns [x,y,z] point'
    if len(data) == 3:
        return [data[0][index],data[1][index],data[2][index]]
    else:
        return [data[0][index],data[1][index]]

"""Calculates and clusters the sets depending on the number of K"""
def myKMeans(k,f):
    data = get_dataPoints(f)
    x = []
    y = []
    z = []
    'centroids = [[x,y,z],[x,y,z]]'
    centroids = pickKPoints(k, len(data[0]),data)
    refVector = centroids.copy()
    Set_Points = {i: [] for i in range(len(centroids))}
    list_Dis = [None]*k
    j = 0
    if f >=3:
        while (True):
            j += 1
            for i in range(0, len(data[0])):
                """get the coordinates of the observation and put it in Set Points"""
                coord = get_points(data, i)
                found_clusterPoint = closet_point(centroids, coord)
                indexAt = centroids.index(found_clusterPoint)
                Set_Points[indexAt].append(coord)
            for num_k in range(k):
                x1,y1,z1 = zip(*Set_Points[num_k])
                x.append(x1)
                y.append(y1)
                z.append(z1)

                matrix  = Set_Points[num_k]
                matrix  = np.asarray(matrix)
                meanMatrix = get_mean(matrix)
                dis = ManhattanDist(centroids[num_k],meanMatrix)
                list_Dis[num_k] = dis
                centroids[num_k] = meanMatrix
                centroids = np.array(centroids).tolist()

            plotData(x, y, z, k,j,f,refVector)
            refVector = centroids.copy()
            x.clear()
            y.clear()
            z.clear()
            for key in Set_Points:
                Set_Points[key].clear()

            if check_done(list_Dis):
                break
            else:
                continue
    else:
        while (True):
            j += 1
            for i in range(0, len(data[0])):
                """get the coordinates of the observation and put it in Set Points"""
                coord = get_points(data, i)
                found_clusterPoint = closet_point(centroids, coord)
                indexAt = centroids.index(found_clusterPoint)
                Set_Points[indexAt].append(coord)
            for num_k in range(k):
                x1, y1= zip(*Set_Points[num_k])
                x.append(x1)
                y.append(y1)
                matrix = Set_Points[num_k]
                matrix = np.asarray(matrix)
                meanMatrix = get_mean(matrix)
                dis = ManhattanDist(centroids[num_k], meanMatrix)
                list_Dis[num_k] = dis
                centroids[num_k] = meanMatrix
                centroids = np.array(centroids).tolist()

            plotData(x, y, z,k,j, f, refVector)
            x.clear()
            y.clear()
            for key in Set_Points:
                Set_Points[key].clear()

            if check_done(list_Dis):
                break
            else:
                continue


    files = glob.glob('*.png')
    for f in files:
        os.remove(f)

"""Calculates the Manhattan Distance"""
def ManhattanDist(p1,p2):
    if len(p1)== 3:
        x0,x1 = p1[0],p2[0]
        y0,y1 = p1[1],p2[1]
        z0,z1 = p1[2],p2[2]

        return math.fabs(x0-x1) + math.fabs(y0-y1) + math.fabs(z0-z1)
    else:
        x0, x1 = p1[0], p2[0]
        y0, y1 = p1[1], p2[1]
        return math.fabs(x0 - x1) + math.fabs(y0 - y1)

"""Checks if the we should end the iterations"""
def check_done(change_dist):
    if float(sum(change_dist)) < 2**(-23):
        return True
    else:
        return False

def closet_point(centroids, A):
    minimum = 9999999
    point = centroids[0]
    for i in centroids:
        D = Euclidiandistance(i,A)
        if D < minimum:
            minimum = D
            point = i

    return point

def Euclidiandistance(A,B):
    """A = [x,y,z] B=[x,y,z]"""
    if len(A) == 3:
        x0,x1 = A[0],B[0]
        y0,y1 = A[1],B[1]
        z0,z1 = A[2],B[2]

        return math.sqrt((x0 - x1)**2 + (y0 - y1)**2 + (z0 - z1)**2)
    else:
        x0, x1 = A[0], B[0]
        y0, y1 = A[1], B[1]
        return math.sqrt((x0 - x1) ** 2 + (y0 - y1) ** 2)

def get_mean(matrix):
    return matrix.mean(axis=0)

def get_standardMatrix(matrix):
    '1x1600 mean'
    m = matrix.mean(axis=0)
    '1x1600 std'
    std = matrix.std(axis=0)

    'get standardized matrix'
    standMatrix = (matrix - m) / std

    return standMatrix

myKMeans(k=3,f=4)
myKMeans(k=4,f=7)
myKMeans(k=5,f=8)



