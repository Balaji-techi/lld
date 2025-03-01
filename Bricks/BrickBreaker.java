package Practice.Bricks;
import java.util.*;

public class BrickBreaker {
    private static final String WALL = "w";
    private static final String BRICK = "1";
    private static final String GROUND = "g";
    private static final String BALL = "o";
    private static final String EMPTY = " ";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BrickBreaker game = new BrickBreaker(7, 7);

        game.placeBricks(2, 2, 2);
        game.placeBricks(2, 3, 2);
        game.placeBricks(2, 4, 2);
        game.placeBricks(3, 2, 2);
        game.placeBricks(3, 3, 2);
        game.placeBricks(3, 4, 2);

        while (ballLife > 0) {
            game.printGameBoard();
            System.out.print("Enter direction (lt/rt/st): ");
            String direction = scanner.next();
            game.moveBall(direction);
        }

        System.out.println("Game Over! Ball life exhausted.");
    }
    private static final Map<Integer, Integer> bricksWithLife = new HashMap<>();
    private static int[] ballPos;

    private static int ballLife = 5;

    private final String[][] gameBoard;

    public BrickBreaker(int rows, int cols) {
        gameBoard = new String[rows][cols];
        prepareBoard();
        ballPos = new int[]{rows - 1, cols / 2};
        gameBoard[ballPos[0]][ballPos[1]] = BALL;
    }

    public void placeBricks(int row, int col, int life) {
        gameBoard[row][col] = BRICK;
        int exactPosition = getExactPosition(row, col);
        bricksWithLife.put(exactPosition, life);
    }

    public void moveBall(String direction) {
        int rowDir = 0, colDir = 0;

        // Determine direction
        switch (direction) {
            case "lt" -> { rowDir = -1; colDir = -1; } // Diagonal up-left
            case "rt" -> { rowDir = -1; colDir = 1; }  // Diagonal up-right
            case "st" -> rowDir = -1;                  // Straight up
            default -> {
                System.out.println("Invalid direction! Use 'lt', 'rt', or 'st'.");
                return;
            }
        }

        moveDirection(ballPos[0], ballPos[1], rowDir, colDir);
    }

    private void moveDirection(int row, int col, int rowDir, int colDir) {
        while (true) {
            row += rowDir;
            col += colDir;

            if (row < 0 || col < 0 || col >= gameBoard[0].length) {
                System.out.println("Ball hit the wall!");
                return;
            }

            if (gameBoard[row][col].equals(WALL)) {
                System.out.println("Ball hit a wall!");
                return;
            } else if (gameBoard[row][col].equals(BRICK)) {
                handleBrickCollision(row, col);
                return;
            } else if (gameBoard[row][col].equals(GROUND)) {
                System.out.println("Ball reached the ground.");
                ballLife--;
                return;
            } else {
                // Ball is in empty space
                updateBallPosition(row, col);
            }
        }
    }

    private void handleBrickCollision(int row, int col) {
        int position = getExactPosition(row, col);
        int brickLife = bricksWithLife.getOrDefault(position, 0);

        if (brickLife > 0) {
            bricksWithLife.put(position, brickLife - 1);
            System.out.println("Brick hit! Remaining brick life: " + (brickLife - 1));

            if (brickLife - 1 == 0) {
                gameBoard[row][col] = EMPTY; // Brick destroyed
                bricksWithLife.remove(position);
            }
        }
        ballLife--;
    }

    private void updateBallPosition(int row, int col) {
        gameBoard[ballPos[0]][ballPos[1]] = EMPTY; // Clear old ball position
        ballPos[0] = row;
        ballPos[1] = col;
        gameBoard[row][col] = BALL;
        printGameBoard();
    }

    private int getExactPosition(int row, int col) {
        return row * gameBoard[0].length + col;
    }

    private void prepareBoard() {
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[0].length; col++) {
                if (row == 0 || col == 0 || col == gameBoard[0].length - 1) {
                    gameBoard[row][col] = WALL;
                } else if (row == gameBoard.length - 1) {
                    gameBoard[row][col] = GROUND;
                } else {
                    gameBoard[row][col] = EMPTY;
                }
            }
        }
    }

    public void printGameBoard() {
        for (String[] row : gameBoard) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println("Ball Life: " + ballLife);
    }
}
