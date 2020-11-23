package ChessGame;

public class Player {
    private ColorEnum color;
    public Player(ColorEnum color){
        this.color = color;
    }
    public ColorEnum getColor(){
        return color;
    }
}
