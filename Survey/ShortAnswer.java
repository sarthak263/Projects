package Survey;

import java.io.*;
import java.util.ArrayList;

public class ShortAnswer extends Essay implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private int AnswerLen = 50;

    public ShortAnswer() {this.type="ShortAnswer";}

    //same as essay with character length
    @Override
    public void create() {
        this.prompt = inp.getString("Enter the prompt for your Short Answer question");
        this.MultiChoice = inp.checkNumChoice("How many answers will this question have?. Please enter a number.");
        if(this.MultiChoice > 1)
            this.prompt = this.prompt + ".Please give " + this.MultiChoice + " reasons:\n";

    }
    //use to assign this.answer
    @Override
    public void userResponse() {
        this.display();
        String input;
        int i =0;
        while(i < this.MultiChoice ) {
            do {
                input = inp.getInput("Answer here:");
                if (input.length() > getAnswerLen()) {
                    out.print("Please enter a shorter answer");
                }else {
                    break;
                }
            }while(true);
            this.saveResp.add(input);
            i++;
        }
    }

    @Override
    public ArrayList<String> setCorrectAnswer() {
        ArrayList<String> ans  = new ArrayList<String>();
        for(int i =0; i < this.MultiChoice;i++) {
            ans.add(inp.getInput("Enter Correct Answer:"));
        }
        return ans;
    }
    private void setAnswerLen(int len) {
        this.AnswerLen = len;
    }
    public int getAnswerLen() {
        return this.AnswerLen;
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
