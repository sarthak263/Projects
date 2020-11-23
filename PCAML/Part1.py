#! /usr/bin/python3
from PCAFunc import *
import numpy as np

def main():
    X = [[-2,1],
         [-5,-4],
         [-3,1],
         [0,3],
         [-8,11],
         [-2,5],
         [1,0],
         [5,-1],
         [-1,-3],
         [6,1]]

    matrix = np.asarray(X)

    standMatrix = get_standardMatrix(matrix)
    eigenVal, eigenVec = get_eigens(standMatrix)
    Pca1 = np.dot(standMatrix,eigenVec[:,:1])
    Pca2 = np.dot(standMatrix,eigenVec[:,:])


main()