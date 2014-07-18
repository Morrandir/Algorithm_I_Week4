import java.util.Arrays;

/**
 * Created by Morrandir on 2014/7/15.
 */
public class Board {

    private int[][] blocks;
    private int N;
    private boolean hasH;
    private int heuristic;

    public Board(int[][] blocks) {
        // construct a board from an N-by-N array of blocks
        // (where blocks[i][j] = block in row i, column j)
        int[][] newBlocks;
        hasH = false;
        N = blocks.length;
        newBlocks = new int[N][N];

        for (int i = 0; i < N; i++) {
            newBlocks[i] = Arrays.copyOf(blocks[i], N);
        }
        this.blocks = newBlocks;
    }

    public int dimension() {
        // board dimension N
        return N;
    }

    public int hamming() {
        // number of blocks out of place
        if (hasH) {
            return heuristic;
        } else {
            heuristic = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if ((blocks[i][j] != i * N + j + 1) && (blocks[i][j] != 0)) {
                        heuristic++;
                    }
                }
            }
            hasH = true;
            return heuristic;
        }
    }

    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        if (hasH) {
            return heuristic;
        } else {
            heuristic = 0;

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if ((blocks[i][j] != i * N + j + 1) && (blocks[i][j] != 0)) {
                        if ((blocks[i][j] - 1) / N > i) {
                            heuristic += (blocks[i][j] - 1) / N - i;
                        } else {
                            heuristic += i - (blocks[i][j] - 1) / N;
                        }
                        if ((blocks[i][j] - 1) % N > j) {
                            heuristic += (blocks[i][j] - 1) % N - j;
                        } else {
                            heuristic += j - (blocks[i][j] - 1) % N;
                        }
                    }
                }
            }
            hasH = true;
            return heuristic;
        }
    }

    public boolean isGoal() {
        // is this board the goal board?
        if (!hasH) {
            manhattan();
        }
        return heuristic == 0;
    }

    private void swap(int[][] array, int i1, int j1, int i2, int j2) {
        int tmp = array[i1][j1];
        array[i1][j1] = array[i2][j2];
        array[i2][j2] = tmp;
    }

    public Board twin() {
        // a board obtained by exchanging two adjacent blocks in the same row
        int[][] twinBlocks = new int[N][N];

        for (int i = 0; i < N; i++) {
            twinBlocks[i] = Arrays.copyOf(blocks[i], N);
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N - 1; j++) {
                if (twinBlocks[i][j] != 0 && twinBlocks[i][j + 1] != 0) {
                    swap(twinBlocks, i, j, i, j + 1);
                    return new Board(twinBlocks);
                }
            }
        }

        return null; // should never reach here
    }

    public boolean equals(Object y) {
        // does this board equal y?

        if (y == this) return true;
        if (y == null) return false;
        if (!y.getClass().equals(this.getClass())) return false;

        Board that = (Board) y;
        for (int i = 0; i < N; i++) {
            if (!Arrays.equals(blocks[i], that.blocks[i])) {
                return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        // all neighboring boards
        Queue<Board> neighbors = new Queue<Board>();
        int indexI;
        int indexJ = 0;

        for (indexI = 0; indexI < N; indexI++) {
            boolean found0 = false;
            for (indexJ = 0; indexJ < N; indexJ++) {
                if (blocks[indexI][indexJ] == 0) {
                    found0 = true;
                    break;
                }
            }
            if (found0) break;
        }

        if (indexI != 0) {
            swap(blocks, indexI - 1, indexJ, indexI, indexJ);
            neighbors.enqueue(new Board(blocks));
            swap(blocks, indexI - 1, indexJ, indexI, indexJ);
        }

        if (indexI != N - 1) {
            swap(blocks, indexI + 1, indexJ, indexI, indexJ);
            neighbors.enqueue(new Board(blocks));
            swap(blocks, indexI + 1, indexJ, indexI, indexJ);
        }

        if (indexJ != 0) {
            swap(blocks, indexI, indexJ - 1, indexI, indexJ);
            neighbors.enqueue(new Board(blocks));
            swap(blocks, indexI, indexJ - 1, indexI, indexJ);
        }

        if (indexJ != N - 1) {
            swap(blocks, indexI, indexJ + 1, indexI, indexJ);
            neighbors.enqueue(new Board(blocks));
            swap(blocks, indexI, indexJ + 1, indexI, indexJ);
        }

        return neighbors;
    }

    public String toString() {
        // string representation of the board (in the output format specified below)
        String output = "";

        output += Integer.toString(N);
        output += "\n";
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                output += String.format("%2d ", blocks[i][j]);
            }
            output += "\n";
        }

        return output;
    }

}
