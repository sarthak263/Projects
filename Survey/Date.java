package Survey;

import java.io.*;
import java.util.ArrayList;

public class Date extends Question implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    public Date() {
        this.create();
        this.type="Date";
    }
    @Override
    public void create() {
        this.prompt = inp.getString("Enter the prompt for your Date question: ");
    }

    @Override
    public void display() {
        out.print(this.prompt);
        out.print("A date should be entered in the following format: YYYY-MM-DD");

        out.print("");
    }

    //for the user to answer the question
    @Override
    public void userResponse() {
        this.display();
        String ans;
        //all this to have the ability for the user to only enter in format YYYY-MM-DD
        do {
            ans = inp.getInput("Answer Here");
        } while (isNotProperAnswer(ans));

        this.saveResp.add(ans);
    }

    //only need to modify the prompt just like essay and short answer
    @Override
    public void modify() {
        if(inp.boolQuestion("Do you wish to modify the prompt? Y/N")) {
            out.print(this.prompt);
            this.create();
        }
    }

    public ArrayList<String> setCorrectAnswer() {
        ArrayList<String> ans  = new ArrayList<>();
        String temp = null;
        //all this to have the ability for the user to only enter in format YYYY-MM-DD
        do {
            temp =inp.getInput("Please put your Correct Answer Here");
        } while (isNotProperAnswer(temp));
        ans.add(temp);
        return ans;
    }

    public boolean isNotProperAnswer(String ans) {
        if (ans.length() != 10) {
            out.print("Please Enter in format YYYY-MM-DD");
            return true;
        } else {
            for (int i = 0; i < ans.length(); i++) {
                if (i == 4 || i == 7) {
                    if (ans.charAt(i) != '-') {
                        out.print("Please Enter in format YYYY-MM-DD");
                        return true;
                    }
                } else {
                    if (!Character.isDigit(ans.charAt(i))) {
                        out.print("Please Enter in format YYYY-MM-DD");
                        return true;
                    }
                }
            }//end for
            return false;
        }
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
