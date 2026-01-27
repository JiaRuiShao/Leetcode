import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 332. Reconstruct Itinerary
 * Eulerian path in a directed graph
 */
public class _332 {
    // Notice question asks to use all tickets not to go through all airports
    // Time: O(ElogE+E!) = O(E!) where E is num of tickets
    // Space: O(E)
    class Solution0_DFS_Backtrack_TLE {
        class Ticket {
            String des;
            boolean used;

            Ticket(String des) {
                this.des = des;
                used = false;
            }
        }

        public List<String> findItinerary(List<List<String>> tickets) {
            Map<String, List<Ticket>> graph = new HashMap<>();
            for (List<String> t : tickets) {
                String from = t.get(0), to = t.get(1);
                graph.computeIfAbsent(from, k -> new ArrayList<>()).add(new Ticket(to));
            }

            for (List<Ticket> des : graph.values()) {
                Collections.sort(des, (a, b) -> a.des.compareTo(b.des));
            }

            List<String> path = new ArrayList<>();
            dfs(graph, "JFK", path, tickets.size());
            return path;
        }

        private boolean dfs(Map<String, List<Ticket>> graph, String curr, List<String> path, int tickets) {
            path.add(curr);
            if (path.size() == tickets + 1) {
                return true;
            }

            for (Ticket next : graph.getOrDefault(curr, new ArrayList<>())) {
                if (!next.used) {
                    next.used = true;
                    if (dfs(graph, next.des, path, tickets)) {
                        return true; // early termination -- don't backtrack if valid res found
                    }
                    next.used = false;
                }
            }
            path.remove(path.size() - 1);
            return false;
        }
    }

    // Time: O(ElogE)
    // Space: O(E)
    class Solution1_DFSVariant_Hierholzer {
        public List<String> findItinerary(List<List<String>> tickets) {
            Map<String, PriorityQueue<String>> graph = new HashMap<>();

            for (List<String> t : tickets) {
                graph.computeIfAbsent(t.get(0), k -> new PriorityQueue<>()).offer(t.get(1));
            }

            LinkedList<String> path = new LinkedList<>();
            dfs(graph, "JFK", path);
            return path;
        }

        private void dfs(Map<String, PriorityQueue<String>> graph, String curr, LinkedList<String> path) {
            PriorityQueue<String> pq = graph.get(curr);
            while (pq != null && !pq.isEmpty()) {
                dfs(graph, pq.poll(), path);
            }
            path.addFirst(curr); // add curr airport when all tickets from here are consumed
        }
    }
}
