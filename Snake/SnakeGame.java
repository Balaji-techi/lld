package Practice.Snake;

import java.util.*;

class SnakeGame {
    public static void main(String[] args) {
        SnakeGame game = new SnakeGame(10, 10);
        game.play();
    }

    private int rows, cols;
    private LinkedList<int[]> snake;
    private Set<String> snakeSet;
    private int[] food;
    private Random random;
    private String direction;
    private boolean gameOver;

    public SnakeGame(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.snake = new LinkedList<>();
        this.snakeSet = new HashSet<>();
        this.food = new int[2];
        this.random = new Random();
        this.direction = "RIGHT";
        this.gameOver = false;

        int startRow = rows / 2, startCol = cols / 2;
        snake.add(new int[]{startRow, startCol});
        snakeSet.add(startRow + "-" + startCol);

        placeFood();
    }

    private void placeFood() {
        do {
            food[0] = random.nextInt(rows);
            food[1] = random.nextInt(cols);
        } while (snakeSet.contains(food[0] + "-" + food[1]));
    }

    public void move() {
        if (gameOver) {
            System.out.println("Game Over! Snake length: " + snake.size());
            return;
        }

        int[] head = snake.getFirst();
        int newRow = head[0], newCol = head[1];

        switch (direction) {
            case "UP":    newRow--; break;
            case "DOWN":  newRow++; break;
            case "LEFT":  newCol--; break;
            case "RIGHT": newCol++; break;
        }

        if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols) {
            gameOver = true;
            return;
        }

        String newHeadPosition = newRow + "-" + newCol;

        int[] tail = snake.getLast();
        String tailPosition = tail[0] + "-" + tail[1];
        if (snakeSet.contains(newHeadPosition) && !newHeadPosition.equals(tailPosition)) {
            gameOver = true;
            return;
        }

        snake.addFirst(new int[]{newRow, newCol});
        snakeSet.add(newHeadPosition);

        if (newRow == food[0] && newCol == food[1]) {
            placeFood();
        } else {
            int[] removedTail = snake.removeLast();
            snakeSet.remove(removedTail[0] + "-" + removedTail[1]);
        }
    }

    public void setDirection(String newDirection) {
        if ((direction.equals("UP") && newDirection.equals("DOWN")) ||
                (direction.equals("DOWN") && newDirection.equals("UP")) ||
                (direction.equals("LEFT") && newDirection.equals("RIGHT")) ||
                (direction.equals("RIGHT") && newDirection.equals("LEFT"))) {
            return;
        }
        this.direction = newDirection;
    }

    public void printGrid() {
        char[][] grid = new char[rows][cols];

        for (char[] row : grid) {
            Arrays.fill(row, '-');
        }

        grid[food[0]][food[1]] = 'F';

        for (int[] cell : snake) {
            grid[cell[0]][cell[1]] = 'S';
        }

        for (char[] row : grid) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Snake Game Started! Use W (up), A (left), S (down), D (right) to control. Enter 'Q' to quit.");
        while (!gameOver) {
            printGrid();
            System.out.print("Enter move: (W-up, A-left, D-right, S-down) ");
            char move = scanner.next().toUpperCase().charAt(0);
            switch (move) {
                case 'W': setDirection("UP"); break;
                case 'D': setDirection("RIGHT"); break;
                case 'S': setDirection("DOWN"); break;
                case 'A': setDirection("LEFT"); break;
                case 'Q': gameOver = true; continue;
                default: System.out.println("Invalid input! Use W, A, S, D to control."); continue;
            }
            move();
        }
        System.out.println("Game Over! Final score: " + (snake.size() - 1));
    }
}
