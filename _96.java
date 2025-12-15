/**
 * 96. Unique Binary Search Trees
 * f(n) = Σ f(left) * f(right)
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
            memo = new int[n+1][n+1];
            return countBST(1, n);
        }

        private int countBST(int left, int right) {
            if (left > right) return 1;
            if (memo[left][right] != 0) return memo[left][right];
            int count = 0;
            for (int root = left; root <= right; root++) {
                int leftCount = countBST(left, root - 1);
                int rightCount = countBST(root + 1, right);
                count += leftCount * rightCount;
            }
            return memo[left][right] = count;
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

}
