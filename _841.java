import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

/**
 * 841. Keys and Rooms
 */
public class _841 {
    class Solution1_DFS {
        public boolean canVisitAllRooms(List<List<Integer>> rooms) {
            int n = rooms.size(), start = 0; // we can only start from 0
            boolean[] visited = new boolean[n];
            dfs(rooms, start, visited);
            for (boolean visit : visited) {
                if (!visit) {
                    return false;
                }
            }
            return true;
        }

        private void dfs(List<List<Integer>> rooms, int room, boolean[] visited) {
            if (visited[room]) return;
            visited[room] = true;
            for (int nextRoom : rooms.get(room)) {
                dfs(rooms, nextRoom, visited);
            }
        }
    }

    class Solution2_BFS {
        public boolean canVisitAllRooms(List<List<Integer>> rooms) {
            int n = rooms.size(), start = 0; // we can only start from 0
            boolean[] visited = new boolean[n];
            Queue<Integer> q = new ArrayDeque<>();
            q.offer(start);
            visited[start] = true;

            int visit = 0;
            while (!q.isEmpty()) {
                int room = q.poll();
                visit++;
                for (int next : rooms.get(room)) {
                    if (!visited[next]) {
                        q.offer(next);
                        visited[next] = true;
                    }
                }
            }
            return visit == n;
        }
    }
}
