package AoC2022.Puzzles.Day6;


import FileReader.GroupStrategy.GroupStrategy;
import FileReader.GroupStrategy.SpaceNewLine;
import FileReader.ReadCharStrategy;
import FileReader.ReadStrategy;

import java.util.ArrayList;

public class Day6 {

    private static ReadStrategy fileReader = new ReadCharStrategy();
    private static GroupStrategy groupStrategy = new SpaceNewLine(false, false);

    private static boolean contains(Object elem, ArrayList<Character> list) {
        for (Object obj : list) {
            if (obj.equals(elem)) {
                return true;
            }
        }
        return false;
    }

    private static int findMarker(ArrayList<Object> list, int offset) {
        int marker = 0;
        ArrayList<Character> subList = new ArrayList<>();
        int j = -1;
        for (int i = 0; i < list.size(); i++) {

            if (contains(list.get(i), subList)) {
                subList.clear();
                j++;
                i = j;
            } else {
                subList.add((Character) list.get(i));
                if (subList.size() == offset) {
                    subList.clear();
                    marker = i+1;
                    break;
                }
            }
        }
        return marker;
    }

    public static void main(String[] args) {
        fileReader.read(groupStrategy, "src/AoC2022/puzzles/Day6/input", false);

        System.out.println(findMarker(fileReader.getInput(), 4));

        // ------------ Part 2 -------------
        System.out.println(findMarker(fileReader.getInput(), 14));
    }
}
