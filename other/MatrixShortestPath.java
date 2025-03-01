package Practice.other;
import java.util.*;

public class MatrixShortestPath {
    public static void main(String[] args) {
        int[][] grid = {
                {0, 1, 0, 0, 0},
                {0, 1, 0, 1, 0},
                {0, 0, 0, 1, 0},
                {1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };
        int[] start = {0, 0};
        int[] end = {4, 4};
        System.out.println("Shortest Path: " + shortestPath(grid, start, end));
    }
    private static final int[] ROW_MOVES = {-1, 1, 0, 0};

    private static final int[] COL_MOVES = {0, 0, -1, 1};

    public static int shortestPath(int[][] grid, int[] start, int[] end) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return -1;
        }

        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[] {start[0], start[1]});
        visited[start[0]][start[1]] = true;

        int steps = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] current = queue.poll();
                if (current[0] == end[0] && current[1] == end[1]) {
                    return steps;
                }
                for (int j = 0; j < 4; j++) {
                    int newRow = current[0] + ROW_MOVES[j];
                    int newCol = current[1] + COL_MOVES[j];
                    if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && !visited[newRow][newCol] && grid[newRow][newCol] == 0) {
                        queue.offer(new int[] {newRow, newCol});
                        visited[newRow][newCol] = true;
                    }
                }
            }
            steps++;
        }
        return -1;
    }
}