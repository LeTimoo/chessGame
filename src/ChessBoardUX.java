import Enums.ColorGame;
import Enums.GameState;
import Pieces.King;
import Pieces.Piece;
import Pieces.Position;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ChessBoardUX extends Application {

    private static final int TILE_SIZE = 80;
    private static final int BOARD_SIZE = 8;

    private GridPane grid = new GridPane();
    private Pane dragLayer = new Pane();
    private static GameApp app = new GameApp();
    Piece currentPiece;

    private StackPane highlightSquare = new StackPane();

    private List<Circle> moveIndicators = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();
        root.getChildren().addAll(grid, dragLayer);

        dragLayer.setPickOnBounds(false);
        dragLayer.setMouseTransparent(true);

        // Créer les cases avec inversion verticale
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                StackPane square = new StackPane();
                square.setPrefSize(TILE_SIZE, TILE_SIZE);
                String color = (row + col) % 2 == 0 ? "#f0d9b5" : "#b58863";
                square.setStyle("-fx-background-color: " + color + ";");

                // Coordonnées sur l'échiquier (facultatif)
                if (col == 0) {
                    Label label = new Label(String.valueOf(row + 1));
                    label.setStyle("-fx-font-size: 11px; -fx-text-fill: gray;");
                    label.setTranslateX(-TILE_SIZE / 2 + 10);
                    label.setTranslateY(-TILE_SIZE / 2 + 8);
                    square.getChildren().add(label);
                }
                if (row == 0) {
                    Label label = new Label(String.valueOf((char) ('a' + col)));
                    label.setStyle("-fx-font-size: 11px; -fx-text-fill: gray;");
                    label.setTranslateX(TILE_SIZE / 2 - 10);
                    label.setTranslateY(TILE_SIZE / 2 - 18);
                    square.getChildren().add(label);
                }

                // 🟢 INVERSION ici
                grid.add(square, col, 7 - row);
            }
        }

        // Highlight
        Rectangle highlight = new Rectangle(TILE_SIZE, TILE_SIZE);
        highlight.setFill(Color.rgb(100, 100, 255, 0.3));
        highlight.setVisible(false);
        highlightSquare.getChildren().add(highlight);
        dragLayer.getChildren().add(highlightSquare);

        List<Piece> Pieces = app.chessBoard.getPieces();
        for(Piece piece: Pieces){
            addPiece(piece.name, Math.abs(piece.position.y-7),piece.position.x);
        }
        Scene scene = new Scene(root, TILE_SIZE * 8, TILE_SIZE * 8);
        stage.setTitle("Échiquier inversé (coord. logiques)");
        stage.setScene(scene);
        stage.show();


    }

    private void addPiece(String imageName, int row, int col) {
        Image image = new Image(getClass().getResourceAsStream("/" + imageName));
        ImageView piece = new ImageView(image);
        piece.setFitWidth(TILE_SIZE * 0.8);
        piece.setFitHeight(TILE_SIZE * 0.8);

        StackPane square = getSquare(row, col);
        square.getChildren().add(piece);

        piece.setOnMousePressed((MouseEvent e) -> {
            int originRow = GridPane.getRowIndex((StackPane) piece.getParent());
            int originCol = GridPane.getColumnIndex((StackPane) piece.getParent());

            originRow = 7 - originRow;

            checkAvaibleMoove(originRow,originCol);

            piece.getProperties().put("originRow", originRow);
            piece.getProperties().put("originCol", originCol);

            ((StackPane) piece.getParent()).getChildren().remove(piece);
            dragLayer.getChildren().add(piece);
            piece.setLayoutX(e.getSceneX() - TILE_SIZE / 2);
            piece.setLayoutY(e.getSceneY() - TILE_SIZE / 2);

            highlightSquare.setVisible(true);
        });

        piece.setOnMouseDragged((MouseEvent e) -> {
            piece.setLayoutX(e.getSceneX() - TILE_SIZE / 2);
            piece.setLayoutY(e.getSceneY() - TILE_SIZE / 2);

            int colUnder = (int) (e.getSceneX() / TILE_SIZE);
            int rowUnder = (int) (e.getSceneY() / TILE_SIZE);

            if (colUnder >= 0 && colUnder < BOARD_SIZE && rowUnder >= 0 && rowUnder < BOARD_SIZE) {
                highlightSquare.setTranslateX(colUnder * TILE_SIZE);
                highlightSquare.setTranslateY(rowUnder * TILE_SIZE);
                highlightSquare.setVisible(true);
            } else {
                highlightSquare.setVisible(false);
            }
        });

        piece.setOnMouseReleased((MouseEvent e) -> {
            for (Circle dot : moveIndicators) {
                ((Pane) dot.getParent()).getChildren().remove(dot);
            }
            moveIndicators.clear();
            highlightSquare.setVisible(false);


            int newCol = (int) (e.getSceneX() / TILE_SIZE);
            int newRow = (int) (e.getSceneY() / TILE_SIZE);

            dragLayer.getChildren().remove(piece);
            piece.setLayoutX(0);
            piece.setLayoutY(0);

            // 🔁 Inverser coordonnées visuelles → logiques
            int logicRow = 7 - newRow;

            if (logicRow >= 0 && logicRow < BOARD_SIZE && newCol >= 0 && newCol < BOARD_SIZE) {
                StackPane target = getSquare(logicRow, newCol);
                target.getChildren().add(piece);

                int originRow = (int) piece.getProperties().get("originRow");
                int originCol = (int) piece.getProperties().get("originCol");


                Position startPos = new Position(originCol,Math.abs(originRow-7));
                Position nextPos = new Position(newCol,Math.abs(logicRow-7));
                //pas très optis
                for(Piece piece_pos: app.chessBoard.getPieces()){
                    if(piece_pos.active && piece_pos.position.equals(startPos)){
                        this.currentPiece=piece_pos;
                    }
                }
                if(!startPos.equals(nextPos)){
                    if(this.currentPiece.isNextMooveAvailable(app.chessBoard.getPieces(),this.currentPiece.position,nextPos) && ((currentPiece.type== ColorGame.BLACK && app.state== GameState.BLACK_TURN)|| (currentPiece.type== ColorGame.WHITE && app.state== GameState.WHITE_TURN))){
                        GameState state = app.nextTurnFromUX(startPos, nextPos);
                        if(state==GameState.WHITE_CHECK){
                            Position KingPos=null;
                            for(Piece seekKing: app.chessBoard.getPieces()){
                                if(seekKing instanceof King && seekKing.type==ColorGame.WHITE ){
                                        KingPos = seekKing.position;
                                }
                            }
                            int flashRow = Math.abs(KingPos.y - 7);
                            int flashCol = KingPos.x;
                            flashSquare(flashRow, flashCol, () -> {
                                int originRow2 = (int) piece.getProperties().get("originRow");
                                int originCol2 = (int) piece.getProperties().get("originCol");
                                StackPane originalSquare = getSquare(originRow2, originCol2);
                                dragLayer.getChildren().remove(piece);
                                piece.setLayoutX(0);
                                piece.setLayoutY(0);
                                originalSquare.getChildren().add(piece);
                            });
                        } else if (state == GameState.BLACK_CHECK) {
                            Position KingPos=null;
                            for(Piece seekKing: app.chessBoard.getPieces()){
                                if(seekKing instanceof King && seekKing.type==ColorGame.BLACK ){
                                    KingPos = seekKing.position;
                                }
                            }
                            int flashRow = Math.abs(KingPos.y - 7);
                            int flashCol = KingPos.x;
                            flashSquare(flashRow, flashCol, () -> {
                                int originRow2 = (int) piece.getProperties().get("originRow");
                                int originCol2 = (int) piece.getProperties().get("originCol");
                                StackPane originalSquare = getSquare(originRow2, originCol2);
                                dragLayer.getChildren().remove(piece);
                                piece.setLayoutX(0);
                                piece.setLayoutY(0);
                                originalSquare.getChildren().add(piece);
                            });
                        }else{
                            this.clearAllPiecesFromBoard();
                            List<Piece> Pieces = app.chessBoard.getPieces();
                            for(Piece piece1: Pieces){
                                if(piece1.active){
                                    addPiece(piece1.name, Math.abs(piece1.position.y-7),piece1.position.x);
                                }
                             }
                        }


                    }else{
                        int originRow2 = (int) piece.getProperties().get("originRow");
                        int originCol2 = (int) piece.getProperties().get("originCol");
                        StackPane originalSquare = getSquare(originRow2, originCol2);
                        originalSquare.getChildren().add(piece);
                    }
                }


                //app.handleMoveFromUI(from, to); // ✅ envoie à la logique métier
            } else {
                int originRow = (int) piece.getProperties().get("originRow");
                int originCol = (int) piece.getProperties().get("originCol");
                StackPane originalSquare = getSquare(originRow, originCol);
                originalSquare.getChildren().add(piece);
            }
        });
    }

    private StackPane getSquare(int logicalRow, int col) {
        int displayRow = 7 - logicalRow;
        for (javafx.scene.Node node : grid.getChildren()) {
            Integer r = GridPane.getRowIndex(node);
            Integer c = GridPane.getColumnIndex(node);
            if (r != null && c != null && r == displayRow && c == col) {
                return (StackPane) node;
            }
        }
        return null;
    }

    private String toChessNotation(int row, int col) {
        char file = (char) ('A' + col);
        int rank = 8 - row;
        return "" + file + rank;
    }


    public static void setApp(GameApp gameApp) {
        app = gameApp;
    }

    public void checkAvaibleMoove(int y,int x){
        Position currentMoove = new Position(x,Math.abs(y-7));
        for(Piece piece: app.chessBoard.getPieces()){
            if(piece.active==true && piece.position.equals(currentMoove)){
                if((piece.type== ColorGame.BLACK && app.state== GameState.BLACK_TURN)|| (piece.type== ColorGame.WHITE && app.state== GameState.WHITE_TURN)){
                    for(int i=0;i<8;i++){
                        for(int j=0;j<8;j++){
                            Position checkPos = new Position(i,j);
                            if(piece.isNextMooveAvailable(app.chessBoard.getPieces(),currentMoove,checkPos)){
                                StackPane square = getSquare(Math.abs(j-7), i);
                                Circle dot = new Circle(TILE_SIZE * 0.1);
                                dot.setFill(Color.rgb(50, 150, 255, 0.4));
                                square.getChildren().add(dot);
                                moveIndicators.add(dot);

                            }
                        }
                    }
                }
            }
        }

    }
    public void clearAllPiecesFromBoard() {
        for (javafx.scene.Node node : grid.getChildren()) {
            if (node instanceof StackPane square) {
                square.getChildren().removeIf(child -> child instanceof ImageView);
            }
        }
    }

    //show that king is check
    private void flashSquare(int row, int col, Runnable onFinished) {
        StackPane square = getSquare(row, col);
        if (square == null) return;

        Rectangle overlay = new Rectangle(TILE_SIZE, TILE_SIZE);
        overlay.setFill(Color.rgb(255, 0, 0, 0.4));
        overlay.setMouseTransparent(true);
        square.getChildren().add(overlay);

        PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds(0.6));
        pause.setOnFinished(e -> {
            square.getChildren().remove(overlay);
            if (onFinished != null) onFinished.run();
        });
        pause.play();
    }
}
