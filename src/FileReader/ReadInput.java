package FileReader;

import FileReader.GroupStrategy.GroupStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class ReadInput {


    ArrayList<String> input = new ArrayList<>();


    public void read(String pathname) {

        try {
            File file = new File(pathname);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {

                String data = scanner.nextLine();
                input.add(data);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    public int[][] parseAsMatrix(String pathname) {

        int row = input.size();
        int col = input.get(0).toCharArray().length;
        int[][] inputMatrix = new int[row][col];
        int i = 0;
        try {
            File file = new File(pathname);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {

                String data = scanner.nextLine();
                input.add(data);
                char [] array = data.toCharArray();
                for(int c = 0; c < array.length; c++){
                    inputMatrix[i][c] = array[c];
                }
                i++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }

        return inputMatrix;
    }

    public ArrayList<String> getInput() {
        return input;
    }

}
