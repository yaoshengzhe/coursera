
public class Exercise {

    public static void main(String[] args) {
        QuickFindUF uf = new QuickFindUF(10);

        uf.union(0, 9);
        uf.union(8, 4);
        uf.union(1, 5);
        uf.union(0, 8);
        uf.union(4, 3);
        uf.union(2, 3);

        WeightedQuickUnionUF weightedUF = new WeightedQuickUnionUF(10);

        weightedUF.union(7, 9);
        weightedUF.union(3, 1);
        weightedUF.union(0, 5);
        weightedUF.union(9, 2);
        weightedUF.union(8, 6);
        weightedUF.union(8, 1);
        weightedUF.union(2, 0);
        weightedUF.union(7, 4);
        weightedUF.union(8, 2);

    }
}
