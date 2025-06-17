package Pieces;

import Enums.ColorGame;
import java.util.ArrayList;
import java.util.List;

public class MysteryPieceNoJump extends Piece {
    public int display;
    public int range;
    public String movementType; // "diagonal", "line"
    public int number;
    private List<Position> nextPossiblePositions = new ArrayList<>();

    public MysteryPieceNoJump(ColorGame type, int number, Position position, int range, String movementType) {
        super(type, number);
        if (type == ColorGame.WHITE) {
            this.name = "w_mystery.png";
            this.display = 0x265E;
        } else {
            this.name = "b_mystery.png";
            this.display = 0x2658;
        }
        this.position = position;
        this.range = range;
        this.movementType = movementType;
        computeNextPossibleMoove();
    }

    @Override
    public void initialize() {
        computeNextPossibleMoove();
    }

    public void computeNextPossibleMoove() {
        nextPossiblePositions.clear();
        int[][] directions;

        if (movementType.equalsIgnoreCase("diagonal")) {
            directions = new int[][]{{1,1}, {1,-1}, {-1,1}, {-1,-1}};
        } else if (movementType.equalsIgnoreCase("line")) {
            directions = new int[][]{{1,0}, {-1,0}, {0,1}, {0,-1}};
        } else {
            directions = new int[0][0];
        }

        for (int[] dir : directions) {
            for (int i = 1; i <= range; i++) {
                int newX = position.x + dir[0] * i;
                int newY = position.y + dir[1] * i;
                Position newPos = new Position(newX, newY);
                if (newPos.isInsideChessBoard()) {
                    nextPossiblePositions.add(newPos);
                }
            }
        }
    }

    @Override
    public boolean isNextMooveAvailable(List<Piece> piecesPosition, Position currentPosition, Position nextMoove) {
        if (!nextPossiblePositions.contains(nextMoove)) return false;

        int dx = Integer.compare(nextMoove.x, currentPosition.x);
        int dy = Integer.compare(nextMoove.y, currentPosition.y);

        int x = currentPosition.x + dx;
        int y = currentPosition.y + dy;

        while (x != nextMoove.x || y != nextMoove.y) {
            Position pos = new Position(x, y);
            for (Piece piece : piecesPosition) {
                if (piece.position.equals(pos) && piece.active) {
                    return false;
                }
            }
            x += dx;
            y += dy;
        }

        // Vérifier destination
        for (Piece piece : piecesPosition) {
            if (piece.position.equals(nextMoove) && piece.active && piece.type == this.type) {
                return false; // allié en destination
            }
        }

        return true;
    }

    @Override
    public void update(Position position) {
        this.position = position;
        computeNextPossibleMoove();
    }

    @Override
    public List<Position> getNextPossiblePosition() {
        return new ArrayList<>(nextPossiblePositions);
    }

    @Override
    public int getDisplay() {
        return this.display;
    }

    @Override
    public Piece clone() {
        return new MysteryPieceNoJump(this.type, this.number, new Position(this.position.x, this.position.y), this.range, this.movementType);
    }
}
