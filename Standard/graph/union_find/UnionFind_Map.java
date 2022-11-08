import java.util.*;








// It should be generics












/**
 * A UnionFind that maintains the connectivity of n vertexes.
 * n can be not finalized when initializing UnionFind, and be dynamicallly set later.
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
	Map<Integer, Integer> parent = new HashMap<>();
    
    /**
     * Represent the size of the connectivity component that one vertex is in.
     * size.get(i) only valid when i == parent.get(i).
     * Use size.get(getRoot(i)) to guarantee valid.
     */
	Map<Integer, Integer> size = new HashMap<>();

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
     * @param n the quantity of vertexes
     */
	UnionFind_Map(int[] nums) {
	    this.n = nums.length;
		this.count = n;
		for (int num : nums) {
			parent.put(num, num);  // Each vertex is initially in a connectivity component that only contains itself as root.
            size.put(num, 1);  // Initially every connectivity component only has one vertex in.
		}
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
        int aSize = size.get(a);
        int bSize = size.get(b);
		if (aSize < bSize) {  // Keep the depth as shallow as possible, to optimize the efficiency to get root.
			parent.put(a, b);
			size.put(b, aSize + bSize);
		} else {
			parent.put(b, a);
			size.put(a, bSize + aSize);
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
		int parent = parent.get(x);
		if (parent == x) {
			return x;
		}
		int root = getRoot(parent);
		parent.put(x, root);
		return root;
	}
}
