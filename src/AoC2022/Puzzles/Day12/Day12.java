package AoC2022.Puzzles.Day12;

import FileReader.GroupStrategy.GroupStrategy;
import FileReader.GroupStrategy.SpaceNewLine;
import FileReader.ReadInput;
import FileReader.ReadLineStrategy;
import FileReader.ReadStrategy;

import java.awt.*;
import java.util.*;

public class Day12 {
    private static ReadInput fileReader = new ReadInput();


    // A Data Structure for queue used in BFS
    static class queueNode {
        Point pt; // The coordinates of a cell
        int dist; // cell's distance of from the source

        public queueNode(Point pt, int dist) {
            this.pt = pt;
            this.dist = dist;
        }
    }


    // check whether given cell (row, col)
// is a valid cell or not.
    static boolean isValid(int row, int col, int ROW, int COL) {
        // return true if row number and
        // column number is in range
        return (row >= 0) && (row < ROW) &&
                (col >= 0) && (col < COL);
    }

    // These arrays are used to get row and column
// numbers of 4 neighbours of a given cell
    static int rowNum[] = {-1, 0, 0, 1};
    static int colNum[] = {0, -1, 1, 0};

    // function to find the shortest path between
    // a given source cell to a destination cell.
    static int BFS(int mat[][], Point src, Point dest, int ROW, int COL) {

        boolean[][] visited = new boolean[ROW][COL];

        // Mark the source cell as visited
        visited[src.x][src.y] = true;

        // Create a queue for BFS
        Queue<queueNode> q = new LinkedList<>();

        // Distance of source cell is 0
        queueNode s = new queueNode(src, 0);
        q.add(s); // Enqueue source cell

        // Do a BFS starting from source cell
        while (!q.isEmpty()) {
            queueNode curr = q.peek();
            Point pt = curr.pt;

            // If we have reached the destination cell,
            // we are done
            if (pt.x == dest.x && pt.y == dest.y)
                return curr.dist;

            // Otherwise dequeue the front cell
            // in the queue and enqueue
            // its adjacent cells
            q.remove();

            for (int i = 0; i < 4; i++) {
                int row = pt.x + rowNum[i];
                int col = pt.y + colNum[i];

                // if adjacent cell is valid, has path
                // and not visited yet, enqueue it.
                if (isValid(row, col, ROW, COL) && (mat[pt.x][pt.y] >= mat[row][col] || mat[pt.x][pt.y] + 1 == mat[row][col]) &&
                        !visited[row][col]) {
                    // mark cell as visited and enqueue it
                    visited[row][col] = true;
                    queueNode Adjcell = new queueNode
                            (new Point(row, col),
                                    curr.dist + 1);
                    q.add(Adjcell);
                }
            }
        }

        // Return -1 if destination cannot be reached
        return -1;
    }

    // This code is contributed by PrinciRaj1992

    public static void main(String[] args) {
        fileReader.read("src/AoC2022/puzzles/Day12/input");
        int[][] maze = fileReader.parseAsMatrix("src/AoC2022/puzzles/Day12/input");
        Point source = null;
        Point dest = null;

        // Find source and destination
        boolean S = false;
        boolean E = false;
        for (int row = 0; row < maze.length; row++) {
            if (S && E) {
                break;
            }
            for (int col = 0; col < maze[row].length; col++) {
                if (!S && maze[row][col] == 'S') {
                    source = new Point(row, col);
                    maze[row][col] = 97;
                    S = true;
                }
                if (!E && maze[row][col] == 'E') {
                    dest = new Point(row, col);
                    maze[row][col] = 123;
                    E = true;
                }
            }
        }

        int dist = BFS(maze, source, dest, maze.length, maze[0].length);

        if (dist != -1)
            System.out.println("Shortest Path is " + dist);
        else
            System.out.println("Shortest Path doesn't exist");

        // ------------------ Part 2 ------------------
        maze = fileReader.parseAsMatrix("src/AoC2022/puzzles/Day12/input");
        ArrayList<Point> startingPositions = new ArrayList<>();
        dest = null;
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {
                if (maze[row][col] == 'S') {
                    maze[row][col] = 97;
                    startingPositions.add(new Point(row,col));

                } else if(maze[row][col] == 'a'){
                    startingPositions.add(new Point(row,col));
                }
                if (maze[row][col] == 'E') {
                    dest = new Point(row, col);
                    maze[row][col] = 123;
                }
            }
        }
        int shortest = Integer.MAX_VALUE;
        for(Point startingPoint : startingPositions){

            dist = BFS(maze, startingPoint, dest, maze.length, maze[0].length);
            if (dist != -1 && dist <= shortest){
                shortest = dist;
            }

        }
        System.out.println("Shortest Path is " + shortest);

    }
}


