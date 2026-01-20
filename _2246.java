import java.util.ArrayList;
import java.util.List;

/**
 * 2246. Longest Path With Different Adjacent Characters
 */
public class _2246 {
    class Solution1_DFS {
        int maxLen;
        public int longestPath(int[] parent, String s) {
            // dp[i] = if s[parent(i)] != s[i]: 1
            //       = else                   : 1 + dp[parent(i)]
            // max(dp[i])
            maxLen = 0;
            int n = parent.length;
            List<Integer>[] tree = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                tree[i] = new ArrayList<>();
            }

            for (int node = 0; node < n; node++) {
                int p = parent[node];
                if (p >= 0) {
                    tree[p].add(node);
                }
            }

            dp(tree, 0, s);
            return maxLen;
        }

        private int dp(List<Integer>[] tree, int node, String s) {
            int maxLen1 = 0, maxLen2 = 0;
            for (int child : tree[node]) {
                int len = dp(tree, child, s);
                if (s.charAt(node) != s.charAt(child)) {
                    len++;
                    if (len > maxLen1) {
                        maxLen2 = maxLen1;
                        maxLen1 = len;
                    } else if (len > maxLen2) {
                        maxLen2 = len;
                    }
                }            
            }
            maxLen = Math.max(maxLen, maxLen1 + maxLen2 + 1);
            return maxLen1;
        }
    }
}
