import Enums.GameState;

import javafx.application.Application;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {//TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        GameApp chessGame = new GameApp();
        ChessBoardUX.setApp(chessGame);
        Application.launch(ChessBoardUX.class);
        while(chessGame.state == GameState.WHITE_TURN || chessGame.state == GameState.BLACK_TURN){

            //chessGame.nextTurn(); //use this method to play using console
            //System.out.println(chessGame.chessBoard);
        }
    }
}