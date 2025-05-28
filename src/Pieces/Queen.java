package Pieces;

import Enums.ColorGame;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {
    public int display;
    private List<Position> nextPossiblePosition = new ArrayList<>();

    public Queen(ColorGame type){
        super(type,0);
        if(type == ColorGame.WHITE){
            this.display =0x265B;
        }
        else {
            this.display =0x2655;
        }

    }

    @Override
    public void initialize() throws Exception {
        switch (type){
            case WHITE:
                this.position = new Position(3,7);
                break;
            case BLACK:
                this.position = new Position(3,0);
                break;
            default:
                throw new Exception();

        }
        this.computeNextPossibleMoove();
    }
    public void computeNextPossibleMoove(){
        this.computeBishopNextPossibleMoove();
        this.computeRookNextPossibleMoove();
    }

    public void computeBishopNextPossibleMoove(){
        List<Position> possiblePos = new ArrayList<>();
        for(int i=0;i<8;i++){
            Position topRight = new Position(this.position.x+i,this.position.y+i);
            Position bottomRight = new Position(this.position.x+i,this.position.y-i);
            Position bottomLeft = new Position(this.position.x-i,this.position.y-i);
            Position topLeft = new Position(this.position.x-i,this.position.y+i);
            possiblePos.add(topRight);
            possiblePos.add(topLeft);
            possiblePos.add(bottomRight);
            possiblePos.add(bottomLeft);
        }
        for(Position pos : possiblePos){
            if(pos.isInsideChessBoard()){
                this.nextPossiblePosition.add(pos);
            }
        }
    }

    public void computeRookNextPossibleMoove(){
        for(int i=1;i<8;i++){
            Position pos = new Position(this.position.x,this.position.y+i);
            Position pos1 = new Position(this.position.x,this.position.y-i);
            Position pos2 = new Position(this.position.x-i,this.position.y);
            Position pos3 = new Position(this.position.x+i,this.position.y);
            if(pos.isInsideChessBoard()){
                this.nextPossiblePosition.add(pos);
            }
            if(pos1.isInsideChessBoard()){
                this.nextPossiblePosition.add(pos1);
            }
            if(pos2.isInsideChessBoard()){
                this.nextPossiblePosition.add(pos2);
            }
            if(pos3.isInsideChessBoard()){
                this.nextPossiblePosition.add(pos3);
            }
        }
    }


    @Override
    public boolean isNextMooveAvailable(List<Piece> piecesPosition, Position currentPosition, Position nextMoove) {
        //queen possibilities is equal to rook + bishop possibilities
        boolean isAvailable = this.bishopMovementAvailable(piecesPosition,currentPosition,nextMoove) || this.rookMovementAvailable(piecesPosition,currentPosition, nextMoove);
        return isAvailable;
    }

    public boolean bishopMovementAvailable(List<Piece> piecesPosition, Position currentPosition, Position nextMoove){
        int bottomLeftStepMax=0;
        int bottomRightStepMax=0;
        int topLeftStepMax=0;
        int topRightStepMax=0;
        Position bottomLeftMax=null;
        Position bottomRightMax = null;
        Position topLeftMax = null;
        Position topRightMax = null;
        if(this.position.y>this.position.x){
            bottomLeftStepMax=this.position.y;
        }
        else{
            bottomLeftStepMax=this.position.x;
        }
        if(this.position.y>(7-this.position.x)){
            bottomRightStepMax=this.position.y;
        }else{
            bottomRightStepMax=7-this.position.x;
        }if((7-this.position.y)>this.position.x){
            topLeftStepMax=7-this.position.y;
        }else{
            topLeftStepMax=this.position.x;
        }
        if((7-this.position.y>(7-this.position.x))){
            topRightStepMax=7-this.position.y;
        }else{
            topRightStepMax=7-this.position.x;
        }
        for(int i=1;i<bottomLeftStepMax;i++){
            for(Piece piece: piecesPosition){
                if(piece.position.equals(new Position(this.position.x-i,this.position.y-i))){
                    if(piece.type==this.type){
                        bottomLeftMax = new Position(this.position.x-i+1,this.position.y-i+1);
                    }else{
                        bottomLeftMax = new Position(this.position.x-i,this.position.y-i);
                    }
                    break;
                }
            }
            if(bottomLeftMax!=null){
                break;
            }
        }
        for (int i = 1; i <= bottomRightStepMax; i++) {
            for (Piece piece : piecesPosition) {
                if (piece.position.equals(new Position(this.position.x + i, this.position.y - i))) {
                    if (piece.type == this.type) {
                        bottomRightMax = new Position(this.position.x + i - 1, this.position.y - i + 1);
                    } else {
                        bottomRightMax = new Position(this.position.x + i, this.position.y - i);
                    }
                    break;
                }
            }
            if (bottomRightMax != null) break;
        }
        for (int i = 1; i <= topLeftStepMax; i++) {
            for (Piece piece : piecesPosition) {
                if (piece.position.equals(new Position(this.position.x - i, this.position.y + i))) {
                    if (piece.type == this.type) {
                        topLeftMax = new Position(this.position.x - i + 1, this.position.y + i - 1);
                    } else {
                        topLeftMax = new Position(this.position.x - i, this.position.y + i);
                    }
                    break;
                }
            }
            if (topLeftMax != null) break;
        }
        for (int i = 1; i <= topRightStepMax; i++) {
            for (Piece piece : piecesPosition) {
                if (piece.position.equals(new Position(this.position.x + i, this.position.y + i))) {
                    if (piece.type == this.type) {
                        topRightMax = new Position(this.position.x + i - 1, this.position.y + i - 1);
                    } else {
                        topRightMax = new Position(this.position.x + i, this.position.y + i);
                    }
                    break;
                }
            }
            if (topRightMax != null) break;
        }
        if (bottomLeftMax == null) {
            bottomLeftMax = new Position(this.position.x - bottomLeftStepMax, this.position.y - bottomLeftStepMax);
        }if (bottomRightMax == null) {
            bottomRightMax = new Position(this.position.x + bottomRightStepMax, this.position.y - bottomRightStepMax);
        }if (topLeftMax == null) {
            topLeftMax = new Position(this.position.x - topLeftStepMax, this.position.y + topLeftStepMax);
        }if (topRightMax == null) {
            topRightMax = new Position(this.position.x + topRightStepMax, this.position.y + topRightStepMax);
        }

        if (nextMoove.x <= this.position.x && nextMoove.y <= this.position.y &&
                nextMoove.x >= bottomLeftMax.x && nextMoove.y >= bottomLeftMax.y) {
            if (this.nextPossiblePosition.contains(nextMoove)) {
                return true;
            }
        }
        if (nextMoove.x >= this.position.x && nextMoove.y <= this.position.y &&
                nextMoove.x <= bottomRightMax.x && nextMoove.y >= bottomRightMax.y) {
            if (this.nextPossiblePosition.contains(nextMoove)) {
                return true;
            }
        }

        if (nextMoove.x <= this.position.x && nextMoove.y >= this.position.y &&
                nextMoove.x >= topLeftMax.x && nextMoove.y <= topLeftMax.y) {
            if (this.nextPossiblePosition.contains(nextMoove)) {
                return true;
            }
        }

        if (nextMoove.x >= this.position.x && nextMoove.y >= this.position.y &&
                nextMoove.x <= topRightMax.x && nextMoove.y <= topRightMax.y) {
            if (this.nextPossiblePosition.contains(nextMoove)) {
                return true;
            }
        }



        return false;
    }

    public boolean rookMovementAvailable(List<Piece> piecesPosition, Position currentPosition, Position nextMoove){
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