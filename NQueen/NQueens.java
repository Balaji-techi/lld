package Practice.NQueen;
import java.util.*;

    public class NQueens {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the value of N (size of the board and number of queens): ");
        int N = scanner.nextInt();

        if (!Validator.validateN(N)) {
            System.out.println("Please enter a valid value of N (N > 0).");
            return;
        }

        SolutionHandler solutionHandler = new SolutionHandler();
        NQueensSolver solver = new NQueensSolver(N, solutionHandler);

        solver.solve();
    }
    }

    class Queen {
    private int row;
    private int col;

    public Queen(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
    }

    class Board {
    private int N;
    private int[][] board;

    public Board(int N) {
        this.N = N;
        this.board = new int[N][N];
    }

    public void printBoard() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(board[i][j] == 1 ? "Q " : ". ");
            }
            System.out.println();
        }
    }

    public boolean isSafe(int row, int col, boolean[] cols, boolean[] diag1, boolean[] diag2) {
        if (cols[col] || diag1[row - col + N - 1] || diag2[row + col]) {
            return false;
        }
        return true;
    }

    public void placeQueen(int row, int col, boolean[] cols, boolean[] diag1, boolean[] diag2) {
        board[row][col] = 1;
        cols[col] = true;
        diag1[row - col + N - 1] = true;
        diag2[row + col] = true;
    }

    public void removeQueen(int row, int col, boolean[] cols, boolean[] diag1, boolean[] diag2) {
        board[row][col] = 0;
        cols[col] = false;
        diag1[row - col + N - 1] = false;
        diag2[row + col] = false;
    }

    public int getN() {
        return N;
    }
    }

    class Validator {
    public static boolean validateN(int N) {
        return N > 0;
    }
    }

    class SolutionHandler {
    private List<List<Queen>> allSolutions = new ArrayList<>();

    public void addSolution(List<Queen> solution) {
        allSolutions.add(new ArrayList<>(solution));
    }

    public void printSolutions() {
        if (allSolutions.isEmpty()) {
            System.out.println("No solutions found.");
            return;
        }

        int solutionCount = 1;
        for (List<Queen> solution : allSolutions) {
            System.out.println("Astroid_Collision " + solutionCount++);
            Board board = new Board(solution.size());

            boolean[] cols = new boolean[board.getN()];
            boolean[] diag1 = new boolean[2 * board.getN() - 1];
            boolean[] diag2 = new boolean[2 * board.getN() - 1];

            for (Queen queen : solution) {
                board.placeQueen(queen.getRow(), queen.getCol(), cols, diag1, diag2);
            }

            board.printBoard();
            System.out.println();
        }
    }
}

    class NQueensSolver {
    private Board board;
    private int N;
    private SolutionHandler solutionHandler;

    public NQueensSolver(int N, SolutionHandler solutionHandler) {
        this.N = N;
        this.board = new Board(N);
        this.solutionHandler = solutionHandler;
    }

    public void solve() {
        boolean[] cols = new boolean[N];
        boolean[] diag1 = new boolean[2 * N - 1];
        boolean[] diag2 = new boolean[2 * N - 1];

        List<Queen> solution = new ArrayList<>();
        placeQueens(0, cols, diag1, diag2, solution);
        solutionHandler.printSolutions();
    }

    private void placeQueens(int row, boolean[] cols, boolean[] diag1, boolean[] diag2, List<Queen> solution) {
        if (row == N) {
            solutionHandler.addSolution(solution);
            return;
        }

        for (int col = 0; col < N; col++) {
            if (board.isSafe(row, col, cols, diag1, diag2)) {
                board.placeQueen(row, col, cols, diag1, diag2);
                solution.add(new Queen(row, col));

                placeQueens(row + 1, cols, diag1, diag2, solution);

                board.removeQueen(row, col, cols, diag1, diag2);
                solution.remove(solution.size() - 1);
            }
        }
    }
}