package AoC2022.Puzzles.Day5;

import FileReader.GroupStrategy.GroupStrategy;
import FileReader.GroupStrategy.SpaceNewLine;
import FileReader.ReadLineStrategy;
import FileReader.ReadStrategy;

import java.util.*;

public class Day5 {

    static ReadStrategy readStrategy = new ReadLineStrategy();
    static GroupStrategy groupStrategy = new SpaceNewLine(false, true);

    public static void main(String[] args) {

        // -------------------- Part 1 ------------------------
        readStrategy.read(groupStrategy, "src/AoC2022/puzzles/Day5/input", false);
        ArrayList<Stack<Character>> stacks = new ArrayList<>();

        // Create stacks
        for (int i = 0; i < 9; i++) {
            stacks.add(new Stack<>());
        }

        // Sort stacks
        sortStacks(stacks);

        // Filter instructions
        ArrayList<ArrayList<Integer>> instructions = getInstructions(readStrategy.getInput());


        // Do instructions
        for (ArrayList<Integer> instruction : instructions) {
            for (int i = 0; i < instruction.get(0); i++) {
                // From
                Stack<Character> stack = stacks.get(instruction.get(1) - 1);
                char goods = stack.pop();

                // To
                stacks.get(instruction.get(2) - 1).push(goods);
            }
        }


        StringBuilder sb = new StringBuilder();
        for(Stack<Character> s : stacks){
            sb.append(s.peek());
            s.clear(); // For part 2
        }
        System.out.println("Code: " + sb);

        // -------------------- Part 2 ------------------------

        sortStacks(stacks);

        // Do instructions
        Stack<Character> intermediate = new Stack<>();
        for (ArrayList<Integer> instruction : instructions) {

            for (int i = 0; i < instruction.get(0); i++) {
                // From
                Stack<Character> stack = stacks.get(instruction.get(1) - 1);
                char goods = stack.pop();
                intermediate.push(goods);
            }
            // To
            while(!intermediate.isEmpty()){
                stacks.get(instruction.get(2) - 1).push(intermediate.pop());
            }

        }
        sb = new StringBuilder();
        for(Stack<Character> s : stacks){
            sb.append(s.peek());
        }
        System.out.println("Code: " + sb);

    }

    private static void sortStacks(ArrayList<Stack<Character>> stacks){
        boolean done = false;
        for (Object str : readStrategy.getInput()) {

            if (done) {
                break;
            }
            String line = (String) str;
            int stack = 0;
            int counter = 0;
            for (char c : line.toCharArray()) {
                if (Character.isLetter(c)) {
                    stacks.get(stack).add(0, c);

                } else if (c == '[') {
                    counter = 0;
                } else if (c == ']') {
                    stack++;
                } else if (c == ' ') {
                    counter++;
                    if (counter > 3) {
                        stack++;
                        counter = 0;
                    }
                } else if (c == '1') {
                    done = true;
                    break;
                }
            }
        }
    }

    private static  ArrayList<ArrayList<Integer>> getInstructions(ArrayList<Object> list){
        ArrayList<ArrayList<Integer>> instructions = new ArrayList<>();
        boolean foundInstructions = false;
        for (Object obj : readStrategy.getInput()) {
            String line = (String) obj;
            if (!foundInstructions && line.matches("move 3 from 8 to 9")) {
                foundInstructions = true;
            }
            if (foundInstructions) {
                ArrayList<Integer> group = new ArrayList<>();

                for (String word : line.split(" ")) {
                    try {
                        int value = Integer.parseInt(word);
                        group.add(value);
                    } catch (NumberFormatException ignored) {
                    }
                }
                instructions.add(group);
            }
        }
        return instructions;
    }
}
