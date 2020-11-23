package ChessGame;

public enum ColorEnum {
        BLUE(1), BLACK(2);
    private int id;
    private ColorEnum(int id){
        this.id = id;
    }
    public int getID(){
        return id;
    }
}
