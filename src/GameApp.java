import Enums.ColorGame;
import Enums.GameState;
import Pieces.Piece;

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

    public static boolean validFormat(String answer) {
        return answer.matches("^[A-H][1-8]$");
    }
}
