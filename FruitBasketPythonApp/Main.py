from FruitBasket import *
import csv, sys

#reads the csv file and returns the data
def fileReader(filename):
    data = []
    try:
        with open(filename, 'r') as file:
            reader = csv.reader(file)

            for row in reader:
                data.append(row)
    except FileNotFoundError:
        print("File was not found. Exiting..")
        sys.exit(0)
    except UnicodeError:
        print("Cannot read the given file. Exiting..")
        sys.exit(0)

    return data[1:]

#starting of the program
def main(argv):
    if len(argv)==0:
        print("Please provide file name.")
    elif len(argv) ==1:
        data = fileReader(argv[0])
        Basket(data)
    else:
        print("Too many arguments")

main(sys.argv[1:])