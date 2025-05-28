import Enums.GameState;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {//TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        GameApp chessGame = new GameApp();
        while(chessGame.state == GameState.WHITE_TURN || chessGame.state == GameState.BLACK_TURN){
            chessGame.nextTurn();
            System.out.println(chessGame.chessBoard);
        }
    }
}