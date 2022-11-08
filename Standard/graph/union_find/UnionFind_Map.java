//import java.util.*;

/**
 * A UnionFind that maintains the connectivity of n vertexes.
 * n can be not finalized when initializing UnionFind, and be dynamicallly set later.
 * If you are sure that the vertexes you are operating are already in UnionFind, do operatings with normal methods.
 * If not, do operatings with the methods whose suffix is -Auto. They will automatically insert the vertexs that are not in UnionFind.
 * Implemented by HashMap.
 *
 * @author cary61
 */
class UnionFind_Map {

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
    java.util.Map<Integer, Integer> parent = new java.util.HashMap<>();

    /**
     * Represent the size of the connectivity component that one vertex is in.
     * size.get(i) only valid when i == parent.get(i).
     * Use size.get(getRoot(i)) to guarantee valid.
     */
    java.util.Map<Integer, Integer> size = new java.util.HashMap<>();

    /**
     * Instantiate a UnionFind that contains no vertex.
     */
    UnionFind_Map() {
        this.n = 0;
        this.count = 0;
    }

    /**
     * Instantiate a UnionFind that maintains the connectivity of n vertexes.
     *
     * @param nums the vertex array
     */
    UnionFind_Map(int[] nums) {
        this.n = nums.length;
        this.count = n;
        for (int num : nums) {
            parent.put(num, num); // Each vertex is initially in a connectivity component that only contains itself as root.
            size.put(num, 1); // Initially every connectivity component only has one vertex in.
        }
    }

    /**
     * Check if a vertex is already in UnionFind.
     * 
     * @param x the index of vertex
     * @return if it is already in
     */
    boolean contains(int x) {
        return parent.containsKey(x);
    }

    /**
     * Insert a new node to UnionFind.
     * 
     * @param x the new node that will be inserted
     */
    void insert(int x) {
        parent.put(x, x);
        size.put(x, 1);
        ++n;
        ++count;
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
        if (a == b) { // a and b are already in the same connectivity component.
            return;
        }
        int aSize = size.get(a);
        int bSize = size.get(b);
        if (aSize < bSize) { // Keep the depth as shallow as possible, to optimize the efficiency to get root.
            parent.put(a, b);
            size.put(b, aSize + bSize);
        } else {
            parent.put(b, a);
            size.put(a, bSize + aSize);
        }
        --count; // Maintain the quantity of connectivity components
    }

    /**
     * Get the root of the connectivity component that vertex x is in.
     *
     * @param x the index of vertex x
     * @return the root of the connectivity component that vertex x is in
     */
    int getRoot(int x) {
        int parent_node = parent.get(x);
        if (parent_node == x) {
            return x;
        }
        int root = getRoot(parent_node);
        parent.put(x, root);
        return root;
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
     * Insert a new node to UnionFind. If alreary exists, do nothing.
     * 
     * @param x the new node that will be inserted
     */
    void insertAuto(int x) {
        if (parent.containsKey(x)) {
            return;
        }
        parent.put(x, x);
        size.put(x, 1);
        ++n;
        ++count;
    }

    /**
     * Merge the connectivity components that vertex a and vertex b are in together.
     * Any vertex is not in UnionFind will be inserted.
     * If they are already in the same connectivity component, nothing will be done.
     *
     * @param a the index of vertex a
     * @param b the index of vertex b
     */
    void mergeAuto(int a, int b) {
        if (!parent.containsKey(a))     insert(a);
        else                            a = getRoot(a);
        if (!parent.containsKey(b))     insert(b);
        else                            b = getRoot(b);
        merge(a, b);
    }

    /**
     * Get the root of the connectivity component that vertex x is in.
     * If vertex x is not in UnionFind, it will be inserted.
     *
     * @param x the index of vertex x
     * @return the root of the connectivity component that vertex x is in
     */
    int getRootAuto(int x) {
        if (!parent.containsKey(x)) {
            insert(x);
            return x;
        }
        return getRoot(x);
    }

    /**
     * Check if vertex a and vertex b are in the same connectivity component.
     * Any vertex is not in UnionFind will be inserted.
     *
     * @param a the index of vertex a
     * @param b the index of vertex b
     * @return if they are in the same connectivity or not
     */
    boolean isConnectedAuto(int a, int b) {
        return getRootAuto(a) == getRootAuto(b);
    }
}
