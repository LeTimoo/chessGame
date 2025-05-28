package Pieces;

public class Position {
    public int x;
    public int y;

    public Position(int x, int y){
        this.x=x;
        this.y=y;
    }

    public boolean isInsideChessBoard(){
        if((this.x>=0 && this.x<=7) && (this.y>=0 && this.y<=7 )){
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Position){
            return this.x==((Position) obj).x && this.y==((Position) obj).y;
        }
        return false;
    }
}
