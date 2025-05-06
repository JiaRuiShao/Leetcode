import java.util.Arrays;

/**
 * 16. 3Sum Closest
 */
public class _16 {
    class Solution {
        // [-1000,-1000,-1000], target = 10000, res = -3000
        public int threeSumClosest(int[] nums, int target) {
            Arrays.sort(nums);
            int closest = nums[0] + nums[1] + nums[2];

            for (int i = 0; i < nums.length - 2; i++) {
                int lo = i + 1, hi = nums.length - 1;

                while (lo < hi) {
                    int sum = nums[i] + nums[lo] + nums[hi];

                    if (Math.abs(sum - target) < Math.abs(closest - target)) {
                        closest = sum;
                    }

                    if (sum == target) {
                        return sum; // exact match
                    } else if (sum < target) {
                        lo++;
                    } else {
                        hi--;
                    }
                }
            }

            return closest;
        }
    }

    class Solution2 {
        public int threeSumClosest(int[] nums, int target) {
            Arrays.sort(nums);
            int closestSum = 26001; // 2 * (max(target) + max(sum(nums[i] + nums[j] + nums[k]))) + 1
            for (int i = 0; i < nums.length - 2; i++) {
                // find min(delta) so that n2 + n3 + delta = supplement ==> delta = supplement - sum(n2, n3)
                int twoSum = findTwoSumClosest(nums, i + 1, nums.length - 1, target - nums[i]);
                closestSum = findClosestSum(closestSum, twoSum + nums[i], target);
            }
            return closestSum;
        }
    
        private int findTwoSumClosest(int[] nums, int start, int end, int target) {
            int lo = start, hi = end, closestSum = 24001; // 2 * (max(target) + max(sum(nums[i] + nums[j]))) + 1
            while (lo < hi) {
                int twoSum = nums[lo] + nums[hi];
                closestSum = findClosestSum(closestSum, twoSum, target);
                if (twoSum >= target) {
                    hi--;
                } else {
                    lo++;
                }
            }
            return closestSum;
        }
    
        private int findClosestSum(int n1, int n2, int target) {
            int diff1 = Math.abs(n1 - target), diff2 = Math.abs(n2 - target);
            return diff1 < diff2 ? n1 : n2;
        }
    }
}
