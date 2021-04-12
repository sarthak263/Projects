package Basket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Fruits {

    //initialize class attributes
    protected String path;
    protected ArrayList<List<String>> data = new ArrayList<>();
    protected List<String> fruitNames = new ArrayList<>();
    protected Set<List<String>> fruitCharacs = new HashSet<>();

    //Constructor created
    public Fruits(String path) {
        this.path = path;
        this.parseData();
        this.sortDataByAge();
    }

    //Methods to add data
    private void addData(List<String> data) {
		try {
			this.data.add(data);
			this.fruitNames.add(data.get(0));
			this.fruitCharacs.add(Arrays.asList(data.get(0),null, data.get(2),data.get(3)));
		}catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Index out of bound exception occured. Maybe the file is not csv structured.");
			System.exit(1);
		}
    }

    //getters method
    public List<String> getFruitNames() {
        return this.fruitNames;
    }
    public List<List<String>> getData() {
        return this.data;
    }

    public void parseData() {
        //read the file
        String line = null;
        int checkCSVStruct;
        String[] arr;
        try {
            File f = new File(this.path);
            Scanner scan = new Scanner(f);
            try {
                line = scan.nextLine();
            }
            catch (Exception e){
                System.out.println("This is not the correct CSV structure. Please use a different file.");
                System.exit(1);
            }
            //get the length of the data
            checkCSVStruct = line.split(",").length;

            //loops through each line
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                arr = line.split(",");
                //if the length is not the same that means there is a missing data or given more data
                if(checkCSVStruct!=arr.length) {
                    System.out.println("This is not the correct CSV structure. There are more elements then data columns."
                            + " Please use a different file.");
                    System.exit(1);
                }
                //adds the data in the current line.
                this.addData(Arrays.asList(arr));
            }
            //causes an exception if it can't find the file.
        } catch (IOException e) {
            System.out.println("File does not exist. Make sure you have the correct file name.");
            System.exit(0);
        }
    }
    //sorts the data by age increasing
    public void sortDataByAge() {
        this.data.sort(new Comparator<List<String>>() {
            @Override
            public int compare(List<String> o1, List<String> o2) {
                return Integer.parseInt(o1.get(1)) - Integer.parseInt(o2.get(1));
            }
        });
    }
    //finds the oldest fruits
    public void oldestFruit() {
        //gets the max age which is the last value of the 2d list
        int maxAge = Integer.parseInt(this.data.get(this.data.size()-1).get(1));
        int age;
        System.out.println("\nOldest fruit & age:");
        //loops backwards because sorted in an increasing order
        for(int i =this.data.size()-1; i >=0; i--){
            age = Integer.parseInt(this.data.get(i).get(1));
            //if the age does not equal to max age means we found all the oldest fruit
            if(age != maxAge) {
                break;
            }
            System.out.format("%s: %s\n",this.data.get(i).get(0),this.data.get(i).get(1));
        }
    }
    //gets the set of all the fruit with its age
    public void typesFoodDesc() {
        //creates a temp variable
        ArrayList<String> temp = new ArrayList<>();
        System.out.println("\nThe number of each type of fruit in descending order: ");

        //loops backward and adds the current elem to temp
        for (int i = this.data.size()-1;i>=0;i--) {
            //if it is not in temp add it and print since we haven't found the elem
            if(!temp.contains(this.data.get(i).get(0))) {
                temp.add(this.data.get(i).get(0));
                System.out.format("%s: %s\n",this.data.get(i).get(0),this.data.get(i).get(1));
            }
        }
        //clear the list
        temp.clear();
    }

    public void commonFruitTypes() {
        int count;
        System.out.println("\nThe various characteristics (count, color, shape, etc.) of each fruit by type:");
        for (List<String> hs : this.fruitCharacs) {
            count  =0;
            for (List<String> datum : this.data) {
                if (find(hs, datum)) {
                    count = Integer.parseInt(datum.get(1)) + count;
                    String updated = String.valueOf(count);
                    hs.set(1,updated);
                }
            }
        }

        for (List ls : this.fruitCharacs) {
            System.out.format("%s %s: %s, %s\n", ls.get(1),ls.get(0),ls.get(2),ls.get(3));
        }
    }

    //runs all the method
    public void run() {
        Set<String> distinctFruitNames = new HashSet<>(this.getFruitNames());
        System.out.format("Total number of fruit:\n%d\n",this.data.size());
        System.out.format("\nTotal types of fruit:\n%d\n",distinctFruitNames.size());
        this.oldestFruit();
        this.typesFoodDesc();
        this.commonFruitTypes();
    }
    //finds the elem but excludes the age of the fruit
    public boolean find(List<String> temp, List elem) {
        //did it find the elem
        return temp.get(0).equals(elem.get(0)) && temp.get(2).equals(elem.get(2)) && temp.get(3).equals(elem.get(3));
    }

}//end class
