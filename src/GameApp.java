
import Enums.ColorGame;
import Enums.GameState;
import Pieces.King;
import Pieces.Piece;
import Pieces.Position;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameApp {
    GameState state = GameState.WHITE_TURN;
    String AnswerPositionStart;
    String AnswerPositionMoove;
    Piece currentPiece;
    ChessBoard chessBoard;
    int turn=0;

    public GameApp(){
        chessBoard = new ChessBoard();
        chessBoard.initialize();
        System.out.println(chessBoard);
    }
    //play from console
    public void nextTurn() {
        String play=askMoove();
        while(!convertAnswerToPosition(play)){
            System.out.println("Le format est invalide veuillez renter un format sous la forme : A3-B7 ");
            play=askMoove();
        }
        chessBoard.convertChoiceToPositionOnChessBoard(this.AnswerPositionStart,this.AnswerPositionMoove);
        String displayMsgErrorNotAvailable = isStartPieceExisting(chessBoard.getPieces());
        if(!displayMsgErrorNotAvailable.equals("available")){
            System.out.println(displayMsgErrorNotAvailable);
            nextTurn();
            return;
        }
        boolean available = this.currentPiece.isNextMooveAvailable(chessBoard.getPieces(),this.currentPiece.position,chessBoard.moovePosition);
        if(available){
            this.chessBoard.checkPieceGotEaten();
            this.currentPiece.update(chessBoard.moovePosition);
            this.chessBoard.update();
        }else{
            System.out.println("Ce coup est n'est pas possible");
            nextTurn();
            return;
        }
        this.turn++;
        //Now check if there is checkMateOrPat

    }
    //method in case we use the UX interface instead of player on console
    public GameState nextTurnFromUX(Position startPos, Position nextPos){
        chessBoard.startPosition=startPos;
        chessBoard.moovePosition=nextPos;
        isStartPieceExisting(chessBoard.getPieces());
        boolean available = this.currentPiece.isNextMooveAvailable(chessBoard.getPieces(),startPos,nextPos);
        if(available){
            if(!isKingCheck()){
                this.chessBoard.checkPieceGotEaten();
                this.currentPiece.update(chessBoard.moovePosition);
                this.chessBoard.update();
            }else{
                if(this.currentPiece.type == ColorGame.WHITE){
                    return GameState.WHITE_CHECK;
                }
                else{
                    return GameState.BLACK_CHECK;
                }
            }
        }else{
            return null;
        }
        this.turn++;
        if(turn% 2 == 0 ){
            /*if(isCheckMate()){
                System.out.println(GameState.MAT);
            }*/
            this.state = GameState.WHITE_TURN;
        }else{
           /* if(isCheckMate()){
                System.out.println(GameState.MAT);
            }*/
            this.state = GameState.BLACK_TURN;
        }
        return null;



    }

    public boolean isKingCheck(){
        List<Piece> clonedPieces = this.chessBoard.clonePieces();
        Piece clonedCurrentPiece=null;
        Piece pieceClonedKing=null;
        Position clonedKing= null;
        for(Piece piece: clonedPieces){
            if(piece.position.equals(this.currentPiece.position) && piece.active){
                clonedCurrentPiece=piece;
            }
            if(piece instanceof King && this.currentPiece.type == piece.type){
                clonedKing=piece.position;
                pieceClonedKing=piece;
            }
        }
        if(clonedKing!=null && clonedCurrentPiece!=null){
            if(!clonedCurrentPiece.equals(pieceClonedKing)){
                chessBoard.checkClonedPieceGotEaten(clonedPieces);
            }
            //chessBoard.checkClonedPieceGotEaten(clonedPieces);
            clonedCurrentPiece.update(chessBoard.moovePosition);
            if(clonedCurrentPiece instanceof King){
                clonedKing = clonedCurrentPiece.position;
            }
            for(Piece piece: clonedPieces){
                if(piece.isNextMooveAvailable(clonedPieces,piece.position,clonedKing) && piece.active){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isKingCheckForCheckMate(){
        List<Piece> clonedPieces = this.chessBoard.clonePieces();
        Piece clonedCurrentPiece=null;
        Piece pieceClonedKing=null;
        Position clonedKing= null;
        for(Piece piece: clonedPieces){
            if(piece.position.equals(this.currentPiece.position) && piece.active){
                clonedCurrentPiece=piece;
            }
            if(piece instanceof King && this.currentPiece.type != piece.type){
                clonedKing=piece.position;
                pieceClonedKing=piece;
            }
        }
        if(clonedKing!=null && clonedCurrentPiece!=null){
            if(!clonedCurrentPiece.equals(pieceClonedKing)){
                chessBoard.checkClonedPieceGotEaten(clonedPieces);
            }
            //chessBoard.checkClonedPieceGotEaten(clonedPieces);
            clonedCurrentPiece.update(chessBoard.moovePosition);
            if(clonedCurrentPiece instanceof King){
                clonedKing = clonedCurrentPiece.position;
            }
            for(Piece piece: clonedPieces){
                if(piece.isNextMooveAvailable(clonedPieces,piece.position,clonedKing) && piece.active){
                    return true;
                }
            }
        }
        return false;
    }

    public String isStartPieceExisting(List<Piece> pieces){
        for (Piece piece : pieces) {
            if(piece.active){
                if(piece.position.equals(chessBoard.startPosition)){
                    if((this.state == GameState.WHITE_TURN && piece.type == ColorGame.WHITE )
                            || (this.state == GameState.BLACK_TURN && piece.type == ColorGame.BLACK )){
                        this.currentPiece = piece;
                        return "available";
                    }else{
                        return "Vous jouer les "+piece.type;
                    }
                }
            }
        }
        return "Aucune piece n'existe à l'emplacement : "+this.AnswerPositionStart;
    }

    public String askMoove(){
        Scanner sc = new Scanner(System.in);
        if(turn% 2 == 0 ){
            this.state = GameState.WHITE_TURN;
            System.out.println("Tour au blanc : veuillez donner une position de départ et d'arriver");
        }else{
            this.state = GameState.BLACK_TURN;
            System.out.println("Tour au noir : veuillez donner une position de départ et d'arriver");
        }
        return sc.next();
    }
    public boolean convertAnswerToPosition(String answer) {
        String answerPos[] = answer.split("-");
        if (answerPos.length == 2 && validFormat(answerPos[0]) && validFormat(answerPos[1])) {
            this.AnswerPositionStart = answerPos[0];
            this.AnswerPositionMoove = answerPos[1];
            return true;
        }
        return false;

    }

    public boolean isCheckMate(){
        //vérifier que le roi ne peux plus faire aucun moove
        // si aucun moove possible et check -> échec et Mat
        // si moove possible mais check après ( sur tous les coups possibles) -> échec et Mat
        List<Piece> clonedPieces = this.chessBoard.clonePieces();
        Piece king = null;
        //Tout d'abord vérifier que le roi est en échecs avant de vérifier l'échecs et mat
        if(this.state == GameState.WHITE_TURN) {
            for (Piece piece : clonedPieces) {
                if (piece instanceof King && piece.type == ColorGame.BLACK) {
                    king = piece;
                }
            }
        }else{
            for (Piece piece : clonedPieces) {
                if (piece instanceof King && piece.type == ColorGame.WHITE) {
                    king = piece;
                }
            }
        }
        this.currentPiece=king;
        this.chessBoard.moovePosition=king.position;
        if(isKingCheck()){
            if(this.state == GameState.WHITE_TURN){
                for(Piece piece: clonedPieces){
                    if(piece instanceof King && piece.type==ColorGame.BLACK){
                        king=piece;
                    }
                }
                if(king!=null){
                    for(Position position: ((King) king).getNextPossiblePosition()){
                        if(king.isNextMooveAvailable(clonedPieces,king.position,position)){
                            this.currentPiece=king;
                            this.chessBoard.moovePosition=position;
                            if(!isKingCheck()){
                                return false; //le prochain a une possiblité de sortir de l'échecs
                            }else{
                                //le roi n'a pas de possibilité de sortir de l'échecs, il faut donc vérifier qu'une piece permet de bloquer l'échec
                                List<Piece> clonePiecesAgain = this.chessBoard.clonePieces();
                                for(Piece piece: clonePiecesAgain){
                                    if(piece.type==ColorGame.BLACK && piece.active){
                                        //on vérifie que les pieces noirs restantes peuvent ou non stopper l'échec
                                        for(Position positionBlack: piece.getNextPossiblePosition()){
                                            if(piece.active && piece.isNextMooveAvailable(clonePiecesAgain,piece.position,positionBlack)){
                                                if(!(piece instanceof King) && piece.type==ColorGame.BLACK){
                                                    this.currentPiece=piece;
                                                    this.chessBoard.moovePosition=positionBlack;
                                                    if(!isKingCheck()){//attention ici on vérifie sur le mauvais rois
                                                        return false;
                                                        //l'échec peut être parrer
                                                    }
                                                }

                                            }
                                        }

                                    }
                                }
                            }

                            //vérifier maintenant que nous sommes en CHECK, si c'est le cas nous sommes échecs et mat (si c'est valide pour chaque position)
                        }
                    }
                }
            }else{
                for(Piece piece: clonedPieces){
                    if(piece instanceof King && piece.type==ColorGame.WHITE){
                        king=piece;
                    }
                }
                if(king!=null) {
                    for (Position position : ((King) king).getNextPossiblePosition()) {
                        if (king.isNextMooveAvailable(clonedPieces, king.position, position)) {
                            this.currentPiece = king;
                            this.chessBoard.moovePosition = position;
                            if (!isKingCheck()) {
                                return false; //le prochain a une possiblité de sortir de l'échecs
                            } else {
                                //le roi n'a pas de possibilité de sortir de l'échecs, il faut donc vérifier qu'une piece permet de bloquer l'échec
                                List<Piece> clonePiecesAgain = this.chessBoard.clonePieces();
                                for (Piece piece : clonePiecesAgain) {
                                    if (piece.type == ColorGame.WHITE && piece.active) {
                                        //on vérifie que les pieces blanches restantes peuvent ou non stopper l'échec
                                        for (Position positionBlack : piece.getNextPossiblePosition()) {
                                            if (piece.active && piece.isNextMooveAvailable(clonePiecesAgain, piece.position, positionBlack)) {
                                                this.currentPiece = piece;
                                                this.chessBoard.moovePosition = positionBlack;
                                                if (!isKingCheck()) {
                                                    return false;
                                                    //l'échec peut être parrer
                                                }
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
        }
        }else{
            return false;
        }
        return true;
    }

    public static boolean validFormat(String answer) {
        return answer.matches("^[A-H][1-8]$");
    }
}
