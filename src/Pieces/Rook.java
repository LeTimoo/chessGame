package Pieces;

import Enums.ColorGame;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    public int number;
    public int display;

    private List<Position> nextPossiblePosition = new ArrayList<>();

    public Rook(ColorGame type, int number){
        super(type,number);
        if(type == ColorGame.WHITE){
            this.display =0x265C;
        }
        else {
            this.display =0x2656;
        }
        this.number = number;

    }

    @Override
    public void initialize() throws Exception {
        switch (number){
            case 1:
                if(this.type== ColorGame.BLACK){
                    this.position = new Position(0,0);
                }else{
                    this.position = new Position(0,7);

                }
                break;
            case 2:
                if(this.type== ColorGame.BLACK){
                    this.position = new Position(7,0);
                }else{
                    this.position = new Position(7,7);
                }
                break;
            default:
                throw new Exception();

        }
        this.computeNextPossibleMoove();
    }
    //modifier cette fonction j'ai fais 4 for alors que je peux en faire qu'un
    public void computeNextPossibleMoove(){
        for(int i=1;i<8;i++){
            Position pos = new Position(this.position.x,this.position.y+i);
            if(pos.isInsideChessBoard()){
                this.nextPossiblePosition.add(pos);
            }
        }
        for(int i=1;i<8;i++){
            Position pos = new Position(this.position.x,this.position.y-i);
            if(pos.isInsideChessBoard()){
                this.nextPossiblePosition.add(pos);
            }
        }
        for(int i=1;i<8;i++){
            Position pos = new Position(this.position.x+i,this.position.y);
            if(pos.isInsideChessBoard()){
                this.nextPossiblePosition.add(pos);
            }
        }
        for(int i=1;i<8;i++){
            Position pos = new Position(this.position.x-i,this.position.y);
            if(pos.isInsideChessBoard()){
                this.nextPossiblePosition.add(pos);
            }
        }
    }

    @Override
    public boolean isNextMooveAvailable(List<Piece> piecesPosition, Position currentPosition, Position nextMoove) {
        int bottomMax=7;
        int topMax=0;
        int leftMax=0;
        int RightMax=7;
        for(Piece piece :piecesPosition){
            if(!piece.position.equals(this.position)){
                if(this.position.x==piece.position.x){
                    if(piece.position.y<= bottomMax && piece.position.y>this.position.y){
                        if(this.type==piece.type){
                            bottomMax=piece.position.y-1;
                        }else{
                            bottomMax=piece.position.y;
                        }
                    }
                    if(piece.position.y>=topMax && piece.position.y<this.position.y){
                        if(this.type==piece.type){
                            topMax=piece.position.y+1;
                        }else{
                            topMax=piece.position.y;
                        }
                    }
                }
                if(this.position.y==piece.position.y){
                    if(piece.position.x>=leftMax && piece.position.x<this.position.x){
                        if(this.type==piece.type){
                            leftMax=piece.position.x+1;
                        }else{
                            leftMax=piece.position.x;
                        }
                    }
                    if(piece.position.x<=RightMax && piece.position.x>this.position.x){
                        if(this.type==piece.type){
                            RightMax=piece.position.x-1;
                        }else{
                            RightMax=piece.position.x;
                        }
                    }
            }
            }
        }
        if((nextMoove.y<=bottomMax && nextMoove.y>=topMax) && this.position.x==nextMoove.x){
            if(this.nextPossiblePosition.contains(nextMoove)){
                return true;
            }
        }
        if((nextMoove.x>=leftMax && nextMoove.x<=RightMax) && this.position.y==nextMoove.y){
            if(this.nextPossiblePosition.contains(nextMoove)){
                return true;
            }
        }
        //for debug
        //System.out.println("topMax: "+topMax+ " bottomMax: "+ bottomMax +" leftMax: "+leftMax+" RightMax : "+RightMax);
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