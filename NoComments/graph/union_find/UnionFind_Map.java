//import java.util.*;

class UnionFind_Map {

    int n;
    int count;
    java.util.Map<Integer, Integer> parent = new java.util.HashMap<>();
    java.util.Map<Integer, Integer> size = new java.util.HashMap<>();

    UnionFind_Map() {
        this.n = 0;
        this.count = 0;
    }

    UnionFind_Map(int[] nums) {
        this.n = nums.length;
        this.count = n;
        for (int num : nums) {
            parent.put(num, num);
            size.put(num, 1);
        }
    }

    boolean contains(int x) {
        return parent.containsKey(x);
    }

    void insert(int x) {
        parent.put(x, x);
        size.put(x, 1);
        ++n;
        ++count;
    }

    void merge(int a, int b) {
        a = getRoot(a);
        b = getRoot(b);
        if (a == b) {
            return;
        }
        int aSize = size.get(a);
        int bSize = size.get(b);
        if (aSize < bSize) {
            parent.put(a, b);
            size.put(b, aSize + bSize);
        } else {
            parent.put(b, a);
            size.put(a, bSize + aSize);
        }
        --count;
    }

    int getRoot(int x) {
        int parent_node = parent.get(x);
        if (parent_node == x) {
            return x;
        }
        int root = getRoot(parent_node);
        parent.put(x, root);
        return root;
    }

    boolean isConnected(int a, int b) {
        return getRoot(a) == getRoot(b);
    }

    void insertAuto(int x) {
        if (parent.containsKey(x)) {
            return;
        }
        parent.put(x, x);
        size.put(x, 1);
        ++n;
        ++count;
    }

    void mergeAuto(int a, int b) {
        if (!parent.containsKey(a))     insert(a);
        else                            a = getRoot(a);
        if (!parent.containsKey(b))     insert(b);
        else                            b = getRoot(b);
        merge(a, b);
    }

    int getRootAuto(int x) {
        if (!parent.containsKey(x)) {
            insert(x);
            return x;
        }
        return getRoot(x);
    }

    boolean isConnectedAuto(int a, int b) {
        return getRootAuto(a) == getRootAuto(b);
    }
}
