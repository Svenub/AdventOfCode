package AoC2022.Puzzles.Day10;

import FileReader.GroupStrategy.GroupStrategy;
import FileReader.GroupStrategy.SpaceNewLine;
import FileReader.ReadLineStrategy;
import FileReader.ReadStrategy;
import java.util.ArrayList;


public class Day10 {
    private static ReadStrategy fileReader = new ReadLineStrategy();
    private static GroupStrategy groupStrategy = new SpaceNewLine(false, false);

    private static int function(ArrayList<Object> list) {
        int register = 1;
        int cycle = 0;
        int th_cycle = 20;
        int value;
        ArrayList<Integer> signals = new ArrayList<>();
        for (Object string : list) {
            String s = (String) string;
            String[] instructionSplit = s.split(" ");
            String instruction = instructionSplit[0];


            switch (instruction) {
                case "addx" -> {
                    cycle++;
                    if ((cycle) % th_cycle == 0) {
                        signals.add(th_cycle * register);
                        th_cycle += 40;
                    }
                    cycle++;
                    if ((cycle) % th_cycle == 0) {
                        signals.add(th_cycle * register);
                        th_cycle += 40;
                    }
                    value = Integer.parseInt(instructionSplit[1]);
                    register += value;
                }
                case "noop" -> {
                    cycle++;
                    if ((cycle) % th_cycle == 0) {
                        signals.add(th_cycle * register);
                        th_cycle += 40;
                    }
                }
            }
        }
        int sum1 = 0;
        for (int i : signals) {
            sum1 += i;
        }
        return sum1;
    }

    private static String[][] function2(ArrayList<Object> list) {
        String[][] CRT = new String[6][40];
        int row = 0;
        int register = 1;
        int cycle = 0;
        int spritePosition;
        int pixel = 0;

        for (Object string : list) {
            String s = (String) string;
            String[] instructionSplit = s.split(" ");
            String instruction = instructionSplit[0];


            switch (instruction) {
                case "addx" -> {
                    cycle++;
                    if ((cycle-1) % 40 == 0 && cycle != 1) {
                        pixel = 0;
                        row++;
                    }
                    drawPixel(row, pixel,register, CRT);
                    pixel++;
                    cycle++;
                    if ((cycle-1) % 40 == 0) {
                        pixel = 0;
                        row++;
                    }
                    drawPixel(row, pixel,register, CRT);
                    pixel++;
                    spritePosition = Integer.parseInt(instructionSplit[1]);
                    register += spritePosition;

                }
                case "noop" -> {
                    cycle++;
                    if ((cycle-1) % 40 == 0 && cycle != 1) {
                        pixel = 0;
                        row++;
                    }
                    drawPixel(row, pixel,register, CRT);
                    pixel++;
                }
            }
        }
        return CRT;
    }

    private static boolean isInsideSprite(int pixel, int sprite) {
        return (sprite - 1) == pixel || (sprite + 1) == pixel || sprite == pixel;
    }

    private static void drawPixel(int row, int pixel, int sprite, String[][] CRT) {
        if (isInsideSprite(pixel, sprite)) {
            CRT[row][pixel] = "#";
        } else {
            CRT[row][pixel] = ".";
        }
    }

    public static void main(String[] args) {

        fileReader.read(groupStrategy, "src/AoC2022/puzzles/Day10/input", false);
        System.out.println(function(fileReader.getInput()));


        // ------------ Part 2 -------------
        String[][] CRT = function2(fileReader.getInput());

        for(int row = 0; row < CRT.length; row++ ){
            if(row != 0){
                System.out.println();
            }
            for (int col = 0; col < CRT[row].length; col++){
                System.out.print(CRT[row][col] + " ");

            }
        }
    }
}
