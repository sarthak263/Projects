# RushHour
To Run the Code:
sh run.sh print <boardName> if no boardName given it will use the default Board.
sh run.sh next <boardName> prints all the scenarios of the board if no board given prints the default board
sh run.sh done <boardName> will print True or False if the car near the end of the open space on the right

The main.py will run and imports the class Cars.py

> sh run.sh print
------
| o aa|
| o   |
|xxo
|ppp q|
| q   |
| q   |
------
> sh run.sh print " ooo |ppp q |xx qa|rrr qa|b c dd|b c ee"
 ------
| ooo  |
|ppp q |
|xx qa
|rrr qa|
|b c dd|
|b c ee|
 ------
 > sh run.sh done " oaa | o | o xx| pppq| q| q"
True

sh run.sh next
 ------  ------  ------  ------  ------
| oaa | | o aa| | o aa| | o aa| | o aa|
| o   | | o   | | o   | | o   | | o  q|
|xxo  | | xxo   |xxo    |xxo q  |xxo q
|ppp q| |ppp q| | pppq| |ppp q| |ppp q|
| q   | |    q| |    q| |    q| |     |
| q   | |    q| |    q| |     | |     |
------  ------   ------  ------  ------
