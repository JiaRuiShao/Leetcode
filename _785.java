import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 785. Is Graph Bipartite?
 */
public class _785 {
	/**
	 * Time: O(n+e)
	 * Space: O(n)
	 */
	class Solution1_BFS {
		
		private boolean isBipartite = true;
		private boolean[] visited;
		private boolean[] inSet;
		
		public boolean isBipartite(int[][] graph) {
			int n = graph.length;
			visited = new boolean[n];
			inSet = new boolean[n];
			for (int node = 0; node < n; node++) {
				if (!visited[node]) traverse(graph, node);
			}
			return isBipartite;
		}
		
		private void traverse(int[][] g, int start) {
			Deque<Integer> q = new ArrayDeque<>();
			q.offer(start);
			visited[start] = true;
			
			while (!q.isEmpty()) {
				int node = q.poll();
				for (int neighbor : g[node]) {
					if (!visited[neighbor]) {
						visited[neighbor] = true;
						inSet[neighbor] = !inSet[node];
						q.offer(neighbor);
					} else if (inSet[neighbor] == inSet[node]) {
						isBipartite = false;
						return;
					}
				}
			}
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
