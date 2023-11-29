package AoC2022.Puzzles.Day3;

import FileReader.GroupStrategy.GroupStrategy;
import FileReader.GroupStrategy.SpaceNewLine;
import FileReader.ReadCharStrategy;
import FileReader.ReadLineStrategy;
import FileReader.ReadStrategy;

import java.util.ArrayList;
import java.util.Arrays;

public class Day3 {


    public static void main(String[] args) {
        ReadStrategy readLineStrategy = new ReadCharStrategy();
        GroupStrategy groupStrategy = new SpaceNewLine(false, true);
        readLineStrategy.read(groupStrategy, "src/AoC2022/puzzles/Day3/input", false);


        ArrayList<Integer> priorityArr = new ArrayList<>();
        ArrayList<Integer> result = new ArrayList<>();

        // Convert
        for (Object obj : readLineStrategy.getInput()) {
            char c = (char) obj;
            // lowercase
            if (c >= 97 && c <= 122) {
                int value = c - 96;
                priorityArr.add(value);
                // uppercase
            } else if (c >= 65 && c <= 90) {
                int value = c - 38;
                priorityArr.add(value);

                // Newline
            } else if (c == 10) {
                Object[] arr1 = Arrays.copyOfRange(priorityArr.toArray(), 0, priorityArr.size() / 2);
                Object[] arr2 = Arrays.copyOfRange(priorityArr.toArray(), priorityArr.size() / 2, priorityArr.size());
                boolean foundEqual = false;
                for (Object o1 : arr1) {

                    if (foundEqual) {
                        break;
                    }
                    for (Object o2 : arr2) {
                        if (o1.equals(o2)) {
                            result.add((Integer) o1);
                            priorityArr.clear();
                            foundEqual = true;
                            break;
                        }
                    }
                }
            }

        }

        int sum = 0;
        for (Object o : result) {
            sum += (int) o;
        }

        System.out.println(sum);

        // Part 2

        ReadStrategy readLineStrategy2 = new ReadLineStrategy();
        readLineStrategy2.read(groupStrategy, "src/AoC2022/puzzles/Day3/input", false);

        ArrayList<Character> filter = new ArrayList<>();

        for (int i = 0; i < readLineStrategy2.getInput().size(); i += 3) {
            String first = (String) readLineStrategy2.getInput().get(i);
            String second = (String) readLineStrategy2.getInput().get(i + 1);
            String third = (String) readLineStrategy2.getInput().get(i + 2);
            boolean found = false;

            for (char s1 : first.toCharArray()) {
                if (found) {
                    break;
                }
                for (char s2 : second.toCharArray()) {
                    if (found) {
                        break;
                    }
                    for (char s3 : third.toCharArray()) {
                        if (s1 == s2 && s1 == s3) {
                            filter.add(s1);
                            found = true;
                            break;
                        }
                    }

                }
            }

        }

        ArrayList <Integer> sum2 = new ArrayList<>();
        for (char ch : filter) {
            // lowercase
            if (ch >= 97 && ch <= 122) {
                int value = ch - 96;
                sum2.add(value);
                // uppercase
            } else if (ch >= 65 && ch <= 90) {
                int value = ch - 38;
                sum2.add(value);

            }
        }

        int result2 = 0;
        for (Object o : sum2) {
            result2 += (int) o;
        }
        System.out.println(result2);
    }
}