//import java.util.*;

class UnionFind_Generics<T> {

    int serial = 0;
    int n;
    int count;
    java.util.Map<T, Integer> vertexToSerial = new java.util.HashMap<>();
    java.util.Map<Integer, Integer> parent = new java.util.HashMap<>();
    java.util.Map<Integer, Integer> size = new java.util.HashMap<>();

    UnionFind_Generics() {
        this.n = 0;
        this.count = 0;
    }

    UnionFind_Generics(T[] arr) {
        this.n = arr.length;
        this.count = n;
        for (T element : arr) {
            vertexToSerial.put(element, serial);
            parent.put(serial, serial);
            size.put(serial, 1);
            ++serial;
        }
    }

    boolean contains(T x) {
        return vertexToSerial.containsKey(x);
    }

    int getSerial(T x) {
        return vertexToSerial.get(x);
    }

    int insert(T x) {
        vertexToSerial.put(x, serial);
        parent.put(serial, serial);
        size.put(serial, 1);
        ++n;
        ++count;
        return serial++;
    }

    void merge(T a, T b) {
        mergeBySerial(vertexToSerial.get(a), vertexToSerial.get(b));
    }

    int getRoot(T x) {
        return getRootBySerial(vertexToSerial.get(x));
    }

    boolean isConnected(T a, T b) {
        return getRootBySerial(vertexToSerial.get(a)) == getRootBySerial(vertexToSerial.get(b));
    }

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

    void mergeAuto(T a, T b) {
        int aSerial = vertexToSerial.computeIfAbsent(a, vertex -> insert(vertex));
        int bSerial = vertexToSerial.computeIfAbsent(b, vertex -> insert(vertex));
        mergeBySerial(aSerial, bSerial);
    }

    int getRootAuto(T x) {
        if (vertexToSerial.containsKey(x)) {
            return getRootBySerial(vertexToSerial.get(x));
        }
        return vertexToSerial.computeIfAbsent(x, vertex -> insert(vertex));
    }

    boolean isConnectedAuto(T a, T b) {
        return getRootAuto(a) == getRootAuto(b);
    }

    void mergeBySerial(int a, int b) {
        a = getRootBySerial(a);
        b = getRootBySerial(b);
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

    int getRootBySerial(int x) {
        int parent_node = parent.get(x);
        if (parent_node == x) {
            return x;
        }
        int root = getRootBySerial(parent_node);
        parent.put(x, root);
        return root;
    }

    boolean isConnectedBySerial(int a, int b) {
        return getRootBySerial(a) == getRootBySerial(b);
    }
}
