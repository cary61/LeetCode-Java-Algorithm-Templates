package no_comments.graph.union_find;

import java.util.Arrays;

class UnionFind {

    int n;
    int count;
    int[] parent;
    int[] size;

    UnionFind(int n) {
        this.n = n;
        this.count = n;
        this.parent = new int[n];
        for (int i = 0; i < n; ++i) {
            parent[i] = i;
        }
        this.size = new int[n];
        Arrays.fill(size, 1);
    }

    void merge(int a, int b) {
        a = getRoot(a);
        b = getRoot(b);
        if (a == b) {
            return;
        }
        if (size[a] < size[b]) {
            parent[a] = b;
            size[b] += size[a];
        } else {
            parent[b] = a;
            size[a] += size[b];
        }
        --count;
    }

    boolean isConnected(int a, int b) {
        return getRoot(a) == getRoot(b);
    }

    int getRoot(int x) {
        if (x == parent[x]) {
            return x;
        }
        return parent[x] = getRoot(parent[x]);
    }
}