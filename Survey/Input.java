package Survey;

import java.io.*;

public abstract class Input implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    protected String word;
    protected int num;

    public abstract String getString(String msg);
    public abstract int getInt(String msg);
}
