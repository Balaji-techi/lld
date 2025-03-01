package Practice.Matric;

import java.util.*;

public class MatrixRayGame {
    static int[] dx = {-1, -1, 1, 1};
    static int[] dy = {-1, 1, -1, 1};

    public static boolean isValid(int x, int y, int rows, int cols) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }

    public static void printMatrix(char[][] matrix, int rows, int cols) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void applyRules(char[][] matrix, int rows, int cols, List<String> rays, Set<String> atomPositions) {
        for (String ray : rays) {
            String[] rayInfo = ray.split(" ");
            String rayType = rayInfo[0];
            int rayNum = Integer.parseInt(rayInfo[1]);
            if (rayType.startsWith("R")) {
                int row = rayNum - 1;
                boolean hit = false;

                // Move across the row and check for hit or deflection
                for (int col = 0; col < cols; col++) {
                    if (matrix[row][col] == 'x') { // Atom found
                        matrix[row][col] = 'H'; // Mark hit
                        hit = true;
                    }
                }
                if (!hit) {
                    for (int col = 0; col < cols; col++) {
                        if (matrix[row][col] == '-') {
                            matrix[row][col] = 'H'; // Ray continues until hit or boundary
                        }
                    }
                }
            } else if (rayType.startsWith("C")) {
                int col = rayNum - 1;
                boolean hit = false;
                for (int row = 0; row < rows; row++) {
                    if (matrix[row][col] == 'x') { // Atom found
                        matrix[row][col] = 'H'; // Mark hit
                        hit = true;
                    }
                }
                if (!hit) {
                    for (int row = 0; row < rows; row++) {
                        if (matrix[row][col] == '-') {
                            matrix[row][col] = 'H';
                        }
                    }
                }
            }
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (matrix[i][j] == 'x') {
                        int diagonalCount = 0;
                        for (int k = 0; k < 4; k++) {
                            int nx = i + dx[k];
                            int ny = j + dy[k];
                            if (isValid(nx, ny, rows, cols) && matrix[nx][ny] == 'H') {
                                diagonalCount++;
                            }
                        }
                        if (diagonalCount == 2) {
                            matrix[i][j] = 'R';
                        } else if (diagonalCount == 1) {
                            matrix[i][j] = 'r';
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int rows = sc.nextInt();
        int cols = sc.nextInt();

        char[][] matrix = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = '-';
            }
        }

        int numAtoms = sc.nextInt();
        for (int i = 0; i < numAtoms; i++) {
            int atomX = sc.nextInt() - 1;
            int atomY = sc.nextInt() - 1;
            matrix[atomX][atomY] = 'x';
        }

        int numRays = sc.nextInt();
        List<String> rays = new ArrayList<>();
        sc.nextLine();
        for (int i = 0; i < numRays; i++) {
            String ray = sc.nextLine();
            rays.add(ray);
        }

        applyRules(matrix, rows, cols, rays, new HashSet<>());

        printMatrix(matrix, rows, cols);

        sc.close();
    }
}
