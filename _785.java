import java.util.LinkedList;
import java.util.Queue;

/**
 * 785. Is Graph Bipartite?
 */
public class _785 {
	/**
	 * Time: O(n+e)
	 * Space: O(n)
	 */
	class Solution {
		public boolean isBipartite(int[][] graph) {
			int n = graph.length;
			int[] color = new int[n]; // 1 , 2

			for (int node = 0; node < n; node++) {
				if (color[node] == 0) {
					if (!bfs(node, color, graph)) {
						return false;
					}
				}
			}
			return true;
		}

		private boolean bfs(int node, int[] color, int[][] graph) {
			Queue<Integer> q = new LinkedList<>();
			color[node] = 1;
			q.offer(node);

			while (!q.isEmpty()) {
				int curr = q.poll();
				for (int neighbor : graph[curr]) {
					if (color[neighbor] == 0) {
						color[neighbor] = 3 - color[curr];
						q.offer(neighbor);
					} else if (color[neighbor] == color[curr]) {
						return false;
					}
				}
			}
			return true;
		}
	}

	class Solution2_DFS {
		boolean isBipartite = true;

		public boolean isBipartite(int[][] graph) {
			int n = graph.length;
			int[] color = new int[n];
			for (int node = 0; node < n; node++) {
				if (color[node] != 0 || !isBipartite) {
					continue;
				}
				dfs(node, graph, color, 1);
			}
			return isBipartite;
		}

		private void dfs(int node, int[][] graph, int[] color, int parentColor) {
			if (color[node] != 0) {
				if (color[node] == parentColor) {
					isBipartite = false;
				}
				return;
			}
			if (!isBipartite) {
				return;
			}
			color[node] = parentColor == 1 ? 2 : 1;
			for (int neighbor : graph[node]) {
				dfs(neighbor, graph, color, color[node]);
			}
		}
	}

	/**
	 * Time:	O(n + e)
	 * Space:	O(n)
	 */
	class Solution3_DFS_Improved {
		public boolean isBipartite(int[][] graph) {
			int n = graph.length;
			int[] color = new int[n]; // 0 = unvisited, 1 or 2 = color sets

			for (int node = 0; node < n; node++) {
				if (color[node] == 0) {
					if (!dfs(node, graph, color, 1)) {
						return false;
					}
				}
			}
			return true;
		}

		private boolean dfs(int node, int[][] graph, int[] color, int currColor) {
			if (color[node] != 0) {
				return color[node] == currColor;
			}

			color[node] = currColor;
			for (int neighbor : graph[node]) {
				if (!dfs(neighbor, graph, color, 3 - currColor)) {
					return false;
				}
			}
			return true;
		}
	}
}
