package Bonus;

import java.io.*;
import java.util.*;

public class FruitsBonus {
    //initialize class attributes
    protected String path;
    protected ArrayList<List<String>> data = new ArrayList<>();
    protected List<String> fruitNames = new ArrayList<>();
    protected Set<List<String>> copy = new HashSet<>(data);
    //Constructor created
    public FruitsBonus(String path) {
        this.path = path;
        this.parseData();
        this.sortDataByAge();
    }

    //Methods to add data
    private void addData(List<String> data) {
        String[] arr = new String[data.size()];
        this.data.add(data);
        for (int i = 0; i < data.size(); i++) {
            if(i==1)
                arr[1] = null;
            else
                arr[i] = data.get(i);
        }
        this.copy.add(Arrays.asList(arr));
        this.fruitNames.add(data.get(0));
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
                if(checkCSVStruct<arr.length) {
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
    public void sortDataByAge() {
        this.data.sort(new Comparator<List<String>>() {
            @Override
            public int compare(List<String> o1, List<String> o2) {
                return Integer.parseInt(o1.get(1)) - Integer.parseInt(o2.get(1));
            }
        });
    }
    public void oldestFruit() {
        int maxAge = Integer.parseInt(this.data.get(this.data.size()-1).get(1));
        int age;
        System.out.println("\nOldest fruit & age:");
        for(int i =this.data.size()-1; i >=0; i--){
            age = Integer.parseInt(this.data.get(i).get(1));
            if(age != maxAge) {
                break;
            }
            System.out.format("%s: %s\n",this.data.get(i).get(0),this.data.get(i).get(1));
        }
    }
    public void typesFoodDesc() {
        ArrayList<String> temp = new ArrayList<>();
        System.out.println("\nThe number of each type of fruit in descending order: ");
        for (int i = this.data.size()-1;i>=0;i--) {
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
        for (List<String> hs : this.copy) {
            count  =0;
            for (List<String> datum : this.data) {
                if (find(hs, datum)) {
                    count = Integer.parseInt(datum.get(1)) + count;
                    String updated = String.valueOf(count);
                    //datum.set(1, updated);
                    hs.set(1,updated);
                }
            }
        }

        for (List ls : this.copy) {
            System.out.format("%s %s: ", ls.get(1),ls.get(0));
            for(int i = 2; i < ls.size();i++) {
                System.out.format("%s ",ls.get(i));
            }
            System.out.println();
        }
    }

    public void run() {
        Set<String> distinctFruitNames = new HashSet<>(this.getFruitNames());
        System.out.format("Total number of fruit:\n%d\n",this.data.size());
        System.out.format("\nTotal types of fruit:\n%d\n",distinctFruitNames.size());
        this.oldestFruit();
        this.typesFoodDesc();
        this.commonFruitTypes();
    }

    //returns true if temp and elem are exactly the same excluding the age of the fruit
    public boolean find(List<String> temp, List elem) {
        //did it find the elem
        if(temp.size()<= elem.size()){
            for (int i = 0; i < temp.size();i++) {
                if(i!=1)
                    if(!temp.get(i).equals(elem.get(i))){
                        return false;
                    }
            }
        }else {
            for (int i = 0; i < elem.size();i++) {
                if(i!=1)
                    if(!temp.get(i).equals(elem.get(i))){
                        return false;
                    }
            }
        }
        return true;
    }
}
