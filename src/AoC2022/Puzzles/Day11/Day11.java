package AoC2022.Puzzles.Day11;

import FileReader.GroupStrategy.GroupStrategy;
import FileReader.GroupStrategy.SpaceNewLine;
import FileReader.ReadLineStrategy;
import FileReader.ReadStrategy;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.function.BiFunction;

public class Day11 {

    private static ReadStrategy fileReader = new ReadLineStrategy();
    private static GroupStrategy groupStrategy = new SpaceNewLine(false, false);

    public static class Operator{
        private final String symbol;

        public Operator(String symbol) {
            this.symbol = symbol;
        }

        public long apply(long a, long b){
            long sum = 0;
            switch (symbol){
                case "+" ->{
                    sum =  a+b;
                }
                case "*" ->{
                    sum = a*b;
                }
            }
            return sum;
        }
    }
    /*
    public enum ArithmeticOperator implements BiFunction<Integer, Integer, Integer> {
        PLUS("+", (a, b) -> a + b),
        MINUS("-", (a, b) -> a - b),
        MULTIPLY("*", (a, b) -> a * b),
        DIVIDE("/", (a, b) -> a / b);

        private final String symbol;
        private final BiFunction<Long, Long, Long> operation;

        ArithmeticOperator(String symbol, BiFunction<Integer, Integer, Long> operation) {
            this.symbol = symbol;
            this.operation = operation;
        }

        public Integer apply(Integer a, Integer b) {
            return operation.apply(a, b);
        }

        public String toString() {
            return symbol;
        }
    }

     */

    private static class Monkey {
        String name;
        long inspectedItems = 0;
        Deque<Long> starting_items = new ArrayDeque<>();
        long currentItem;
        long op_operand;
        boolean op_operand_is_old = false;

        Operator operation;
        int div_operand;

        int trueMonkey;
        int falseMonkey;

        public Monkey(String name) {
            this.name = name;
        }

        private void operation() {
            if (op_operand_is_old) {
                currentItem = operation.apply(currentItem, currentItem);
            } else {
                currentItem = operation.apply(currentItem, op_operand);
            }

        }

        private void test(ArrayList<Monkey> monkeys) {
            if (currentItem % div_operand == 0) {
                monkeys.get(trueMonkey).starting_items.add(currentItem);
            } else {
                monkeys.get(falseMonkey).starting_items.add(currentItem);
            }
        }

        public void play(ArrayList<Monkey> monkeys) {
            while (!starting_items.isEmpty()) {
                currentItem = starting_items.pop();
                operation();
                currentItem = currentItem / 3;
                test(monkeys);
                inspectedItems++;
            }
        }

        public void play2(ArrayList<Monkey> monkeys) {
            while (!starting_items.isEmpty()) {
                currentItem = starting_items.pop();
                operation();
                test(monkeys);
                inspectedItems++;
            }
        }

        @Override
        public String toString() {
            return "InspectedItems:" + inspectedItems;
        }
    }

    private static ArrayList<Monkey> parseMonkey(ArrayList<Object> list) {
        int monkeyNum = 0;
        ArrayList<Monkey> monkeys = new ArrayList<>();
        Monkey monkey = null;
        for (Object obj : list) {
            String string = (String) obj;
            string = string.trim();


            if (string.matches("Monkey.*")) {
                monkey = new Monkey(string.replaceAll(":", ""));

            } else if (string.matches("Starting items.*")) {
                String regex = string.replaceAll("[A-Z]+[a-z]* |[a-z]*: |,", "");
                String[] items = regex.split(" ");
                for (String item : items) {
                    monkey.starting_items.add(Long.valueOf(item));
                }

            } else if (string.matches("Operation.*")) {
                String regex = string.replaceAll("Operation: new = ", "");
                String[] op = regex.split(" ");
                switch (op[1]) {
                    case "+" -> {
                        monkey.operation = new Operator("+");
                    }
                    case "*" -> {
                        monkey.operation = new Operator("*");
                    }
                }
                if (op[2].matches("old")) {
                    monkey.op_operand_is_old = true;
                } else {
                    monkey.op_operand = Integer.parseInt(op[2]);
                }

            } else if (string.matches("Test.*")) {
                String regex = string.replaceAll("Test: divisible by ", "");
                monkey.div_operand = Integer.parseInt(regex);
            } else if (string.matches("If true: throw to monkey.*")) {
                String regex = string.replaceAll("If true: throw to monkey ", "");
                monkey.trueMonkey = Integer.parseInt(regex);
            } else if (string.matches("If false: throw to monkey.*")) {
                String regex = string.replaceAll("If false: throw to monkey ", "");
                monkey.falseMonkey = Integer.parseInt(regex);
            } else {
                monkeys.add(monkey);

            }


        }
        monkeys.add(monkey);
        return monkeys;
    }

    public static void main(String[] args) {

        fileReader.read(groupStrategy, "src/AoC2022/puzzles/Day11/test", false);
        ArrayList<Monkey> monkeys = parseMonkey(fileReader.getInput());

        // -------------- Part 1-------------------

        for (int i = 0; i < 20; i++) {
            for (Monkey monkey : monkeys) {
                monkey.play(monkeys);
            }
        }
        long[] monkeyBusiness = new long[monkeys.size()];
        for (int i = 0; i < monkeys.size(); i++) {
            monkeyBusiness[i] = monkeys.get(i).inspectedItems;
        }
        Arrays.sort(monkeyBusiness);
        long result = monkeyBusiness[monkeyBusiness.length - 1] * monkeyBusiness[monkeyBusiness.length - 2];
        System.out.println(result);

        // -------------- Part 2 -------------------

        monkeys.clear();
        monkeys = parseMonkey(fileReader.getInput());

        for (int i = 0; i < 10000; i++) {
            for (Monkey monkey : monkeys) {
                monkey.play2(monkeys);
            }
        }

        long[] monkeyBusiness2 = new long[monkeys.size()];
        for (int i = 0; i < monkeys.size(); i++) {
            monkeyBusiness2[i] = monkeys.get(i).inspectedItems;
        }
        System.out.println(Arrays.toString(monkeyBusiness2));
        Arrays.sort(monkeyBusiness2);

        long result2 = monkeyBusiness2[monkeyBusiness2.length - 1] * monkeyBusiness2[monkeyBusiness2.length - 2];
        System.out.println(result2);
        long a = 1000000000;

    }
}
