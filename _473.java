import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 473. Matchsticks to Square
 */
public class _473 {
    // Backtracking on matchsticks which takes O(n) time for each call stack level
    // Time: O(n^4) -- normally this is way smaller than O(4^n) for n >= 6 but notice we cannot guarantee that's only 4 levels and there're too much redundant work
    // Space: O(n)
    class Solution0_Backtrack_Side_By_Side_TLE {
        private boolean makeSquare = false;
        public boolean makesquare(int[] matchsticks) {
            int sum = Arrays.stream(matchsticks).sum();
            Arrays.sort(matchsticks);
            reverse(matchsticks);
            if (sum % 4 != 0) return false;
            int[] sides = new int[4];
            Arrays.fill(sides, sum / 4);
            backtrack(matchsticks, new boolean[matchsticks.length], sides, 0);
            return makeSquare;
        }

        private void backtrack(int[] nums, boolean[] used, int[] target, int idx) {
            if (makeSquare || idx == target.length) { // implies all matchsticks are used
                makeSquare = true;
                return;
            }
            
            for (int i = 0; i < nums.length; i++) {
                int len = nums[i];
                if (i > 0 && len == nums[i - 1] && !used[i - 1]) continue;
                if (used[i] || target[idx] < len) continue;

                used[i] = true;
                target[idx] -= nums[i];
                if (target[idx] == 0) {
                    backtrack(nums, used, target, idx + 1);
                } else {
                    backtrack(nums, used, target, idx);
                }
                used[i] = false;
                target[idx] += nums[i];
            }
        }

        private void reverse(int[] nums) {
            int n = nums.length;
            int lo = 0, hi = n - 1;
            while (lo < hi) {
                int tmp = nums[lo];
                nums[lo] = nums[hi];
                nums[hi] = tmp;
                lo++;
                hi--;
            }
        }
    }

    class Solution0_Backtrack_Side_By_Side_TLE_Without_Global_Variable {
        public boolean makesquare(int[] matchsticks) {
            int sum = Arrays.stream(matchsticks).sum();
            Arrays.sort(matchsticks);
            reverse(matchsticks);
            if (sum % 4 != 0) return false;
            int[] sides = new int[4];
            Arrays.fill(sides, sum / 4);
            
            return backtrack(matchsticks, new boolean[matchsticks.length], sides, 0);
        }

        private boolean backtrack(int[] nums, boolean[] used, int[] target, int idx) {
            if (idx == target.length) { // implies all matchsticks are used
                return true;
            }
            
            for (int i = 0; i < nums.length; i++) {
                int len = nums[i];
                if (i > 0 && len == nums[i - 1] && !used[i - 1]) continue;
                if (used[i] || target[idx] < len) continue;

                used[i] = true;
                target[idx] -= nums[i];
                boolean makeSquare = false;
                if (target[idx] == 0) {
                    makeSquare = backtrack(nums, used, target, idx + 1);
                } else {
                    makeSquare = backtrack(nums, used, target, idx);
                }
                if (makeSquare) return true; // short-circuit if successful
                used[i] = false;
                target[idx] += nums[i];
            }
            
            return false;
        }

        private void reverse(int[] nums) {
            int n = nums.length;
            int lo = 0, hi = n - 1;
            while (lo < hi) {
                int tmp = nums[lo];
                nums[lo] = nums[hi];
                nums[hi] = tmp;
                lo++;
                hi--;
            }
        }
    }

    // Improvement 1 -- Memorization: use memo to track previously used sticks to fill a bucket
    // Improvement 2 -- Bitmask: since n < 16 here, we can use the binary form of a int to function as boolean[] used; if n > 30, we need to use Arrays.stream(used) to generate the memo key
    // Ref: https://labuladong.online/algo/practice-in-action/partition-to-k-equal-sum-subsets / https://www.notion.so/jiaruishao/473-Backtrack-23fa71ce74f180eb8427d3af87ac6578
    // Time: O(n*2^n) -- at most 2^n different used states, unique recursive call stack # is 2^n; at most traverse n matchsticks for each call stack
    // Space: O(2^n)
    class Solution1_Backtrack_Side_By_Side_Without_Global_Variable_Improved {
        public boolean makesquare(int[] matchsticks) {
            int sum = Arrays.stream(matchsticks).sum();
            if (sum % 4 != 0) return false;
            int[] sides = new int[4];
            Arrays.fill(sides, sum / 4);
            Map<Integer, Boolean> memo = new HashMap<>();
            return backtrack(matchsticks, 0, sides, 0, memo);
        }

        private boolean backtrack(int[] nums, int used, int[] target, int idx, Map<Integer, Boolean> memo) {
            if (idx == target.length) { // implies all matchsticks are used
                return true;
            }

            if (memo.containsKey(used)) return memo.get(used);
            
            for (int i = 0; i < nums.length; i++) {
                int len = nums[i];
                if (i > 0 && len == nums[i - 1] && ((used >>> (i - 1)) & 1) == 0) continue;
                if (((used >>> i) & 1) == 1 || target[idx] < len) continue;

                used |= (1 << i);
                target[idx] -= nums[i];
                boolean makeSquare = false;
                if (target[idx] == 0) {
                    makeSquare = backtrack(nums, used, target, idx + 1, memo);
                } else {
                    makeSquare = backtrack(nums, used, target, idx, memo);
                }
                if (makeSquare) return true; // short-circuit if successful
                memo.put(used, makeSquare);
                used ^= (1 << i);
                target[idx] += nums[i];
            }

            return false;
        }
    }

    class Solution1_Backtrack_Side_By_Side_Improved {
        private boolean makeSquare = false;
        public boolean makesquare(int[] matchsticks) {
            int sum = Arrays.stream(matchsticks).sum();
            if (sum % 4 != 0) return false;
            int[] sides = new int[4];
            Arrays.fill(sides, sum / 4);
            Map<Integer, Boolean> memo = new HashMap<>();
            backtrack(matchsticks, 0, sides, 0, memo);
            return makeSquare;
        }

        private void backtrack(int[] nums, int used, int[] target, int idx, Map<Integer, Boolean> memo) {
            if (makeSquare || idx == target.length) {
                makeSquare = true;
                return;
            }

            if (memo.containsKey(used)) return;
            
            for (int i = 0; i < nums.length; i++) {
                int len = nums[i];
                if (i > 0 && len == nums[i - 1] && ((used >>> (i - 1)) & 1) == 0) continue;
                if (((used >>> i) & 1) == 1 || target[idx] < len) continue;

                used |= (1 << i);
                target[idx] -= nums[i];
                if (target[idx] == 0) {
                    backtrack(nums, used, target, idx + 1, memo);
                } else {
                    backtrack(nums, used, target, idx, memo);
                }
                memo.put(used, makeSquare);
                if (makeSquare) return; // short-circuit if successful
                used ^= (1 << i);
                target[idx] += nums[i];
            }
        }
    }

    // Backtracking on matchsticks which is in desc order -- no boolean[] needed which takes O(4) time for each call stack level
    // Time: O(4^n)
    // Space: O(n)
    class Solution2_Backtrack_Stick_By_Stick {
        private boolean makeSquare = false;
        public boolean makesquare(int[] matchsticks) {
            int sum = Arrays.stream(matchsticks).sum();
            // sorting in desc order helps to prune the decision tree, without it we will get TLE
            Arrays.sort(matchsticks);
            reverse(matchsticks);
            if (sum % 4 != 0) return false;
            int[] sides = new int[4];
            Arrays.fill(sides, sum / 4);
            backtrack(matchsticks, 0, sides);
            return makeSquare;
        }

        private void backtrack(int[] nums, int idx, int[] sides) {
            if (makeSquare || idx == nums.length) {
                makeSquare = true;
                return;
            }

            for (int i = 0; i < sides.length; i++) {
                if (sides[i] < nums[idx]) continue;
                sides[i] -= nums[idx];
                backtrack(nums, idx + 1, sides);
                sides[i] += nums[idx];
            }
        }

        private void reverse(int[] nums) {
            int n = nums.length;
            int lo = 0, hi = n - 1;
            while (lo < hi) {
                int tmp = nums[lo];
                nums[lo] = nums[hi];
                nums[hi] = tmp;
                lo++;
                hi--;
            }
        }
    }
}
