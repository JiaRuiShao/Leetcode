import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * 269. Alien Dictionary
 */
public class _269 {
    // use array to avoid map overhead, but more corner cases to consider
    // Time: O(WL)
    // Space: O(1)
    class Solution1_TopologicalSort_Array {
        private static final String EMPTY = "";
        private static final Integer CHARS = 26;

        public String alienOrder(String[] words) {
            Set<Integer>[] graph = new HashSet[CHARS];
            int[] inDegree = new int[CHARS];
            int uniqueChars = 0;
            for (String word : words) {
                for (char c : word.toCharArray()) {
                    int idx = c - 'a';
                    if (graph[idx] == null) {
                        graph[idx] = new HashSet<>();
                        uniqueChars++;
                    }
                }
            }

            for (int i = 0; i < words.length - 1; i++) {
                String curr = words[i], next = words[i + 1];
                int m = curr.length(), n = next.length();
                if (curr.startsWith(next) && m > n) {
                    return EMPTY;
                }

                int minLen = Math.min(m, n);
                for (int j = 0; j < minLen; j++) {
                    if (curr.charAt(j) != next.charAt(j)) {
                        int pre = curr.charAt(j) - 'a', post = next.charAt(j) - 'a';
                        if (!graph[pre].contains(post)) {
                            graph[pre].add(post);
                            inDegree[post]++;
                        }
                        break;
                    }
                }
            }

            Queue<Integer> q = new ArrayDeque<>();
            for (int i = 0; i < CHARS; i++) {
                if (graph[i] != null && inDegree[i] == 0) {
                    q.offer(i);
                }
            }

            StringBuilder sorted = new StringBuilder();
            while (!q.isEmpty()) {
                int curr = q.poll();
                sorted.append((char) (curr + 'a'));
                if (graph[curr] == null) continue;
                for (int next : graph[curr]) {
                    if (--inDegree[next] == 0) {
                        q.offer(next);
                    }
                }
            }
            return sorted.length() == uniqueChars ? sorted.toString() : EMPTY;
        }
    }
}
