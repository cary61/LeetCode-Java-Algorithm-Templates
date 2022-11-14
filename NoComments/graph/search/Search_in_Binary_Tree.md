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





## Level-Order Traversal

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





## Pre-Order Traversal

```java
void preorder(TreeNode node) {
	if (node == null) {
        return;
    }
    // do something
    preorder(node.left);
    preorder(node.right);
}
```





## In-Order Traversal

```java
void inorder(TreeNode node) {
	if (node == null) {
        return;
    }
    inorder(node.left);
    // do something
    inorder(node.right);
}
```





## Post-Order Traversal

```java
void postorder(TreeNode node) {
	if (node == null) {
        return;
    }
    postorder(node.left);
    postorder(node.right);
    // do something
}
```

