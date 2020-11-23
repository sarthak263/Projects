package Survey;

import java.io.*;
import java.util.*;

public class MultipleChoice extends Question implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    protected ArrayList<String> choices = new ArrayList<>();

    public MultipleChoice() {
        this.create();
        this.type="MultipleChoice";
    }

    //creates the prompt and choices
    @Override
    public void create() {
        int numChoice;
        this.prompt = inp.getString("Enter the prompt for your multiple-choice question:");
        this.MultiChoice = inp.checkNumChoice("How many answers will this question have?. Please enter a number.");
        if(this.MultiChoice > 1)
            this.prompt = this.prompt + " Please give " + this.MultiChoice + " choices:\n";

        numChoice = inp.checkNumChoice("Enter the number of choices for your multiple-choice question.");
        for (int i = 0; i < numChoice; i++)
            this.choices.add(inp.getInput("Enter choice #" + (i+1)));

    }

    //display the mc question and the answers if available just like the rest of the classes
    @Override
    public void display() {

        out.print(this.prompt);
        char ch ='A';
        for (String choice : this.choices) {
            out.print(ch + ") " + choice);
            ch++;
        }
        out.print("");
    }

    //this method is only used when the user takes the survey and leaves his/her answers
    @Override
    public void userResponse() {
        this.display();
        int i =0;
        while(i < this.MultiChoice ) {
            this.saveResp.add(inp.getInput("Answer here:"));
            i++;
        }
    }

    @Override
    public void modify() {
        char ch  = 'A';
        int choiceEntered;
        //for modifying prompt
        if(inp.boolQuestion("Do you wish to modify the prompt? Y/N")) {
            out.print(this.prompt);
            this.prompt = inp.getString("Enter a new prompt:");
            this.MultiChoice = inp.checkNumChoice("How many answers will this question have?. Please enter a number.");
            if (this.MultiChoice > 1)
                this.prompt = this.prompt + " Please give " + this.MultiChoice + " choices:\n";

        }
        //for choices
        if(inp.boolQuestion("Do you wish to modify choices? Y/N")) {
            do {
                out.print("Which choice do you want to modify?");
                for (String choice : this.choices) {
                    out.print(ch + ") " + choice);
                    ch++;
                }
                choiceEntered = inp.checkNumChoice("")-1;
                if (choiceEntered >= this.choices.size()) {
                    out.print("Please enter choices with that range");
                }else {
                    this.choices.set(choiceEntered, inp.getInput("Enter Choice: "));
                    break;
                }
            }while(true);
        }

    }
    @Override
    public ArrayList<String> setCorrectAnswer() {
        StringBuilder s = new StringBuilder();
        ArrayList<String> ans  = new ArrayList<String>();
        for(int i =0; i < this.MultiChoice;i++) {
            ans.add(inp.getInput("Enter Correct Answer in Alphabetical order"));
        }
        return ans;
    }
    @Override
    public int compareTo(Question o) {
        if(o.type.equals(this.type)) {
            return 0;
        }
        if(o.type.compareTo(this.type)>0) {
            return -5;
        }
        return 5;
    }
}
