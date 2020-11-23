from HelperFunc import *

if __name__ == '__main__':

    dataset = getCSVData()
    trainingData, trainingTarget,length = randomizeData(dataset)
    length = round(length*2/3)
    """Computes the theta by using the training data and target"""
    theta = computeWeight(trainingData[:length], trainingTarget[:length])
    SE,MSE, RMSE = RootError(trainingData[length:],theta,trainingTarget[length:])
    print("Root Mean Squared Error ",RMSE)
    print(theta)


