package Survey;

import java.io.File;
import java.util.ArrayList;

public class Menu {
    Output out =  new ConsoleOutput();
    ConsoleInput inp = new ConsoleInput();

    public Menu() {
        do {
            out.print("Pick One");
            String ch = inp.getInput("1) Survey\n2) Test");
            if (ch.equalsIgnoreCase("test") || ch.equalsIgnoreCase("2")) {
                TestMenu();
                break;

            }else if(ch.equalsIgnoreCase("Survey") || ch.equalsIgnoreCase("1")) {
                SurveyMenu();
                break;
            }
        }while(true);

    }
    /*this whole method is to add questions and store it to list of questions and return it*/
    private ArrayList<Question> createQuestions()  {
        out.print("Creating a Survey");
        ConsoleInput inp = new ConsoleInput();
        ArrayList<Question> q = new ArrayList<>();
        boolean flag = true;
        while(flag) {
            displayChoices();

            int choice = inp.getInt("Enter Choice: ");
            switch (choice) {
                case 1:
                    Question mc = new MultipleChoice();
                    q.add(mc);
                    break;
                case 2:
                    Question tf = new TrueFalse();
                    q.add(tf);
                    break;
                case 3:
                    Question e = new Essay();
                    q.add(e);
                    break;
                case 4:
                    Question d = new Date();
                    q.add(d);
                    break;
                case 5:
                    Question sa = new ShortAnswer();
                    q.add(sa);
                    break;
                case 6:
                    Question m = new Matching();
                    q.add(m);
                    break;
                case 7:
                    System.out.println("Finish Creating");
                    flag = false;
                    break;
                default:
                    System.out.println("Try Again");
            }
        }//end while

        return q;
    }

    public void SurveyMenu() {
        ArrayList<Question> q;
        ConsoleInput inp = new ConsoleInput();
        Survey s1= new Survey();
        Survey takenSurvey;
        Responses SurveyResp = new Responses();
        //simple menu To Create, Store, Load, Modify, Take, Display)
        out.print("Welcome, what would you like to do");
        while (true) {
            out.print("1 - Create a new Survey");
            out.print("2 - Save the current Survey");
            out.print("3 - Load an existing Survey");
            out.print("4 - Modifying the current Survey");
            out.print("5 - Take the current Survey");
            out.print("6 - Display an existing Survey");
            out.print("7 - Tabulate an existing Survey");
            out.print("8 - Quit");
            out.print("");
            int choice = inp.getInt("Enter Choice: ");
            switch (choice) {
                case 1:
                    //create
                    try {
                        //check if the object is null could happen if loaded couldn't find any files
                        if(s1 == null) {
                            s1 = new Survey();
                        }
                        q = createQuestions();
                        s1.setQuestion(q);
                    }catch (Exception e) {
                        out.print("Error setting questions. Try Again");
                    }
                    break;
                case 2:
                    //save
                    if(s1 == null) {
                        out.print("Survey file not found to save. Add questions");
                        break;
                    }
                    //second check to see if there are any questions
                    if(s1.questions.isEmpty()) {
                        out.print("Please add questions to the survey to save it or load a file");
                        break;
                    }
                    do{
                        s1.currentFile = inp.getInput("Please Enter the name of the Survey to Store it.");
                        if(s1.isExist(s1.SURVEYFOLDER)) {
                            out.print("File already Exist.");
                            continue;
                        }
                        s1.Save(s1,s1.SURVEYFOLDER);
                        break;
                    }while(true);
                    break;
                case 3:
                    //load
                    //check if file is empty if it is then there is nothing to load
                    if(s1==null) {
                        out.print("No Files Saved. Please save the file first to load.\n");
                        break;
                    }
                    s1 = (Survey) s1.Load(s1.SURVEYFOLDER);
                    if(s1 == null) {
                        out.print("Couldn't load. Please save file first.");
                    }
                    break;
                case 4:
                    //modify
                    if(s1==null) {
                        out.print("Please add questions to survey to modify.");
                        break;
                    }
                    if(s1.questions.isEmpty()) {
                        out.print("Please add questions to modify it or load a survey file.\n");
                        break;
                    }
                    s1.modify();
                    //save the file after modify if the file exist
                    if(s1.isExist(s1.SURVEYFOLDER)) {
                        s1.Save(s1,s1.SURVEYFOLDER);
                    }
                    break;
                case 5:
                    //take
                    //check if file is empty if it is return false
                    if(SurveyResp!=null) {
                        SurveyResp = (Responses) SurveyResp.Load(true);
                    }
                    if(SurveyResp==null) {
                        SurveyResp= new Responses();
                        out.print("No responses saved to be loaded");
                    }
                    if(s1 ==null) {
                        out.print("Please save the survey and add questions to take it.");
                        break;
                    }
                    takenSurvey = (Survey)s1.Load(s1.SURVEYFOLDER);
                    if(takenSurvey== null) {
                        out.print("Couldn't load file. Please save a file first.\n");
                        break;
                    }
                    String tempFile = takenSurvey.currentFile;
                    //ask to if the user wants to save the file after he has completed it
                    takenSurvey.Take(SurveyResp);
                    if(inp.boolQuestion("Would you like to save this file")) {
                        do {
                            String currFile = inp.getInput("Please enter the name of your Survey to save it");
                            takenSurvey.currentFile = currFile + "-" + tempFile;
                            String folder = takenSurvey.TAKENSURVEYFOLDER+ File.separator + tempFile;
                            createDir(folder);
                            if(takenSurvey.isExist(folder)) {
                                out.print("File already exist");
                                continue;
                            }

                            takenSurvey.Save(takenSurvey,folder);
                            SurveyResp.Save(SurveyResp,true);
                            break;
                        }while(true);
                        //flag = false;
                    }
                    break;
                case 6:
                    //display
                    if(s1 ==null) {
                        out.print("Please save the survey and add questions to display it.");
                        break;
                    }
                    if(s1.questions.isEmpty()) {
                        out.print("Please add questions to display it or load a survey file.\n");
                        break;
                    }
                    s1.display();
                    break;
                case 7:
                    //tabulate
                    if(s1==null) {
                        out.print("Please save the survey and take it to tabulate it.");
                        break;
                    }
                    if(SurveyResp==null) {
                        out.print("There are no responses");
                        break;
                    }
                    SurveyResp = (Responses) SurveyResp.Load(true);
                    if(SurveyResp!=null && SurveyResp.storedResp.size()!=0) {
                        s1.Tabulate(SurveyResp);
                        break;
                    }
                    out.print("No Response saved");
                    break;
                 case 8:
                    out.print("Quit");
                    System.exit(0);
                default:
                    out.print("Try Again");

            }//end switch

        }//end while

    }

    public void TestMenu() {
        ArrayList<Question> q;
        ConsoleInput inp = new ConsoleInput();
        Test t = new Test();
        Test takenTest = null;
        Responses testResp = new Responses();
        //simple menu To Create, Store, Load, Modify, Take, Display)
        out.print("Welcome, what would you like to do");
        while (true) {
            out.print("1 - Create a new Test");
            out.print("2 - Save the current Test");
            out.print("3 - Load an existing Test");
            out.print("4 - Modifying the current Test");
            out.print("5 - Take the current Test");
            out.print("6 - Display an existing Test without correct answers");
            out.print("7 - Display an existing Test with correct answers");
            out.print("8 - Tabulate a Test");
            out.print("9 - Grade a test");
            out.print("10 - Quit");
            out.print("");
            int choice = inp.getInt("Enter Choice: ");
            switch (choice) {
                case 1:
                    //create
                    try {
                        //check if the object is null could happen if loaded couldn't find any files
                        if(t == null) {
                            t = new Test();
                        }
                        q = createTestQuestions(t);
                        t.setQuestion(q);
                    }catch (Exception e) {
                        out.print("Error setting questions. Try Again");
                    }
                    break;
                case 2:
                    //save
                    if(t == null) {
                        out.print("Test file not found to save. Add questions");
                        break;
                    }
                    //second check to see if there are any questions
                    if(t.questions.isEmpty()) {
                        out.print("Please add questions to the Test to save it or load a file");
                        break;
                    }
                    do{
                        t.currentFile = inp.getInput("Please Enter the name of the Test to Store it.");
                        if(t.isExist(t.TESTFOLDER)) {
                            out.print("File already Exist.");
                            continue;
                        }
                        t.Save(t,t.TESTFOLDER);
                        break;
                    }while(true);
                    break;
                case 3:
                    //load
                    //check if file is empty if it is then there is nothing to load
                    if(t==null) {
                        out.print("No Files Saved. Please save the file first to load.\n");
                        break;
                    }
                    t = (Test) t.Load(t.TESTFOLDER);
                    if(t == null) {
                        out.print("Couldn't load. Please save file first.");
                    }
                    break;
                case 4:
                    //modify
                    if(t==null) {
                        out.print("Please add questions to Test to modify.");
                        break;
                    }
                    if(t.questions.isEmpty()) {
                        out.print("Please add questions to modify it or load a Test file.\n");
                        break;
                    }
                    t.modify();
                    //save the file after modify if the file exist
                    if(t.isExist(t.TESTFOLDER)) {
                        t.Save(t,t.TESTFOLDER);
                    }
                    break;
                case 5:
                    //take
                    //check if file is empty if it is return false
                    if(testResp!=null) {
                        testResp = (Responses) testResp.Load(false);
                    }
                    if(testResp==null) {
                        testResp = new Responses();
                        out.print("No responses saved to be loaded");
                    }

                    if(t ==null) {
                        out.print("Please save the Test and add questions to take it.");
                        break;
                    }
                    takenTest = (Test)t.Load(t.TESTFOLDER);


                    if(takenTest== null) {
                        out.print("Couldn't load file. Please save a file first.\n");
                        break;
                    }
                    String tempFile = takenTest.currentFile;
                    //ask to if the user wants to save the file after he has completed it
                    takenTest.Take(testResp);
                    if(inp.boolQuestion("Would you like to save this file")) {
                        do {
                            String currFile = inp.getInput("Please enter the name of your Test to save it");
                            takenTest.currentFile = currFile + "-" + tempFile;
                            String folder = takenTest.TAKENTESTFOLDER + File.separator + tempFile;
                            createDir(folder);
                            if(takenTest.isExist(folder)) {
                                System.out.println("File already exist");
                                continue;
                            }
                            takenTest.Save(takenTest,folder);
                            testResp.Save(testResp, false);
                            break;
                        }while(true);
                        //flag = false;
                    }

                    break;
                case 6:
                    //display without correct answer
                    if(t ==null) {
                        out.print("Please save the Test and add questions to display it.");
                        break;
                    }
                    if(t.questions.isEmpty()) {
                        out.print("Please add questions to display it or load a Test file.\n");
                        break;
                    }
                    t.display();
                    break;
                case 7:
                    //display with correct answer
                    if(t==null) {
                        out.print("Please save the Test and add questions to display it.");
                        break;
                    }
                    if(t.questions.isEmpty()) {
                        out.print("Please add questions to display it or load a Test file.\n");
                        break;
                    }

                    t.displayTestWithAns();
                    break;
                case 8:
                    //tabulate
                    if(t==null) {
                        out.print("Please save the Test and add questions to display it.");
                        break;
                    }
                    if(testResp==null) {
                        System.out.println("There are no responses");
                        break;
                    }
                    testResp = (Responses) testResp.Load(false);
                    if(testResp!=null && testResp.storedResp.size()!=0) {
                        t.Tabulate(testResp);
                        break;
                    }
                    out.print("No Response saved");

                    break;
                case 9:
                    //grade
                    if(t==null) {
                        out.print("No Files Saved. Please save the file first to grade.\n");
                        break;
                    }
                    t= (Test) t.Load(t.TESTFOLDER);
                    if(t==null) {
                        out.print("No Files taken for it to grade.");
                        break;
                    }
                    tempFile = t.currentFile;
                    String folder = t.TAKENTESTFOLDER + File.separator + tempFile;
                    takenTest = (Test) t.Load(folder);
                    if(takenTest==null) {
                        System.out.println("You haven't taken that test yet.");
                        break;
                    }
                    takenTest.grade();
                    break;

                case 10:
                    out.print("Quit");
                    System.exit(0);
                default:
                    out.print("Try Again");

            }//end switch

        }//end while

    }
    private void displayChoices() {
        out.print("1 - Add a new multiple-choice question");
        out.print("2 - Add a new T/F question");
        out.print("3 - Add a new essay question");
        out.print("4 - Add a new date question");
        out.print("5 - Add a new short answer question");
        out.print("6 - Add a new matching question");
        out.print("7 - Return to previous menu");
    }
    private ArrayList<Question> createTestQuestions(Test t)  {
        out.print("Creating a Test");
        t.ansSheet.correctAns.clear();
        ConsoleInput inp = new ConsoleInput();
        ArrayList<Question> q = new ArrayList<>();
        boolean flag = true;
        //Output out = new Output();
        while(flag) {
            displayChoices();
            int choice = inp.getInt("Enter Choice: ");
            switch (choice) {
                case 1:
                    Question mc = new MultipleChoice();
                    q.add(mc);
                    t.setCorrectAnswer(mc);
                    break;
                case 2:
                    Question tf = new TrueFalse();
                    q.add(tf);
                    t.setCorrectAnswer(tf);
                    break;
                case 3:
                    Question e = new Essay();
                    q.add(e);
                    t.setCorrectAnswer(e);
                    break;
                case 4:
                    Question d = new Date();
                    q.add(d);
                    t.setCorrectAnswer(d);
                    break;
                case 5:
                    Question sa = new ShortAnswer();
                    q.add(sa);
                    t.setCorrectAnswer(sa);
                    break;
                case 6:
                    Question m = new Matching();
                    q.add(m);
                    t.setCorrectAnswer(m);
                    break;
                case 7:
                    System.out.println("Finish Creating");
                    flag = false;
                    break;
                default:
                    System.out.println("Try Again");
            }
        }//end while

        return q;
    }

    public void createDir(String folder) {
        try {
            File dir = new File(folder);
            if (!dir.exists()) {
                boolean flag = dir.mkdirs();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
