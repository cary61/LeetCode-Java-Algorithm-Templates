# Search when problem expresses graph with the edges between nodes





## Build Graph

```java
Map<Integer, List<Integer>> nodeToNexts = new HashMap<>();
for (int[] edge : edges) {
	nodeToNexts.computeIfAbsent(edge[0], key -> new ArrayList<>()).add(edge[1]);
	nodeToNexts.computeIfAbsent(edge[1], key -> new ArrayList<>()).add(edge[0]);
}
```





## DFS

```java
void dfs(int node) {
	
	
	if (nodeToNexts.containsKey(node)) {
		for (int next : nodeToNexts.get(node)) {
			
			dfs(next);
			
		}
	}
	
}
```





## BFS

```java
Queue<Integer> queue = new ArrayDeque<>();
queue.offer(startPos);

boolean[] vis = new boolean[n];
vis[startPos] = true;

while (!queue.isEmpty()) {
	
    int node = queue.poll();
    
	
	if (nodeToNexts.containsKey(node)) {
		for (int next : nodeToNexts.get(node)) {
			if (!vis[next]) {
				
				queue.offer(next);
				vis[next] = true;
			}
			
		}
	}
}
```

