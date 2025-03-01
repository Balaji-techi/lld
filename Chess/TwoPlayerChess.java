package Practice.Chess;

import java.util.Scanner;

class ChessGame {
    private static final int SIZE = 8;
    private String[][] board;
    private boolean isWhiteTurn;

    public ChessGame() {
        board = new String[SIZE][SIZE];
        isWhiteTurn = true;
        initializeBoard();
    }
    private void initializeBoard() {
        board[0] = new String[]{"R", "N", "B", "Q", "K", "B", "N", "R"};
        board[1] = new String[]{"P", "P", "P", "P", "P", "P", "P", "P"};

        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = ".";
            }
        }

        board[6] = new String[]{"p", "p", "p", "p", "p", "p", "p", "p"};
        board[7] = new String[]{"r", "n", "b", "q", "k", "b", "n", "r"};
    }

    public void displayBoard() {
        System.out.println("\nChess Board:");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void printBoard() {
        System.out.println("  a b c d e f g h ------>White");
        for (int i = 0; i < 8; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println((8 - i));
        }
        System.out.println("  a b c d e f g h------>Black");
        System.out.println(isWhiteTurn ? "It's White's turn!" : "It's Black's turn!");
    }
    public boolean makeMove(String move) {
        String[] parts = move.split(" ");
        if (parts.length != 2) {
            System.out.println("Invalid move format! Use 'e2 e4' format.");
            return false;
        }

        int startX = 8 - Character.getNumericValue(parts[0].charAt(1));
        int startY = parts[0].charAt(0) - 'a';
        int endX = 8 - Character.getNumericValue(parts[1].charAt(1));
        int endY = parts[1].charAt(0) - 'a';
        if (!isValidIndex(startX, startY) || !isValidIndex(endX, endY)) {
            System.out.println("Invalid move! Out of board bounds.");
            return false;
        }

        String piece = board[startX][startY];
        if (piece.equals(".")) {
            System.out.println("No piece at the starting position!");
            return false;
        }
        if (isWhiteTurn && !piece.matches("[PNBRQK]")) {
            System.out.println("It's White's turn! Move a white piece.");
            return false;
        }
        if (!isWhiteTurn && !piece.matches("[pnbrqk]")) {
            System.out.println("It's Black's turn! Move a black piece.");
            return false;
        }

        board[endX][endY] = piece;
        board[startX][startY] = ".";
        isWhiteTurn = !isWhiteTurn;
        return true;
    }
    private boolean isValidIndex(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
    }
    public boolean isGameOver() {
        boolean whiteKingExists = false;
        boolean blackKingExists = false;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j].equals("K")) {
                    whiteKingExists = true;
                }
                if (board[i][j].equals("k")) {
                    blackKingExists = true;
                }
            }
        }

        return !(whiteKingExists && blackKingExists);
    }
    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }
}

public class TwoPlayerChess {
    public static void main(String[] args) {
        ChessGame game = new ChessGame();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Chess!");
        System.out.println("Format for moves: 'e2 e4' (start position end position)");

        while (!game.isGameOver()) {
            game.printBoard();
            System.out.print((game.isWhiteTurn() ? "White's" : "Black's") + " move: ");
            String move = scanner.nextLine();

            if (!game.makeMove(move)) {
                System.out.println("Invalid move. Try again.");
            }
        }
        System.out.println("Game over!");
        game.printBoard(); // Print final state after the game is over
    }
}