import java.util.ArrayList;
import java.util.List;

public class Board {

    private int[][] board = null;
    private int dimension = 0;
    private int blankRow = -1;
    private int blankColumn = -1;

    public Board(int[][] blocks) {
        dimension = blocks.length;
        this.board = new int[dimension][dimension];

        for (int i = 0; i < dimension; ++i) {
            for (int j = 0; j < dimension; ++j) {
                board[i][j] = blocks[i][j];

                if (board[i][j] == 0) {
                    blankRow = i;
                    blankColumn = j;
                }
            }
        }
    }

    public int dimension() {
        return dimension;
    }

    public int hamming() {
        int cost = 0;
        for (int i = 0; i < dimension(); ++i) {
            for (int j = 0; j < dimension(); ++j) {

                if (board[i][j] != 0 && board[i][j] != (i * dimension() + j + 1)) {
                    ++cost;
                }
            }
        }
        return cost;
    }

    public int manhattan() {
        int cost = 0;
        for (int i = 0; i < dimension(); ++i) {
            for (int j = 0; j < dimension(); ++j) {
                if (board[i][j] != 0) {
                    int num = board[i][j] - 1;
                    int correctRow = num / dimension();
                    int correctColumn = num - correctRow * dimension();
                    cost += Math.abs(i - correctRow);
                    cost += Math.abs(j - correctColumn);
                }
            }
        }
        return cost;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public Board twin() {
        Board that = new Board(board);

        for (int i = 0; i < dimension(); ++i) {
            if (i == blankRow) {
                continue;
            }
            that.swap(i, 0, i, 1);
            break;
        }
        return that;
    }

    @Override
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }

        if (y == null) {
            return false;
        }

        if (this.getClass() != y.getClass()) {
            return false;
        }

        Board that = (Board) y;

        if (this.dimension() != that.dimension()) {
            return false;
        }

        for (int i = 0; i < dimension(); ++i) {
            for (int j = 0; j < dimension(); ++j) {
                if (board[i][j] != that.board[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public Iterable<Board> neighbors() {
        List<Board> neighborColl = new ArrayList<Board>();
        Board newBoard = moveBlank(Direction.UP);
        if (newBoard != null) {
            neighborColl.add(newBoard);
        }

        newBoard = moveBlank(Direction.DOWN);
        if (newBoard != null) {
            neighborColl.add(newBoard);
        }

        newBoard = moveBlank(Direction.LEFT);
        if (newBoard != null) {
            neighborColl.add(newBoard);
        }

        newBoard = moveBlank(Direction.RIGHT);
        if (newBoard != null) {
            neighborColl.add(newBoard);
        }

        return neighborColl;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(dimension());
        builder.append('\n');
        for (int i = 0; i < dimension(); ++i) {
            for (int j = 0; j < dimension(); ++j) {
                builder.append(board[i][j]);
                builder.append(' ');
            }
            builder.deleteCharAt(builder.length() - 1);
            builder.append('\n');
        }

        builder.deleteCharAt(builder.length() - 1);

        return builder.toString();
    }

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private Board moveBlank(Direction direction) {
        Board that = null;
        if (direction == Direction.UP) {
            if (blankRow - 1 > -1) {
                that = new Board(board);
                that.swap(blankRow, blankColumn, blankRow - 1, blankColumn);
                that.blankRow--;
            }
        } else if (direction == Direction.DOWN) {
            if (blankRow + 1 < dimension()) {
                that = new Board(board);
                that.swap(blankRow, blankColumn, blankRow + 1, blankColumn);
                that.blankRow++;
            }
        } else if (direction == Direction.LEFT) {
            if (blankColumn - 1 > -1) {
                that = new Board(board);
                that.swap(blankRow, blankColumn, blankRow, blankColumn - 1);
                that.blankColumn--;
            }
        } else if (direction == Direction.RIGHT) {
            if (blankColumn + 1 < dimension()) {
                that = new Board(board);
                that.swap(blankRow, blankColumn, blankRow, blankColumn + 1);
                that.blankColumn++;
            }
        }
        return that;
    }

    private void swap(int row, int column, int targetRow, int targetColumn) {
        int tmp = board[row][column];
        board[row][column] = board[targetRow][targetColumn];
        board[targetRow][targetColumn] = tmp;
    }
}
