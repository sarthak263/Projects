package Survey;

import java.io.Serializable;
import java.util.ArrayList;

public class Essay extends Question implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;

    public Essay() {
        this.create();
        this.type="Essay";
    }

    //this is almost same as the short answer class just doesnt have length of the answer
    @Override
    public void create() {
        this.prompt = inp.getString("Enter the prompt for your Essay question:");
        this.MultiChoice = inp.checkNumChoice("How many answers will this question have?. Please enter a number.");
        if (this.MultiChoice > 1)
            this.prompt = this.prompt + ".Please give " + this.MultiChoice + " reasons:";

    }

    @Override
    public void display() {
        System.out.println(this.prompt);
        out.print("");
    }
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
        if(inp.boolQuestion("Do you wish to modify the prompt? Y/N")) {
            out.print(this.prompt);
            this.create();
        }
    }

    @Override
    public ArrayList<String> setCorrectAnswer() {
        ArrayList<String> ans  = new ArrayList<>();
        ans.add("No Correct Answer set");
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
