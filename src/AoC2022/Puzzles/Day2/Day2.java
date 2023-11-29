package AoC2022.Puzzles.Day2;

import FileReader.GroupStrategy.GroupStrategy;
import FileReader.GroupStrategy.SpaceNewLine;
import FileReader.ReadCharStrategy;
import FileReader.ReadStrategy;

import java.util.ArrayList;
import java.util.Objects;

public class Day2 {


    public static void main(String[] args) {
        ReadStrategy readLineStrategy = new ReadCharStrategy();
        GroupStrategy groupStrategy = new SpaceNewLine(false, true);
        readLineStrategy.read(groupStrategy, "src/AoC2022/puzzles/Day2/input", false);


        // Convert
        ArrayList<Integer> game = new ArrayList<>();
        for (Object o : readLineStrategy.getOutput()) {
            if (o.equals('A') || o.equals('X')) {   // Rock
                game.add(1);
            } else if (o.equals('B') || o.equals('Y')) { // Paper
                game.add(2);
            } else if (o.equals('C') || o.equals('Z')) { // Scissors
                game.add(3);
            }
        }

        int playerscore = 0;
        for (int i = 0; i < game.size(); i += 2) {
            if (game.get(i) == 1 && game.get(i + 1) == 2) {            // Rock vs Paper    WIN
                playerscore += (8);
            } else if (game.get(i) == 2 && game.get(i + 1) == 3) {  // Paper vs Scissor    WIN
                playerscore += (9);
            } else if (game.get(i) == 3 && game.get(i + 1) == 1) {  // Scissor vs Rock  WIN
                playerscore += (7);
            } else if (game.get(i) == 2 && game.get(i + 1) == 1) {            // Rock vs Paper    Loose
                playerscore += (1);
            } else if (game.get(i) == 3 && game.get(i + 1) == 2) {  // Paper vs Scissor    Loose
                playerscore += (2);
            } else if (game.get(i) == 1 && game.get(i + 1) == 3) {  // Scissor vs Rock  Loose
                playerscore += (3);
            } else if (Objects.equals(game.get(i), game.get(i + 1))) {   // Draw
                playerscore += (3 + game.get(i));
            }

        }

        System.out.println("Score: " + playerscore);

        // Part two

        int score = 0;
        for (int i = 0; i < game.size(); i += 2) {

            // ROCK
            if (game.get(i) == 1) {
                // Lose
                if (game.get(i + 1) == 1) {
                    score += 3;
                    // Draw
                } else if (game.get(i + 1) == 2) {
                    score += (3 + 1);
                    // Win
                } else if (game.get(i + 1) == 3) {
                    score += (6 + 2);
                }

                // Paper
            } else if (game.get(i) == 2) {
                // Lose
                if (game.get(i + 1) == 1) {
                    score += 1;
                    // Draw
                } else if (game.get(i + 1) == 2) {
                    score += (3 + 2);
                    // Win
                } else if (game.get(i + 1) == 3) {
                    score += (6 + 3);
                }

                // Scissors
            } else if (game.get(i) == 3) {

                  // Lose
                if (game.get(i + 1) == 1) {
                    score += 2;
                    // Draw
                } else if (game.get(i + 1) == 2) {
                    score += (3 + 3);
                    // Win
                } else if (game.get(i + 1) == 3) {
                    score += (6 + 1);
                }

            }

        }
        System.out.println("Score: " + score);

    }
}

