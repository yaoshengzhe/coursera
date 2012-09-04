import java.util.Comparator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

public class Solver {

    private class Puzzle implements Comparable<Puzzle> {

        private final int cost;
        private final Board board;
        private final Puzzle parent;
        private final int level;

        public Puzzle(Board board, int cost, Puzzle parent, int level) {
            this.board = board;
            this.cost = cost + level;
            this.parent = parent;
            this.level = level;
        }

        public Board getBoard() {
            return board;
        }

        public Puzzle getParent() {
            return parent;
        }

        public int getCost() {
            return cost;
        }

        public int getLevel() {
            return level;
        }

        @Override
        public int compareTo(Puzzle that) {
            if (board.equals(that.board)) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    private final Comparator<Puzzle> puzzleCostComparator = new Comparator<Puzzle>() {
        @Override
        public int compare(Puzzle a, Puzzle b) {
            return a.cost - b.cost;
        }
    };

    private int minMoveNum = 0;
    private MinPQ<Puzzle> priorityQueue = null;
    private MinPQ<Puzzle> twinPriorityQueue = null;
    private LinkedList<Board> solutionStepColl = null;

    public Solver(Board initial) {
        solutionStepColl = new LinkedList<Board>();
        priorityQueue = new MinPQ<Puzzle>(puzzleCostComparator);
        priorityQueue.insert(new Puzzle(initial, initial.manhattan(), null, 0));

        twinPriorityQueue = new MinPQ<Puzzle>(puzzleCostComparator);
        Board twinInitial = initial.twin();
        twinPriorityQueue.insert(new Puzzle(twinInitial, twinInitial.manhattan(), null, 0));

        Set<Puzzle> closedPuzzleSet = new TreeSet<Puzzle>();
        Set<Puzzle> twinClosedPuzzleSet = new TreeSet<Puzzle>();

        while (!priorityQueue.isEmpty() && !twinPriorityQueue.isEmpty()) {

            Puzzle puzzle = priorityQueue.delMin();
            Puzzle twinPuzzle = twinPriorityQueue.delMin();

            closedPuzzleSet.add(puzzle);
            twinClosedPuzzleSet.add(twinPuzzle);

            if (puzzle.getBoard().isGoal() || twinPuzzle.getBoard().isGoal()) {
                if (puzzle.getBoard().isGoal()) {
                    minMoveNum = 0;
                    Puzzle current = puzzle;
                    while (current.getParent() != null) {
                        solutionStepColl.addFirst(current.getBoard());
                        current = current.getParent();
                        ++minMoveNum;
                    }
                    solutionStepColl.addFirst(current.getBoard());
                } else {
                    solutionStepColl = null;
                    minMoveNum = -1;
                }
                break;
            }

            for (Board neighbor : puzzle.getBoard().neighbors()) {
                Puzzle p = new Puzzle(neighbor, neighbor.manhattan(), puzzle, puzzle.getLevel() + 1);

                if (!closedPuzzleSet.contains(p)) {
                    if ((puzzle.getParent() == null || !p.getBoard().equals(puzzle.getParent().getBoard()))) {
                        priorityQueue.insert(p);
                    }
                }
            }

            for (Board neighbor : twinPuzzle.getBoard().neighbors()) {
                Puzzle p = new Puzzle(neighbor, neighbor.manhattan(), twinPuzzle, twinPuzzle.getLevel() + 1);

                if (!twinClosedPuzzleSet.contains(p)) {
                    if ((twinPuzzle.getParent() == null || !p.getBoard().equals(twinPuzzle.getParent().getBoard()))) {
                        twinPriorityQueue.insert(p);
                    }
                }
            }
        }
    }

    public boolean isSolvable() {
        return solutionStepColl != null;
    }

    public int moves() {
        return minMoveNum;
    }

    public Iterable<Board> solution() {
        return solutionStepColl;
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
