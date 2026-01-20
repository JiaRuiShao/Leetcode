import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * 3607. Power Grid Maintenance
 */
public class _3607 {
    class Solution1_UF {
        class UF {
            int[] parent;
            int[] size;
            UF(int n) {
                parent = new int[n];
                size = new int[n];
                for (int i = 0; i < n; i++) {
                    parent[i] = i;
                    size[i] = 1;
                }
            }

            void union(int n1, int n2) {
                int p1 = find(n1), p2 = find(n2);
                if (p1 != p2) {
                    if (size[p1] >= size[p2]) {
                        parent[p2] = p1;
                        size[p1] += size[p2];
                    } else {
                        parent[p1] = p2;
                        size[p2] += size[p1];
                    }
                }
            }

            int find(int node) {
                if (parent[node] != node) {
                    parent[node] = find(parent[node]);
                }
                return parent[node];
            }
        }

        public int[] processQueries(int c, int[][] connections, int[][] queries) {
            // id: [1, c] = need conver to base-0
            // union-find then group power stations together by root id
            // use TreeSet for logn delete & peekFirst
            UF uf = new UF(c + 1);
            for (int[] connection : connections) {
                uf.union(connection[0], connection[1]);
            }

            Map<Integer, TreeSet<Integer>> powerGrid = new HashMap<>();
            for (int station = 1; station <= c; station++) {
                powerGrid.computeIfAbsent(uf.find(station), k -> new TreeSet<>()).add(station);
            }

            List<Integer> res = new ArrayList<>();
            for (int[] query : queries) {
                int station = query[1];
                int root = uf.find(station);
                if (query[0] == 1) {
                    if (powerGrid.get(root).isEmpty()) {
                        res.add(-1);
                    } else if (powerGrid.get(root).contains(station)) {
                        res.add(station);
                    } else {
                        res.add(powerGrid.get(root).getFirst());
                    }
                } else if (query[0] == 2) {
                    powerGrid.get(root).remove(station);
                }
            }
            int[] queryRes = new int[res.size()];
            for (int i = 0; i < res.size(); i++) {
                queryRes[i] = res.get(i);
            }
            return queryRes;
        }
    }
}
