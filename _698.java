import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * 698. Partition to K Equal Sum Subsets
 */
public class _698 {
    class Solution1_Backtrack_By_Box {
        public boolean canPartitionKSubsets(int[] nums, int k) {
            int sum = Arrays.stream(nums).sum();
            if (sum % k != 0) return false;
            int target = sum / k;
            long used = 0L;
            Arrays.sort(nums);
            Map<Long, Boolean> memo = new HashMap<>();
            return backtrack(nums, used, 0, target, k, memo);
        }

        private boolean backtrack(int[] nums, long used, int curr, int target, int bucket, Map<Long, Boolean> memo) {
            if (bucket == 0) return true;
            if (curr == target) {
                return backtrack(nums, used, 0, target, bucket - 1, memo);
            }
            if (memo.containsKey(used)) return memo.get(used);
            for (int i = nums.length - 1; i >= 0; i--) {
                int mask = 1 << i;
                if ((used & mask) == mask || curr + nums[i] > target) continue;
                used ^= mask;
                if (backtrack(nums, used, curr + nums[i], target, bucket, memo)) {
                    memo.put(used, true);
                    return true;
                }
                used ^= mask;
            }
            memo.put(used, false);
            return false;
        }
    }

    class Solution2_Backtrack_By_Ball {
        public boolean canPartitionKSubsets(int[] nums, int k) {
            int sum = Arrays.stream(nums).sum();
            if (sum % k != 0) return false;
            int target = sum / k;
            int[] subsets = new int[k];
            Arrays.fill(subsets, target);
            Arrays.sort(nums);
            return backtrack(nums, nums.length - 1, subsets);
        }

        private boolean backtrack(int[] nums, int idx, int[] subsets) {
            if (idx == -1) {
                return Arrays.stream(subsets).allMatch(e -> e == 0);
            }
            for (int i = 0; i < subsets.length; i++) {
                if (subsets[i] - nums[idx] < 0) continue;
                subsets[i] -= nums[idx];
                if (backtrack(nums, idx - 1, subsets)) return true;
                subsets[i] += nums[idx];
            }
            return false;
        }
    }
}
