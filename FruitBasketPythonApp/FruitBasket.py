class Basket:
    data = None
    fruitsList = []
    fruitChar = []
    oldFruits = []
    def __init__(self, data):
        self.data = data
        self.run()

    '''
    HELPER METHODS
    '''
    def getData(self):
        return self.data

    def getDataLen(self):
        return len(self.data)

    #gets the list of all the fruits
    def makeFruits(self):
        return [d[0] for d in self.data]

    #gets the list of fruits older than 3 days
    def makeOldFruits(self):
        t = []
        for d in self.data:
            if int(d[1])>3:
                t.append(d[0])
        return t

    #gets the list of fruits with its characteristics
    def getFruitChar(self):
        return [[d[0],d[2],d[3]] for d in self.data]

    #counts the number of fruits in the csv file
    def fruitType(self):
        print()
        temp =[]
        fruits = self.makeFruits()
        for fruit in fruits:
            if fruit not in temp:
                temp.append(fruit)
                self.fruitsList.append([fruit,fruits.count(fruit)])

        #sorts the list in reverse order
        self.fruitsList= sorted(self.fruitsList,key=lambda a:a[1],reverse=True)

        print("Types of fruits: {0}\n".format(len(self.fruitsList)))

        for d in self.fruitsList:
            print("{0}: {1}".format(d[0],d[1]))

        #clears the temp variable
        temp.clear()

    def getFruitCharacteristics(self):
        print()
        fruitChar = self.getFruitChar()
        temp =[]
        for d in fruitChar:
            if d not in temp:
                temp.append(d)
                self.fruitChar.append([fruitChar.count(d),d])

        for d in self.fruitChar:
            print("{0} {1}: {2}, {3}".format(d[0],d[1][0],d[1][1], d[1][2]))

        #clears the temp variable
        temp.clear()

    #counts the old fruits
    def getOldFruits(self):
        print()
        oldFruits = self.makeOldFruits()
        temp =[]
        res = ""
        for d in oldFruits:
            if d not in temp:
                self.oldFruits.append([oldFruits.count(d),d])
                temp.append(d)

        for d in self.oldFruits:
            res+= "{0} {1}, ".format(d[0],d[1])

        res+= "are over 3 days old."

        #clears the temp variable
        temp.clear()
        return res

    #run method to call all the necessary helper methods
    def run(self):
        #getting number of fruits
        print("Total number of fruits: ",self.getDataLen())
        self.fruitType()
        self.getFruitCharacteristics()
        print(self.getOldFruits())