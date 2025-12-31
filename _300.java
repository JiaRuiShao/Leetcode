import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 300. Longest Increasing Subsequence
 * 
 * - S1: BF O(n^2), O(n)
 * - S2: DP O(n^2), O(n)
 * - S3: BS + DP O(nlogn), O(n)
 * 
 * Clarification:
 * - What's the range? 1 ≤ nums.length ≤ 2500, -10^4 ≤ nums[i] ≤ 10^4
 * - Return length or actual subsequence? Length only, but be ready to return subsequence
 * 
 * Followup:
 * - Return the LIS instead? Use parents[] to track the prev index
 * - Prove subseqs array is sorted? Contradiction theory: subseqs[i] > subseqs[j] for i < j if it's not sorted; based on array definition, we know subseqs[i] is smallest tail for subsequences of length i + 1; if i < j & subseqs[i] is the largest, then by definition, subseqs[j] = subseqs[i]
 * - Find longest decreasing subsequence instead? 
 * - Find longest bitonic subsequence (Bitonic = increasing then decreasing)? 
 * - Why we can't use mono stack here? Because mono stack is a greedy approach and it assumes current elem is part of the LIS; this problem needs global optimization however stack can only keep one selection
 */
public class _300 {
    // Time: O(2^n)
    // Space: O(n)
    class Solution0_Backtrack_TLE {
        public int lengthOfLIS(int[] nums) {
            return backtrack(nums, -10001, 0);
        }

        private int backtrack(int[] nums, int prev, int i) {
            if (i == nums.length) return 0;
            int num = nums[i++], maxLen = 0;
            // not select
            maxLen = backtrack(nums, prev, i);
            // select
            if (num > prev) {
                maxLen = Math.max(maxLen, 1 + backtrack(nums, num, i));
            }
            return maxLen;
        }

        // Time: O(n^2)
        private int backtrackWithMemo(int[] nums, int prev, int i, Map<String, Integer> memo) {
            if (i == nums.length) return 0;
            String key = prev + "-" + i;
            if (memo.containsKey(key)) {
                return memo.get(key);
            }

            int num = nums[i++], maxLen = 0;
            // not select
            maxLen = backtrackWithMemo(nums, prev, i, memo);
            // select
            if (num > prev) {
                maxLen = Math.max(maxLen, 1 + backtrackWithMemo(nums, num, i, memo));
            }
            memo.put(key, maxLen);
            return maxLen;
        }
    }

    // Time: O(2^n × n)
    // Space: O(n) temporary subsequence list
    class Solution0_BF_BitMasking {
        public int lengthOfLIS(int[] nums) {
            if (nums == null || nums.length == 0) return 0;
            
            int n = nums.length;
            int maxLen = 1;
            
            // Try all possible subsets (2^n)
            for (int mask = 0; mask < (1 << n); mask++) {
                List<Integer> subsequence = new ArrayList<>();
                
                // Build subsequence based on mask
                for (int i = 0; i < n; i++) {
                    if ((mask & (1 << i)) != 0) {
                        subsequence.add(nums[i]);
                    }
                }
                
                // Check if it's strictly increasing
                if (isStrictlyIncreasing(subsequence)) {
                    maxLen = Math.max(maxLen, subsequence.size());
                }
            }
            
            return maxLen;
        }

        private boolean isStrictlyIncreasing(List<Integer> list) {
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i) <= list.get(i - 1)) {
                    return false;
                }
            }
            return true;
        }
    }

    class Solution1_DP {
        // Time: O(n^2)
        // Space: O(n)
        public int lengthOfLIS(int[] nums) {
            int n = nums.length, maxLen = 0;
            int[] dp = new int[n]; // max len subseq ends at index i
            Arrays.fill(dp, 1);

            for (int i = 0; i < n; i++) {
                for (int j = i - 1; j >= 0; j--) {
                    if (nums[i] > nums[j]) { // >= if needs non-decreasing subsequence
                        dp[i] = Math.max(dp[i], dp[j] + 1);
                    }
                }
                maxLen = Math.max(maxLen, dp[i]);
            }

            return maxLen;
        }
    }

    // In patience sorting (card game):
    // - Place cards in piles
    // - Card can go on pile only if smaller than top card
    // - Otherwise, start new pile
    // - Use binary search to find the leftmost pile where card fits
    // the subseqs array represents the top card of each pile
    // num of piles = len of LIS
    class Solution2_PatienceSorting_BinarySearch {
        public int lengthOfLIS(int[] nums) {
            int n = nums.length, len = 0; // len of the increasing subsequence is the LIS
            int[] subseqs = new int[n]; // sorted tails of increasing subsequences
            for (int num : nums) {
                int left = 0, right = len - 1;
                while (left <= right) {
                    int mid = left + (right - left) / 2;
                    if (subseqs[mid] >= num) {
                        right = mid - 1;
                    } else {
                        left = mid + 1;
                    }
                }
                if (left == len) len++;
                subseqs[left] = num;
            }
            return len;
        }
    }

    /**
     * dp[i] = Math.max(dp[i], dp[j] + 1) for i > j is a range max problem and thus can be solved by using segment tree.
     * - treat array values as segment tree indices to query for max LIS len given nums[i] and a MIN_VALUE
     * - store max LIS length for each value range
     * 
     * Problem becomes: query max LIS among all nums[j] < nums[i] for each i in [0...n]
     */
    class Solution3_ValueBasedSegmentTree {
        public int lengthOfLIS(int[] nums) {
            if (nums == null || nums.length == 0) return 0;
            
            // Find value range
            int minVal = Integer.MAX_VALUE;
            int maxVal = Integer.MIN_VALUE;
            for (int num : nums) {
                minVal = Math.min(minVal, num);
                maxVal = Math.max(maxVal, num);
            }
            
            HashMapSegmentTree segTree = new HashMapSegmentTree(minVal, maxVal);
            
            int maxLen = 0;
            for (int num : nums) {
                // Query max LIS for all values < num
                int currentLIS = segTree.query(minVal, num - 1) + 1;
                
                // Update segment tree
                segTree.update(num, currentLIS);
                
                maxLen = Math.max(maxLen, currentLIS);
            }
            
            return maxLen;
        }

        class HashMapSegmentTree {
            // Map from node identifier to value
            private Map<String, Integer> tree;
            private int minVal;
            private int maxVal;
            
            public HashMapSegmentTree(int minVal, int maxVal) {
                this.tree = new HashMap<>();
                this.minVal = minVal;
                this.maxVal = maxVal;
            }
            
            public void update(int idx, int value) {
                update(minVal, maxVal, idx, value);
            }
            
            private void update(int start, int end, int idx, int value) {
                String nodeKey = start + "," + end;
                
                if (start == end) {
                    tree.put(nodeKey, Math.max(tree.getOrDefault(nodeKey, 0), value));
                    return;
                }
                
                int mid = start + (end - start) / 2;
                if (idx <= mid) {
                    update(start, mid, idx, value);
                } else {
                    update(mid + 1, end, idx, value);
                }
                
                // Update current node
                String leftKey = start + "," + mid;
                String rightKey = (mid + 1) + "," + end;
                int leftVal = tree.getOrDefault(leftKey, 0);
                int rightVal = tree.getOrDefault(rightKey, 0);
                tree.put(nodeKey, Math.max(leftVal, rightVal));
            }
            
            public int query(int L, int R) {
                if (L > R) return 0;
                return query(minVal, maxVal, L, R);
            }
            
            private int query(int start, int end, int L, int R) {
                if (R < start || end < L) {
                    return 0;
                }
                
                if (L <= start && end <= R) {
                    String nodeKey = start + "," + end;
                    return tree.getOrDefault(nodeKey, 0);
                }
                
                int mid = start + (end - start) / 2;
                int leftMax = query(start, mid, L, R);
                int rightMax = query(mid + 1, end, L, R);
                
                return Math.max(leftMax, rightMax);
            }
        }
    }

    class Followup_ReturnLIS {
        public List<Integer> getLIS(int[] nums) {
            int n = nums.length, maxLen = 0, endsAt = -1;
            int[] dp = new int[n]; // max len subseq ends at index i
            int[] parents = new int[n];
            Arrays.fill(dp, 1);
            Arrays.fill(parents, -1);

            for (int i = 0; i < n; i++) {
                for (int j = i - 1; j >= 0; j--) {
                    if (nums[i] > nums[j]) {
                        if (dp[j] + 1 > dp[i]) {
                            dp[i] = dp[j] + 1;
                            parents[i] = j;
                        }
                    }
                }
                if (dp[i] > maxLen) {
                    maxLen = dp[i];
                    endsAt = i;
                }
            }

            // reconstruct LIS by following parent pointers
            LinkedList<Integer> res = new LinkedList<>();
            for (int i = endsAt; i >= 0; i = parents[i]) {
                res.addFirst(nums[i]);
            }
            return res;
        }
    }

    class Followup_LongestDecreasingSubsequence {
        public int lengthOfLIS(int[] nums) {
            int n = nums.length, maxLen = 0;
            int[] dp = new int[n]; // max len subseq ends at index i
            Arrays.fill(dp, 1);

            for (int i = 0; i < n; i++) {
                for (int j = i - 1; j >= 0; j--) {
                    if (nums[i] < nums[j]) { // ← Change > to <
                        dp[i] = Math.max(dp[i], dp[j] + 1);
                    }
                }
                maxLen = Math.max(maxLen, dp[i]);
            }

            return maxLen;
        }
    }

    class Followup_LongestBitonicSequence {
        public int longestBitonicSubsequence(int[] nums) {
            int n = nums.length;
            
            // LIS ending at i
            int[] lis = new int[n];
            Arrays.fill(lis, 1);
            for (int i = 1; i < n; i++) {
                for (int j = 0; j < i; j++) {
                    if (nums[i] > nums[j]) {
                        lis[i] = Math.max(lis[i], lis[j] + 1);
                    }
                }
            }
            
            // LDS starting from i
            int[] lds = new int[n];
            Arrays.fill(lds, 1);
            for (int i = n - 2; i >= 0; i--) {
                for (int j = n - 1; j > i; j--) {
                    if (nums[i] > nums[j]) {
                        lds[i] = Math.max(lds[i], lds[j] + 1);
                    }
                }
            }
            
            // Find max lis[i] + lds[i] - 1 (peak counted once)
            int maxLen = 0;
            for (int i = 0; i < n; i++) {
                maxLen = Math.max(maxLen, lis[i] + lds[i] - 1);
            }
            
            return maxLen;
        }
    }
}
