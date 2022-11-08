
class SegmentTree_Product {
    
    static final int DEFAULT_VALUE = 1;
    
    class Node {
        int product = DEFAULT_VALUE;
        Node lc, rc;
    }
    
    int LOWERBOUND;
    int UPPERBOUND;
    Node root;
    
    public SegmentTree_Product() {
        this.LOWERBOUND = Integer.MIN_VALUE;
        this.UPPERBOUND = Integer.MAX_VALUE;
        this.root = new Node();
    }
    
    public SegmentTree_Product(int LOWERBOUND, int UPPERBOUND) {
        this.LOWERBOUND = LOWERBOUND;
        this.UPPERBOUND = UPPERBOUND;
        this.root = new Node();
    }
    
    public SegmentTree_Product(int[] arr) {
        this.LOWERBOUND = 0;
        this.UPPERBOUND = arr.length - 1;
        this.root = new Node();
        build(arr, root, LOWERBOUND, UPPERBOUND);
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
    
    public int product(int l, int r) {
        return product(l, r, root, LOWERBOUND, UPPERBOUND);
    }



    // Internal Inpletations



    void set(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.product = val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        if (idx <= c)   set(idx, val, node.lc, s, c);
        else            set(idx, val, node.rc, c + 1, t);
        node.product = node.lc.product * node.rc.product;
    }

    void add(int idx, int val, Node node, int s, int t) {
        node.product += val;
        if (s == t) {
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        if (idx <= c)   add(idx, val, node.lc, s, c);
        else            add(idx, val, node.rc, c + 1, t);
    }

    void multiply(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.product *= val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (idx <= c)   multiply(idx, val, node.lc, s, c);
        else            multiply(idx, val, node.rc, c + 1, t);
        node.product = node.lc.product * node.rc.product;
    }

    int get(int idx, Node node, int s, int t) {
        if (s == t) {
            return node.product;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (idx <= c)   return node.lc == null ? DEFAULT_VALUE : get(idx, node.lc, s, c);
        else            return node.rc == null ? DEFAULT_VALUE : get(idx, node.rc, c + 1, t);
    }

    int product(int l, int r, Node node, int s, int t) {
        if (l <= s && t <= r) {
            return node.product;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        int ret = 1;
        if (l <= c) ret = product(l, r, node.lc, s, c);
        if (c < r)  ret += product(l, r, node.rc, c + 1, t);
        return ret;
    }

    void build(int[] arr, Node node, int s, int t) {
        if (s == t) {
            node.product = arr[s];
            return;
        }
        int c = (s & t) +((s ^ t) >> 1);
        node.lc = new Node();
        node.rc = new Node();
        build(arr, node.lc, s, c);
        build(arr, node.rc, c + 1, t);
        node.product = node.lc.product * node.rc.product;
    }
}
