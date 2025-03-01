package Practice.Matric;

import java.util.*;

import java.util.Scanner;
//
//public class MatrixGame {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        // Read matrix dimensions
//        int rows = scanner.nextInt();
//        int cols = scanner.nextInt();
//        char[][] matrix = new char[rows][cols];
//
//        // Initialize the matrix with '-'
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                matrix[i][j] = '-';
//            }
//        }
//
//        // Read the number of atoms
//        int numAtoms = scanner.nextInt();
//        for (int i = 0; i < numAtoms; i++) {
//            int atomRow = scanner.nextInt() - 1;
//            int atomCol = scanner.nextInt() - 1;
//            matrix[atomRow][atomCol] = 'x';
//        }
//
//        // Read the number of rays
//        int numRays = scanner.nextInt();
//        String[] rays = new String[numRays];
//        for (int i = 0; i < numRays; i++) {
//            rays[i] = scanner.next();
//        }
//
//        // Process the rays
//        for (String ray : rays) {
//            processRay(matrix, ray);
//        }
//
//        // Print the matrix
//        printMatrix(matrix);
//    }
//
//    private static void processRay(char[][] matrix, String ray) {
//        int rows = matrix.length;
//        int cols = matrix[0].length;
//        boolean isHorizontal = ray.charAt(0) == 'R' || ray.charAt(0) == 'L';
//        boolean isVertical = ray.charAt(0) == 'C';
//
//        int index = Integer.parseInt(ray.substring(1)) - 1;
//        boolean hit = false;
//
//        // Horizontal rays
//        if (isHorizontal) {
//            int row = index;
//            if (ray.charAt(0) == 'R') {
//                for (int col = 0; col < cols; col++) {
//                    if (matrix[row][col] == 'x') {
//                        hit = true;
//                        matrix[row][0] = 'H';
//                        break;
//                    }
//                }
//            } else if (ray.charAt(0) == 'L') {
//                for (int col = cols - 1; col >= 0; col--) {
//                    if (matrix[row][col] == 'x') {
//                        hit = true;
//                        matrix[row][cols - 1] = 'H';
//                        break;
//                    }
//                }
//            }
//            if (!hit) {
//                matrix[row][cols - 1] = 'R';
//            }
//        }
//
//        // Vertical rays
//        if (isVertical) {
//            int col = index;
//            if (ray.charAt(0) == 'C') {
//                for (int row = 0; row < rows; row++) {
//                    if (matrix[row][col] == 'x') {
//                        hit = true;
//                        matrix[0][col] = 'H';
//                        break;
//                    }
//                }
//            } else if (ray.charAt(0) == 'L') {
//                for (int row = rows - 1; row >= 0; row--) {
//                    if (matrix[row][col] == 'x') {
//                        hit = true;
//                        matrix[rows - 1][col] = 'H';
//                        break;
//                    }
//                }
//            }
//            if (!hit) {
//                matrix[rows - 1][col] = 'R';
//            }
//        }
//    }
//
//    private static void printMatrix(char[][] matrix) {
//        for (char[] row : matrix) {
//            for (char cell : row) {
//                System.out.print(cell + " ");
//            }
//            System.out.println();
//        }
//    }
//}
//

public class MatrixGame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of rows and columns: ");
        int rows = sc.nextInt();
        int cols = sc.nextInt();
        char[][] matrix = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            Arrays.fill(matrix[i], '-');
        }
        System.out.print("Enter number of atoms: ");
        int numAtoms = sc.nextInt();
        System.out.println("Enter atom positions (row col):");
        for (int i = 0; i < numAtoms; i++) {
            int r = sc.nextInt() - 1;
            int c = sc.nextInt() - 1;
            matrix[r][c] = 'X';
        }
        System.out.print("Enter number of rays: ");
        int numRays = sc.nextInt();
        System.out.println("Enter ray origins (e.g., R3, C1):");
        List<String> rays = new ArrayList<>();
        for (int i = 0; i < numRays; i++) {
            rays.add(sc.next());
        }

        // Process rays
        for (String ray : rays) {
            simulateRay(matrix, ray, rows, cols);
        }

        // Print final matrix
        System.out.println("Final Matrix:");
        for (char[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

    private static void simulateRay(char[][] matrix, String ray, int rows, int cols) {
        int r = -1, c = -1, dr = 0, dc = 0; // Initial position and direction

        // Determine ray origin and direction
        if (ray.startsWith("R")) {
            r = rows - Integer.parseInt(ray.substring(1));
            c = 0;
            dc = 1;
        } else if (ray.startsWith("C")) {
            c = Integer.parseInt(ray.substring(1)) - 1;
            r = rows - 1;
            dr = -1;
        }

        // Simulate ray movement
        while (r >= 0 && r < rows && c >= 0 && c < cols) {
            if (matrix[r][c] == 'X') {
                // Rule 1: Hit
                matrix[r][c] = 'H';
                break;
            } else {
                // Rule 2 & 3: Refraction
                if (isDiagonalAtom(matrix, r, c)) {
                    matrix[r][c] = 'R';
                    break;
                }

                // Rule 4: Reflection
                if (isDoubleDiagonalAtom(matrix, r, c)) {
                    matrix[r][c] = 'R';
                    break;
                }
            }

            r += dr;
            c += dc;
        }
    }

    private static boolean isDiagonalAtom(char[][] matrix, int r, int c) {
        int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        for (int[] dir : directions) {
            int nr = r + dir[0];
            int nc = c + dir[1];
            if (nr >= 0 && nr < matrix.length && nc >= 0 && nc < matrix[0].length) {
                if (matrix[nr][nc] == 'X') return true;
            }
        }
        return false;
    }

    private static boolean isDoubleDiagonalAtom(char[][] matrix, int r, int c) {
        int count = 0;
        int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        for (int[] dir : directions) {
            int nr = r + dir[0];
            int nc = c + dir[1];
            if (nr >= 0 && nr < matrix.length && nc >= 0 && nc < matrix[0].length) {
                if (matrix[nr][nc] == 'X') count++;
            }
        }
        return count == 2;
    }
}
/*
5 5

2

2 2
4 4

2

R2
C4
 */