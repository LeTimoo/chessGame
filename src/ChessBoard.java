import Enums.ColorGame;
import Pieces.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;

public class ChessBoard {

    Position startPosition;
    Position moovePosition;
    private Piece[][] PiecesPosition = new Piece[8][8];
    private List<Piece> pieces = new ArrayList<>();

    public ChessBoard(){
        this.ResetBoard();
        this.initialize();
    }
    private String empty()
    {
        return " ";
    }


    private String show(int piece)
    {
        return new String(Character.toChars(piece));
    }

    public void initialize(){
        this.pieces.forEach(piece -> {
            try {
                piece.initialize();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            this.PiecesPosition[piece.position.y][piece.position.x]=piece;
        });


    }

    public void checkPieceGotEaten(int turn){
        for (Piece piece : this.pieces) {
            if(piece.position.equals(this.moovePosition)){
                piece.active=false;
            }
        }
    }
    public void checkClonedPieceGotEaten(List<Piece> clonedPieces){
        for (Piece piece : clonedPieces) {
            if(piece.position.equals(this.moovePosition)){
                piece.active=false;
            }
        }
    }
    /*public void pieceClonedReactivate(int turn){
       if(eatenPiece!=null ){
           eatenPiece.active=true;
       }
    }*/
    public List<Piece> clonePieces() {
        List<Piece> clone = new ArrayList<>();
        for (Piece piece : this.pieces) {
            clone.add(piece.clone());
        }
        return clone;
    }

    public void update(){
        this.PiecesPosition = new Piece[8][8];//clear the table
        this.pieces.forEach(piece -> {
            if(piece.active){
                this.PiecesPosition[piece.position.y][piece.position.x]=piece;
            }
        });
    }

    @Override
    public String toString() {
        String displayChessBoard ="";
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){//display all the column here
                if(j==0) {
                    displayChessBoard += 8 -i + "|";
                }
                if(this.PiecesPosition[i][j]!=null){
                    displayChessBoard+=show(this.PiecesPosition[i][j].getDisplay())+"|";
                }else{
                    displayChessBoard+=empty()+"|";
                }
            }
            displayChessBoard+=System.lineSeparator();
        }
        displayChessBoard+="  A B C D E F G H";
        return displayChessBoard;

    }

    public void convertChoiceToPositionOnChessBoard(String start, String moove){
            int start_Xposition = (int) start.charAt(0)-65;
            int start_Yposition = 8-Integer.parseInt(start.substring(1));
            int moove_Xposition = (int) moove.charAt(0)-65;
            int moove_Yposition = 8-Integer.parseInt(moove.substring(1));
            this.startPosition = new Position(start_Xposition,start_Yposition);
            this.moovePosition = new Position(moove_Xposition,moove_Yposition);

    }

    public void ResetBoard(){
        Pawn white_pawn1 = new Pawn(ColorGame.WHITE,1);
        Pawn white_pawn2 = new Pawn(ColorGame.WHITE,2);
        Pawn white_pawn3 = new Pawn(ColorGame.WHITE,3);
        Pawn white_pawn4 = new Pawn(ColorGame.WHITE,4);
        Pawn white_pawn5 = new Pawn(ColorGame.WHITE,5);
        Pawn white_pawn6 = new Pawn(ColorGame.WHITE,6);
        Pawn white_pawn7 = new Pawn(ColorGame.WHITE,7);
        Pawn white_pawn8 = new Pawn(ColorGame.WHITE,8);
        Pawn black_pawn1 = new Pawn(ColorGame.BLACK,1);
        Pawn black_pawn2 = new Pawn(ColorGame.BLACK,2);
        Pawn black_pawn3 = new Pawn(ColorGame.BLACK,3);
        Pawn black_pawn4 = new Pawn(ColorGame.BLACK,4);
        Pawn black_pawn5 = new Pawn(ColorGame.BLACK,5);
        Pawn black_pawn6 = new Pawn(ColorGame.BLACK,6);
        Pawn black_pawn7 = new Pawn(ColorGame.BLACK,7);
        Pawn black_pawn8 = new Pawn(ColorGame.BLACK,8);
        Bishop white_bishop1= new Bishop(ColorGame.WHITE,1);
        Bishop white_bishop2= new Bishop(ColorGame.WHITE,2);
        Bishop black_bishop1= new Bishop(ColorGame.BLACK,1);
        Bishop black_bishop2= new Bishop(ColorGame.BLACK,2);
        Rook white_rook1 = new Rook(ColorGame.WHITE,1);
        Rook white_rook2 = new Rook(ColorGame.WHITE,2);
        Rook black_rook1 = new Rook(ColorGame.BLACK,1);
        Rook black_rook2 = new Rook(ColorGame.BLACK,2);
        Knight white_knight1 = new Knight(ColorGame.WHITE,1);
        Knight white_knight2 = new Knight(ColorGame.WHITE,2);
        Knight black_knight1 = new Knight(ColorGame.BLACK,1);
        Knight black_knight2 = new Knight(ColorGame.BLACK,2);
        Queen white_queen = new Queen(ColorGame.WHITE);
        Queen black_queen = new Queen(ColorGame.BLACK);
        King white_king = new King(ColorGame.WHITE);
        King black_king = new King(ColorGame.BLACK);
        this.pieces.add(white_pawn1);
        this.pieces.add(white_pawn2);
        this.pieces.add(white_pawn3);
        this.pieces.add(white_pawn4);
        this.pieces.add(white_pawn5);
        this.pieces.add(white_pawn6);
        this.pieces.add(white_pawn7);
        this.pieces.add(white_pawn8);
        this.pieces.add(black_pawn1);
        this.pieces.add(black_pawn2);
        this.pieces.add(black_pawn3);
        this.pieces.add(black_pawn4);
        this.pieces.add(black_pawn5);
        this.pieces.add(black_pawn6);
        this.pieces.add(black_pawn7);
        this.pieces.add(black_pawn8);
        this.pieces.add(white_bishop1);
        this.pieces.add(white_bishop2);
        this.pieces.add(black_bishop1);
        this.pieces.add(black_bishop2);
        this.pieces.add(black_rook1);
        this.pieces.add(black_rook2);
        this.pieces.add(white_rook1);
        this.pieces.add(white_rook2);
        this.pieces.add(white_knight1);
        this.pieces.add(white_knight2);
        this.pieces.add(black_knight1);
        this.pieces.add(black_knight2);
        this.pieces.add(white_queen);
        this.pieces.add(black_queen);
        this.pieces.add(white_king);
        this.pieces.add(black_king);
    }

    public List<Piece> getPieces(){
        return this.pieces;
    }
}
