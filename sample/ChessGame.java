package Practice.sample;

import java.util.Scanner;

public class ChessGame {
    private static final int SIZE = 8;
    private static String[][] board = new String[SIZE][SIZE];
    private static boolean isWhiteTurn = true;

    public static void main(String[] args) {
        initializeBoard();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to 2-Player Chess Game!");

        while (true) {
            displayBoard();
            System.out.println((isWhiteTurn ? "White" : "Black") + "'s turn.");
            System.out.print("Enter your move (e.g., e2 e4): ");
            String input = scanner.nextLine();

            if (!isValidInput(input)) {
                System.out.println("Invalid input. Use the format 'e2 e4'.");
                continue;
            }


            String[] positions = input.split(" ");
            String start = positions[0];
            String end = positions[1];

            if (!movePiece(start, end)) {
                System.out.println("Invalid move! Try again.");
                continue;
            }

            isWhiteTurn = !isWhiteTurn;
        }

    }

    private static void initializeBoard() {
        String[] whitePieces = {"R", "N", "B", "Q", "K", "B", "N", "R"};
        String[] blackPieces = {"r", "n", "b", "q", "k", "b", "n", "r"};

        board[0] = blackPieces.clone();
        board[1] = new String[] {"p", "p", "p", "p", "p", "p", "p", "p"};
        board[6] = new String[] {"P", "P", "P", "P", "P", "P", "P", "P"};
        board[7] = whitePieces.clone();

        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = ".";
            }
        }
    }
    private static boolean isInCheck(String[][] board, boolean isWhite) {
        int kingRow = -1, kingCol = -1;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if ((isWhite && board[i][j].equals("K")) || (!isWhite && board[i][j].equals("k"))) {
                    kingRow = i;
                    kingCol = j;
                    break;
                }
            }
        }

        return isAttacked(board, kingRow, kingCol, isWhite);
    }

    private static boolean isAttacked(String[][] board, int row, int col, boolean isWhite) {
        int direction = isWhite ? -1 : 1;
        if (isWithinBounds(row + direction, col - 1) &&
                board[row + direction][col - 1].equals(isWhite ? "p" : "P")) return true;
        if (isWithinBounds(row + direction, col + 1) &&
                board[row + direction][col + 1].equals(isWhite ? "p" : "P")) return true;

        return false;
    }

    private static boolean isWithinBounds(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }


    private static void displayBoard() {
        System.out.println("   a  b  c  d  e  f  g  h");
        for (int i = 0; i < SIZE; i++) {
            System.out.print((8 - i) + "  ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + "  ");
            }
            System.out.println(" " + (8 - i));
        }
        System.out.println("   a  b  c  d  e  f  g  h");
    }

    private static boolean isValidInput(String input) {
        return input.matches("[a-h][1-8] [a-h][1-8]");
    }

    private static boolean movePiece(String start, String end) {
        int startRow = 8 - Character.getNumericValue(start.charAt(1));
        int startCol = start.charAt(0) - 'a';
        int endRow = 8 - Character.getNumericValue(end.charAt(1));
        int endCol = end.charAt(0) - 'a';

        String piece = board[startRow][startCol];

        if (piece.equals(".")) {
            System.out.println("No piece at the starting position.");
            return false;
        }

        if ((isWhiteTurn && piece.equals(piece.toLowerCase())) || (!isWhiteTurn && piece.equals(piece.toUpperCase()))) {
            System.out.println("It's not your turn.");
            return false;
        }

        board[endRow][endCol] = piece;
        board[startRow][startCol] = ".";
        return true;
    }
}