package Survey;

import java.io.Serializable;
import java.util.*;

public class AnswerSheet implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    protected ArrayList<ArrayList<String>> correctAns = new ArrayList<>();

    public AnswerSheet() {};

    //returns answer per question
    public ArrayList<String> getAnswer(int num) {
        return correctAns.get(num);
    }

    //adds answer saves in the list
    public void addAnswer(ArrayList<String> r) {
        correctAns.add(r);
    }

}
