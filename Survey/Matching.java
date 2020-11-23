package Survey;

import java.io.*;
import java.util.*;

public class Matching extends Question implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private ArrayList<String> question = new ArrayList<>();
    private ArrayList<String> choices  = new ArrayList<>();


    public Matching() {
        this.create();
        this.type="Matching";
    }
    //create it by asking number of choices title and then user will enter question and choice one by one
    public void create() {
        this.prompt = inp.getString("Enter the title for this matching question");
        this.MultiChoice = inp.checkNumChoice("Enter the number of choices for matching");
        for(int i =0; i < this.MultiChoice; i++) {
            this.question.add(inp.getString("Enter a matching question"));
            this.choices.add(inp.getInput("Enter a matching choice"));

        }
    }

    @Override
    public ArrayList<String> setCorrectAnswer() {
        this.display();
        ArrayList<String> ans = new ArrayList<String>();
        for(int i =0; i < this.MultiChoice;i++) {
            ans.add(inp.getInput("Enter the correct choices in order"));
        }
        return ans;
    }

    //display the matching but shuffles it first so the choices and questions don't exactly match
    @Override
    public void display() {
        //need this to shuffle the answers every time
        out.print("Title: " + this.prompt);

        char ch = 'A';
        //display neatly in two columns
        for(int i = 0; i < this.choices.size(); i++) {
            System.out.printf("%c) %-30.30s%d) %-30.30s%n",ch,this.question.get(i), (i+1),this.choices.get(i));
            ch++;
        }

        out.print("");
    }

    //same thing as all the other answer method asking user to enter answers
    @Override
    public void userResponse() {
        this.display();
        out.print("Enter the characters in order");
        int numAns;
        char ch = 'A';
        for (int i =0; i < this.choices.size();i++) {
            do{
                numAns = inp.checkNumChoice("Answer #" + (i+1));
                if(numAns > this.choices.size()) {
                    out.print("Please select choices in range");
                }else {
                    break;
                }
            }while(true);
            this.saveResp.add(Integer.toString(numAns));
            ch++;
        }
    }
    //ask to modify the title, question, answers and to add a choice
    @Override
    public void modify() {
        int ans;
        int i =1;
        //change title
        if(inp.boolQuestion("Do you want to modify the Title. Y/N")) {
            this.prompt = inp.getString("Enter the new Title");
        }
        //change Question choices
        if(inp.boolQuestion("Do you want to modify the Question choices. Y/N")) {
            for(String s: this.question) {
                out.print("Questions " + i + " : " + s );
                i++;
            }
            i=1;
                do {
                    ans = inp.checkNumChoice("Enter the number of the question")-1;
                    if (ans >= question.size()) {
                        out.print("Please enter in range");
                    } else {
                        break;
                    }
                } while (true);
                this.question.set(ans, inp.getString("Enter the question"));
            }

        //change choices choices
        if(inp.boolQuestion("Do you want to modify the choices Y/N")) {
            for(String s: this.choices) {
                out.print("Choices " + i + " : " + s);
                i++;
            }
            i=1;
            do {
                ans = inp.checkNumChoice("Enter the number of the choices")-1;
                if (ans >= question.size()) {
                    out.print("Please enter in range");
                } else {
                    break;
                }
            } while (true);
            this.choices.set(ans,inp.getInput("Enter Choice: "));
        }

        if(inp.boolQuestion("Do you want to add a choices. Y/N")) {
            this.MultiChoice++;
            this.question.add(inp.getInput("Enter a question"));
            this.choices.add(inp.getInput("Enter an choices"));

        }

    }//end modify

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


