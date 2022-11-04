package cary61.algorithm.graph.union_find;

import java.util.*;

/**
 * A UnionFind that maintains the connectivity of n vertexes.
 * n should be finalized before initializing UnionFind.
 * Implemented by array.
 *
 * @author cary61
 */
class UnionFind {

    /**
     * The quantity of vertexes.
     */
    int n;

    /**
     * The quantity of connectivity components, maintained when merging.
     */
    int count;

    /**
     * Represent the parent node of each vertex.
     */
    int[] parent;

    /**
     * Represent the size of the connectivity component that one vertex is in.
     * size[i] only valid when i == parent[i].
     * Use size[getRoot(i)] to guarantee valid.
     */
    int[] size;

    /**
     * Instantiate a UnionFind that maintains the connectivity of n vertexes.
     *
     * @param n the quantity of vertexes
     */
    UnionFind(int n) {
        this.n = n;
        this.count = n;
        this.parent = new int[n];
        for (int i = 0; i < n; ++i) {
            parent[i] = i;  // Each vertex is initially in a connectivity component that only contains itself as root.
        }
        this.size = new int[n];
        Arrays.fill(size, 1);  // Initially every connectivity component only has one vertex in.
    }

    /**
     * Merge the connectivity components that vertex a and vertex b are in together.
     * If they are already in the same connectivity component, nothing will be done.
     *
     * @param a the index of vertex a
     * @param b the index of vertex b
     */
    void merge(int a, int b) {
        a = getRoot(a);
        b = getRoot(b);
        if (a == b) {  // a and b are already in the same connectivity component.
            return;
        }
        if (size[a] < size[b]) {  // Keep the depth as shallow as possible, to optimize the efficiency to get root.
            parent[a] = b;
            size[b] += size[a];
        } else {
            parent[b] = a;
            size[a] += size[b];
        }
        --count;  // Maintain the quantity of connectivity components
    }

    /**
     * Check if vertex a and vertex b are in the same connectivity component.
     *
     * @param a the index of vertex a
     * @param b the index of vertex b
     * @return if they are in the same connectivity or not
     */
    boolean isConnected(int a, int b) {
        return getRoot(a) == getRoot(b);
    }

    /**
     * Get the root of the connectivity component that vertex x is in.
     *
     * @param x the index of vertex x
     * @return the root of the connectivity component that vertex x is in
     */
    int getRoot(int x) {
        if (x == parent[x]) {
            return x;
        }
        return parent[x] = getRoot(parent[x]);
    }
}