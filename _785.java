import java.util.LinkedList;
import java.util.Queue;

/**
 * 785. Is Graph Bipartite?
 */
public class _785 {
	class Solution1_DFS {
		public boolean isBipartite(int[][] graph) {
			int n = graph.length;
			int[] color = new int[n]; // 0 - unvisit; 1 - red; 2 - black
			for (int start = 0; start < n; start++) {
				if (color[start] == 0 && !isBipartite(graph, start, 1, color)) {
					return false;
				}
			}
			return true;
		}

		private boolean isBipartite(int[][] graph, int node, int parentColor, int[] color) {
			color[node] = (parentColor == 1 ? 2 : 1);
			if (graph[node] == null || graph[node].length == 0) {
				return true;
			}

			for (int nbr : graph[node]) {
				if (color[nbr] == color[node]) {
					return false;
				}
				if (color[nbr] == 0 && !isBipartite(graph, nbr, color[node], color)) {
					return false;
				}
			}
			return true;
		}
	}

	// Time: O(V+E)
	// Space: O(V)
	class Solution2_BFS {
		public boolean isBipartite(int[][] graph) {
			int n = graph.length;
			int[] colors = new int[n];  // 0 - unvisit; 1 - red; 2 - black

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

	// Time: O(V+E*alpha(V)) = O(V+E)
	// Space: O(V)
	class Solution3_UF {
		private int[] parent;
		
		public boolean isBipartite(int[][] graph) {
			int n = graph.length;
			parent = new int[n];
			for (int i = 0; i < n; i++) {
				parent[i] = i;
			}
			
			for (int node = 0; node < n; node++) {
				if (graph[node].length == 0) continue;
				
				// All neighbors of 'node' must be in the same group
				// And that group must be different from 'node's group
				int firstNeighbor = graph[node][0];
				
				for (int neighbor : graph[node]) {
					// Check if node and neighbor are in same set
					if (find(node) == find(neighbor)) {
						return false; // Both in same set → not bipartite
					}
					
					// Union all neighbors together (they're in opposite set from node)
					union(firstNeighbor, neighbor);
				}
			}
			
			return true;
		}
		
		private int find(int x) {
			if (parent[x] != x) {
				parent[x] = find(parent[x]);
			}
			return parent[x];
		}
		
		private void union(int x, int y) {
			int rootX = find(x);
			int rootY = find(y);
			if (rootX != rootY) {
				parent[rootX] = rootY;
			}
		}
	}
}
