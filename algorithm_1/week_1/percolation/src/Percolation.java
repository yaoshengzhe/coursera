/* @(#)Percolation.java
 */
/**
 * 
 *
 * @author Shengzhe Yao
 */

public class Percolation {

    private WeightedQuickUnionUF unionFind = null;
    private SiteStatus[][] grid = null;
    private boolean[][] connectedToBottom = null;
    private boolean isPercolated = false;

    private enum SiteStatus {
        BLOCKED, OPEN, FULL
    }

    private int N = 0;
    public Percolation(int N) {
        unionFind = new WeightedQuickUnionUF(N*N);
        grid = new SiteStatus[N][N];
        connectedToBottom = new boolean[N][N];
        this.N = N;
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                grid[i][j] = SiteStatus.BLOCKED;
                if (i == N - 1) {
                    connectedToBottom[i][j] = true;
                } else {
                    connectedToBottom[i][j] = false;
                }
            }
        }
    }

    private void ensureIndiceInBound(int i, int j) {
        if (!isIndiceInBound(i, j)) {
            throw new IndexOutOfBoundsException(String.format("Indices (%d, %d) out of bound for N = %d.", i, j, N));
        }
    }

    private boolean isIndiceInBound(int i, int j) {
        return i >= 1 && j >= 1 && i <= N && j <= N;
    }

    private int toUnionFindIndex(int i, int j) {
        return (i-1) * N + j-1;
    }

    private int[] fromUnionFindIndex(int val) {
        int i = (val / N) + 1;
        int j = val - (i-1)*N + 1;
        return new int[] {i, j};
    }

    public void open(int i, int j) {
        ensureIndiceInBound(i, j);
        if (!isOpen(i, j)) {
            if (i == 1) {
                setSiteStatus(i, j, SiteStatus.FULL);
                if (connectedToBottom[i-1][j-1]) {
                    isPercolated = true;
                }
            } else {
                setSiteStatus(i, j, SiteStatus.OPEN);
                if (isOpenWithoutException(i-1, j)) {
                    unionNeighbor(i, j, i-1, j);
                }
                if (isOpenWithoutException(i, j-1)) {
                    unionNeighbor(i, j, i, j-1);
                }
                if (isOpenWithoutException(i, j+1)) {
                    unionNeighbor(i, j, i, j+1);
                }
            }
            if (isOpenWithoutException(i+1, j)) {
                unionNeighbor(i, j, i+1, j);
            }
        }
    }

    public boolean isOpen(int i, int j) {
        return getSiteStatus(i, j) == SiteStatus.OPEN
            || getSiteStatus(i, j) == SiteStatus.FULL;
    }

    private boolean isOpenWithoutException(int i, int j) {
        return isIndiceInBound(i, j) && isOpen(i, j);
    }

    public boolean isFull(int i, int j) {
        return isOpen(i, j) && (getSiteStatus(i, j) == SiteStatus.FULL || isRootFull(i, j));
    }

    private SiteStatus getSiteStatus(int i, int j) {
        ensureIndiceInBound(i, j);
        return grid[i-1][j-1];
    }

    private void setSiteStatus(int i, int j, SiteStatus status) {
        ensureIndiceInBound(i, j);
        grid[i-1][j-1] = status;
    }

    private void unionNeighbor(int i, int j, int x, int y) {

        int leftIndex = toUnionFindIndex(i, j);
        int rightIndex = toUnionFindIndex(x, y);

        int[] leftRoot = fromUnionFindIndex(unionFind.find(leftIndex));
        int[] rightRoot = fromUnionFindIndex(unionFind.find(rightIndex));

        if (connectedToBottom[leftRoot[0]-1][leftRoot[1]-1]
            || connectedToBottom[rightRoot[0]-1][rightRoot[1]-1]) {
            connectedToBottom[leftRoot[0]-1][leftRoot[1]-1] = true;
            connectedToBottom[rightRoot[0]-1][rightRoot[1]-1] = true;
        }

        if (getSiteStatus(leftRoot[0], leftRoot[1]) == SiteStatus.FULL
            || getSiteStatus(rightRoot[0], rightRoot[1]) == SiteStatus.FULL) {
            unionFind.union(leftIndex, rightIndex);
            int[] root = fromUnionFindIndex(unionFind.find(leftIndex));
            setSiteStatus(root[0], root[1], SiteStatus.FULL);
            if (connectedToBottom[root[0]-1][root[1]-1]) {
                isPercolated = true;
            }
            setSiteStatus(i, j, SiteStatus.FULL);
            setSiteStatus(x, y, SiteStatus.FULL);
        } else {
            unionFind.union(leftIndex, rightIndex);
        }
    }

    private boolean isRootFull(int i, int j) {
        ensureIndiceInBound(i, j);
        int[] index = fromUnionFindIndex(unionFind.find(toUnionFindIndex(i, j)));
        if (getSiteStatus(index[0], index[1]) == SiteStatus.FULL) {
            setSiteStatus(i, j, SiteStatus.FULL);
            return true;
        }
        return false;
    }

    public boolean percolates() {
        return isPercolated;
    }
}
