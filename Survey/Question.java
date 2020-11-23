package Survey;

import java.io.*;
import java.util.*;

public abstract class Question implements Serializable, Comparable<Question> {

    protected String prompt;
    protected int MultiChoice;
    protected ArrayList<String> saveResp = new ArrayList<>();
    private static final long serialVersionUID = 6529685098267757690L;
    protected ConsoleInput inp = new ConsoleInput();
    protected Output out = new ConsoleOutput();
    protected String type;

    public abstract void display();
    public abstract void userResponse();
    public abstract void modify();
    public abstract  void create();
    public abstract ArrayList<String> setCorrectAnswer();

}
