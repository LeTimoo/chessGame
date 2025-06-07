package Pieces;

import Enums.ColorGame;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    public int number;
    public int display;
    private boolean isFirstMoove;
    private List<Position> nextPossiblePosition = new ArrayList<>();
    private List<Position> attackingPosition =new ArrayList<>();

    public Pawn(ColorGame type, int number){
        super(type,number);
        if(type == ColorGame.WHITE){
            this.name="w_pawn.png";
            this.display =0x265F;
        }
        else {
            this.name="b_pawn.png";
            this.display =0x2659;
        }
        this.number = number;
        this.isFirstMoove=true;

    }

    @Override
    public void initialize() throws Exception {
        switch (number){
            case 1:
                if(this.type== ColorGame.BLACK){
                    this.position = new Position(0,1);
                }else{
                    this.position = new Position(0,6);
                }
                break;
            case 2:
                if(this.type== ColorGame.BLACK){
                    this.position = new Position(1,1);
                }else{
                    this.position = new Position(1,6);
                }
                break;
            case 3:
                if(this.type== ColorGame.BLACK){
                    this.position = new Position(2,1);
                }else{
                    this.position = new Position(2,6);
                }
                break;
            case 4:
                if(this.type== ColorGame.BLACK){
                    this.position = new Position(3,1);
                }else{
                    this.position = new Position(3,6);
                }
                break;
            case 5:
                if(this.type== ColorGame.BLACK){
                    this.position = new Position(4,1);
                }else{
                    this.position = new Position(4,6);
                }
                break;
            case 6:
                if(this.type== ColorGame.BLACK){
                    this.position = new Position(5,1);
                }else{
                    this.position = new Position(5,6);
                }
                break;
            case 7:
                if(this.type== ColorGame.BLACK){
                    this.position = new Position(6,1);
                }else{
                    this.position = new Position(6,6);
                }
                break;
            case 8:
                if(this.type== ColorGame.BLACK){
                    this.position = new Position(7,1);
                }else{
                    this.position = new Position(7,6);
                }
                break;
            default:
                throw new Exception();

        }
        this.computeNextPossibleMoove();
        this.computePotentialAttackingPosition();
    }

    //ne prend pas encore en compte la prise en passant et la promotion sur dernière ligne
    public void computeNextPossibleMoove(){
       if(this.position.y <= 7 && this.position.y>=0){
           if(this.type == ColorGame.WHITE){
               this.nextPossiblePosition.add(new Position(this.position.x,this.position.y-1));
               if(this.isFirstMoove){
                   this.nextPossiblePosition.add(new Position(this.position.x,this.position.y-2));
               }
           }else{
               this.nextPossiblePosition.add(new Position(this.position.x,this.position.y+1));
               if(this.isFirstMoove){
                   this.nextPossiblePosition.add(new Position(this.position.x,this.position.y+2));
               }
           }
       }
    }

    public void computePotentialAttackingPosition(){
        if(this.position.y <= 7 && this.position.y>=0){
            if(this.type == ColorGame.WHITE){
                if(this.position.x == 0){
                    this.attackingPosition.add(new Position(this.position.x+1,this.position.y-1));
                }
                else if(this.position.x == 7){
                    this.attackingPosition.add(new Position(this.position.x-1,this.position.y-1));
                }
                else{
                    this.attackingPosition.add(new Position(this.position.x+1,this.position.y-1));
                    this.attackingPosition.add(new Position(this.position.x-1,this.position.y-1));
                }
            }else{
                if(this.position.x == 0){
                    this.attackingPosition.add(new Position(this.position.x+1,this.position.y+1));
                }
                else if(this.position.x == 7){
                    this.attackingPosition.add(new Position(this.position.x-1,this.position.y+1));
                }
                else{
                    this.attackingPosition.add(new Position(this.position.x+1,this.position.y+1));
                    this.attackingPosition.add(new Position(this.position.x-1,this.position.y+1));
                }
            }

        }
    }

    @Override
    public void update(Position position) {
        if(isFirstMoove){
            isFirstMoove=false;
        }
        this.attackingPosition.clear();
        this.nextPossiblePosition.clear();
        this.position=position;
        this.computeNextPossibleMoove();
        this.computePotentialAttackingPosition();
    }

    @Override
    public boolean isNextMooveAvailable(List<Piece> piecesPosition, Position current,Position nextMoove) {
        //pour plus de lisibilité peut-être scindé en deux la partie vérification que nextMoove est parmis les mooves possible et vérifier si ce moove est faisable
        boolean basicMoove=true;
        boolean attackingMoove=false;
        for (Piece piece : piecesPosition) {
            if (piece.position.equals(nextMoove) || !this.nextPossiblePosition.contains(nextMoove)) {
                basicMoove=false;
            }
            //vérifie que lorsque le pion se déplace de deux qu'il n'y aucun pion qui gêne
            if(current.x == nextMoove.x && (current.y-2 == nextMoove.y || current.y+2==nextMoove.y)){
                if(current.y-2 == nextMoove.y){
                    if(piece.position.x == current.x && piece.position.y == current.y-1){
                        basicMoove=false;
                    }
                }
                if(current.y+2 == nextMoove.y){
                    if(piece.position.x == current.x && piece.position.y == current.y+1){
                        basicMoove=false;
                    }
                }
            }
            if(this.attackingPosition.contains(nextMoove) && (this.attackingPosition.contains(piece.position)&&piece.active) && nextMoove.equals(piece.position)){
                if(!(piece.type ==this.type)){
                    attackingMoove=true;
                }
            }
        }
        return basicMoove || attackingMoove;
    }

    @Override
    public int getDisplay(){
        return this.display;
    }

    //patern prototype
    @Override
    public Piece clone() {
        Pawn clone = new Pawn(this.type,this.number);
        clone.active=this.active;
        clone.position = this.position;
        clone.isFirstMoove=this.isFirstMoove;
        clone.computeNextPossibleMoove();
        clone.computePotentialAttackingPosition();
        return clone;
    }

}
