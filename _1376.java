import java.util.ArrayList;
import java.util.List;

/**
 * 1376. Time Needed to Inform All Employees
 */
public class _1376 {
    class Solution1_DFS {
        public int numOfMinutes(int n, int headID, int[] manager, int[] informTime) {
            // Build adjacency list - just store children IDs
            List<Integer>[] tree = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                tree[i] = new ArrayList<>();
            }
            
            for (int employee = 0; employee < n; employee++) {
                if (employee != headID) {
                    tree[manager[employee]].add(employee);
                }
            }
            
            return dfs(tree, informTime, headID);
        }
        
        private int dfs(List<Integer>[] tree, int[] informTime, int node) {
            int maxTime = 0;
            // Find the maximum time among all subordinate branches
            for (int subordinate : tree[node]) {
                maxTime = Math.max(maxTime, dfs(tree, informTime, subordinate));
            }
            // Time = current node's inform time + max time in subordinate branches
            return informTime[node] + maxTime;
        }
    }
}
