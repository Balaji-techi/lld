package Practice.other;
import java.util.*;

public class MatrixShortestPathWithMonster {
    public static void main(String[] args) {
        //  0=free space, 2=monster)
        int[][] matrix = {
                {0, 0, 0, 0, 0},
                {0, 2, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };

        int startX = 0, startY = 0, endX = 3, endY = 4;
        int n = 5, m = 5;
        int shortestPath = bfs(matrix, startX, startY, endX, endY, n, m);

        if (shortestPath != -1) {
            System.out.println("Shortest path avoiding the monster: " + shortestPath);
        } else {
            System.out.println("No valid path found avoiding the monster.");
        }

        System.out.println("Final Maze:");
        printMatrix(matrix, n, m);
    }

    static int[] dx = {-1, 1, 0, 0};

    static int[] dy = {0, 0, -1, 1};

    public static boolean isValid(int x, int y, int n, int m, boolean[][] visited, int[][] matrix) {
        return (x >= 0 && x < n && y >= 0 && y < m && matrix[x][y] != 2 && !visited[x][y]);
    }

    public static void printMatrix(int[][] matrix, int n, int m) {
        System.out.println("Maze after the current step:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] == 2) {
                    System.out.print("M ");
                } else if (matrix[i][j] == 9) {
                    System.out.print("P ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    public static int bfs(int[][] matrix, int startX, int startY, int endX, int endY, int n, int m) {
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[n][m];
        int[][] dist = new int[n][m];
        queue.add(new int[]{startX, startY});
        visited[startX][startY] = true;
        dist[startX][startY] = 0;
        matrix[startX][startY] = 9;
        printMatrix(matrix, n, m);

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];

            if (x == endX && y == endY) {
                return dist[x][y];
            }
            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (isValid(nx, ny, n, m, visited, matrix)) {
                    visited[nx][ny] = true;
                    dist[nx][ny] = dist[x][y] + 1;
                    matrix[nx][ny] = 9;
                    queue.add(new int[]{nx, ny});

                    printMatrix(matrix, n, m);
                }
            }
        }
        return -1;
    }
}