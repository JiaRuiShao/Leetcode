import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 785. Is Graph Bipartite?
 */
public class _785 {
	/**
	 * Analysis:
	 * - There are no self-edges (`graph[u]` does not contain `u`) —> `u` is not in `graph[u]`
	 * - There are no parallel edges (`graph[u]` does not contain duplicate values) —> no duplicate value in `graph[u]`
	 * - The graph may not be connected, meaning there may be two nodes u and v such that there is no path between them —> start from each node to traverse
	 *
	 * Time: O(n+e) where n is num of nodes in the given graph, whereas e is the number of edges in the given graph
	 * Space: O(n)
	 */
	class Solution1_DFS {
		boolean isBipartite = true;
		boolean[] visited;
		boolean[] colored;
		
		public boolean isBipartite(int[][] graph) {
			int nodeNum = graph.length;
			visited = new boolean[nodeNum];
			colored = new boolean[nodeNum];
			
			// traverse starting from each node
			for (int node = 0; node < graph.length; node++) {
				if (!visited[node]) {
					traverse(graph, node);
				}
			}
			return isBipartite;
		}
		
		private void traverse(int[][] g, int node) {
			visited[node] = true; // mark this node as visited
			for (int neighbor : g[node]) {
				if (visited[neighbor]) {
					if (colored[neighbor] == colored[node]) {
						isBipartite = false;
						return;
					}
				} else {
					colored[neighbor] = !colored[node];
					traverse(g, neighbor);
				}
			}
		}
	}
	
	/**
	 * Time: O(n+e)
	 * Space: O(n)
	 */
	class Solution2_BFS {
		
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
}
