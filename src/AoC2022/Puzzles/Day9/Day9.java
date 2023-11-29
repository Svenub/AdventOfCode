package AoC2022.Puzzles.Day9;

import FileReader.GroupStrategy.GroupStrategy;
import FileReader.GroupStrategy.SpaceNewLine;
import FileReader.ReadLineStrategy;
import FileReader.ReadStrategy;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day9 {

    public static int[][] readFile(String pathname, int row, int col) {
        int[][] matrix = new int[row][col];
        try {
            File file = new File(pathname);
            Scanner scanner = new Scanner(file);
            int i = 0;
            while (scanner.hasNextLine()) {

                String data = scanner.nextLine();
                int j = 0;
                for (String word : data.split("")) {
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
    private static JFrame gui = new JFrame();
    private static JPanel panel = new JPanel();

    private int tileAmount = 1;


    static  {
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.add(panel);
        gui.setLayout(new GridLayout());
        gui.setPreferredSize(new Dimension(860, 600));
        gui.pack();
        gui.setVisible(true);
        gui.setLocationRelativeTo(null);
        gui.repaint();
        gui.validate();
    }
    private static ReadStrategy fileReader = new ReadLineStrategy();
    private static GroupStrategy groupStrategy = new SpaceNewLine(false, false);
    private static TimerListener timerListener = new TimerListener();
    private static javax.swing.Timer startGameTimer = new Timer(1000, timerListener);

    private static class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {



        }
    }

    public static class Rope {
        Point[] body;

        public Rope(int startX, int startY, int size) {
            body = new Point[size];
            for (int i = 0; i < size; i++) {
                body[i] = new Point(startX, startY);

            }
        }

        public boolean tooFar(Point tail, Point head, int threshold) {
            int distance = (int) tail.distance(head);
            return distance > threshold;
        }

        public Point checkShortestPath(Point tail, Point head) {
            HashMap<Double, Point> paths = new HashMap<>();
            Point newCoord = null;

            double minD = tail.distance(head.x - 1, head.y);
            paths.put(tail.distance(head.x - 1, head.y), new Point(head.x - 1, head.y));
            paths.put(tail.distance(head.x + 1, head.y), new Point(head.x + 1, head.y));
            paths.put(tail.distance(head.x, head.y + 1), new Point(head.x, head.y + 1));
            paths.put(tail.distance(head.x, head.y - 1), new Point(head.x, head.y - 1));
            paths.put(tail.distance(head.x - 1, head.y + 1), new Point(head.x - 1, head.y + 1));
            paths.put(tail.distance(head.x + 1, head.y + 1), new Point(head.x + 1, head.y + 1));
            paths.put(tail.distance(head.x - 1, head.y - 1), new Point(head.x - 1, head.y - 1));
            paths.put(tail.distance(head.x + 1, head.y - 1), new Point(head.x + 1, head.y - 1));

            for (double p : paths.keySet()) {
                if (p < minD) {
                    minD = p;

                }
            }
            newCoord = paths.get(minD);

            return newCoord;
        }

        public Point propagate(int distance) {
            for (int i = 0; i < body.length; i++) {
                if (i + 1 != body.length && tooFar(body[i + 1], body[i], distance)) {
                    Point HEAD = body[i];
                    Point TAIL = body[i+1];
                    body[i+1] = checkShortestPath(HEAD, TAIL);
                }
            }

            return body[body.length - 1];
        }
    }



    public static void main(String[] args) {


        fileReader.read(groupStrategy, "src/AoC2022/puzzles/Day9/input", false);
        Point HEAD = new Point(0, 0);
        Point TAIL = new Point(0, 0);
        Set<Point> positions = new HashSet<>();
        positions.add(new Point(TAIL));
        startGameTimer.start();

        for (Object string : fileReader.getInput()) {
            String instruction = (String) string;
            String[] instructionSplit = instruction.split(" ");
            String direction = instructionSplit[0];
            int steps = Integer.parseInt(instructionSplit[1]);

            switch (direction) {
                case "R":
                    for (int i = 0; i < steps; i++) {
                        Point oldPosition = new Point(HEAD.x, HEAD.y);
                        HEAD.x++;
                        if ((int) HEAD.distance(TAIL) > 1) {
                            TAIL.setLocation(oldPosition.x, oldPosition.y);
                            positions.add(new Point(oldPosition));
                        }
                    }
                    break;
                case "L":
                    for (int i = 0; i < steps; i++) {
                        Point oldPosition = new Point(HEAD.x, HEAD.y);
                        HEAD.x--;
                        if ((int) HEAD.distance(TAIL) > 1) {
                            TAIL.setLocation(oldPosition.x, oldPosition.y);
                            positions.add(new Point(oldPosition));
                        }
                    }
                    break;
                case "U":
                    for (int i = 0; i < steps; i++) {
                        Point oldPosition = new Point(HEAD.x, HEAD.y);
                        HEAD.y++;
                        if ((int) HEAD.distance(TAIL) > 1) {
                            TAIL.setLocation(oldPosition.x, oldPosition.y);
                            positions.add(new Point(oldPosition));
                        }
                    }
                    break;
                case "D":
                    for (int i = 0; i < steps; i++) {
                        Point oldPosition = new Point(HEAD.x, HEAD.y);
                        HEAD.y--;
                        if ((int) HEAD.distance(TAIL) > 1) {
                            TAIL.setLocation(oldPosition.x, oldPosition.y);
                            positions.add(new Point(oldPosition));
                        }
                    }
                    break;
            }
        }

        System.out.println(positions.size());


        // ----------- Part 2 -----------------

        Rope rope = new Rope(0,0,10);
        Set<Point> positions2 = new HashSet<>();

        for (Object string : fileReader.getInput()) {
            String instruction = (String) string;
            String[] instructionSplit = instruction.split(" ");
            String direction = instructionSplit[0];
            int steps = Integer.parseInt(instructionSplit[1]);

            switch (direction) {
                case "R":
                    for (int i = 0; i < steps; i++) {
                        rope.body[0].x++;
                        Point tail = rope.propagate(1);
                        positions2.add(tail);
                    }
                    break;
                case "L":
                    for (int i = 0; i < steps; i++) {
                        rope.body[0].x--;
                        Point tail = rope.propagate(1);
                        positions2.add(tail);
                    }
                    break;
                case "U":
                    for (int i = 0; i < steps; i++) {
                        rope.body[0].y++;
                        Point tail = rope.propagate(1);
                        positions2.add(tail);
                    }
                    break;
                case "D":
                    for (int i = 0; i < steps; i++) {
                        rope.body[0].y--;
                        Point tail = rope.propagate(1);
                        positions2.add(tail);
                    }
                    break;
            }

        }

        System.out.println(positions2.size());
    }
}
