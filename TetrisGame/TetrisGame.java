package Practice.TetrisGame;

import java.util.Random;
import java.util.Scanner;

public class TetrisGame {
    private static final int ROWS = 10;
    private static final int COLS = 10;
    private static final char EMPTY = '.';
    private static final char BLOCK = '#';

    private static final char[][][] TETROMINOES = {
            { {'#', '#', '#', '#'} }, // I
            { {'#', '#'}, {'#', '#'} }, // O
            { {' ', '#', ' '}, {'#', '#', '#'} }, // T
            { {'#', '#', ' '}, {' ', '#', '#'} }, // S
            { {' ', '#', '#'}, {'#', '#', ' '} }, // Z
            { {'#', ' ', ' '}, {'#', '#', '#'} }, // L
            { {' ', ' ', '#'}, {'#', '#', '#'} }  // J
    };

    private char[][] board = new char[ROWS][COLS];
    private char[][] currentTetromino;
    private int tetrominoRow, tetrominoCol;
    private boolean isRunning = true;
    private Random random = new Random();
    private Scanner scanner = new Scanner(System.in);

    public TetrisGame() {
        resetBoard();
        spawnTetromino();
    }

    private void resetBoard() {
        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c < COLS; c++)
                board[r][c] = EMPTY;
    }

    private void spawnTetromino() {
        currentTetromino = TETROMINOES[random.nextInt(TETROMINOES.length)];
        tetrominoRow = 0;
        tetrominoCol = COLS / 2 - currentTetromino[0].length / 2;
        if (!isValidMove(tetrominoRow, tetrominoCol)) {
            isRunning = false; // Game Over
        }
    }

    private boolean isValidMove(int newRow, int newCol) {
        for (int r = 0; r < currentTetromino.length; r++) {
            for (int c = 0; c < currentTetromino[r].length; c++) {
                if (currentTetromino[r][c] == BLOCK) {
                    int boardRow = newRow + r;
                    int boardCol = newCol + c;
                    if (boardRow < 0 || boardRow >= ROWS || boardCol < 0 || boardCol >= COLS || board[boardRow][boardCol] == BLOCK) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void placeTetromino() {
        for (int r = 0; r < currentTetromino.length; r++) {
            for (int c = 0; c < currentTetromino[r].length; c++) {
                if (currentTetromino[r][c] == BLOCK) {
                    board[tetrominoRow + r][tetrominoCol + c] = BLOCK;
                }
            }
        }
        clearLines();
        spawnTetromino();
    }

    private void clearLines() {
        for (int r = ROWS - 1; r >= 0; r--) {
            boolean fullLine = true;
            for (int c = 0; c < COLS; c++) {
                if (board[r][c] == EMPTY) {
                    fullLine = false;
                    break;
                }
            }
            if (fullLine) {
                for (int rowAbove = r; rowAbove > 0; rowAbove--) {
                    board[rowAbove] = board[rowAbove - 1].clone();
                }
                board[0] = new char[COLS];
                for (int c = 0; c < COLS; c++) {
                    board[0][c] = EMPTY;
                }
                r++; // Check same row again
            }
        }
    }

    private void moveTetromino(int dRow, int dCol) {
        if (isValidMove(tetrominoRow + dRow, tetrominoCol + dCol)) {
            tetrominoRow += dRow;
            tetrominoCol += dCol;
        } else if (dRow == 1) {
            placeTetromino(); // Landed, place and spawn new
        }
    }

    private void rotateTetromino() {
        int newRows = currentTetromino[0].length;
        int newCols = currentTetromino.length;
        char[][] rotated = new char[newRows][newCols];

        for (int r = 0; r < newRows; r++) {
            for (int c = 0; c < newCols; c++) {
                rotated[r][c] = currentTetromino[newCols - c - 1][r];
            }
        }

        char[][] oldTetromino = currentTetromino;
        currentTetromino = rotated;
        if (!isValidMove(tetrominoRow, tetrominoCol)) {
            currentTetromino = oldTetromino; // Revert rotation if invalid
        }
    }

    private void printBoard() {
        char[][] tempBoard = new char[ROWS][COLS];
        for (int r = 0; r < ROWS; r++) {
            System.arraycopy(board[r], 0, tempBoard[r], 0, COLS);
        }

        for (int r = 0; r < currentTetromino.length; r++) {
            for (int c = 0; c < currentTetromino[r].length; c++) {
                if (currentTetromino[r][c] == BLOCK) {
                    tempBoard[tetrominoRow + r][tetrominoCol + c] = BLOCK;
                }
            }
        }

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                System.out.print(tempBoard[r][c] + " ");
            }
            System.out.println();
        }
    }

    public void play() {
        while (isRunning) {
            printBoard();
            System.out.println("Controls: A (Left), D (Right), S (Down), W (Rotate), Q (Quit)");
            char command = scanner.next().charAt(0);

            switch (command) {
                case 'A', 'a': moveTetromino(0, -1); break;
                case 'D', 'd': moveTetromino(0, 1); break;
                case 'S', 's': moveTetromino(1, 0); break;
                case 'W', 'w': rotateTetromino(); break;
                case 'Q', 'q': isRunning = false; break;
            }
        }
        System.out.println("Game Over!");
    }

    public static void main(String[] args) {
        TetrisGame game = new TetrisGame();
        game.play();
    }
}