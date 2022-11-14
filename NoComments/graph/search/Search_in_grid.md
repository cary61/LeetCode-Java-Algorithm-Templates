# Search in grid





## DFS

```java
int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
// int[][] dirs = new int[][]{{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

void dfs(int row, int col, boolean[][] vis) {
    
    for (int[] dir : dirs) {
        int x = row + dir[0];
        int y = col + dir[1];
        if (0 <= x && x < m && 0 <= y && y < n && !vis[x][y]) {
            vis[x][y] = true;
            dfs(x, y, vis);
            vis[x][y] = false;
        }
    }
    
}
```







## BFS

```java
int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
// int[][] dirs = new int[][]{{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

Queue<Pair<Integer, Integer>> queue = new ArrayDeque<>();
queue.offer(new Pair(startX, startY));

boolean[][] vis = new boolean[m][n];
vis[startX][startY] = true;

while(!queue.isEmpty()) {
    var pos = queue.poll();
    int row = pos.getKey();
    int col = pos.getValue();
    
    
    for (int[] dir : dirs) {
        int x = row + dir[0];
        int y = col + dir[1];
        if (0 <= x && x < m && 0 <= y && y < n && !vis[x][y]) {
            
            queue.offer(new Pair(x, y));
            vis[x][y] = true;
        }
    }
}
```

