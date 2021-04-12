package Bonus;

public class Main {

    public static void main(String[] args) {
        //check if path is provided
        if(args.length==0) {
            System.out.println("No path provided. Please provide a path next time.");
            System.exit(0);
        }
        if(args.length==1 && args[0].equals("-help")) {
            System.out.println("Provide a filepath with as many characteristics as you can and the algorithm will " +
                    "result appropriately. I have given a file FruitDataCH1.csv as a testing file.");
            System.exit(0);
        }
        //create a Fruit object
        FruitsBonus fb = new FruitsBonus(args[0]);
        fb.run();
    }



}
