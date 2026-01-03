import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 70. Climbing Stairs
 * 
 * Fibonacci Sequence variant LC 509
 * 
 * Clarification:
 * - Does order matter? Yes 1+2 vs 2+1 are different ways
 * - Potential overflow for large n
 * 
 * Followup:
 * - What if you can climb 1, 2, or 3 steps at a time?
 * - Climb any number of steps from a given array [1, 3, 5]? Unbounded Knapsack
 * - Return all distinct ways to climb, not just the count? Use backtrack to track
 * - Some steps are broken and you can't step on them?
 * - Each step has a cost and you want minimum cost to reach top? LeetCode 746 variant
 * - Find the minimum number of steps (not ways)
 */
public class _70 {
    class Solution1_DP {
        public int climbStairs(int n) {
            int[] dp = new int[n + 1]; // ways to reach i
            dp[0] = 1;
            dp[1] = 1;
            for (int i = 2; i <= n; i++) {
                dp[i] = dp[i - 1] + dp[i - 2];
            }
            return dp[n];
        }
    }

    class Solution1_DP_SpaceOptimized {
        public int climbStairs(int n) {
            int prev = 1, curr = 1;
            for (int i = 2; i <= n; i++) {
                int temp = curr;
                curr = curr + prev;
                prev = temp;
            }
            return curr;
        }
    }

    class Solution2_Recursion_WithMemo {
        public int climbStairs(int n) {
            int[] memo = new int[n + 1];
            return dp(n, memo);
        }

        private int dp(int stair, int[] memo) {
            if (memo[stair] > 0) return memo[stair];
            int ways = 1;
            if (stair >= 2) {
                ways = dp(stair - 1, memo) + dp(stair - 2, memo);
            }
            return memo[stair] = ways;
        }
    }

    // Time: O(logn)
    // Space: O(1)
    class Solution3_MatrixExponential {
        public int climbStairs(int n) {
            if (n == 1) return 1;
            if (n == 2) return 2;
            
            // Fibonacci matrix: [[1,1],[1,0]]
            long[][] base = {{1, 1}, {1, 0}};
            long[][] result = matrixPower(base, n);
            
            return (int) result[0][0];
        }

        private long[][] matrixPower(long[][] matrix, int n) {
            if (n == 1) return matrix;
            
            long[][] result = {{1, 0}, {0, 1}}; // Identity matrix
            long[][] base = matrix;
            
            while (n > 0) {
                if (n % 2 == 1) {
                    result = multiply(result, base);
                }
                base = multiply(base, base);
                n /= 2;
            }
            
            return result;
        }

        private long[][] multiply(long[][] a, long[][] b) {
            long[][] c = new long[2][2];
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    for (int k = 0; k < 2; k++) {
                        c[i][j] += a[i][k] * b[k][j];
                    }
                }
            }
            return c;
        }
    }

    // dp[i] = dp[i-1] + dp[i-2] + dp[i-3]
    class Followup_ClimbWithThreeSteps {
        public int climbStairs(int n) {
            int[] dp = new int[n + 1]; // ways to reach i
            dp[0] = 1;
            dp[1] = 1;
            dp[2] = 2;
            for (int i = 3; i <= n; i++) {
                dp[i] = dp[i - 1] + dp[i - 2] + dp[i - 3];
            }
            return dp[n];
        }
    }

    // Time: O(nk)
    // Space: O(n)
    // Unbounded Knapsack
    class Followup_ClimbWithAnyArraySteps {
        public int climbStairs(int n, int[] steps) {
            int[] dp = new int[n + 1]; // ways to reach i
            dp[0] = 1;
            for (int i = 1; i <= n; i++) {
                for (int step : steps) {
                    if (i >= step) dp[i] += dp[i - step];
                }
            }
            return dp[n];
        }
    }

    // Time: O(2^n)
    // Space: O(n)
    class Followup_ReturnWaysToClimb {
        public List<List<Integer>> climbStairsAllWays(int n) {
            List<List<Integer>> result = new ArrayList<>();
            backtrack(n, new ArrayList<>(), result);
            return result;
        }

        private void backtrack(int remaining, List<Integer> path, List<List<Integer>> result) {
            if (remaining == 0) {
                result.add(new ArrayList<>(path));
                return;
            }
            
            if (remaining < 0) {
                return;
            }
            
            // Try taking 1 step
            path.add(1);
            backtrack(remaining - 1, path, result);
            path.remove(path.size() - 1);
            
            // Try taking 2 steps
            path.add(2);
            backtrack(remaining - 2, path, result);
            path.remove(path.size() - 1);
        }
    }

    class Followup_ReturnWaysToClimbWithMemo {
        public List<List<Integer>> climbStairsAllWaysMemo(int n) {
            Map<Integer, List<List<Integer>>> memo = new HashMap<>();
            return helper(n, memo);
        }

        private List<List<Integer>> helper(int n, Map<Integer, List<List<Integer>>> memo) {
            if (n == 0) {
                List<List<Integer>> result = new ArrayList<>();
                result.add(new ArrayList<>());
                return result;
            }
            
            if (memo.containsKey(n)) {
                return memo.get(n);
            }
            
            List<List<Integer>> result = new ArrayList<>();
            
            if (n >= 1) {
                for (List<Integer> path : helper(n - 1, memo)) {
                    List<Integer> newPath = new ArrayList<>(path);
                    newPath.add(1);
                    result.add(newPath);
                }
            }
            
            if (n >= 2) {
                for (List<Integer> path : helper(n - 2, memo)) {
                    List<Integer> newPath = new ArrayList<>(path);
                    newPath.add(2);
                    result.add(newPath);
                }
            }
            
            memo.put(n, result);
            return result;
        }
    }

    class Followup_BrokenStairs {
        public int climbStairsWithBroken(int n, Set<Integer> broken) {
            if (broken.contains(n)) return 0; // Can't reach if destination is broken
            
            int[] dp = new int[n + 1];
            dp[0] = 1;
            
            for (int i = 1; i <= n; i++) {
                if (broken.contains(i)) {
                    dp[i] = 0; // Can't step on broken stairs
                    continue;
                }
                
                if (i >= 1) dp[i] += dp[i - 1];
                if (i >= 2) dp[i] += dp[i - 2];
            }
            
            return dp[n];
        }
    }

    class Followup_StairsWithCosts {
        public int minCostClimbingStairs(int n, int[] cost) {
            // cost[i] = cost to step on stair i
            // Can start from step 0 or step 1
            
            int[] dp = new int[n + 1];
            dp[0] = 0; // No cost to start
            dp[1] = 0; // No cost to start from step 1
            
            for (int i = 2; i <= n; i++) {
                // Either came from i-1 or i-2, pay the cost of that step
                dp[i] = Math.min(
                    dp[i - 1] + cost[i - 1],
                    dp[i - 2] + cost[i - 2]
                );
            }
            
            return dp[n];
        }

        public int minCostClimbingStairs_SpaceOptimized(int n, int[] cost) {
            int prev2 = 0;
            int prev1 = 0;
            
            for (int i = 2; i <= n; i++) {
                int current = Math.min(
                    prev1 + cost[i - 1],
                    prev2 + cost[i - 2]
                );
                prev2 = prev1;
                prev1 = current;
            }
            
            return prev1;
        }
    }

    class Followup_MinSteps {
        public int minStepsToClimb_DP(int n) {
            int[] dp = new int[n + 1];
            dp[0] = 0;
            dp[1] = 1;
            
            for (int i = 2; i <= n; i++) {
                // Take minimum of (1 step from i-1) or (1 step from i-2)
                dp[i] = Math.min(
                    dp[i - 1] + 1,  // Take 1 step
                    dp[i - 2] + 1   // Take 2 steps
                );
            }
            
            return dp[n];
        }

        // Optimal O(1) solution
        // Always take 2 steps when possible, then 1 step for remainder: n/2 + n%2
        public int minStepsToClimbOptimal(int n) {
            return (n + 1) / 2; // Greedy: always take 2 steps when possible
        }
    }
}
