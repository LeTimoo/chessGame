package Pieces;

import Enums.ColorGame;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public int display;
    private List<Position> nextPossiblePosition = new ArrayList<>();

    public King(ColorGame type){
        super(type,0);
        if(type == ColorGame.WHITE){
            this.display =0x265A;
        }
        else {
            this.display =0x2654;
        }

    }

    @Override
    public void initialize() throws Exception {
        switch (type){
            case ColorGame.WHITE:
                this.position = new Position(4,7);
                break;
            case ColorGame.BLACK:
                this.position = new Position(4,0);
                break;
            default:
                throw new Exception();

        }
        this.computeNextPossibleMoove();
    }

    public void computeNextPossibleMoove(){
        List<Position> possibleMoove = new ArrayList<>();
        Position top = new Position(this.position.x,this.position.y-1);
        Position bottom = new Position(this.position.x,this.position.y+1);
        Position right = new Position(this.position.x+1,this.position.y);
        Position left = new Position(this.position.x-1,this.position.y);
        Position topRight = new Position(this.position.x+1,this.position.y-1);
        Position topLeft = new Position(this.position.x-1,this.position.y-1);
        Position bottomRight = new Position(this.position.x+1,this.position.y+1);
        Position bottomLeft = new Position(this.position.x-1,this.position.y+1);
        possibleMoove.add(top);
        possibleMoove.add(bottom);
        possibleMoove.add(right);
        possibleMoove.add(left);
        possibleMoove.add(topRight);
        possibleMoove.add(topLeft);
        possibleMoove.add(bottomRight);
        possibleMoove.add(bottomLeft);
        possibleMoove.forEach(pos->{
            if(pos.isInsideChessBoard()){
                this.nextPossiblePosition.add(pos);
            }
        });
    }

    @Override
    public boolean isNextMooveAvailable(List<Piece> piecesPosition, Position currentPosition, Position nextMoove) {
        //rajouter la logic MAT , coup illégal , PAT
        for(Piece piece : piecesPosition){
            if(piece.position.equals(nextMoove) && piece.type == this.type){
                return false;
            }
        }
        if(this.nextPossiblePosition.contains(nextMoove)){
            return true;
        }
        return false;
    }

    @Override
    public void update(Position position) {
        this.nextPossiblePosition.clear();
        this.position=position;
        this.computeNextPossibleMoove();
    }

    @Override
    public int getDisplay(){
        return this.display;
    }

}