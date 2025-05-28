package Pieces;

import Enums.ColorGame;

import java.util.List;

public class Piece {
    public boolean active;
    public ColorGame type;
    public Position position;
    public int display;

    public Piece(ColorGame type, int display){
        this.active=true;
        this.type=type;
    }

    public void initialize() throws Exception {

    }

    public boolean isNextMooveAvailable(List<Piece> piecesPosition, Position currentPosition,Position nextMoove){
        return false;
    }


    public void update(Position position){

    }

    public int getDisplay() {
        return display;
    }
}
