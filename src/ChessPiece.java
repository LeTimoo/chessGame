public class ChessPiece {
    public String name;
    public String unicode;
    public String color;
    public String startPositionX;
    public String startPositionY;
    public Movement movement;
    public Movement capture;
}

class Movement {
    public String type;
    public int range;
    public boolean canJump;
}
