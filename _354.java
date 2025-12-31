import java.util.Arrays;

/**
 * 354. Russian Doll Envelopes
 * 
 * Sort by one dimension asc and then another dimension desc
 * By this, we convert this problem into a 1D LIS problem
 * 
 * Followup:
 * - What if we could rotate envelopes? Expand input: for each [w, h], add both [w, h] and [h, w]; then apply the same algo
 * - Return actual envelope sequence instead? Use int[] parents to track parent pointers
 * - Can you solve it without sorting? Yes but we need to iterate multiple times
 */
public class _354 {
    // Time: O(nlogn + n^2) = O(n^2)
    // Space: O(logn + n) = O(n)
    class Solution1_DP {
        // sort by width asc, then by height desc
        public int maxEnvelopes(int[][] envelopes) {
            Arrays.sort(envelopes, (e1, e2) -> {
                if (e1[0] == e2[0]) {
                    return Integer.compare(e2[1], e1[1]);
                }
                return Integer.compare(e1[0], e2[0]);
            });

            int n = envelopes.length, maxLen = 0;
            int[] dp = new int[n]; // LIS ends at index i
            Arrays.fill(dp, 1);
            for (int i = 0; i < n; i++) {
                for (int j = i - 1; j >= 0; j--) {
                    if (envelopes[i][1] > envelopes[j][1]) {
                        dp[i] = Math.max(dp[i], dp[j] + 1);
                    }
                }
                maxLen = Math.max(maxLen, dp[i]);
            }
            return maxLen;
        }
    }

    // Time: O(nlogn)
    // Space: O(logn + n) = O(n)
    class Solution2_PatienceSorting_BinarySearch {
        public int maxEnvelopes(int[][] envelopes) {
            // sort by width asc, then by height desc
            Arrays.sort(envelopes, (e1, e2) -> {
                if (e1[0] == e2[0]) {
                    return Integer.compare(e2[1], e1[1]);
                }
                return Integer.compare(e1[0], e2[0]);
            });

            int n = envelopes.length, len = 0;
            int[] piles = new int[n]; // a sorted tails of increasing heights for [1...i]
            for (int i = 0; i < n; i++) {
                int height = envelopes[i][1];
                int index = lowerBoundBinarySearch(piles, 0, len - 1, height);
                if (index == len) len++;
                piles[index] = height;
            }
            return len;
        }

        // based on patience sorting logic, we find the index of piles we can add this new card (height) into
        private int lowerBoundBinarySearch(int[] nums, int left, int right, int target) {
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (nums[mid] >= target) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            return left;
        }
    }

    // Time: O(n^3)
    // Space: O(n)
    class Followup_NoSorting {
        public int maxEnvelopes(int[][] envelopes) {
            if (envelopes == null || envelopes.length == 0) {
                return 0;
            }
            
            int n = envelopes.length;
            int[] dp = new int[n];
            Arrays.fill(dp, 1);
            
            // Keep iterating until no changes
            boolean changed = true;
            while (changed) {
                changed = false;
                
                for (int i = 0; i < n; i++) {
                    int oldVal = dp[i];
                    
                    for (int j = 0; j < n; j++) {
                        if (i == j) continue;
                        
                        // Can j fit into i?
                        if (envelopes[j][0] < envelopes[i][0] && 
                            envelopes[j][1] < envelopes[i][1]) {
                            dp[i] = Math.max(dp[i], dp[j] + 1);
                        }
                    }
                    
                    if (dp[i] != oldVal) {
                        changed = true;
                    }
                }
            }
            
            return Arrays.stream(dp).max().getAsInt();
        }
    }
}
