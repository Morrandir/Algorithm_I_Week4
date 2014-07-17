import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Created by Morrandir on 2014/7/15.
 */
public class Board {

    private int[] blocks;
    private int[] goal;
    private int N;

    public Board(int[][] blocks) {
        // construct a board from an N-by-N array of blocks
        // (where blocks[i][j] = block in row i, column j)
        N = blocks.length;
        this.blocks = new int[N * N];
        goal = new int[N * N];

        for (int i = 0; i < N * N; i++) {
            this.blocks[i] = blocks[i / N][i % N];
            goal[i] = i + 1;
        }

        goal[goal.length - 1] = 0;

    }

    public int dimension() {
        // board dimension N

        return N;
    }

    public int hamming() {
        // number of blocks out of place
        int count = 0;
        for (int i = 0; i < N * N; i++) {
            if ((blocks[i] != goal[i]) && (blocks[i] != 0)) {
                count++;
            }
        }
        return count;
    }

    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        int distance = 0;

        for (int i = 0; i < N * N; i++) {
            if ((blocks[i] != goal[i]) && (blocks[i] != 0)) {
                if ((blocks[i] - 1) / N > i / N) {
                    distance += (blocks[i] - 1) / N - i / N;
                } else {
                    distance += i / N - (blocks[i] - 1) /N;
                }
                if ((blocks[i] - 1) % N > i % N) {
                    distance += (blocks[i] - 1) % N - i % N;
                } else {
                    distance += i % N - (blocks[i] - 1) % N;
                }
            }
        }
        return distance;
    }

    public boolean isGoal() {
        // is this board the goal board?
        return Arrays.equals(blocks, goal);
    }

    private void swap(int[][] array, int i1, int j1, int i2, int j2) {
        int tmp = array[i1][j1];
        array[i1][j1] = array[i2][j2];
        array[i2][j2] = tmp;
    }

    public Board twin() {
        // a board obtained by exchanging two adjacent blocks in the same row
        int[][] twinBlocks = new int[N][N];

        for (int i = 0; i < N * N; i++) {
            twinBlocks[i / N][i % N] = blocks[i];
        }

        for (int i = 0; i < N; i++) {
            boolean breaking = false;

            for (int j = 0; j < N - 1; j++) {
                if (twinBlocks[i][j] != 0 && twinBlocks[i][j + 1] != 0) {
                    swap(twinBlocks, i, j, i, j + 1);
                    breaking = true;
                    break;
                }
            }

            if (breaking) break;
        }

        return new Board(twinBlocks);
    }

    public boolean equals(Object y) {
        // does this board equal y?
        if (y == this) return true;
        if (y == null) return false;
        if (!y.getClass().equals(this.getClass())) return false;

        return this.toString().equals(y.toString());
    }

    public Iterable<Board> neighbors() {
        // all neighboring boards
        Queue<Board> neighbors = new Queue<Board>();
        int index = 0;
        int indexI;
        int indexJ;

        for (int i = 0; i < N * N; i++) {
            if (blocks[i] == 0) {
                index = i;
                break;
            }
        }

        indexI = index / N;
        indexJ = index % N;
        int[][] newBlocks = new int[N][N];

        for (int i = 0; i < N * N; i++) {
            newBlocks[i / N][i % N] = blocks[i];
        }

        if (indexI != 0) {
            swap(newBlocks, indexI - 1, indexJ, indexI, indexJ);
            neighbors.enqueue(new Board(newBlocks));
            swap(newBlocks, indexI - 1, indexJ, indexI, indexJ);
        }

        if (indexI != N - 1) {
            swap(newBlocks, indexI + 1, indexJ, indexI, indexJ);
            neighbors.enqueue(new Board(newBlocks));
            swap(newBlocks, indexI + 1, indexJ, indexI, indexJ);
        }

        if (indexJ != 0) {
            swap(newBlocks, indexI, indexJ - 1, indexI, indexJ);
            neighbors.enqueue(new Board(newBlocks));
            swap(newBlocks, indexI, indexJ - 1, indexI, indexJ);
        }

        if (indexJ != N - 1) {
            swap(newBlocks, indexI, indexJ + 1, indexI, indexJ);
            neighbors.enqueue(new Board(newBlocks));
            swap(newBlocks, indexI, indexJ + 1, indexI, indexJ);
        }

        return neighbors;
    }

    public String toString() {
        // string representation of the board (in the output format specified below)
        String output = "";

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                output += " ";
                output += Integer.toString(blocks[i * N + j]);
                output += " ";
            }
            output += "\n";
        }

        return output;
    }

}
