package Survey;

import java.io.Serializable;

public class ConsoleOutput extends Output implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    public ConsoleOutput() {}


    public void out(String msg) {
        System.out.println(msg);
    }
}
