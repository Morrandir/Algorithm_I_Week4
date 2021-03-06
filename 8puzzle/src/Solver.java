/**
 * Created by Qubo Song on 7/17/2014.
 */
public class Solver {

    private boolean isSolvable;
    private int moves;
    private Queue<SearchNode> goals = new Queue<SearchNode>();
    //Queue<SearchNode> twinGoals = new Queue<SearchNode>();

    private class SearchNode implements Comparable<SearchNode> {
        private int moves;
        private int priority;
        private Board board;
        private SearchNode previous;

        public SearchNode(Board board) {
            this.board = board;
            this.previous = null;
            moves = 0;
            priority = board.manhattan() + moves;
        }

        public SearchNode(Board board, SearchNode previous) {
            this.board = board;
            this.previous = previous;
            moves = previous.moves + 1;
            priority = board.manhattan() + moves;
        }

        @Override
        public int compareTo(SearchNode o) {
            if (this.priority == o.priority) {
                return Integer.compare(this.moves, o.moves);
            }
            return Integer.compare(this.priority, o.priority);
        }
    }

    public Solver(Board initial) {
        // find a solution to the initial board (using the A* algorithm)
        isSolvable = true;
        MinPQ<SearchNode> gameTree = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinGameTree = new MinPQ<SearchNode>();
        SearchNode initSN = new SearchNode(initial);
        SearchNode twinInitSN = new SearchNode(initial.twin());

        gameTree.insert(initSN);
        twinGameTree.insert(twinInitSN);

        boolean searching = true;
        boolean twinSearching = true;
        while (searching || twinSearching) {
            if (!gameTree.isEmpty()) {
                SearchNode current = gameTree.delMin();
                //System.out.printf("%d%n", current.moves);
                if (current.board.isGoal()) {
                    moves = current.moves;
                    goals.enqueue(current);
                    break;
                }
                for (Board neighbor : current.board.neighbors()) {
                    if (current.previous == null
                            || !neighbor.equals(current.previous.board)) {
                        SearchNode next = new SearchNode(neighbor, current);
                        gameTree.insert(next);
                    }
                }
            } else {
                searching = false;
            }

            if (!twinGameTree.isEmpty()) {
                SearchNode current = twinGameTree.delMin();
                if (current.board.isGoal()) {
                    isSolvable = false;
                    break;
                }
                for (Board neighbor : current.board.neighbors()) {
                    if (current.previous == null
                            || !neighbor.equals(current.previous.board)) {
                        SearchNode next = new SearchNode(neighbor, current);
                        twinGameTree.insert(next);
                    }
                }
            } else {
                twinSearching = false;
            }
        }

    }

    public boolean isSolvable() {
        // is the initial board solvable?
        return isSolvable;
    }

    public int moves() {
        // min number of moves to solve initial board; -1 if no solution
        if (isSolvable) {
            return moves;
        } else {
            return -1;
        }
    }

    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if no solution
        if (isSolvable) {
            Stack<Board> solution = new Stack<Board>();
            SearchNode goal = goals.peek();

            for (SearchNode move = goal; move != null; move = move.previous) {
                solution.push(move.board);
            }

            return solution;

        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}