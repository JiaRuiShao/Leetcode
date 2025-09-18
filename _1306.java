import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 1306. Jump Game III
 */
public class _1306 {
    class Solution1_BFS {
        public boolean canReach(int[] arr, int start) {
            int n = arr.length;
            boolean[] visited = new boolean[n];
            Queue<Integer> q = new ArrayDeque<>();
            q.offer(start);
            visited[start] = true;
            while (!q.isEmpty()) {
                int curr = q.poll();
                if (arr[curr] == 0) {
                    return true;
                }
                int right = curr + arr[curr], left = curr - arr[curr];
                if (isValidIndex(right, n) && !visited[right]) {
                    q.offer(right);
                    visited[right] = true;
                }
                if (isValidIndex(left, n) && !visited[left]) {
                    q.offer(left);
                    visited[left] = true;
                }
            }
            return false;
        }

        private boolean isValidIndex(int idx, int n) {
            return idx >= 0 && idx < n;
        }
    }

    class Solution2_DFS {
        public boolean canReach(int[] arr, int start) {
            int n = arr.length;
            boolean[] visited = new boolean[n];
            return dfs(arr, start, visited);
        }

        private boolean dfs(int[] arr, int idx, boolean[] visited) {
            int n = visited.length;
            if (idx < 0 || idx >= n || visited[idx]) return false;
            if (arr[idx] == 0) return true;
            visited[idx] = true;
            return dfs(arr, idx - arr[idx], visited) || dfs(arr, idx + arr[idx], visited);
        }
    }
}
