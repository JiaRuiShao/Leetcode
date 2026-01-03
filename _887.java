import java.util.Arrays;

/**
 * 887. Super Egg Drop
 * 
 * - S1: DP O(kn^2), O(kn) / O(n)
 * - S2: DP w/ BS O(knlogn), O(kn)/O(n)
 * - S3: Reverse DP O(kn), O(kn)/O(k)
 * 
 * Clarification:
 * - Range of k and n?
 * - What if k > n?
 * 
 * Followup:
 * - Why we can't use binary search? Each binary search might break an egg, k could be < logn
 * With limited k eggs:
 * - If k ≥ log n: Can use binary search
 * - If k < log n: Must be more conservative
 * - If k = 1: Must use linear search (n moves)
 * 
 */
public class _887 {
    // Time: O(kn^2)
    // Space: O(kn)
    class Solution0_DP_TLE {
        // dp[i][j] = min moves needed to find f with k eggs and n floors
        // egg break if j > f; doesn't break j <= f
        public int superEggDrop(int k, int n) {
            int[][] dp = new int[k + 1][n + 1];
            // dp[0][j] = InF
            // dp[1][j] = j
            // dp[i][0] = 0
            // dp[i][1] = 1
            for (int i = 1; i <= k; i++) {
                dp[i][1] = 1;
            }
            for (int j = 1; j <= n; j++) {
                dp[1][j] = j;
            }

            for (int i = 2; i <= k; i++) {
                for (int j = 2; j <= n; j++) {
                    dp[i][j] = j + 1; // function as Integer.MAX_VALUE with out overflow risk
                    for (int dropAt = 1; dropAt <= j; dropAt++) {
                        dp[i][j] = Math.min(dp[i][j], 1 + Math.max(
                            dp[i - 1][dropAt - 1], // egg break
                            dp[i][j - dropAt] // egg doesn't break
                            )
                        );
                    }
                }
            }

            return dp[k][n];
        }
    }

    // for a fixed i, j, when dropAt increases from 1 to j, we know egg broken dp[i-1][dropAt-1] would increase and egg not broken dp[i][j-dropAt] would decrease
    // at dropAt = 1, dp[i-1][0] = 0 & dp[i][j-1] > 0 for any j>=2
    // at dropAt = j, dp[i-1][j-1] > 0 & dp[i][0] = 0 for any j>=2
    // we can use binary search to find the intersection if it exist; if not take egg not broken val when dropAt = j
    // dp[i][j] = min(max(dp[i-1][dropAt-1], dp[i][j-dropAt]))
    // Time: O(knlogn)
    // Space: O(kn)
    class Solution1_DP_BinarySearch {
        // dp[i][j] = min moves needed to find f with k eggs and n floors
        public int superEggDrop(int k, int n) {
            int[][] dp = new int[k + 1][n + 1];
            for (int i = 1; i <= k; i++) {
                dp[i][1] = 1;
            }
            for (int j = 1; j <= n; j++) {
                dp[1][j] = j;
            }

            for (int i = 2; i <= k; i++) {
                for (int j = 2; j <= n; j++) {
                    dp[i][j] = 1 + findIntersect(dp, i, j, 1, j);
                }
            }

            return dp[k][n];
        }

        // intersect val of dp[i-1][x-1] and dp[i][j-x] given range of x [lo, hi]
        private int findIntersect(int[][] dp, int i, int j, int lo, int hi) {
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                int eggBreak = dp[i - 1][mid - 1];
                int notBreak = dp[i][j - mid];
                if (eggBreak == notBreak) {
                    return eggBreak;
                } else if (eggBreak < notBreak) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
            return dp[i][j - hi]; // no intersection, take notBreak val at hi which is the max val
        }
    }

    // Time: O(knlogn)
    // Space: O(n)
    class Solution1_DP_BinarySearch_SpaceOptimized {
        public int superEggDrop(int k, int n) {
            int[] prev = new int[n + 1];
            for (int j = 1; j <= n; j++) {
                prev[j] = j;
            }

            for (int i = 2; i <= k; i++) {
                int[] curr = new int[n + 1];
                curr[1] = 1;
                for (int j = 2; j <= n; j++) {
                    curr[j] = 1 + findIntersect(prev, curr, j, 1, j);
                }
                prev = curr;
            }

            return prev[n];
        }

        private int findIntersect(int[] prev, int[] curr, int j, int lo, int hi) {
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                int eggBreak = prev[mid - 1];
                int notBreak = curr[j - mid];
                if (eggBreak == notBreak) {
                    return eggBreak;
                } else if (eggBreak < notBreak) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
            return curr[j - hi];
        }
    }

    // Instead of: Instead of "min moves for n floors", ask "max floors with m moves"
    // dp[k][m] = max floors can check with k eggs & m moves
    // dp[i][j] = dp[i-1][m-1] + dp[i][m-1] + 1
    //            floors below  floors above  current
    //            (egg breaks) (doesn't break) floor
    // Time: O(km) where m is n at worst and logn at best
    // Space: O(kn)/O(min(n, k))
    class Solution3_ReverseDP {
        public int superEggDrop(int k, int n) {
            // dp[i][j] = max floors can check with i eggs & j moves
            int[][] dp = new int[k + 1][n + 1];
            // dp[i][j] = dp[i-1][m-1] + dp[i][m-1] + 1
            int moves = 0;
            while (dp[k][moves] < n) {
                moves++;
                // With eggs and moves, we can check:
                // 1. Floors below if breaks: dp[eggs-1][moves-1]
                // 2. Floors above if doesn't break: dp[eggs][moves-1]
                // 3. Current floor: 1
                for (int i = 1; i <= k; i++) {
                    dp[i][moves] = 1 + dp[i - 1][moves - 1] + dp[i][moves - 1];
                }
            }
            return moves;
        }
    }

    // dp[i][j] = max floors can check with i eggs & j moves
    // dp[i][j] = dp[i-1][j-1] + dp[i][j-1] + 1
    class Solution3_ReverseDP_1D {
        // 1 - traverse left to right, bottom up
        // dp[i-1] = dp[i-1][j-1]
        // dp[i] = dp[i][j-1]
        public int superEggDrop2(int k, int n) {
            int[] dp = new int[k + 1];
            int moves = 0;
            while (dp[k] < n) {
                moves++;
                for (int i = k; i >= 1; i--) {
                    dp[i] = 1 + dp[i - 1] + dp[i];
                }
            }
            return moves;
        }
        
        // 2 - traverse left to right, top down
        public int superEggDrop2(int k, int n) {
            int[] prev = new int[k + 1]; // prev row for col j-1
            int moves = 0;
            while (prev[k] < n) {
                moves++;
                int[] curr = new int[k + 1];
                for (int i = 1; i <= k; i++) {
                    curr[i] = 1 + prev[i - 1] + prev[i];
                }
                prev = curr;
            }
            return moves;
        }
    }

    // Time: O(kn^2)
    // Space: O(kn)
    class Solution0_Recursion_WithMemo {
        public int superEggDrop(int k, int n) {
            int[][] memo = new int[k + 1][n + 1];
            for (int[] row : memo) {
                Arrays.fill(row, -1);
            }
            return dp(k, n, memo);
        }

        // dp(k, n) = min egg drops with k eggs and n floors
        int dp(int k, int n, int[][] memo) {
            // base case
            if (k == 1) return n;
            if (n == 0) return 0;
            if (memo[k][n] != -1) {
                return memo[k][n];
            }

            // try drop the egg at every possible floor
            int minDrop = Integer.MAX_VALUE;
            for (int i = 1; i <= n; i++) {
                minDrop = Math.min(
                    minDrop,
                    // worst case of egg drop or doesn't drop
                    1 + Math.max(dp(k, n - i, memo), dp(k - 1, i - 1, memo))
                );
            }
            memo[k][n] = minDrop;
            return minDrop;
        }
    }
}
