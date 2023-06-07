package edu.kit.kastel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PathCounter {

    static int[][] adjMatrix;
    static int numVertices;

    public static void main(String[] args) throws IOException {
        String filePath = args[0];
        int start = Integer.parseInt(args[1]);
        int end = Integer.parseInt(args[2]);
        int length = Integer.parseInt(args[3]);

        List<String> lines = readFile(filePath);
        numVertices = findMaxVertex(lines) + 1;

        adjMatrix = new int[numVertices][numVertices];
        buildAdjacencyMatrix(lines);

        int[][] resultMatrix = matrixPower(adjMatrix, length);

        System.out.println(resultMatrix[start][end]);
    }

    static List<String> readFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filePath));
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        scanner.close();
        return lines;
    }

    static int findMaxVertex(List<String> lines) {
        int max = 0;
        for (String line : lines) {
            String[] vertices = line.split(" ");
            max = Math.max(max, Math.max(Integer.parseInt(vertices[0]), Integer.parseInt(vertices[1])));
        }
        return max;
    }

    static void buildAdjacencyMatrix(List<String> lines) {
        for (String line : lines) {
            String[] vertices = line.split(" ");
            int start = Integer.parseInt(vertices[0]);
            int end = Integer.parseInt(vertices[1]);
            adjMatrix[start][end] = 1;
        }
    }

    static int[][] matrixPower(int[][] matrix, int power) {
        int[][] result = matrix.clone();
        for (int p = 1; p < power; p++) {
            result = multiplyMatrix(result, matrix);
        }
        return result;
    }

    static int[][] multiplyMatrix(int[][] matrix1, int[][] matrix2) {
        int[][] result = new int[numVertices][numVertices];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                for (int k = 0; k < numVertices; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return result;
    }
}
