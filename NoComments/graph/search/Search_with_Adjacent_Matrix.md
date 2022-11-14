# Search when problem expresses graph with adjacent matrix





## DFS

```java
void dfs(int pos, int[][] graph) {
        
        for(int j = 0; j < n; ++j) {
            
            dfs(graph[pos][j], graph);
            
        }
    }
```





## BFS

```java
Queue<Integer> queue = new ArrayDeque<>();
queue.offer(startPos);

boolean[] vis = new boolean[n];
vis[startPos] = true;

while(!queue.isEmpty()) {
	
	int pos = queue.poll();
	
	
	for (int j = 0; j < n; ++j) {
		if (graph[pos][j] /*boolean*/ && !vis[pos][j]) {
            
            queue.offer(j);
            vis[j] = true;
        }
	}
    
}
```



