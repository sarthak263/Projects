package Survey;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Survey implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    protected String currentFile;
    protected final String SURVEYFOLDER = "surveyFiles";
    protected final String TAKENSURVEYFOLDER = "takenSurveyFiles";
    protected ArrayList<Question> questions = new ArrayList<Question>();
    protected Output out = new ConsoleOutput();
    protected HashMap<Question, Integer> sameResp = new HashMap<>();



    //this will used mainly when there is a user input to do all the error checking
    protected ConsoleInput input = new ConsoleInput();

    /*used this to store object and load objects with ease*/
    public Survey(String currentFile, ArrayList<Question> questions) {
        this.questions = questions;
        this.currentFile = currentFile;
        this.createDir();
    }
    //Empty Constructer
    public Survey() {this.createDir();}
    public void setQuestion(ArrayList<Question> questions) {
        this.questions = questions;
    }
    public ArrayList<Question> getQuestion() {return this.questions;}
    public String getCurrentFile() {return this.currentFile;}
    public String returnPath(String dir, String fileName) {
        return dir + File.separator + fileName;
    }

    //creates Directories surveyfolder and takensurveyfolder
    private void createDir() {
        try {
            File dir = new File(this.SURVEYFOLDER);
            if (!dir.exists()) {
                boolean flag = dir.mkdirs();
            }
            dir = new File(this.TAKENSURVEYFOLDER);
            if(!dir.exists()) {
                boolean flag = dir.mkdirs();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //list the files in the directory
    public File[] listFiles(String directory) {
        try {
            File folder = new File(directory);
            return folder.listFiles();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //checks if the file exist in the directory
    public boolean isExist(String directory) {
        try {
            File file = new File(returnPath(directory, this.currentFile));
            return file.exists();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //displays all the questions in the survey
    public void display() {
        for(int i =0; i < this.questions.size();i++) {
            out.print("Question " +(i+1) + ". " );
            this.questions.get(i).display();
        }
    }

    //displays all the questions and ask the user which question to modify
    public int modify() {
        int num;
        this.display();
        do {
            num = input.checkNumChoice("Which question number would you like to modify?") - 1;
            if (num >= this.questions.size()) {
                out.print("Please select in range");
                continue;

            }
            this.questions.get(num).modify();
            break;
        }while(true);
        return num;
    }

    //simply ask the user for the answer to the related questions and assign it and stores it in response class
    // response class would have saved all the responses so it mostly used for tabulation purposes
    public void Take(Responses r1) {

        for(int i =0; i < this.questions.size();i++) {
            out.print("Question " + (i + 1) + ". ");
            this.questions.get(i).userResponse();
            out.print("");
        }
        try {
            for (Question question : this.questions)
                r1.addHashPrompt(question.prompt, question);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //tabulates all the responses
    public void Tabulate(Responses r1) {

        //first it prints out all the responses per question
        for(Map.Entry<String, ArrayList<Question>> entry : r1.storedResp.entrySet()) {
            boolean flag = true;
            int numSameValue =0;
            for(Question q: entry.getValue()) {
                numSameValue++;
                if(flag) {

                    q.display();
                    out.print("Responses:");
                    flag = false;
                }
                if(q.type.equals("Matching")) {
                    char ch = 'A';
                    for (String s : q.saveResp) {
                        out.print(ch + ") " + s);
                        ch++;
                    }
                }else {
                    for (String s : q.saveResp) {
                        out.print(s);
                    }
                }
            }

            out.print("");
        }
        //counts the same response it and maps it to the question
        ArrayList<Integer> savedIndex = new ArrayList<>();
        for(Map.Entry<String, ArrayList<Question>> entry: r1.storedResp.entrySet()) {
            ArrayList<Question> q = entry.getValue();
            Question temp = null;
            int count =1;
            if(q.size()==1) {
                sameResp.put(q.get(0),1);
            }else {
               for(int i=0; i < q.size(); i++) {
                   if(savedIndex.contains(i)) {
                       continue;
                   }
                   if(i==q.size()-1) {
                       if(!savedIndex.contains(i))
                            sameResp.put(q.get(i),1);
                       break;
                   }
                   for(int j=i+1; j < q.size(); j++) {
                       temp = q.get(i);
                       if(!savedIndex.contains(j)) {
                           if (isRespSame(q.get(i), q.get(j))) {
                               savedIndex.add(j);
                               count++;
                           }
                       }
                   }
                   sameResp.put(temp,count);
                   count=1;
               }
            }
            savedIndex.clear();
        }
        out.print("Tabulation");
        //used compareTo method in question classes to order it in alphabetical order
        HashMap<Question, Integer> result = sameResp.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        String type = null;
        String prompt = null;
        //prints out the result, which are number of same responses per questions
        for(Map.Entry<Question, Integer> entry: result.entrySet()) {
            Question key = entry.getKey();
            if(!key.type.equals(type)){
                key.display();
                type = key.type;
                prompt = key.prompt;
            }else {
                if(!key.prompt.equals(prompt)) {
                    key.display();
                    type = key.type;
                    prompt = key.prompt;
                }
            }

            out.print("Number of same Response: " + Integer.toString(entry.getValue()));
            for(String s: key.saveResp) {
                out.print(s);
            }
            out.print("");

        }
        result.clear();
        sameResp.clear();
    }

    //used to count if the response is the same for tabulation
    public boolean isRespSame(Question q1, Question q2) {
        for(String s: q1.saveResp) {
            if(!q2.saveResp.contains(s)) {
                return false;
            }
        }
        return true;
    }

    //load the object by passing all the saved non taken survey files
    public Object Load(String directory) {
        int fileChoice;
        int count = 1;
        File[] listFiles = this.listFiles(directory);
        if(listFiles==null) {
            return null;
        }
        if(listFiles.length== 0) {
            return null;
        }
        for(File f: listFiles) {
            if(f.isFile()) {
                out.print(count + ". "+ f.getName());
                count++;
            }
        }
        do {
            fileChoice = input.checkNumChoice("Which File: ")-1;
            if(fileChoice>=listFiles.length)
                out.print("Please choose in range");
            else
                break;
        }while (true);
        //this will load by using the file user has selected
        try {
            File file = new File(String.valueOf(listFiles[fileChoice]));
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return ois.readObject();
        }catch (Exception e)  {
           out.print("Error Loading the file." + e);
        }
        return null;
    }


    //save the file by using this.currentfile
    public void Save(Object obj,String directory) {
        try {
            File file = new File(returnPath(directory,this.currentFile));
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.close();
        }catch(Exception e) {
            out.print("Error Saving file" + e);
        }
    }

}
