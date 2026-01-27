import java.util.*;

/**
 * 198. House Robber
 * 
 * Followup:
 * - Houses are arranged in a circle? LC 213
 * - Houses are in a binary tree? LC 337
 * - Rob at most k houses?
 * - Rob exactly k houses?
 * - Cooldown period of d days after robbing (d=2 means after robbing house i, must skip i+1 and i+2)?
 * - Return actual robbed houses?
 */
public class _198 {
    class Solution1_DP {
        public int rob(int[] nums) {
            int n = nums.length;
            int[] dp = new int[n + 1]; // max money to rob in first i house
            dp[1] = nums[0];
            for (int i = 2; i <= n; i++) {
                dp[i] = Math.max(
                    dp[i - 1], // do not rub house i - 1
                    dp[i - 2] + nums[i - 1]  // rob house i - 1, skip i - 2
                );
            }
            return dp[n];
        }
    }

    class Solution1_DP_SpaceOptimized {
        public int rob(int[] nums) {
            int n = nums.length;
            int prev2 = 0;
            int prev1 = nums[0];
            for (int i = 2; i <= n; i++) {
                int curr = Math.max(
                    prev1, // do not rub house i - 1
                    prev2 + nums[i - 1]  // rob house i - 1, skip i - 2
                );
                prev2 = prev1;
                prev1 = curr;
            }
            return prev1;
        }
    }

    // Time: O(nk)
    // Space: O(nk)/O(k) with optimized
    class Followup_RobAtMostKHouses {
        public int robKHouses(int[] nums, int k) {
            int n = nums.length;
            if (k >= (n + 1) / 2) {
                // Can rob all non-adjacent houses
                return robAllNonAdjacent(nums);
            }
            
            // dp[i][j][0/1] = max money robbing at most j houses from first i houses
            // last dimension: 0 = didn't rob i-th, 1 = robbed i-th
            int[][][] dp = new int[n + 1][k + 1][2];
            
            for (int i = 1; i <= n; i++) {
                for (int j = 0; j <= k; j++) {
                    // Don't rob i-th house
                    dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j][1]);
                    
                    // Rob i-th house (must not rob i-1)
                    if (j > 0) {
                        dp[i][j][1] = dp[i - 1][j - 1][0] + nums[i - 1];
                    }
                }
            }
            
            return Math.max(dp[n][k][0], dp[n][k][1]);
        }

        private int robAllNonAdjacent(int[] nums) {
            int prev2 = 0, prev1 = 0;
            for (int num : nums) {
                int current = Math.max(prev1, prev2 + num);
                prev2 = prev1;
                prev1 = current;
            }
            return prev1;
        }
    }

    class Followup_RobKHouses {
        public int robExactlyKHouses(int[] nums, int k) {
            int n = nums.length;
            
            // dp[i][j][0/1] = max money robbing exactly j houses from first i
            // 0 = didn't rob i-th, 1 = robbed i-th
            int[][][] dp = new int[n + 1][k + 1][2];
            
            // Initialize with negative infinity (impossible states)
            for (int i = 0; i <= n; i++) {
                for (int j = 0; j <= k; j++) {
                    dp[i][j][0] = Integer.MIN_VALUE / 2;
                    dp[i][j][1] = Integer.MIN_VALUE / 2;
                }
            }
            
            dp[0][0][0] = 0; // Base case: 0 houses, 0 robbed

            for (int i = 1; i <= n; i++) {
                for (int j = 0; j <= k; j++) {
                    // Don't rob i-th house
                    dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j][1]);
                    
                    // Rob i-th house
                    if (j > 0) {
                        dp[i][j][1] = dp[i - 1][j - 1][0] + nums[i - 1];
                    }
                }
            }
            
            int result = Math.max(dp[n][k][0], dp[n][k][1]);
            return result < 0 ? -1 : result; // Return -1 if impossible
        }
    }

    class Followup_WithCoolDown {
        public int robWithCooldown(int[] nums, int d) {
            int n = nums.length;
            if (n == 0) return 0;
            if (d >= n) return Arrays.stream(nums).max().getAsInt();
            
            // dp[i] = max money from first i houses
            int[] dp = new int[n + 1];
            dp[0] = 0;
            dp[1] = nums[0];
            
            for (int i = 2; i <= n; i++) {
                // Don't rob i-th house
                dp[i] = dp[i - 1];
                
                // Rob i-th house (must have skipped d previous houses)
                int robIdx = i - d - 1;
                if (robIdx >= 0) {
                    dp[i] = Math.max(dp[i], dp[robIdx] + nums[i - 1]);
                } else {
                    // Can rob if within cooldown from start
                    dp[i] = Math.max(dp[i], nums[i - 1]);
                }
                // int prevIdx = Math.max(0, i - d - 1);
                // dp[i] = Math.max(dp[i], dp[prevIdx] + nums[i - 1]);
            }
            
            return dp[n];
        }

        public int robWithCooldown_SlidingWindow(int[] nums, int d) {
            int n = nums.length;
            if (n == 0) return 0;
            
            // Keep track of last (d+1) values
            Queue<Integer> window = new ArrayDeque<>();
            window.offer(0); // dp[0]
            
            int maxSoFar = 0;
            
            for (int num : nums) {
                // Current max without robbing
                int notRob = maxSoFar;
                
                // Max if we rob current house
                int rob = num;
                if (window.size() > d) {
                    rob += window.poll();
                }
                
                int current = Math.max(rob, notRob);
                window.offer(current);
                maxSoFar = Math.max(maxSoFar, current);
            }
            
            return maxSoFar;
        }
    }

    class Followup_ReturnRobbedHouses {
        public List<Integer> robHousesPath(int[] nums) {
            int n = nums.length;
            if (n == 0) return new ArrayList<>();
            if (n == 1) return Arrays.asList(0);
            
            int[] dp = new int[n];            
            dp[0] = nums[0];
            dp[1] = Math.max(nums[0], nums[1]);
            
            for (int i = 2; i < n; i++) {
                if (dp[i - 1] > dp[i - 2] + nums[i]) {
                    dp[i] = dp[i - 1];
                } else {
                    dp[i] = dp[i - 2] + nums[i];
                }
            }
            
            // iterate backward to find path
            List<Integer> path = new ArrayList<>();
            for (int i = n - 1; i > 0;) {
                // Check if we robbed house i
                if (i >= 2) {
                    // Did we rob house i?
                    if (dp[i] == dp[i - 2] + nums[i]) {
                        path.add(i);
                        i -= 2; // Skip adjacent
                    } else {
                        i--; // We didn't rob house i
                    }
                } else {
                    if (dp[1] == nums[1]) {
                        path.add(1);
                    } else {
                        path.add(0);
                    }
                    i--;
                }
            }

            Collections.reverse(path);
            return path;
        }
    }
}
