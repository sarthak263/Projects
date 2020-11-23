import random
import math
import sys
class RushHour:

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
    def next_for_car(self,car,dicCars,currboard):
        l1 = []
        updatedBoardsofCurrentCar = []
        'loops and checks if the car is vertical or horizontal and calls the function vertical and horizontal with the car and index of that car'
        for i in range(len(currboard)):
            if currboard[i] == car:
                if dicCars[car] == 'V':
                    del dicCars[car]
                    updatedBoardsofCurrentCar= self.vertical(car, i,currboard)
                    break

                else:
                    updatedBoardsofCurrentCar = self.horizontal(car, i,currboard)
                    del dicCars[car]
                    break

        'it appends the list of updated boards to l1 and also splits them by | thus creating a 2d list'
        for i in range(len(updatedBoardsofCurrentCar)):
            l1.append(self.boards(updatedBoardsofCurrentCar[i]))

        return l1

    'functions to move the car horizontally'
    def horizontal(self, car, index,currboard):
        'creates two clone boards so it doesnt overlap'
        board = list(self.clone(currboard)).copy()
        lenCar = board.count(car)
        'use to add all the boards in this list'
        listofBoards = []
        boards=list(self.clone(currboard)).copy()
        'goes to the index before that specific car'
        start = index - 1
        'goes to the end of the car'
        end = index + lenCar
        flag = True
        'loops until all the moves are done horizontally for a specific car'
        while(True):
            if flag:
                if not start < 0:
                    if board[start] == " ":
                        board[start] = car
                        board[start+lenCar] = " "
                        listofBoards.append(''.join(board))
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
    def vertical(self,car,index,currboard):

        'clones and creates two boards'
        board = list(self.clone(currboard)).copy()
        boards = list(self.clone(currboard)).copy()
        listOfB= []
        lenCar = boards.count(car)
        partition = self.boards(self.argv)
        'goes to index on top of the car and assigns to start'
        startup = index-len(partition[0])-1
        startdown= index-len(partition[0]) - 1
        'same as end used to change endup for the first board '
        endup = index+(len(partition[0])*(lenCar-1)) + (lenCar-1)
        end = index+(len(partition[0])*(lenCar-1)) + (lenCar-1)
        flag = True
        'moves cars until all the scenarios are looked at'
        while(True):
            if flag:
                'moving cars up'
                if not startup < 0:
                    if board[startup] == " ":
                        board[startup] = car
                        board[endup] = " "
                        listOfB.append(''.join(board))
                        startup=startup-len(partition[0])-1
                        endup = endup-(len(partition[0]))-1
                        if not board[startup] == " ":
                            flag = False
                        continue
            'moving cars down'
            if (end+len(partition[0])+1) < len(self.argv):
                if boards[end+len(partition[0])+1] == " ":
                    boards[end+len(partition[0])+1] = car
                    boards[startdown+len(partition[0])+1] = " "
                    listOfB.append(''.join(boards))
                    end = end+len(partition[0])+1
                    startdown = startdown+len(partition[0])+1
                    continue
                else:
                    break
            else:
                break

        return listOfB

    def next(self, Flag,board):
        """

        calls next_for_car with specific cars and next_for_car function will return the updatedBoard with that moved
        car and keeps looping until all the cars are finished once all of the cars are moved then sequenceBoards function will
        be called with list of all the updated boards to print them out.

        """
        l1=[]
        listUpdatedBoards = []
        updatedBoards =[]
        listOfCars = (list(set(list(board))))
        listOfCars.sort()
        dicCars = {}
        dicCars = self.position(dicCars, listOfCars)
        for i in range(len(listOfCars)):
            updatedBoards = self.next_for_car(listOfCars[i], dicCars, board)
            if updatedBoards:
                listUpdatedBoards.append(updatedBoards)

        l1 = ([e for sl in listUpdatedBoards for e in sl])
        if Flag:
            #l1 = ([e for sl in listUpdatedBoards for e in sl])
            #print(l1)
            self.sequenceBoards(l1)
        else:
            return l1




    'finds the position of the individual cars to see if its vertical or Horizontal and returns the dictionary'
    def position(self, dicCars, listOfCars):
        if "|" in listOfCars:
            listOfCars.remove("|")
        if " " in listOfCars:
            listOfCars.remove(" ")
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

        space = "  "
        print("",end=space)

    'this function will get the 2d list and print it out horizontally'
    def sequenceBoards(self,boardList):
        res = boardList.copy()
        LENBOARD = 6
        p = math.floor(len(res)/LENBOARD)
        right = len(res) - LENBOARD*p
        end = LENBOARD
        start = 0
        while(p > 0):
            #prints the dashes at the top
            for k in range(start,end):
                self.printlines(res[0])
            print()

            'prints the board'
            for i in range(0,len(res[0])):
                for k in range(start,end):
                    if self.car in res[k][i]:
                        print("|" + res[k][i] + " ", end=' ')
                    else:
                        print("|" + res[k][i] + "|", end=' ')
                print()

            'prints the dashes at the bottom'
            for k in range(start,end):
                self.printlines(res[k])
            print()
            end+=LENBOARD
            start+=LENBOARD
            p-=1
        if not right ==0:
            for k in range(start, len(res)):
                self.printlines(res[0])
            print()
            'prints the board'
            for i in range(0, len(res[0])):
                for k in range(start, len(res)):
                    if self.car in res[k][i]:
                        print("|" + res[k][i] + " ", end=' ')
                    else:
                        print("|" + res[k][i] + "|", end=' ')
                print()
            'prints the dashes at the bottom'
            for k in range(start, len(res)):
                self.printlines(res[k])
            print()
        print("\n")


    def done(self,board):
        partition = self.boards(board)
        flag = 0
        for i in range(0,len(partition)):
            if self.car in partition[i][len(partition[0])-1]:
                #print("True")
                flag = 1
                break
            else:
                flag = 0

        return flag

    def random(self):
        board = self.clone(self.argv)
        listUpdatedBoards = []
        n = 10
        flag = False
        l2 = []
        while (n >= 0):
            if flag:
                randnum = random.randint(0,len(listUpdatedBoards)-1)
                board  = '|'.join(map(str,listUpdatedBoards[randnum]))
                l2.append(listUpdatedBoards[randnum])
                if self.done(board):
                    break
            listUpdatedBoards = self.next(False,board)
            flag=True
            n-=1
        self.sequenceBoards(l2)

    def currpath(self, parent, child, fullpath):
        l1 = []
        i = len(fullpath)-1
        if len(fullpath) <1:
            l1.append(parent)
            l1.append(child)
            #print(l1)
            return l1
        else:
            while i >= 0:
                if fullpath[i][-1] == parent:
                    temp = fullpath[i]
                    l1= temp + [child]

                    return l1

                i -= 1
            l1.append(parent)
            l1.append(child)
            return l1
            


    def printPath(self, fullpath):
        for i in range(0, len(fullpath)):
            self.sequenceBoards(fullpath[i])

    def bfs(self):
        board = self.clone(self.argv)
        element = None
        n = 0
        openList = [self.boards(board)]
        closeList = []
        savedPath = []
        l1=[]
        l1.append(self.boards(board))
        self.sequenceBoards(l1)
        l1 = []
        while len(openList) > 0:
            n+=1
            element = openList.pop(0)
            board = '|'.join(map(str, element))
            if self.done(board):
                break

            listUpdatedBoards = self.next(False, board)
            if len(listUpdatedBoards) > 0:
                for boards in listUpdatedBoards:
                    if not boards in closeList:
                        closeList.append(boards)
                        openList.append(boards)
                        currpath = self.currpath(element,boards, savedPath)
                        savedPath.append(currpath)
                        self.sequenceBoards(currpath)
                        b = '|'.join(map(str, currpath[-1]))
                        if self.done(b):
                            lenPath = len(savedPath) + 1
                            print(lenPath)
                            sys.exit()

        lenPath = len(savedPath) + 1
        print(lenPath)

    def dfs(self):
        board = self.clone(self.argv)
        element = None
        n = 0
        stack = [self.boards(board)]
        closeList = []
        closeList = []
        savedPath = []
        l1 = []
        l1.append(self.boards(board))
        self.sequenceBoards(l1)
        l1 = []
        while len(stack) > 0:

            element = stack.pop(0)
            board = '|'.join(map(str, element))
            if self.done(board):
                break
            listUpdatedBoards = self.next(False, board)
            if len(listUpdatedBoards) > 0:
                for boards in listUpdatedBoards:
                    if not boards in closeList:
                        stack.insert(0,boards)
                        closeList.append(boards)
                        currpath = self.currpath(element,boards, savedPath)
                        savedPath.append(currpath)
                        self.sequenceBoards(currpath)
                        b = '|'.join(map(str, currpath[-1]))
                        if self.done(b):
                            lenPath = len(savedPath) + 1
                            print(lenPath)
                            sys.exit()
        #self.printPath(savedPath)
        lenPath = len(savedPath) + 1
        print(lenPath)

    def heuristic(self, board):
        dis = 0
        for i in range(len(board)):
            if 'x' in board[i]:
                num = board[i].count('x')
                dis = len(board[i]) - (board[i].index('x') + num)
                return dis

        return dis

    def astar(self):
        board = self.clone(self.argv)
        element = None
        node = Node()
        node.parent = self.boards(board)
        node.g = 0
        node.h = self.heuristic(node.parent)
        node.f = node.g + node.h
        closeList = []
        openList = [node]
        savedPath = []
        l1=[]
        l1.append(self.boards(board))
        self.sequenceBoards(l1)
        l1=[]
        n =0
        while len(openList) > 0:
            currNode = openList[0]

            for index, item in enumerate(openList):
                if item.f < currNode.f:
                    currNode = item

            element = currNode.parent
            openList.remove(currNode)
            closeList.append(element)
            board = '|'.join(map(str, element))
            if self.done(board):
                break
            listUpdatedBoards = self.next(False, board)
            if len(listUpdatedBoards) > 0:
                for board in listUpdatedBoards:
                    if not board in closeList:
                        b = Node()
                        b.parent = board
                        b.g = node.g + 1
                        b.h = self.heuristic(board)
                        b.f = 0 + b.h
                        closeList.append(b.parent)

                        #if b.g > currNode.g and b.parent in openList:
                        #    continue
                        for openNode in openList:
                            if b.parent == openNode and b.g > openNode.g:
                                continue

                        openList.append(b)
                        currpath = self.currpath(element, board, savedPath)
                        savedPath.append(currpath)
                        self.sequenceBoards(currpath)
                        n=n+len(currpath)
                        b = '|'.join(map(str, currpath[-1]))
                        if self.done(b):
                            lenPath = len(savedPath)+1
                            print(lenPath)
                            sys.exit()

        #self.printPath(savedPath)
        lenPath = len(savedPath)+1
        print(lenPath)

class Node:
    def __int__(self,parent=None):
        self.parent = parent

        self.g = 0
        self.h = 0
        self.f = 0





