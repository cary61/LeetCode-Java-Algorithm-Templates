//import java.util.*;

/**
 * A UnionFind that maintains the connectivity of n vertexes.
 * n can be not finalized when initializing UnionFind, and be dynamicallly set later.
 * If you are sure that the vertexes you are operating are already in UnionFind, do operatings with normal methods.
 * If not, do operatings with the methods whose suffix is -Auto. They will automatically insert the vertexs that are not in UnionFind.
 * The vertexes are generics, so you can use strings as vertexes.
 * Implemented by HashMap.
 *
 * @author cary61
 */
class UnionFind_Generics<T> {

    /**
     * The serial number of vertex.
     */
    int serial = 0;

    /**
     * The quantity of vertexes.
     */
    int n;

    /**
     * The quantity of connectivity components, maintained when merging.
     */
    int count;

    /**
     * Store the map from vertex to serial number.
     */
    java.util.Map<T, Integer> vertexToSerial = new java.util.HashMap<>();

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
    UnionFind_Generics() {
        this.n = 0;
        this.count = 0;
    }

    /**
     * Instantiate a UnionFind that maintains the connectivity of n vertexes.
     *
     * @param nums the vertex array
     */
    UnionFind_Generics(T[] arr) {
        this.n = arr.length;
        this.count = n;
        for (T element : arr) {
            vertexToSerial.put(element, serial);
            parent.put(serial, serial); // Each vertex is initially in a connectivity component that only contains itself as root.
            size.put(serial, 1); // Initially every connectivity component only has one vertex in.
            ++serial;
        }
    }

    /**
     * Check if a vertex is already in UnionFind.
     * 
     * @param x the index of vertex
     * @return if it is already in
     */
    boolean contains(T x) {
        return vertexToSerial.containsKey(x);
    }

    /**
     * Get the serial number of the vertex.
     * @param x the vertex
     * @return the serial number
     */
    int getSerial(T x) {
        return vertexToSerial.get(x);
    }

    /**
     * Insert a new node to UnionFind.
     * 
     * @param x the new node that will be inserted
     */
    int insert(T x) {
        vertexToSerial.put(x, serial);
        parent.put(serial, serial);
        size.put(serial, 1);
        ++n;
        ++count;
        return serial++;
    }

    /**
     * Merge the connectivity components that vertex a and vertex b are in together.
     * If they are already in the same connectivity component, nothing will be done. 
     * 
     * @param a
     * @param b
     */
    void merge(T a, T b) {
        mergeBySerial(vertexToSerial.get(a), vertexToSerial.get(b));
    }

    /**
     * Get the serial number of root of the connectivity component that vertex x is in.
     * 
     * @param x the vertex
     * @return the serial number of root
     */
    int getRoot(T x) {
        return getRootBySerial(vertexToSerial.get(x));
    }

    /**
     * Check if vertex a and vertex b are in the same connectivity component.
     * 
     * @param a the vertex a
     * @param b the vertex b
     * @return if they are in the same connectivity or not
     */
    boolean isConnected(T a, T b) {
        return getRootBySerial(vertexToSerial.get(a)) == getRootBySerial(vertexToSerial.get(b));
    }

    /**
     * Insert a new node to UnionFind. If alreary exists, do nothing.
     * 
     * @param x the new node that will be inserted
     */
    int insertAuto(T x) {
        if (vertexToSerial.containsKey(x)) {
            return vertexToSerial.get(x);
        }
        vertexToSerial.put(x, serial);
        parent.put(serial, serial);
        size.put(serial, 1);
        ++n;
        ++count;
        return serial++;
    }

    /**
     * Merge the connectivity components that vertex a and vertex b are in together.
     * Any vertex is not in UnionFind will be inserted.
     * If they are already in the same connectivity component, nothing will be done.
     *
     * @param a the index of vertex a
     * @param b the index of vertex b
     */
    void mergeAuto(T a, T b) {
        int aSerial = vertexToSerial.computeIfAbsent(a, vertex -> insert(vertex));
        int bSerial = vertexToSerial.computeIfAbsent(b, vertex -> insert(vertex));
        mergeBySerial(aSerial, bSerial);
    }

    /**
     * Get the root of the connectivity component that vertex x is in.
     * If vertex x is not in UnionFind, it will be inserted.
     *
     * @param x the index of vertex x
     * @return the root of the connectivity component that vertex x is in
     */
    int getRootAuto(T x) {
        if (vertexToSerial.containsKey(x)) {
            return getRootBySerial(vertexToSerial.get(x));
        }
        return vertexToSerial.computeIfAbsent(x, vertex -> insert(vertex));
    }

    /**
     * Check if vertex a and vertex b are in the same connectivity component.
     * Any vertex is not in UnionFind will be inserted.
     *
     * @param a the index of vertex a
     * @param b the index of vertex b
     * @return if they are in the same connectivity or not
     */
    boolean isConnectedAuto(T a, T b) {
        return getRootAuto(a) == getRootAuto(b);
    }

    /**
     * Merge the connectivity components that vertex a and vertex b are in together.
     * If they are already in the same connectivity component, nothing will be done.
     *
     * @param a the serial number of vertex a
     * @param b the serial number of vertex b
     */
    void mergeBySerial(int a, int b) {
        a = getRootBySerial(a);
        b = getRootBySerial(b);
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
     * @param x the serial number of vertex x
     * @return the serial number of the root of the connectivity component that vertex x is in
     */
    int getRootBySerial(int x) {
        int parent_node = parent.get(x);
        if (parent_node == x) {
            return x;
        }
        int root = getRootBySerial(parent_node);
        parent.put(x, root);
        return root;
    }

    /**
     * Check if vertex a and vertex b are in the same connectivity component.
     *
     * @param a the serial number of vertex a
     * @param b the serial number of vertex b
     * @return if they are in the same connectivity or not
     */
    boolean isConnectedBySerial(int a, int b) {
        return getRootBySerial(a) == getRootBySerial(b);
    }
}
