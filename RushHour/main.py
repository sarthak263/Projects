from RushHour import *
def main(argv):

    defaultBoard = "  o aa|  o   |xxo   |ppp  q|     q|     q"
    l1=[]
    board = RushHour('x',defaultBoard)
    if argv[0] == 'print':
        if len(argv) <=1:
            #board = Cars("x", defaultBoard)
            l1.append(board.boards(defaultBoard))
            board.sequenceBoards(l1)
        else:
            board.argv = argv[1]
            l1.append(board.boards(board.argv))
            board.sequenceBoards(l1)
    elif argv[0]=='done':
            if len(argv) <=1:
                board.argv = defaultBoard
            else:
                board.argv = argv[1]

            board.done(board.argv)
    elif argv[0]=='next':
        if len(argv) <= 1:
            board.argv = defaultBoard
        else:
            board.argv = argv[1]

        board.next(True,board.argv)
    elif argv[0]=='random':
        if len(argv) <= 1:
            board.argv = defaultBoard
        else:
            board.argv = argv[1]

        board.random()
    elif argv[0] == 'bfs':
        if len(argv) <= 1:
            board.argv = defaultBoard
        else:
            board.argv = argv[1]
        board.bfs()
    elif argv[0] == 'dfs':
        if len(argv) <= 1:
            board.argv = defaultBoard
        else:
            board.argv = argv[1]
        board.dfs()
    elif argv[0] == 'astar':
        if len(argv) <= 1:
            board.argv = defaultBoard
        else:
            board.argv = argv[1]
        board.astar()

main(sys.argv[1:])