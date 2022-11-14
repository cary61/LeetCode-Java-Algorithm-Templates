class SegmentTree {

    class Node {
        int value = DEFAULT_VALUE;
        Node lc, rc;
    }
    
    int DEFAULT_VALUE;
    int LOWERBOUND;
    int UPPERBOUND;
    Node root;
    java.util.function.IntBinaryOperator pushUp;
    
    public SegmentTree(int LOWERBOUND, int UPPERBOUND, int DEFAULT_VALUE, java.util.function.IntBinaryOperator pushUp) {
        this.LOWERBOUND = LOWERBOUND;
        this.UPPERBOUND = UPPERBOUND;
        this.DEFAULT_VALUE = DEFAULT_VALUE;
        this.pushUp = pushUp;
        this.root = new Node();
    }
    
    public void update(int idx, java.util.function.IntUnaryOperator operation) {
        update(idx, operation, root, LOWERBOUND, UPPERBOUND);
    }
    
    public void set(int idx, int val) {
        set(idx, val, root, LOWERBOUND, UPPERBOUND);
    }
    
    public void add(int idx, int val) {
        add(idx, val, root, LOWERBOUND, UPPERBOUND);
    }
    
    public void multiply(int idx, int val) {
        multiply(idx, val, root, LOWERBOUND, UPPERBOUND);
    }
    
    public int get(int idx) {
        return get(idx, root, LOWERBOUND, UPPERBOUND);
    }
    
    public int query(int l, int r) {
        return query(l, r, root, LOWERBOUND, UPPERBOUND);
    }



   // Internal Implementations 



    void update(int idx, java.util.function.IntUnaryOperator operation, Node node, int s, int t) {
        if (s == t) {
            node.value = operation.applyAsInt(node.value);
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        if (idx <= c)   update(idx, operation, node.lc, s, c);
        else            update(idx, operation, node.rc, c + 1, t);
        node.value = pushUp.applyAsInt(node.lc.value, node.rc.value);
    }

    void set(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.value = val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        if (idx <= c)   set(idx, val, node.lc, s, c);
        else            set(idx, val, node.rc, c + 1, t);
        node.value = pushUp.applyAsInt(node.lc.value, node.rc.value);
    }

    void add(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.value += val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        if (idx <= c)   add(idx, val, node.lc, s, c);
        else            add(idx, val, node.rc, c + 1, t);
        node.value = pushUp.applyAsInt(node.lc.value, node.rc.value);
    }

    void multiply(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.value *= val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        if (idx <= c)   multiply(idx, val, node.lc, s, c);
        else            multiply(idx, val, node.rc, c + 1, t);
        node.value = pushUp.applyAsInt(node.lc.value, node.rc.value);
    }

    int get(int idx, Node node, int s, int t) {
        if (s == t) {
            return node.value;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (idx <= c)   return node.lc == null ? DEFAULT_VALUE : get(idx, node.lc, s, c);
        else            return node.rc == null ? DEFAULT_VALUE : get(idx, node.rc, c + 1, t);
    }

    int query(int l, int r, Node node, int s, int t) {
        if (l <= s && t <= r) {
            return node.value;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        int ret = DEFAULT_VALUE;
        if (l <= c) ret = query(l, r, node.lc, s, c);
        if (c < r)  ret = pushUp.applyAsInt(ret, query(l, r, node.rc, c + 1, t));
        return ret;
    }
}