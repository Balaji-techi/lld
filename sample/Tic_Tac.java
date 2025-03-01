import java.util.Scanner;
class Player {
    private String name;
    private char symbol;

    public Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }
}

class Grid {
    private char[][] matrix;
    private int size;

    public char[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(char[][] matrix) {
        this.matrix = matrix;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Grid(int size) {
        this.size = size;
        matrix = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = '-';
            }
        }
    }

    public boolean isCellEmpty(int row, int col) {
        return matrix[row][col] == '-';
    }

    public void updateCell(int row, int col, char symbol) {
        matrix[row][col] = symbol;
    }

    public void printGrid() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean checkWin(char symbol){
        for (int i = 0; i < size; i++) {
            if (checkRow(i, symbol) || checkColumn(i, symbol)) return true;
        }
        return checkMainDiagonal(symbol) || checkAntiDiagonal(symbol);
    }

    private boolean checkRow(int row, char symbol) {
        for (int col = 0; col < size; col++) {
            if (matrix[row][col] != symbol) return false;
        }
        return true;
    }

    private boolean checkColumn(int col, char symbol) {
        for (int row = 0; row < size; row++) {
            if (matrix[row][col] != symbol) return false;
        }
        return true;
    }

    private boolean checkMainDiagonal(char symbol) {
        for (int i = 0; i < size; i++) {
            if (matrix[i][i] != symbol) return false;
        }
        return true;
    }

    private boolean checkAntiDiagonal(char symbol) {
        for (int i = 0; i < size; i++) {
            if (matrix[i][size - i - 1] != symbol) return false;
        }
        return true;
    }

    public boolean isFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j] == '-') return false;
            }
        }
        return true;
    }
}

class TicTacToe {
    private Grid grid;
    private Player[] players;
    private int currentPlayerIndex;

    public TicTacToe(int gridSize, Player player1, Player player2) {
        grid = new Grid(gridSize);
        players = new Player[]{player1, player2};
        currentPlayerIndex = 0;
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            grid.printGrid();
            Player currentPlayer = players[currentPlayerIndex];
            System.out.println(currentPlayer.getName() + "'s turn (" + currentPlayer.getSymbol() + "). Enter row and column (0-based): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            if (row < 0 || row >= grid.getSize() || col < 0 || col >= grid.getSize() || !grid.isCellEmpty(row, col)) {
                System.out.println("Invalid move. Try again.");
                continue;
            }

            grid.updateCell(row, col, currentPlayer.getSymbol());

            if (grid.checkWin(currentPlayer.getSymbol())) {
                grid.printGrid();
                System.out.println(currentPlayer.getName() + " wins! "+currentPlayer.getSymbol());
                break;
            }

            if (grid.isFull()) {
                grid.printGrid();
                System.out.println("It's a draw!");
                break;
            }

            currentPlayerIndex = (currentPlayerIndex + 1) % 2;
        }
    }
}

public class Tic_Tac {
    public static void main(String[] args) {
        Player player1 = new Player("Player 1", 'X');
        Player player2 = new Player("Player 2", 'O');
        TicTacToe game = new TicTacToe(3, player1, player2);
        game.playGame();
    }
}
