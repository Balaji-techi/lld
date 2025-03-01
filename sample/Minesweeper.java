package Practice.sample;

import java.util.*;

public class Minesweeper {
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}

class Cell {
    boolean isMine;
    boolean isRevealed;
    boolean isFlagged;
    int adjacentMines;

    public Cell() {
        this.isMine = false;
        this.isRevealed = false;
        this.isFlagged = false;
        this.adjacentMines = 0;
    }

    @Override
    public String toString() {
        if (isFlagged) return "F";
        if (!isRevealed) return "-";
        if (isMine) return "*";
        return adjacentMines > 0 ? String.valueOf(adjacentMines) : " ";
    }
}

class Board {
    private final int rows;
    private final int cols;
    private final int mines;
    private final Cell[][] grid;

    public Board(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        this.grid = new Cell[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    public void generateMines(int firstClickRow, int firstClickCol) {
        Random random = new Random();
        int placedMines = 0;

        while (placedMines < mines) {
            int r = random.nextInt(rows);
            int c = random.nextInt(cols);

            if ((r == firstClickRow && c == firstClickCol) || grid[r][c].isMine) {
                continue;
            }
            grid[r][c].isMine = true;
            placedMines++;
        }

        calculateAdjacentMines();
    }

    private void calculateAdjacentMines() {
        int[] directions = {-1, 0, 1};

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j].isMine) continue;

                for (int dr : directions) {
                    for (int dc : directions) {
                        int nr = i + dr, nc = j + dc;
                        if (isInBounds(nr, nc) && grid[nr][nc].isMine) {
                            grid[i][j].adjacentMines++;
                        }
                    }
                }
            }
        }
    }

    private boolean isInBounds(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    public boolean revealCell(int row, int col) {
        if (!isInBounds(row, col) || grid[row][col].isRevealed || grid[row][col].isFlagged) {
            return false;
        }

        grid[row][col].isRevealed = true;

        if (grid[row][col].isMine) {
            return true; // Game over
        }

        if (grid[row][col].adjacentMines == 0) {
            int[] directions = {-1, 0, 1};

            for (int dr : directions) {
                for (int dc : directions) {
                    revealCell(row + dr, col + dc);
                }
            }
        }

        return false;
    }

    public void toggleFlag(int row, int col) {
        if (isInBounds(row, col) && !grid[row][col].isRevealed) {
            grid[row][col].isFlagged = !grid[row][col].isFlagged;
        }
    }

    public boolean isWin() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!grid[i][j].isMine && !grid[i][j].isRevealed) {
                    return false;
                }
            }
        }
        return true;
    }

    public void display(boolean showMines) {
        System.out.print("  ");
        for (int j = 0; j < cols; j++) {
            System.out.print(j + " ");
        }
        System.out.println();

        for (int i = 0; i < rows; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < cols; j++) {
                if (showMines && grid[i][j].isMine) {
                    System.out.print("* ");
                } else {
                    System.out.print(grid[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}

class Game {
    private final Scanner scanner = new Scanner(System.in);
    private Board board;
    private boolean gameOver;

    public void start() {
        System.out.println("Welcome to Minesweeper!");
        System.out.print("Enter grid size (rows cols): ");
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        System.out.print("Enter number of mines: ");
        int mines = scanner.nextInt();

        board = new Board(rows, cols, mines);
        gameOver = false;

        System.out.println("Game started! Enter your first move:");
        while (!gameOver) {
            board.display(false);
            System.out.print("Enter move (row col) or flag (f row col): ");
            String input = scanner.next();

            if (input.equals("f")) {
                int row = scanner.nextInt();
                int col = scanner.nextInt();
                board.toggleFlag(row, col);
            } else {
                int row = Integer.parseInt(input);
                int col = scanner.nextInt();

                if (!board.isWin() && board.revealCell(row, col)) {
                    gameOver = true;
                    System.out.println("You hit a mine! Game Over.");
                    board.display(true);
                } else if (board.isWin()) {
                    System.out.println("Congratulations! You win!");
                    board.display(true);
                    break;
                }
            }
        }
    }
}