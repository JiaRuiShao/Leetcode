import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * 559. Maximum Depth of N-ary Tree
 */
public class _559 {
	private static class Node {
		public int val;
		public List<Node> children;
		
		public Node() {}
		
		public Node(int _val) {
			val = _val;
		}
		
		public Node(int _val, List<Node> _children) {
			val = _val;
			children = _children;
		}
	};
	
	/**
	 * DFS: Pre-Order Traversal
	 * Time: O(n)
	 * Space: O(h) = O(n) in the worst case
	 */
	class Solution1_DFS_PreOrderTraversal {
		
		int depth = 0, maxDepth = 0;
		
		public int maxDepth(Node root) {
			if (root == null) {
				return maxDepth;
			}
			dfs(root);
			return maxDepth;
		}
		
		private void dfs(Node root) {
			depth++;
			if (root.children == null || root.children.size() == 0) {
				maxDepth = Math.max(maxDepth, depth);
			} else {
				for (Node child : root.children) {
					dfs(child);
				}
			}
			depth--;
		}
	}
	
	/**
	 * DFS: Post-Order Traversal
	 * Time: O(n)
	 * Space: O(h) = O(n) in the worst case
	 */
	class Solution2_DFS_PostOrderTraversal  {
		
		int depth = 0, maxDepth = 0;
		
		public int maxDepth(Node root) {
			if (root == null) {
				return maxDepth;
			}
			dfs(root);
			return maxDepth;
		}
		
		private void dfs(Node root) {
			depth++;
			if (root.children != null && !root.children.isEmpty()) {
				for (Node child : root.children) {
					dfs(child);
				}
			} else {
				maxDepth = Math.max(maxDepth, depth);
			}
			depth--;
		}
	}
	
	/**
	 * BFS: Level-Order traversal
	 * Time: O(n)
	 * Space: O(n) when tree has only one level (flat tree), e.g. Union-Find Tree
	 */
	class Solution3_BFS_LevelOrderTraversal {
		public int maxDepth(Node root) {
			int depth = 0;
			if (root == null) {
				return depth;
			}
			Deque<Node> queue = new ArrayDeque<>();
			queue.offer(root);
			while (!queue.isEmpty()) {
				depth++;
				int nodeNumAtLevel = queue.size();
				for (int nodeNum = 0; nodeNum < nodeNumAtLevel; nodeNum++) {
					Node node = queue.poll();
					if (node.children != null && !node.children.isEmpty()) {
						node.children.forEach(child -> {
							queue.offer(child);
						});
					}
				}
			}
			return depth;
		}
	}
}
