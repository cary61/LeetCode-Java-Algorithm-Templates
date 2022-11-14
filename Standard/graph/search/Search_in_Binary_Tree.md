# Search in binary tree





## DFS

```java
void dfs(TreeNode node) {
	if (node == null) {
		return;
	}
	
	dfs(node.left);
	dfs(node.right);
}
```





## BFS

```java
Queue<TreeNode> queue = new ArrayDeque<>();
queue.offer(root);

while (!queue.isEmpty()) {
    TreeNode node = queue.poll();
    
    if (node.left != null) {
        queue.offer(node.left);
    }
    if (node.rigth != null) {
        queue.offer(node.right);
    }
}
```





## Level-Order Search

```java
Queue<TreeNode> queue = new ArrayDeque<>();
queue.offer(root);

int level = 0;

while (!queue.isEmpty()) {
	int size = queue.size();
	++level;
	
	for (int i = 0; i < size; ++i) {
		TreeNode node = queue.poll();
		
		if (node.left != null) {
            queue.offer(node.left);
        }
        if (node.rigth != null) {
            queue.offer(node.right);
        }
	}
	
}
```



