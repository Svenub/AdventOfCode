package AoC2022.Puzzles.Day8;

import FileReader.GroupStrategy.GroupStrategy;
import FileReader.GroupStrategy.SpaceNewLine;
import FileReader.ReadLineStrategy;
import FileReader.ReadStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Day8 {

    public static int[][] readFile(String pathname, int row, int col) {
        int[][] matrix = new int[row][col];
        try {
            File file = new File(pathname);
            Scanner scanner = new Scanner(file);
            int i = 0;
            while (scanner.hasNextLine()) {

                String data = scanner.nextLine();
                int j = 0;
                for (String word : data.split("")) {
                    try {

                        int value = Integer.parseInt(word.trim());
                        matrix[i][j] = value;
                    } catch (NumberFormatException ignored) {
                    }
                    j++;
                }
                i++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }

        return matrix;
    }

    public static boolean visibleFromRight(int checkRow, int checkCol, int [][] matrix){
            boolean visible = true;
            for(int col = checkCol+1; col < matrix[checkRow].length; col++){
                if (matrix[checkRow][checkCol] <= matrix[checkRow][col]  ) {
                    visible = false;
                    break;
                }
            }
       return visible;
    }

    public static boolean visibleFromLeft(int checkRow, int checkCol, int [][] matrix){
        boolean visible = true;
        for(int col = checkCol-1; col >= 0; col--){
            if (matrix[checkRow][col] >= matrix[checkRow][checkCol]) {
                visible = false;
                break;
            }
        }
        return visible;
    }

    public static boolean visibleFromTop(int checkRow, int checkCol, int [][] matrix){
        boolean visible = true;
        for(int row = checkRow-1; row >= 0; row--){
            if (matrix[checkRow][checkCol] <= matrix[row][checkCol]) {
                visible = false;
                break;
            }
        }
        return visible;
    }

    public static boolean visibleFromBottom(int checkRow, int checkCol, int [][] matrix){
        boolean visible = true;
        for(int row = checkRow+1; row < matrix.length; row++){
            if (matrix[checkRow][checkCol] <= matrix[row][checkCol]) {
                visible = false;
                break;
            }
        }
        return visible;
    }

    public static int scoreRight(int checkRow, int checkCol, int [][] matrix){
        int score = 0;
        for(int col = checkCol+1; col < matrix[checkRow].length; col++){
            if (matrix[checkRow][checkCol] > matrix[checkRow][col]  ) {
                score++;
            } else {
                score++;
                break;
            }
        }
        return score;
    }

    public static int scoreLeft(int checkRow, int checkCol, int [][] matrix){
        int score = 0;
        for(int col = checkCol-1; col >= 0; col--){
            if (matrix[checkRow][col] < matrix[checkRow][checkCol]) {

                score++;
            } else {
                score++;
                break;
            }
        }
        return score;
    }

    public static int scoreTop(int checkRow, int checkCol, int [][] matrix){
        int score = 0;

        for(int row = checkRow-1; row >= 0; row--){
            if (matrix[checkRow][checkCol] > matrix[row][checkCol]) {

                score++;
            } else {
                score++;
                break;
            }
        }
        return score;
    }

    public static int scoreBottom(int checkRow, int checkCol, int [][] matrix){
        int score = 0;
        for(int row = checkRow+1; row < matrix.length; row++){
            if (matrix[checkRow][checkCol] > matrix[row][checkCol]) {
               score++;
            } else {
                score++;
                break;
            }
        }
        return score;
    }


    public static int checkVisibleTrees(int [][] matrix){
        int visibleTrees = 0;
        for(int row = 0; row < matrix.length; row++){
            for(int col = 0; col < matrix[row].length; col++){
                if(checkVisible(row, col, matrix)){
                    visibleTrees++;
                }
            }
        }

        return visibleTrees;
    }

    public static boolean checkVisible(int row,int col, int [][] matrix){
        return visibleFromLeft(row, col, matrix) || visibleFromRight(row, col, matrix)
                || visibleFromTop(row, col, matrix) || visibleFromBottom(row, col, matrix);
    }

    public static void scenicScore(int [][] matrix){
        int left;
        int right;
        int top;
        int bottom;
        int max = 0;
        for(int row = 0; row < matrix.length; row++){
            for(int col = 0; col < matrix[row].length; col++){
                left = scoreLeft(row, col, matrix);
                right = scoreRight(row, col, matrix);
                top = scoreTop(row, col, matrix);
                bottom = scoreBottom(row, col, matrix);
                if(left*right*top*bottom > max){
                    max = left*right*top*bottom;
                }
            }
        }
        System.out.println(max);
    }

    public static void main(String[] args) {
        int[][] forest = readFile("src/AoC2022/puzzles/Day8/input", 99, 99);

        // -------------- Part 1 ---------------
        System.out.println(checkVisibleTrees(forest));


        // ----------------Part 2 -------------

        scenicScore(forest);
    }
}
