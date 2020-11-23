#! /usr/bin/python3
import numpy as np
import glob
import matplotlib.pyplot as plt
from PIL import Image

def P2StandMatrix():
    size = 40,40
    my_matrix = []
    'Open the image'
    listFiles = (glob.glob('yalefaces/*.png'))

    for files in listFiles:
        file = "yalefaces/"+files[10:]
        myImage = Image.open(file)
        'store the array in data'
        data  = np.array(myImage)
        'resize the data to 40x40'
        myImage = myImage.resize(size,Image.ANTIALIAS)
        data2 = np.array(myImage)
        'flatten the image 1x1600'
        flatImage = data2.flatten()

        my_matrix.append(flatImage)

    matrix =  np.asarray(my_matrix)
    return matrix

def get_standardMatrix(matrix):
    m = matrix.mean(axis=0)
    std = np.std(matrix,axis=0,dtype=np.float64)
    standMatrix = (matrix - m) / std
    return np.array(standMatrix)

def get_unstandarMatrix(matrix):

    '1x1600 mean'
    m = matrix.mean(axis=0)
    '1x1600 std'
    std = matrix.std(axis=0)
    unstandMatrix  = (matrix*std)+m

    return unstandMatrix

def get_eigens(standMatrix):

    'getting covariance and eigenvalues and eigenvectors'
    cov = np.cov(standMatrix,rowvar=False)
    evals, eigens = np.linalg.eig(cov)
    evals = evals.real
    eigens = eigens.real

    idx = evals.argsort()[::-1]
    eigenVal = evals[idx]
    eigenVec = eigens[:, idx]
    eigenVec = np.array(eigenVec)

    return eigenVal,eigenVec




