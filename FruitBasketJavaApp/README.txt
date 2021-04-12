Author: Sarthak Patel
Date: 03/07/21

To run java file. 
	1. Please be in the directory FruitBasket
	2. on Command Line run:  java Basket.Main {$PATH}. If no path is given. It will print out a message saying please provide a path
	3. If the file is not CSV structure or can't find the file in the path it will print error message accordingly. I did a manual check instead of using an external library.
		First check is if the given file cannot be read. Second check is when the length of data coloumns (fruit-type, age-in-days, characteristic1, characteristic2) 
		does not equal to the length of the list in each line then return not csv structured file.

	Please use FruitData.csv to test the code or any other readable csv file.
	
To run Bonus file
	1. Please be in the directory FruitBasket
	2. on Command Line run : java Bonus.Main {$PATH} or -help as first args. If -help is the first args then it will provide you with information on how you should run it. 
	   If no args is given It will print out a message saying please provide a path.
	3. If the file is not CSV structure or can't find the file in the path it will print error message accordingly. Same technique as listed in fruit class. Just one change is
	   made and that is if the length of list in that current line is bigger than data columns then it will return not csv structured file. Since those data will not be useful.

	Please use FruitDataCh1.csv to test the code for dynamic characteristics of fruits.
	

If there is any trouble running the java file:
    Try Compliling the files again.
    Go to FruitBasket\Basket directory and run javac *.java.
    To run Bonus file got to FruitBasket\Bonus directory and run javac *.java. 
    Then follow the steps provided above to run java file.