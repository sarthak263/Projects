import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class Menu {
    public static void main(String[] args) throws IOException, InterruptedException {

        String[] values = new String[]{"-NUMTHREADS", "-XLO", "-XHI", "-YLO", "-YHI", "-THRESHOLD", "-SIZE", "-NAME"};
        ArrayList<String> vals = new ArrayList<String>(Collections.nCopies(8, null));

        //adds the values to its corresponding index in the arraylist
        for (int i = 0; i < args.length; i += 2) {
            int idx = Arrays.asList(values).indexOf(args[i]);
            if (idx == -1) {
                System.out.println("Could not read input correctly. Please try again with correct inputs");
                System.exit(1);
            }
            vals.set(idx, args[i + 1]);

        }
        //removes all the null values so the size will change depending on the values in the list
        vals.removeIf(Objects::isNull);
        menu(vals);
        //clears the data
        vals.clear();

    }

    public static void menu(ArrayList<String> args) throws IOException, InterruptedException {

        /*avg 5 runs sequential time for each mandelbrot
            int snowmanTime = 9887;
            int FieldTime = 9412;
            int Dragon = 10031;
            int Coral = 2693;
         */

        //if no argument provided run all the mandelbrot
        if (args.size() == 0) {
            new MandelBrot(ParallelStrategy.Dynamic, 4, -1.236, -1.191, 0.14, 0.172, 5000, 1100,
                    new File("Snowman.jpg"), "Snowman");
            new MandelBrot(ParallelStrategy.Static, 4, -1.236, -1.191, 0.14, 0.172, 5000, 1100,
                    new File("Snowman.jpg"), "Snowman");

            new MandelBrot(ParallelStrategy.Dynamic, 4, -0.74998880248225142145, -0.74998880228812666519
                    , 0.00699725115971273323, 0.00699725130530630042, 5000, 1000,
                    new File("Field.jpg"), "Field");

            new MandelBrot(ParallelStrategy.Static, 4, -0.74998880248225142145, -0.74998880228812666519
                    , 0.00699725115971273323, 0.00699725130530630042, 5000, 1000,
                    new File("Field.jpg"), "Field");

            new MandelBrot(ParallelStrategy.Dynamic, 4, -0.840732, -0.840716, 0.22420, 0.224216, 250, 5500,
                    new File("Dragon.jpg"), "Dragon");
            new MandelBrot(ParallelStrategy.Static, 4, -0.840732, -0.840716, 0.22420, 0.224216, 250, 5500,
                    new File("Dragon.jpg"), "Dragon");

            new MandelBrot(ParallelStrategy.Dynamic, 4, 0.3469233141, 0.3481312224, -0.368, -.365, 200, 2000,
                    new File("Coral.jpg"), "Coral");

            new MandelBrot(ParallelStrategy.Static, 4, 0.3469233141, 0.3481312224, -0.368, -.365, 200, 2000,
                    new File("Coral.jpg"), "Coral");


        }  //the necessary arguments are provided
        else if (args.size() == 7 || args.size() == 8) {
            double[] doubles = new double[4];
            int[] integers = new int[3];
            try {
                for (int i = 1; i < 5; i++) {
                    doubles[i - 1] = Double.parseDouble(args.get(i));
                }
                integers[0] = Integer.parseInt(args.get(0));
                integers[1] = Integer.parseInt(args.get(5));
                integers[2] = Integer.parseInt(args.get(6));

                //if the name is not provided then save the file to yourCreation.jpg
                if (args.size() == 7) {
                    //no file name given
                    new MandelBrot(ParallelStrategy.Dynamic, integers[0], doubles[0], doubles[1], doubles[2], doubles[3], integers[1], integers[2],
                            new File("yourCreation.jpg"), "yourCreation");
                    new MandelBrot(ParallelStrategy.Static, integers[0], doubles[0], doubles[1], doubles[2], doubles[3], integers[1], integers[2],
                            new File("yourCreation.jpg"), "yourCreation");
                } else {
                    //if the name is provided then save it to that name
                    String name = args.get(7) + ".jpg";
                    new MandelBrot(ParallelStrategy.Dynamic, integers[0], doubles[0], doubles[1], doubles[2], doubles[3], integers[1], integers[2],
                            new File(name), args.get(7));
                    new MandelBrot(ParallelStrategy.Static, integers[0], doubles[0], doubles[1], doubles[2], doubles[3], integers[1], integers[2],
                            new File(name), args.get(7));
                }
            } catch (Exception e) {
                // it will print error if the inputs provided are incorrect
                System.out.println("Error in your input arguments!! Please try again.");
            }
        } else {
            System.out.println("Missed an important input. Please try again");
        }
    }
}


