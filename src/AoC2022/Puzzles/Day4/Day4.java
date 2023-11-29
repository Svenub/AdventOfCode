package AoC2022.Puzzles.Day4;

import FileReader.GroupStrategy.GroupStrategy;
import FileReader.GroupStrategy.SumUntilBlank;
import FileReader.ReadLineStrategy;
import FileReader.ReadStrategy;

import java.util.ArrayList;

public class Day4 {

    static ReadStrategy readLineStrategy = new ReadLineStrategy();
    static GroupStrategy groupStrategy = new SumUntilBlank();

    public static void main(String[] args) {
        readLineStrategy.read(groupStrategy, "src/AoC2022/puzzles/Day4/input", false);


        ArrayList<ArrayList<Integer>> filter = new ArrayList<>();
        for (Object str : readLineStrategy.getInput()) {
            filter.add(parser((String) str));
        }

        int result = 0;
        for (ArrayList<Integer> arr : filter) {
            if (arr.get(0) <= arr.get(2) && arr.get(1) >= arr.get(3) ||
                    arr.get(0) >= arr.get(2) && arr.get(1) <= arr.get(3)) {
                result++;
            }
        }
        System.out.println(result);


        // Part 2
        int result2 = 0;
        for (ArrayList<Integer> arr : filter) {
            if (arr.get(0) <= arr.get(2) && arr.get(1) >= arr.get(2) ||
                    arr.get(0) >= arr.get(2) && arr.get(0) <= arr.get(3)) {
                result2++;
            }
        }
        System.out.println(result2);


    }

    public static ArrayList<Integer> parser(String string) {
        ArrayList<Integer> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (char c : string.toCharArray()) {
            if (Character.isDigit(c)) {
                sb.append(c);
            } else if (c == '-' || c == ',') {
                int value = Integer.parseInt(sb.toString());
                list.add(value);
                sb = new StringBuilder();
            }

        }
        int value = Integer.parseInt(sb.toString());
        list.add(value);

        return list;
    }
}
