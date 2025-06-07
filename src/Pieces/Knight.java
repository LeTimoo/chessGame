package Pieces;

import Enums.ColorGame;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    public int number;
    public int display;

    private List<Position> nextPossiblePosition = new ArrayList<>();

    public Knight(ColorGame type, int number){
        super(type,number);
        if(type == ColorGame.WHITE){
            this.name="w_knight.png";
            this.display =0x265E;
        }
        else {
            this.name="b_knight.png";
            this.display =0x2658;
        }
        this.number = number;

    }

    @Override
    public void initialize() throws Exception {
        switch (number){
            case 1:
                if(this.type== ColorGame.BLACK){
                    this.position = new Position(1,0);
                }else{
                    this.position = new Position(1,7);
                }
                break;
            case 2:
                if(this.type== ColorGame.BLACK){
                    this.position = new Position(6,0);
                }else{
                    this.position = new Position(6,7);
                }
                break;
            default:
                throw new Exception();

        }
        this.computeNextPossibleMoove();
    }

    public void computeNextPossibleMoove(){
        List<Position> possibleMoove = new ArrayList<>();
        Position topLeft = new Position(this.position.x-1,this.position.y+2);
        Position topRight = new Position(this.position.x+1,this.position.y+2);
        Position BottomLeft = new Position(this.position.x-1,this.position.y-2);
        Position BottomRight = new Position(this.position.x+1,this.position.y-2);
        Position LeftTop = new Position(this.position.x-2,this.position.y+1);
        Position LeftBottom = new Position(this.position.x-2,this.position.y-1);
        Position RightTop = new Position(this.position.x+2,this.position.y+1);
        Position RightBottom = new Position(this.position.x+2,this.position.y-1);
        possibleMoove.add(topLeft);
        possibleMoove.add(topRight);
        possibleMoove.add(BottomLeft);
        possibleMoove.add(BottomRight);
        possibleMoove.add(LeftTop);
        possibleMoove.add(LeftBottom);
        possibleMoove.add(RightTop);
        possibleMoove.add(RightBottom);
        possibleMoove.forEach(pos->{
            if(pos.isInsideChessBoard()){
                this.nextPossiblePosition.add(pos);
            }
        });

    }

    @Override
    public boolean isNextMooveAvailable(List<Piece> piecesPosition, Position currentPosition, Position nextMoove) {
        for(Piece piece: piecesPosition){
            if(piece.position.equals(nextMoove) && piece.type==this.type){
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

    //patern prototype
    @Override
    public Piece clone() {
        Knight clone = new Knight(this.type,this.number);
        clone.active=this.active;
        clone.position = this.position;
        clone.computeNextPossibleMoove();
        return clone;
    }

}