import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * 721. Accounts Merge
 */
public class _721 {
    // the brute force way to solve is to save all emails belong to one account in one set
    // then merge two sets if there's any email in common
    // this would take cubic time
    class Solution0_BruteForce {
        List<Set<String>> emailSet = new ArrayList<>(); // saves account index and the associated emails
    }

    // This question is tricky in that the name is not identifier and the email is. We need to think the emails as nodes
    // and connect the emails belong to one account together
    class Solution1_Graph_DFS {
        public List<List<String>> accountsMerge(List<List<String>> accounts) {
            // build adjacency list for emails
            Map<String, List<String>> graph = new HashMap<>();
            for (List<String> account : accounts) {
                String firstEmail = account.get(1);
                graph.putIfAbsent(firstEmail, new ArrayList<>());
                for (int i = 2; i < account.size(); i++) {
                    String email = account.get(i);
                    graph.computeIfAbsent(email, k -> new ArrayList<>()).add(firstEmail);
                    graph.get(firstEmail).add(email);
                }
            }

            // dfs/bfs traverse from firstEmail to get all associated email for each account
            List<List<String>> merged = new ArrayList<>();
            Set<String> visited = new HashSet<>();
            for (List<String> account : accounts) {
                String name = account.get(0), firstEmail = account.get(1);
                if (visited.contains(firstEmail)) continue;
                List<String> nameEmails = new ArrayList<>();
                nameEmails.add(name);
                dfs(graph, firstEmail, visited, nameEmails);
                Collections.sort(nameEmails.subList(1, nameEmails.size()));
                merged.add(nameEmails);
            }
            return merged;
        }

        private void dfs(Map<String, List<String>> graph, String email, Set<String> visited, List<String> emails) {
            emails.add(email);
            visited.add(email);
            for (String connectedEmail : graph.get(email)) {
                if (!visited.contains(connectedEmail)) {
                    dfs(graph, connectedEmail, visited, emails);
                }
            }
        }
    }

    class Solution1_Graph_BFS {
        public List<List<String>> accountsMerge(List<List<String>> accounts) {
            // build adjacency list for emails
            Map<String, List<String>> graph = new HashMap<>();
            for (List<String> account : accounts) {
                String firstEmail = account.get(1);
                graph.putIfAbsent(firstEmail, new ArrayList<>());
                for (int i = 2; i < account.size(); i++) {
                    String email = account.get(i);
                    graph.computeIfAbsent(email, k -> new ArrayList<>()).add(firstEmail);
                    graph.get(firstEmail).add(email);
                }
            }

            // dfs/bfs traverse from firstEmail to get all associated email for each account
            List<List<String>> merged = new ArrayList<>();
            Set<String> visited = new HashSet<>();
            for (List<String> account : accounts) {
                String name = account.get(0), firstEmail = account.get(1);
                if (visited.contains(firstEmail)) continue;
                List<String> nameEmails = new ArrayList<>();
                nameEmails.add(name);
                bfs(graph, firstEmail, visited, nameEmails);
                Collections.sort(nameEmails.subList(1, nameEmails.size()));
                merged.add(nameEmails);
            }
            return merged;
        }

        private void bfs(Map<String, List<String>> graph, String email, Set<String> visited, List<String> emails) {
            Queue<String> q = new ArrayDeque<>();
            q.offer(email);
            visited.add(email);
            while (!q.isEmpty()) {
                String curr = q.poll();
                emails.add(curr);
                for (String connectedEmail : graph.get(curr)) {
                    if (!visited.contains(connectedEmail)) {
                        q.offer(connectedEmail);
                        visited.add(connectedEmail);
                    }
                }
            }
        }
    }

    // Time: O(E·α(n)+ElogE) where n is num of accounts & E is num of emails in total
    // Space: O(n + E)
    // add size[] to reduce the runtime of union and find methods
    class Solution2_UF_ByAccount_Improved {
        private int[] parent;
        private int[] size;
        public List<List<String>> accountsMerge(List<List<String>> accounts) {
            int n = accounts.size();
            this.parent = new int[n];
            this.size = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;

            // map email to each account and merge two accounts if duplicate email found
            Map<String, Integer> emailToAccountId = new HashMap<>(); // what if we use TreeMap?
            for (int id = 0; id < n; id++) {
                List<String> account = accounts.get(id);
                for (int i = 1; i < account.size(); i++) {
                    String email = account.get(i);
                    Integer lastAccountId = emailToAccountId.putIfAbsent(email, id);
                    if (lastAccountId != null) {
                        union(lastAccountId, id);
                    }
                }
            }
            
            // build result list
            List<List<String>> merged = new ArrayList<>();
            Map<Integer, Integer> accountIdToMergedId = new HashMap<>();
            for (Map.Entry<String, Integer> entry : emailToAccountId.entrySet()) {
                String email = entry.getKey();
                int id = find(entry.getValue());
                if (!accountIdToMergedId.containsKey(id)) {
                    accountIdToMergedId.put(id, merged.size());
                    merged.add(new ArrayList<>());
                    String name = accounts.get(id).get(0);
                    merged.get(accountIdToMergedId.get(id)).add(name);
                }
                merged.get(accountIdToMergedId.get(id)).add(email);
            }

            for (List<String> account : merged) {
                Collections.sort(account.subList(1, account.size()));
            }
            return merged;
        }

        private void union(int n1, int n2) {
            int p1 = find(n1), p2 = find(n2);
            if (p1 != p2) {
                if (size[p1] < size[p2]) {
                    parent[p1] = p2;
                    size[p2] += size[p1];
                } else {
                    parent[p2] = p1;
                    size[p1] += size[p2];
                }
            }
        }

        private int find(int n) {
            if (parent[n] != n) {
                parent[n] = find(parent[n]);
            }
            return parent[n];
        }
    }

    class Solution3_UF_ByEmail {
        // Implement
    }
}
