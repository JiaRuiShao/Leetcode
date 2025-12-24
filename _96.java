import java.util.Arrays;

/**
 * 96. Unique Binary Search Trees
 * f(n) = Σ f(left) * f(right)
 * 
 * - S1: 1D DP Bottom-Up, O(n^2), O(n) [PREFERRED]
 * - S2: Recursion + Memo, O(n^2), O(n) with more overhead & risk of stack overflow for larger n
 * - S3: Catalan Number, O(n), O(1) not recommended to use
 */
public class _96 {
    // the reason why this solution won't work is because the tree could be same when we select nodes with different order
    // i.e. [2, 1, 3] and [2, 3, 1]  both lead to [2, 1, 3]
    class Solution_Wrong_Permutation {
        private int count;
        public int numTrees(int n) {
            count = 0;
            boolean[] used = new boolean[n];
            backtrack(used, n, null, null);
            return count;
        }

        private void backtrack(boolean[] used, int n, Integer min, Integer max) {
            if (--n == 0) {
                count++;
                return;
            }
            for (int node = 0; node < used.length; node++) {
                if (used[node]) continue;
                if (min != null && node < min) continue;
                if (max != null && node > max) continue;
                used[node] = true;
                // next node as left node
                backtrack(used, n, min, node);
                // next node as right node
                backtrack(used, n, node, max);
                used[node] = false;
            }
        }
    }

    // Time: exponential
    class Solution0_BruteForce_TLE {
        // given root node i, leftNodeCount = i - 1, rightNodeCount = n - i
        // number of structured BST formed with node i as root is leftNodeCount * rightNodeCount
        // notice whenever leftNodeCount or rightNodeCount is 0, we should default to 1 as it's also a valid structure
        public int numTrees(int n) {
            return countBST(1, n);
        }

        private int countBST(int left, int right) {
            if (left > right) return 1;
            int count = 0;
            for (int root = left; root <= right; root++) {
                int leftCount = countBST(left, root - 1);
                int rightCount = countBST(root + 1, right);
                count += leftCount * rightCount;
            }
            return count;
        }
    }

    // Time: O(n^2)
    // Space: O(n^2)
    class Solution1_RecursionWithMemo {
        // given root node i, leftNodeCount = i - 1, rightNodeCount = n - i
        // number of structured BST formed with node i as root is leftNodeCount * rightNodeCount
        // notice whenever leftNodeCount or rightNodeCount is 0, we should default to 1 as it's also a valid structure
        private int[][] memo;
        public int numTrees(int n) {
            memo = new int[n + 1][n + 1];
            return countBST(1, n);
        }

        private int countBST(int left, int right) {
            if (left > right) return 1;
            if (memo[left][right] > 0) return memo[left][right];
            // memo[left][right] = 0;
            for (int root = left; root <= right; root++) {
                memo[left][right] += countBST(left, root - 1) * countBST(root + 1, right);
            }
            return memo[left][right];
        }
    }

    class Solution1_IterativeBottomUp {
        public int numTrees(int n) {
            int[][] dp = new int[n + 2][n + 2];

            // empty ranges
            for (int i = 1; i <= n + 1; i++) {
                dp[i][i - 1] = 1;
            }

            // increasing interval length
            for (int len = 1; len <= n; len++) {
                for (int left = 1; left + len - 1 <= n; left++) {
                    int right = left + len - 1;
                    for (int root = left; root <= right; root++) {
                        dp[left][right] += dp[left][root - 1] * dp[root + 1][right];
                    }
                }
            }
            return dp[1][n];
        }
    }

    // Below code logic is correct, but DP states are used before being computed
    // The reason below solution won't work is because when calculate for range where left = 1 and right = 4
    // when root is 1, res = dp[1][0] + dp[2][4]
    // dp[2][4] should be a valid BST but we hasn't calculated yet and here it's calculated as 0, which is wrong
    // this is why we need to use len as outer loop to always compute smaller sub-problems before larger ones
    class Wrong_BottomUp {
        public int numTrees(int n) {
            int[][] memo = new int[n + 2][n + 2];
            for (int node = 1; node < (n + 2); node++) memo[node][0] = 1;
            Arrays.fill(memo[n + 1], 1);
            for (int left = 1; left <= n; left++) {
                for (int right = left + 1; right <= n; right++) {
                    for (int root = left; root <= right; root++) {
                        memo[left][right] += memo[left][root - 1] * memo[root + 1][right];
                    }
                }
            }
            return memo[1][n];
        }

        // corrected 2D version
        public int numTrees2(int n) {
            int[][] memo = new int[n + 2][n + 2];

            // empty tree
            for (int i = 1; i <= n + 1; i++) {
                memo[i][i - 1] = 1;
            }

            // length = number of nodes in subtree
            for (int len = 1; len <= n; len++) {
                for (int left = 1; left + len - 1 <= n; left++) {
                    int right = left + len - 1;
                    for (int root = left; root <= right; root++) {
                        memo[left][right] += memo[left][root - 1] * memo[root + 1][right];
                    }
                }
            }
            return memo[1][n];
        }
    }

    // Below is the version expected
    // 1D version simplify the 2D version by recognizing that only number of nodes or tree length matters
    // dp[n] = unique BSTs with n nodes
    // dp[n] += dp[i-1] * dp[n-i] if we choose i as the root node; left subtree has i - 1 nodes & right subtree has n - i nodes
    class Followup_IterativeBottomUp1D {
        // Time: O(n²)
        // Space: O(n)
        public int numTrees(int n) {
            // dp[i] = number of unique BSTs with i nodes
            int[] dp = new int[n + 1];
            
            // Base case: empty tree or single node
            dp[0] = 1;  // Empty tree (important for multiplication)
            
            // Fill for each number of nodes from 1 to n
            for (int nodes = 1; nodes <= n; nodes++) {
                // Try each node as root
                for (int root = 1; root <= nodes; root++) {
                    // Left subtree has (root - 1) nodes
                    // Right subtree has (nodes - root) nodes
                    dp[nodes] += dp[root - 1] * dp[nodes - root];
                }
            }
            
            return dp[n];
        }
    }

    class Solution3_CatalanNum {
        public int numTrees(int n) {
            // Catalan number formula: C(n) = (2n)! / ((n+1)! * n!)
            // Or: C(n) = C(2n, n) / (n + 1)
            
            long result = 1;
            
            // Computing C(2n, n) = (2n)! / (n! * n!)
            for (int i = 0; i < n; i++) {
                result = result * (2 * n - i) / (i + 1);
            }
            
            // Divide by (n+1)
            return (int)(result / (n + 1));
        }
    }
}
