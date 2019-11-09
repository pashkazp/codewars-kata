package path_finder_3_the_alpinist;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author Pavlo Degtyerev
 */
public class Finder {
    static String[] strings;
    static int maxIndex;
    static Cell[][] matrix;
    static Queue<Task> queue;

    private static class Cell {
        int height;
        int cost = Integer.MAX_VALUE;

        public Cell(int height) {
            this.height = height;
        }
    }

    private static class Task {
        int y;
        int x;
        int cost;

        public Task(int y, int x, int cost) {
            this.y = y;
            this.x = x;
            this.cost = cost;
        }
    }

    static void checkAndPushTask(int cost, int height, int y, int x) {
        Cell cell = matrix[y][x];
        int steps = Math.abs(cell.height - height);
        if (cell.cost <= steps + cost) {
            return;
        }
        queue.offer(new Task(y, x, steps + cost));
    }

    static int pathFinder(String maze) {
        strings = maze.split("\n");
        maxIndex = strings[0].length() - 1;
        matrix = new Cell[maxIndex + 1][maxIndex + 1];
        for (int i = 0; i <= maxIndex; i++) {
            for (int j = 0; j <= maxIndex; j++) {
                int k = Integer.parseInt(strings[i].substring(j, j + 1));
                Cell cell = new Cell(k);
                matrix[i][j] = cell;
            }
        }
        queue = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost));
        queue.offer(new Task(maxIndex, maxIndex, 0));
        do {
            Task task = queue.poll();
            assert task != null;
            if (task.cost < matrix[task.y][task.x].cost) {
                matrix[task.y][task.x].cost = task.cost;
                if (task.y == 0 && task.x == 0) {
                    break;
                }
                if (task.y < maxIndex) {
                    checkAndPushTask(task.cost, matrix[task.y][task.x].height, task.y + 1, task.x);
                }
                if (task.y > 0) {
                    checkAndPushTask(task.cost, matrix[task.y][task.x].height, task.y - 1, task.x);
                }
                if (task.x < maxIndex) {
                    checkAndPushTask(task.cost, matrix[task.y][task.x].height, task.y, task.x + 1);
                }
                if (task.x > 0) {
                    checkAndPushTask(task.cost, matrix[task.y][task.x].height, task.y, task.x - 1);
                }
            }
        } while (!queue.isEmpty());

        return matrix[0][0].cost;
    }
}
