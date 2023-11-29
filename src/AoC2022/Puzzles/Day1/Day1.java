package AoC2022.Puzzles.Day1;


import FileReader.GroupStrategy.GroupStrategy;
import FileReader.GroupStrategy.SumUntilBlank;
import FileReader.ReadLineStrategy;
import FileReader.ReadStrategy;

import java.util.Arrays;


public class Day1 {


    public static void main(String[] args) {
        ReadStrategy readLineStrategy = new ReadLineStrategy();
        GroupStrategy groupStrategy = new SumUntilBlank();
        readLineStrategy.read(groupStrategy, "src/AoC2022/puzzles/Day1/input", true);

        int max = 0;
        for(Object i : readLineStrategy.getOutput()){
            if((int) i > max)
                max = (int) i;

        }
        System.out.println(max);

        // Part 2

        Object [] arr = groupStrategy.getOutPut().toArray();
        Arrays.sort(arr);
        Object [] res = Arrays.copyOfRange(arr, arr.length-3, arr.length);

        int res2 = 0;
        for(Object i : res){
            res2 += (int) i;
        }
        System.out.println(res2);
    }
}
