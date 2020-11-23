package Survey;

import java.io.Serializable;
import java.util.Scanner;

public class ConsoleInput extends Input implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    public ConsoleInput() {}

    //used for just getting ints. Error checking
    public int getInt(String msg) {
        Scanner kbd = new Scanner(System.in);
        do{
            try {
                System.out.println(msg);
                this.num = kbd.nextInt();
                kbd.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Please Enter a number");
                kbd.nextLine();
            }
        }while (true);

        return this.num;
    }

    //only String required and if its just numbers it will ask again
    public String getString(String msg) {
        Scanner kbd = new Scanner(System.in);
        do{
            try {
                System.out.println(msg);
                this.word = kbd.nextLine();
                try {
                    Integer.parseInt(this.word);
                    System.out.println("Please Don't just enter integers");
                }catch (Exception e) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Please Enter words");
            }
        }while (true);

        return this.word;
    }

    //any input is fine does the error checking
    public String getInput(String msg) {
        Scanner kbd = new Scanner(System.in);
        do{
            try {
                System.out.println(msg);
                this.word = kbd.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Error with Scanner. Try Again");
            }
        }while (true);

        return this.word;
    }
    //used for true false question
    public String getBoolean(String msg) {
        Scanner kbd = new Scanner(System.in);
        do{
            try {
                System.out.println(msg);
                this.word = kbd.nextLine();
                if(this.word.equalsIgnoreCase("True") || this.word.equalsIgnoreCase("False")||
                   this.word.equalsIgnoreCase("T") || this.word.equalsIgnoreCase("F") ||
                   this.word.equalsIgnoreCase("A") || this.word.equalsIgnoreCase("B")) {
                    break;
                }else {
                    System.out.println("Please only Enter True/False or t/f or letter choice A/B");
                }
            } catch (Exception e) {
                System.out.println("Error with Scanner. Try Again");
            }
        }while (true);

        return this.word;
    }

    //used for yes and no questions
    public boolean boolQuestion(String msg) {
        String ans;
        do {
            ans = this.getString(msg);

            if (ans.equals("Y") || ans.equals("y"))
                return true;

            else if (ans.equals("N") || ans.equals("n"))
                return false;

            System.out.println("Please only enter Y/y or N/n");
        }while(true);

    }
    public int checkNumChoice( String msg) {
        int numChoice;
        do{
            numChoice = this.getInt(msg);
            if(numChoice<=0) {
                System.out.println("Please enter greater than 0");
                continue;
            }
            break;
        }while(true);
        return numChoice;
    }

}
