import java.util.LinkedList;
import java.util.Queue;

/**
 * 785. Is Graph Bipartite?
 * Use byte[] colors instead if the graph vertex number is large
 */
public class _785 {
	/**
	 * BFS - color before traverse for the 1st time.
	 * Time: O(n+e)
	 * Space: O(n)
	 */
	class Solution {
		public boolean isBipartite(int[][] graph) {
			int n = graph.length;
			int[] colors = new int[n]; // 1 , 2

			for (int v = 0; v < n; v++) {
				if (colors[v] == 0) {
					if (!bfs(v, colors, graph)) {
						return false;
					}
				}
			}
			return true;
		}

		private boolean bfs(int v, int[] colors, int[][] graph) {
			Queue<Integer> q = new LinkedList<>();
			colors[v] = 1;
			q.offer(v);

			while (!q.isEmpty()) {
				int curr = q.poll();
				for (int nbr : graph[curr]) {
					if (colors[nbr] == 0) {
						colors[nbr] = 3 - colors[curr];
						q.offer(nbr);
					} else if (colors[nbr] == colors[curr]) {
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
	 * DFS - set color when traverse for the 1st time.
	 * Time:	O(n + e)
	 * Space:	O(n)
	 */
	class Solution3_DFS_Without_Global_Variables {
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

	/**
	 * DFS - color before traverse for the 1st time.
	 * Recommended for BFS solution
	 * Time:	O(n + e)
	 * Space:	O(n)
	 */
	class Solution4_DFS_Without_Global_Variables_Without_Extra_Color_Parameter {
		public boolean isBipartite(int[][] graph) {
			int n = graph.length;
			int[] colors = new int[n]; // 1 , 2

			for (int v = 0; v < n; v++) {
				if (colors[v] == 0) {
					colors[v] = 1;
					if (!dfs(v, colors, graph)) {
						return false;
					}
				}
			}
			return true;
		}

		private boolean dfs(int v, int[] colors, int[][] graph) {
			for (int nbr : graph[v]) {
				if (colors[nbr] == 0) {
					colors[nbr] = 3 - colors[v];
					if (!dfs(nbr, colors, graph)) {
						return false;
					}
				} else if (colors[nbr] == colors[v]) {
					return false;
				}
			}
			return true;
		}
	}
}
