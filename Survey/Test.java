package Survey;

import java.io.File;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Test extends Survey implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    protected final String TESTFOLDER = "TestFiles";
    protected final String TAKENTESTFOLDER = "TestTakenFiles";
    protected AnswerSheet ansSheet = new AnswerSheet();
    protected double grade = 0;
    private static DecimalFormat df2 = new DecimalFormat("#.#");

    public Test() {
        this.createDir();
        this.ansSheet.correctAns.clear();
    }

    //creates a directories for tests
    private void createDir() {
        try {
            File dir = new File(this.TESTFOLDER);
            if (!dir.exists()) {
                boolean flag = dir.mkdirs();
            }
            dir = new File(this.TAKENTESTFOLDER);
            if (!dir.exists()) {
                boolean flag = dir.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //sets the correct answers for each question and stores it in answer Sheet class
    public void setCorrectAnswer(Question q) {
        ArrayList<String> r;
        r = q.setCorrectAnswer();
        if (r == null) {
            System.out.println("Cannot set correct answer.");
            return;
        }
        this.ansSheet.addAnswer(r);
    }

    //display test with the answers
    public void displayTestWithAns() {
        if (this.questions.isEmpty()) {
            out.print("No Questions added yet");
            return;
        }
        for (int i = 0; i < this.questions.size(); i++) {
            out.print("Question " + (i + 1) + ". ");
            this.questions.get(i).display();
            out.print("Answer: ");
            if(this.questions.get(i).type.equals("Matching")) {
                displayAns(i,true);
            }else {
                displayAns(i,false);
            }
            out.print("");
        }
    }

    //modifies the question and all has the ability to modify the answers.
    @Override
    public int modify() {
        int num = super.modify();
        while (input.boolQuestion("Would you like to change the correct answer? Y/N")) {
            if (this.questions.get(num).type.equals("Matching")){
                displayAns(num, true);
            }else {
                displayAns(num, false);
            }
            if (getAns(num).size() > 0) {
                if (this.questions.get(num).MultiChoice > 1) {
                    int choice = input.checkNumChoice("Which answer would you like to change") - 1;
                    this.ansSheet.correctAns.get(num).set(choice, input.getInput("Enter your new Answer"));
                    out.print("Answer Changed. You can do this again.");
                } else if (this.questions.get(num).MultiChoice == 1) {
                    this.ansSheet.correctAns.get(num).set(0, input.getInput("Enter your new Answer"));
                    out.print("Answer Changed");
                    break;
                }
            }
        }
        return 0;
    }

    //helper method to display the answers
    private void displayAns(int num, boolean isMatching) {
        if(isMatching) {
            char ch='A';
            for(String s: getAns(num)) {
                out.print(ch + ") " + s);
                ch++;
            }
        }else {
            for (String s : getAns(num)) {
                out.print(s);
            }
        }
    }

    //helper method to get the answer from answer sheet class
    private ArrayList<String> getAns(int num) {
        return this.ansSheet.correctAns.get(num);
    }

    //calls the survey take class
    @Override
    public void Take(Responses r1) {
        super.Take(r1);
    }

    //grades the question
    public void grade() {
        int numEssay = 0;
        this.grade =0;
        boolean right;
        double questionPoint =0;
        //stores up to 2 decimal places
        questionPoint = Double.parseDouble(String.format("%.2f",(double)100/this.questions.size()));
        double gradedPoints = 0;
        for (int i = 0; i < this.questions.size(); i++) {
            right = false;
            for (int j = 0; j < getAns(i).size(); j++) {
                //disregards the essay questions
                if (getAns(i).get(j).equals("No Correct Answer set")) {
                    //essay question
                    out.print("Essay question");
                    numEssay++;
                    break;
                }
                if (this.questions.get(i).saveResp.get(j).equalsIgnoreCase(getAns(i).get(j))) {
                    right = true;
                }else {
                    right = false;
                    break;
                }
            }
            if(right) {
                grade+=questionPoint;
            }
        }
        //calculates the grade without the essays in the questions
        gradedPoints =  100-numEssay*questionPoint;

        if (numEssay !=0) {
            out.print("You received an "+ Double.toString(grade)+ " on the test. The test was worth 100 points, but only "
                    + Double.toString(gradedPoints) + " of those points could be auto graded because there was " +
                      Integer.toString(numEssay) + " essay question.");
        } else {
            out.print("You received an "+ Double.toString(grade)+ " on the test. The test was worth 100 points");
        }
    }
}
