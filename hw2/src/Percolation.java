import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;
import java.util.List;


public class Percolation {
    int size;
    int num;
    int openNum;

    List<Integer> topNum;
    List<Integer> bottomNum;
    boolean[] sites;
    WeightedQuickUnionUF gridSet;

    public Percolation(int N) {
        num = N;
        openNum = 0;
        size = N * N;

        topNum = new ArrayList<>();
        bottomNum = new ArrayList<>();
        gridSet = new WeightedQuickUnionUF(size);
        sites = new boolean[size];
    }

    public void open(int row, int col) {
        int index = posIndex(row, col);
        sites[index] = true;
        unionAround(row, col);
        openNum++;

        appendSites(row, col);
    }

    public boolean isOpen(int row, int col) {
        return sites[posIndex(row, col)];
    }

    public boolean isFull(int row, int col) {
        int index = posIndex(row, col);
        for (int n : topNum) {
            if (gridSet.connected(index, n)) {
                return true;
            }
        }
        return false;
    }

    public int numberOfOpenSites() {
        return openNum;
    }

    public boolean percolates() {
        for (int n : bottomNum) {
            if (isFull(n)){
                return true;
            }
        }
        return false;
    }

    private int posIndex(int row, int col) {
        return row * num + col;
    }

    private void unionAround(int row, int col) {
        int[] n = new int[]{-1, 1};

        for(int x : n) {
            int aroundRow = row + x;

            if ((aroundRow < 0) || (aroundRow >= num)){
                continue;
            }
            if (isOpen(row, col) && isOpen(aroundRow, col)) {
                gridSet.union(posIndex(row,col), posIndex(aroundRow, col));
            }
        }
        for(int x : n) {
            int aroundCol = col + x;

            if ((aroundCol < 0) || (aroundCol >= num)){
                continue;
            }
            if (isOpen(row, col) && isOpen(row , aroundCol)) {
                gridSet.union(posIndex(row,col), posIndex(row, aroundCol));
            }
        }
    }

    private void appendSites(int row, int col) {
        if ((row == 0) && (isOpen(row, col))) {
            topNum.add(posIndex(row, col));
        }
        else if ((row == num - 1) && (isOpen(row, col))) {
            bottomNum.add(posIndex(row, col));
        }
    }

    private boolean isFull(int i) {
        for (int n : topNum) {
            if (gridSet.connected(i, n)) {
                return true;
            }
        }
        return false;
    }
}
