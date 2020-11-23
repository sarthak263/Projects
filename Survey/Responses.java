package Survey;

import java.io.*;
import java.util.*;

public class Responses implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    protected HashMap<String,ArrayList<Question>> storedResp = new HashMap<>();
    protected String RESPONSEFOLDER = "Responses";
    protected String currentTestRespFile = "listTestResp";
    protected String currentSurveyRespFile = "listSurveyResp";
    Output out = new ConsoleOutput();

    public Responses() {this.createDir();}

    //adds to storedResp
    public void addHashPrompt(String prompt, Question q) {
        if (!KeyExist(prompt, q)) {
            storedResp.putIfAbsent(prompt, new ArrayList<Question>());
        }
        storedResp.get(prompt).add(q);
    }

    //checks if the key exist
    public boolean KeyExist(String prompt, Question q) {
        for(Map.Entry<String, ArrayList<Question>> entry: storedResp.entrySet()) {
            String key = entry.getKey();
            if(key.equals(q.prompt)) {
                return true;
            }
        }
        return false;
    }

    //loads the response
    public Object Load(boolean isSurvey) {
        //this will load by using the file user has selected
        File file = null;
        try {
            if(isSurvey) {
                 file = new File(returnPath(RESPONSEFOLDER, this.currentSurveyRespFile));
            }else {
                 file = new File(returnPath(RESPONSEFOLDER, this.currentTestRespFile));
            }
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return ois.readObject();
        }catch (Exception e)  {
            out.print("Error Loading the file." + e);
        }
        return null;
    }

    //save the file by using this.currentFile
    public void Save(Object obj, boolean isSurvey) {
        File file = null;
        try {
            if(isSurvey) {
                file = new File(returnPath(RESPONSEFOLDER, this.currentSurveyRespFile));
            }else {
                file = new File(returnPath(RESPONSEFOLDER, this.currentTestRespFile));
            }
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.close();
        }catch(Exception e) {
            out.print("Error Saving file" + e);
        }
    }
    public String returnPath(String dir, String fileName) {
        return dir + File.separator + fileName;
    }

    private void createDir() {
        try {
            File dir = new File(this.RESPONSEFOLDER);
            if (!dir.exists()) {
                boolean flag = dir.mkdirs();

            }
            File f = new File(this.RESPONSEFOLDER + File.separator + this.currentTestRespFile);
            if(!f.exists()) {
                boolean flag = f.createNewFile();
            }
            File f2 = new File(this.RESPONSEFOLDER + File.separator + this.currentSurveyRespFile);
            if(!f2.exists()) {
                boolean flag = f2.createNewFile();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
