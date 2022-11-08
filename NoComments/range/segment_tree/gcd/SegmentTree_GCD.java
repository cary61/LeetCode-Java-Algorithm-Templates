
class SegmentTree_GCD {
    
    class Node {
        int gcd;
        Node lc, rc;
    }
    
    int LOWERBOUND;
    int UPPERBOUND;
    Node root;
    int[] arr;
    
    public SegmentTree_GCD(int[] arr) {
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
    
    public int gcd(int l, int r) {
        return gcd(l, r, root, LOWERBOUND, UPPERBOUND);
    }

    // Internal Implementations

    void set(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.gcd = val;
            arr[s] = val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (idx <= c)   set(idx, val, node.lc, s, c);
        else            set(idx, val, node.rc, c + 1, t);
        node.gcd = getGcd(node.lc.gcd, node.rc.gcd);
    }

    void add(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.gcd += val;
            arr[s] += val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (idx <= c)   add(idx, val, node.lc, s, c);
        else            add(idx, val, node.rc, c + 1, t);
        node.gcd = getGcd(node.lc.gcd, node.rc.gcd);
    }

    void multiply(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.gcd *= val;
            arr[s] *= val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (idx <= c)   multiply(idx, val, node.lc, s, c);
        else            multiply(idx, val, node.rc, c + 1, t);
        node.gcd = getGcd(node.lc.gcd, node.rc.gcd);
    }

    int get(int idx, Node node, int s, int t) {
        if (s == t) {
            return node.gcd;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (idx <= c)   return get(idx, node.lc, s, c);
        else            return get(idx, node.rc, c + 1, t);
    }

    int gcd(int l, int r, Node node, int s, int t) {
        if (l <= s && t <= r) {
            return node.gcd;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        int ret = -1;
        if (l <= c) ret = gcd(l, r, node.lc, s, c);
        if (c < r)  ret = ret == -1 ? gcd(l, r, node.rc, c + 1, t): getGcd(ret, gcd(l, r, node.rc, c + 1, t));
        return ret;
    }

    void build(int[] arr, Node node, int s, int t) {
        if (s == t) {
            node.gcd = arr[s];
            return;
        }
        int c = (s & t) +((s ^ t) >> 1);
        node.lc = new Node();
        node.rc = new Node();
        build(arr, node.lc, s, c);
        build(arr, node.rc, c + 1, t);
        node.gcd = getGcd(node.lc.gcd, node.rc.gcd);
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
}
