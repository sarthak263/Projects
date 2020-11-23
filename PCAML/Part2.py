#! /usr/bin/python3
from PCAFunc import *
def main():
    matrix  = P2StandMatrix()
    '''
    matrix = [[4, 1, 2],
              [2, 4, 0],
              [2, 3,-8],
              [3, 6, 0],
              [4, 4, 0],
              [9, 10, 1],
              [6, 8,-2],
              [9, 5, 1],
              [8, 7, 10],
              [10, 8,-5]]
    '''
    matrix  = np.asarray(matrix)
    standMatrix = get_standardMatrix(matrix)
    eigenVal,eigenVec = get_eigens(standMatrix)

    p = np.dot(standMatrix,eigenVec[:,0:2])
    fig = plt.figure()
    plt.scatter(p[:,0:1],p[:,1:2])
    plt.savefig("PlotGraph.png")
    plt.show()

main()