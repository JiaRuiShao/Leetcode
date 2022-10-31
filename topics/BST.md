- **findSuccessor**
  ```
  private TreeNode findSuccessor(TreeNode root) {
    // here we choose the leftest right node
    TreeNode curr = root.right, prev = curr;
    while (curr.left != null) {
        prev = curr;
        curr = curr.left;
    } // terminate when curr points to the leftest right node
    if (curr != root.right) {
        prev.left = curr.right; // connect the right subtree of target node to parent.left
        // set target right subtree
        curr.right = root.right;
    }
    curr.left = root.left; // set target left subtree
    return curr;
  }
  ``` 
- **findPredecessor**
  ```
  private TreeNode findPredecessor(TreeNode root) {
    TreeNode curr = root.left, prev = curr;
    while (curr.right != null) {
        prev = curr;
        curr = curr.right;
    }
    if (curr != root.left) { 
        prev.right = curr.left;
        curr.left = root.left;
    }
    curr.right = root.right;
    return curr;
  }
  ``` 