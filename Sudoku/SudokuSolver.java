package Practice.Sudoku;

class Cell {
    private int value;
    private boolean isFixed;

    public Cell(int value, boolean isFixed) {
        this.value = value;
        this.isFixed = isFixed;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (!isFixed) {
            this.value = value;
        } else {
            throw new IllegalStateException("Cannot modify a fixed cell.");
        }
    }

    public boolean isFixed() {
        return isFixed;
    }
}

class Board {
    private Cell[][] grid;
    private static final int SIZE = 9;

    public Board(int[][] initialGrid) {
        grid = new Cell[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                boolean isFixed = initialGrid[i][j] != 0;
                grid[i][j] = new Cell(initialGrid[i][j], isFixed);
            }
        }
    }

    public boolean isValidMove(int row, int col, int value) {
        // Check row
        for (int i = 0; i < SIZE; i++) {
            if (grid[row][i].getValue() == value) return false;
        }

        for (int i = 0; i < SIZE; i++) {
            if (grid[i][col].getValue() == value) return false;
        }

        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (grid[i][j].getValue() == value) return false;
            }
        }

        return true;
    }
    public boolean solveBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (grid[row][col].getValue() == 0) {
                    for (int value = 1; value <= SIZE; value++) {
                        if (isValidMove(row, col, value)) {
                            grid[row][col].setValue(value);
                            if (solveBoard()) {
                                return true;
                            }
                            grid[row][col].setValue(0);
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public void displayBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(grid[i][j].getValue() +" ");
                if ((j + 1) % 3 == 0 && j != SIZE - 1) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if ((i + 1) % 3 == 0 && i != SIZE - 1) {
                System.out.println(" - ");
            }
        }
    }

}

class Game {
    private Board board;

    public Game(int[][] initialGrid) {
        board = new Board(initialGrid);
    }

    public void displayBoard() {
        board.displayBoard();
    }

    public void solveAndDisplayBoard() {
        if (board.solveBoard()) {
            System.out.println("Solved Board:");
            board.displayBoard();
        } else {
            System.out.println("No solution exists for the given board.");
        }
    }
}

public class SudokuSolver {
    public static void main(String[] args) {
        int[][] initialGrid = {
                {0, 0, 1,   0, 0, 0,   0, 0, 0},
                {0, 0, 0,   0, 5, 4,   6, 1, 0},
                {9, 0, 0,   0, 0, 0,   0, 0, 7},

                {0, 0, 0,   0, 0, 7,   4, 0, 0},
                {0, 0, 0,   0, 0, 6,   7, 9, 0},
                {0, 8, 5,   0, 0, 0,   0, 0, 0},

                {8, 3, 0,   0, 6, 0,   0, 0, 0},
                {0, 0, 0,   0, 1, 9,   2, 0, 0},
                {5, 0, 0,   0, 0, 0,   0, 0, 8}
        };

        Game game = new Game(initialGrid);

        System.out.println("Initial Board:");
        game.displayBoard();

        System.out.println("\nSolving the Board..."+"\n");
        game.solveAndDisplayBoard();
    }
}