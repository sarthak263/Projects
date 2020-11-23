package Survey;

import java.io.Serializable;

public abstract class Output implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    public Output() {}

    public void print(String msg) {
        System.out.println(msg);
    }
}
