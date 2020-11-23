from HelperFunc import *

if __name__ == '__main__':
    dataSet = getCSVData()
    S = int(input("Enter Value of S\n"))
    listSE = []
    listRMSE = []
    n = round(len(dataSet)/S)
    for i in range(0,20):
        """Gets the Randomize data and makes into a list and partitions each 
           by the given number of S"""
        Data, Target, length = randomizeData(dataSet)
        Data = Data.tolist()
        Target = Target.tolist()
        partData = [Data[i:i + n] for i in range(0, len(Data), n)]
        partTarget = [Target[i:i + n] for i in range(0, len(Target), n)]
        for j in range(0,S):
            """Gets the Test Data and Y depending on the value of J"""
            testData = partData[j]
            testTarget = partTarget[j]
            testData = np.asarray(testData)
            testTarget = np.asarray(testTarget)

            """Combines the data except for the testData"""
            trainData = partData[:j]+partData[j+1:]
            trainTarget = partTarget[:j]+partTarget[j+1:]
            trainData = [x for sublist in trainData for x in sublist]
            trainTarget =[x for sublist in trainTarget for x in sublist]
            trainData  = np.asarray(trainData)
            trainTarget = np.asarray(trainTarget)

            """Computes the theta and gets then finds the Predicted Y, Used for Squared Error"""
            theta  = computeWeight(trainData,trainTarget)
            predY = np.dot(testData,theta)
            SE = (predY - testTarget) ** 2
            listSE.append(np.mean(SE))

        Rmse = np.mean(np.sqrt(listSE))
        listRMSE.append(Rmse)

    print("Standard deviation is",np.std(listRMSE))
    print("Root Mean Squared Error",np.mean(listRMSE))

