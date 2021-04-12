package Basket;

public class Main {

    public static void main(String[] args) {
        //check if path is provided
        if(args.length==0) {
            System.out.println("No path provided. Please provide a path next time.");
            System.exit(0);
        }
        //create a Fruit object
        Fruits fruits = new Fruits(args[0]);
        fruits.run();
    }



}
