package AoC2018.Day25;

import java.io.*;
import java.util.*;

public class Day25 {

    public static void main(String[] args) {
        // row = 1490    col = 4
        ArrayList<ArrayList<Integer>> list = readFile2("src/AoC2022/puzzles/Day25/test");

        HashMap<ArrayList<Integer>, Constellation> allConstellation = new HashMap<>();



        /*
        for (int i = 0; i < list.size() && j < list.size(); i++) {
            allConstellation.put(list.get(i), new Constellation(list.get(i)));

            for (j = i + 1; j < list.size(); j++) {

                // Check if points has the correct manhattan distance...
                if (checkManhattanDistance(list.get(i), list.get(j), 3)) {
                    Constellation constellation = allConstellation.get(list.get(i));
                    constellation.points.add(list.get(j));


                    // ...if not, it might have with another point in the constellation
                } else {

                    //Check every constellation
                    boolean foundConstellation = false;
                    for (Constellation constellation : allConstellation.values()) {


                        if (belongToConstellation(constellation, list.get(j), 3)) {
                            constellation.points.add(list.get(j));
                            foundConstellation = true;
                            break;
                        }
                    }
                    // If it didn't find any, create a new constellation
                    if (!foundConstellation) {
                        allConstellation.put(list.get(j), new Constellation(list.get(j)));
                    }
                }

            }


        }

         */

        ArrayList<Constellation> allc = new ArrayList<>();

        int i = 0;
        while(!list.isEmpty()) {
            if(allc.isEmpty()){
                allc.add(new Constellation(list.get(0)));
            }

            for(Constellation c : allc){
                if(belongToConstellation(c, list.get(i),3)){
                    c.points.add(list.get(i));
                }
            }


        }
        System.out.println(allc.size());

    }

/*
    public static int[][] readFile(String pathname, int row, int col) {
        int[][] matrix = new int[row][col];
        try {
            File file = new File(pathname);
            Scanner scanner = new Scanner(file);
            int i = 0;
            while (scanner.hasNextLine()) {

                String data = scanner.nextLine();
                int j = 0;
                for (String word : data.split(",")) {
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


 */
    public static ArrayList<ArrayList<Integer>> readFile2(String pathname) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        try {
            File file = new File(pathname);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                int i = 0;
                String data = scanner.nextLine();
                ArrayList<Integer> points = new ArrayList<>();
                for (String word : data.split(",")) {
                    try {

                        int value = Integer.parseInt(word.trim());
                        points.add(value);

                    } catch (NumberFormatException ignored) {
                    }
                    i++;
                }
                list.add(points);

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }

        return list;
    }

    public static class Constellation {
        HashSet<ArrayList<Integer>> points = new HashSet<>();

        public Constellation(ArrayList<Integer> points) {
            this.points.add(points);
        }

        public Constellation() {

        }

        @Override
        public String toString() {
            return "Constellation{" +
                    "points=" + points +
                    '}';
        }
    }

    public static boolean checkManhattanDistance(ArrayList<Integer> p1, ArrayList<Integer> p2, int distance) {
        int d = 0;
        for (int i = 0; i < p1.size(); i++) {
            int fst = p1.get(i);
            int snd = p2.get(i);
            d += Math.abs(fst - snd);
        }
        return d <= distance;
    }

    public static boolean belongToConstellation(Constellation constellation, ArrayList<Integer> points, int distance) {
        for (ArrayList<Integer> p : constellation.points) {
            if (checkManhattanDistance(points, p, distance)) {
                return true;
            }
        }
        return false;
    }
}
