package Survey;

import java.io.*;
import java.util.ArrayList;

public class TrueFalse extends MultipleChoice implements  Serializable{
    private static final long serialVersionUID = 6529685098267757690L;

    public TrueFalse(String prompt) {
        this.prompt = prompt;
        this.MultiChoice=1;
        this.type="TrueFalse";
    }
    public TrueFalse() {this.MultiChoice=1;this.type="TrueFalse";}

    @Override
    public void create() {
        this.prompt = inp.getString("Enter the prompt for your True/False question");
        this.choices.add("A) True");
        this.choices.add("B) False");
        out.print("");
    }
    @Override
    public void modify() {
        if(inp.boolQuestion("Do you wish to modify the prompt? Y/N")) {
            out.print(this.prompt);
            this.prompt =  inp.getString("Enter a new prompt:");
        }
    }
    @Override
    public void display() {
        out.print(this.prompt);
        out.print(this.choices.get(0));
        out.print(this.choices.get(1));
        out.print("");
    }
    //have display method
    @Override
    public void userResponse() {
        this.display();
        this.saveResp.add(inp.getBoolean("Answer here:"));

    }

    @Override
    public ArrayList<String> setCorrectAnswer() {
        ArrayList<String> ans  = new ArrayList<String>();
        ans.add(inp.getBoolean("Input your Correct Answer here: "));
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
