package Pieces;

import Enums.ColorGame;

import java.util.List;

public abstract class  Piece {
    public boolean active;
    public ColorGame type;
    public Position position;
    public int display;
    public String name;

    public Piece(ColorGame type, int display){
        this.active=true;
        this.type=type;
    }

    public void initialize() throws Exception {

    }

    public boolean isNextMooveAvailable(List<Piece> piecesPosition, Position currentPosition,Position nextMoove){
        return false;
    }

    //implémentation du pattern prototype pour gérer le calcul de l'échec du roi en utilisant une copy des pieces et de la liste de pièce
    public abstract Piece clone();

    public void update(Position position){

    }

    public int getDisplay() {
        return display;
    }
}
