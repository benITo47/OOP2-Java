import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.List;

public class SolveMaze {

    private static int[][] mazeData;
    private static final int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // up, down, left, right
    private static int rows, cols;

    private static void changeToInt(String[][] data) {
        int[][] maze = new int[data.length][data[0].length];
        rows = data.length;
        cols = data[0].length;

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                int val = switch (data[i][j]) {
                    case "W" -> -1;
                    case "C" -> 0;
                    case "S" -> 1;
                    case "F" -> -2;
                    default -> -3;
                };

                maze[i][j] = val;
            }
        }
        mazeData = maze;
    }

    public static void printMaze(int[][] data) {
        for (int[] row : data) {
            for (int value : row) {
                String print = switch (value) {
                    case -1 -> "\u2588\u2588\u2588";
                    case 0 -> "   ";
                    case 1 -> " \u25CF ";
                    case -2 -> " F ";
                    default -> (value > 1) ? String.valueOf(value) : " * ";
                };
                System.out.print(String.format("%-4s", print)); // Ensures each cell is three characters wide
            }
            System.out.println();
        }
    }


    public static void makePaths(int[][] data) {
        int startX = -1, startY = -1;
        int endX = -1, endY = -1;


        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] == 1) {
                    startX = i;
                    startY = j;
                } else if (data[i][j] == -2) {
                    endX = i;
                    endY = j;
                }
            }
        }


        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startX, startY, 1});

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0], y = current[1], steps = current[2];


            if (x == endX && y == endY) {

                data[x][y] = -2;
                break;
            }


            for (int[] direction : directions) {
                int newX = x + direction[0];
                int newY = y + direction[1];


                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols && data[newX][newY] == 0) {
                    data[newX][newY] = steps + 1;
                    queue.add(new int[]{newX, newY, steps + 1});
                }
            }
        }
        mazeData=data;
    }

    public static List<int[]> backtrack(int[][] data) {
        int endX = -1, endY = -1;

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] > 1) {
                    endX = i;
                    endY = j;
                }
            }
        }

        if (endX == -1 || endY == -1) {
            System.out.println("Exit point not found!");
            return new ArrayList<>();
        }

        int step = data[endX][endY];
        List<int[]> path = new ArrayList<>();
        boolean foundStart = false;
        int x = endX, y = endY;

        path.add(new int[]{x, y});

        while (!foundStart) {
            for (int[] direction : directions) {
                int newX = x + direction[0];
                int newY = y + direction[1];

                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols && data[newX][newY] == step - 1) {
                    x = newX;
                    y = newY;
                    step = data[x][y];

                    path.add(new int[]{x, y});

                    if (step == 1) {
                        foundStart = true;
                    }

                    break;
                }
            }
        }

        return path;
    }
    public static void main(String[] args) {

        LoadTSVToArray loader = new LoadTSVToArray();


        changeToInt(loader.Load());


        System.out.println("Initial Maze:");
        printMaze(mazeData);

        makePaths(mazeData);
        System.out.println("\nMaze after pathfinding:");
        printMaze(mazeData);

        List<int[]> path = backtrack(mazeData);


        System.out.println("\nShortest path from exit to start:");
        changeToInt(loader.Load());
        for (int[] step : path) {
            mazeData[step[0]][step[1]] = -7;
        }
        printMaze(mazeData);
    }
}
