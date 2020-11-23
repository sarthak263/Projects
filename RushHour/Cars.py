class Cars:

    def __init__(self,car,argv):
        self.car = car
        self.argv = argv

    'Clones the board '
    def clone(self,currboard):
        return currboard

    'helper method to updateboard and sperate it by "|"'
    def boards(self,updateBoard):
        uB = updateBoard.split("|")
        return uB

    'function to print out every scnerios for each car'
    def next_for_car(self,car,dicCars):
        currboard = self.clone(self.argv)
        l1 = []
        updatedBoardsofCurrentCar = []
        'loops and checks if the car is vertical or horizontal and calls the function vertical and horizontal with the car and index of that car'
        for i in range(len(currboard)):
            if currboard[i] == car:
                if dicCars[car] == 'V':
                    del dicCars[car]
                    updatedBoardsofCurrentCar= self.vertical(car, i)
                    break

                else:
                    updatedBoardsofCurrentCar = self.horizontal(car, i)
                    del dicCars[car]
                    break

        'it appends the list of updated boards to l1 and also splits them by | thus creating a 2d list'
        for i in range(len(updatedBoardsofCurrentCar)):
            l1.append(self.boards(updatedBoardsofCurrentCar[i]))

        return l1

    'functions to move the car horizontally'
    def horizontal(self, car, index):
        'creates two clone boards so it doesnt overlap'
        board = list(self.clone(self.argv)).copy()
        lenCar = board.count(car)
        'use to add all the boards in this list'
        listofBoards = []
        boards=list(self.clone(self.argv)).copy()

        'goes to the index before that specific car'
        start = index - 1
        'goes to the end of the car'
        end = index + lenCar
        flag = True

        'loops until all the moves are done horizontally for a specific car'
        while(True):
            if flag:
                if not board[start] == "|":
                    if board[start] == " ":
                        board[start] = car
                        board[start+lenCar] = " "
                        listofBoards.append(''.join(board))
                        #print(listofBoards)
                        start=start-1
                        if not board[start] == " ":
                            flag = False
                        continue
            if end < len(self.argv):
                if boards[end] == " ":
                    boards[end] = car
                    boards[end-lenCar] = " "
                    listofBoards.append(''.join(boards))
                    #print(listofBoards)
                    end=end+1
                    continue
                else:
                    break
            else:
                break
        return listofBoards

    'function to move cars vertically'
    def vertical(self,car,index):

        'clones and creates two boards'
        board = list(self.clone(self.argv)).copy()
        boards = list(self.clone(self.argv)).copy()
        listOfB= []
        lenCar = boards.count(car)
        partition = self.boards(self.argv)
        'goes to index on top of the car and assigns to start'
        start = index-len(partition[0])-1
        'same as end used to change endup for the first board '
        endup = index+(len(partition[0])*(lenCar-1)) + (lenCar-1)
        end = index+(len(partition[0])*(lenCar-1)) + (lenCar-1)

        'moves cars until all the scenarios are looked at'
        while(True):
            'moving cars up'
            if start > 0:
                if board[start] == " ":
                    board[start] = car
                    board[endup] = " "
                    listOfB.append(''.join(board))
                    start=start-len(partition[0])-1
                    endup = endup-(len(partition[0]))-1
                    continue
                else:
                    break
            'moving cars down'
            if (end+len(partition[0])+1) < len(self.argv):
                if boards[end+len(partition[0])+1] == " ":
                    boards[end+len(partition[0])+1] = car
                    boards[end-len(partition[0])-1] = " "
                    listOfB.append(''.join(boards))
                    end = end+len(partition[0])+1
                    continue
                else:
                    break
            else:
                break

        return listOfB

    def next(self):
        """

        calls next_for_car with specific cars and next_for_car function will return the updatedBoard with that moved
        car and keeps looping until all the cars are finished once all of the cars are moved then sequenceBoards function will
        be called with list of all the updated boards to print them out.

        """
        listUpdatedBoards = []
        updatedBoards =[]
        listOfCars = []

        for i in range(0, len(self.argv)-1):
            if self.argv[i] >= '97' and self.argv <='121':
                if not self.argv[i] == '|':
                    listOfCars.append(self.argv[i])

        'gets all the unique cars'
        listOfCars = list(set(listOfCars))
        dicCars = {}
        dicCars = self.position(dicCars, listOfCars)

        for i in range(len(listOfCars)):
            updatedBoards = self.next_for_car(listOfCars[i], dicCars)
            if updatedBoards:
                listUpdatedBoards.append(updatedBoards)

        l1 = ([e for sl in listUpdatedBoards for e in sl])
        self.sequenceBoards(l1)

    'finds the position of the individual cars to see if its vertical or Horizontal and returns the dictionary'
    def position(self, dicCars, listOfCars):
        listCars = listOfCars.copy()
        for i in range(0,len(self.argv)):
            if self.argv[i] in listCars:
                if self.argv[i] == self.argv[i+1]:
                    dicCars[self.argv[i]] = 'H'
                    listCars.remove(self.argv[i])
                else:
                    dicCars[self.argv[i]] = 'V'
                    listCars.remove(self.argv[i])

        return dicCars

    'function to simply print dashes'
    def printlines(self,matrix):

        for i in range(0, len(matrix[0])):
            if i == 0:
                print(" -", end='')

            else:
                print("-", end='')

        space = ""
        for s in range(0,len(matrix)//2):
            space = space + " "
        print("",end=space)

    'this function will get the 2d list and print it out horizontally'
    def sequenceBoards(self,boardList):
        res = boardList.copy()

        #prints the dashes at the top
        for k in range(0,len(res)):
            self.printlines(res[0])
        print()

        'prints the board'
        for i in range(0,len(res[0])):
            for k in range(0,len(res)):
                if self.car in res[k][i]:
                    print("|" + res[k][i] + " ", end='  ')
                else:
                    print("|" + res[k][i] + "|", end='  ')
            print()

        'prints the dashes at the bottom'
        for k in range(0,len(res)):
            self.printlines(res[k])
        print()

    def done(self):
        partition = self.boards(self.argv)
        flag = 0
        for i in range(0,len(partition)):
            if self.car in partition[i][len(partition[0])-1]:
                print("True")
                flag = 1
                break
            else:
                flag = 0
        if not flag:
            print("False")





