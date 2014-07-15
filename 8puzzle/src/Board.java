/**
 * Created by Morrandir on 2014/7/15.
 */
public class Board {

    public Board(int[][] blocks) {
        // construct a board from an N-by-N array of blocks
        // (where blocks[i][j] = block in row i, column j)

    }

    public int dimension() {
        // board dimension N

        return 0;
    }

    public int hamming() {
        // number of blocks out of place
        return 0;
    }

    public int manhattan() {
        // sum of Manhattan distances between blocks and goal

        return 0;
    }

    public boolean isGoal() {
        // is this board the goal board?

        return true;
    }

    public Board twin() {
        // a board obtained by exchanging two adjacent blocks in the same row
    }

    public boolean equals(Object y) {
        // does this board equal y?

        return true;
    }

    public Iterable<Board> neighbors() {
        // all neighboring boards

    }

    public String toString() {
        // string representation of the board (in the output format specified below)
    }

}
