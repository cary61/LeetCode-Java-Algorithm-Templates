class SegmentTree_LCM {
    
    class Node {
        int lcm;
        Node lc, rc;
    }
    
    int LOWERBOUND;
    int UPPERBOUND;
    Node root;
    int[] arr;
    
    public SegmentTree_LCM(int[] arr) {
        this.LOWERBOUND = 0;
        this.UPPERBOUND = arr.length - 1;
        this.root = new Node();
        this.arr = arr;
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
        return arr[idx];
    }
    
    public int lcm(int l, int r) {
        return lcm(l, r, root, LOWERBOUND, UPPERBOUND);
    }
    


    // Internal Implementations
    


    void set(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.lcm = val;
            arr[s] = val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (idx <= c)   set(idx, val, node.lc, s, c);
        else            set(idx, val, node.rc, c + 1, t);
        node.lcm = getLcm(node.lc.lcm, node.rc.lcm);
    }

    void add(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.lcm += val;
            arr[s] += val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (idx <= c)   add(idx, val, node.lc, s, c);
        else            add(idx, val, node.rc, c + 1, t);
        node.lcm = getLcm(node.lc.lcm, node.rc.lcm);
    }

    void multiply(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.lcm *= val;
            arr[s] *= val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (idx <= c)   multiply(idx, val, node.lc, s, c);
        else            multiply(idx, val, node.rc, c + 1, t);
        node.lcm = getLcm(node.lc.lcm, node.rc.lcm);
    }

    int lcm(int l, int r, Node node, int s, int t) {
        if (l <= s && t <= r) {
            return node.lcm;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        int ret = -1;
        if (l <= c) ret = lcm(l, r, node.lc, s, c);
        if (c < r)  ret = ret == -1 ? lcm(l, r, node.rc, c + 1, t): getLcm(ret, lcm(l, r, node.rc, c + 1, t));
        return ret;
    }

    void build(int[] arr, Node node, int s, int t) {
        if (s == t) {
            node.lcm = arr[s];
            return;
        }
        int c = (s & t) +((s ^ t) >> 1);
        node.lc = new Node();
        node.rc = new Node();
        build(arr, node.lc, s, c);
        build(arr, node.rc, c + 1, t);
        node.lcm = getLcm(node.lc.lcm, node.rc.lcm);
    }

    int getGcd(int a, int b) {
        int m;
        while (b != 0) {
            m = a % b;
            a = b;
            b = m;
        }
        return a;
    }

    int getLcm(int a, int b) {
        return a / getGcd(a, b) * b;
    }
}
