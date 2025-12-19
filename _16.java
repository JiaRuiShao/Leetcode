import java.util.Arrays;

/**
 * 16. 3Sum Closest
 * 
 * 1 - BF: O(n^3), O(1)
 * 2 - Sort + TP: O(n^2), O(logn)
 * 
 * Clarification:
 * - range of nums[i] & target?
 * - min array len >= 3?
 * - input array sorted?
 * 
 * Followup:
 * - Add early termination condition
 * - Variant: k-Sum Closest
 * - Alternative approaches without sorting
 * - Binary search alternative instead of two pointers
 */
public class _16 {
    // Time: O(n^2)
    // Space: O(logn)
    class Solution1_Sort_TwoPointers {
        // a + b + c + delta == target
        // res = target - min(abs(delta)) 
        public int threeSumClosest(int[] nums, int target) {
            int n = nums.length;
            int minAbsDelta = target - (nums[0] + nums[1] + nums[2]);
            Arrays.sort(nums);
            for (int i = 0; i < n - 2; i++) {
                int left = i + 1, right = n - 1, k = target - nums[i];
                while (left < right) {
                    int delta = k - nums[left] - nums[right];
                    if (Math.abs(delta) < Math.abs(minAbsDelta)) {
                        minAbsDelta = delta;
                    }
                    if (delta == 0) break;
                    else if (delta > 0) left++;
                    else right--;
                }
            }
            return target - minAbsDelta;
        }

        public int threeSumClosestWithEarlyTermination(int[] nums, int target) {
            int n = nums.length;
            int minAbsDelta = target - (nums[0] + nums[1] + nums[2]);
            Arrays.sort(nums);
            for (int i = 0; i < n - 2; i++) {
                // early termination: min possible sum >= target
                int minSum = nums[i] + nums[i + 1] + nums[i + 2];
                if (minSum >= target) {
                    if (Math.abs(target - minSum) < minAbsDelta) {
                        minAbsDelta = target - minSum;
                    }
                    break;
                }

                // early termination: max possible sum <= target
                int maxSum = nums[i] + nums[n - 2] + nums[n - 1];
                if (maxSum <= target) {
                    if (Math.abs(target - maxSum) < minAbsDelta) {
                        minAbsDelta = target - maxSum;
                    }
                    continue;
                }

                int left = i + 1, right = n - 1, k = target - nums[i];
                while (left < right) {
                    int delta = k - nums[left] - nums[right];
                    if (Math.abs(delta) < Math.abs(minAbsDelta)) {
                        minAbsDelta = delta;
                    }
                    if (delta == 0) break;
                    else if (delta > 0) left++;
                    else right--;
                }
            }
            return target - minAbsDelta;
        }
    }

    class Followup_kSumClosest {
        public int kSumClosest(int[] nums, int target, int k) {
            Arrays.sort(nums);
            return kSumClosestHelper(nums, target, k, 0);
        }

        private int kSumClosestHelper(int[] nums, int target, int k, int start) {
            int n = nums.length;
            
            if (k == 2) {
                // Base case: two-sum closest
                int left = start, right = n - 1;
                int closest = nums[left] + nums[right];
                
                while (left < right) {
                    int sum = nums[left] + nums[right];
                    if (Math.abs(sum - target) < Math.abs(closest - target)) {
                        closest = sum;
                    }
                    if (sum == target) return target;
                    
                    if (sum < target) {
                        left++;
                    } else {
                        right--;
                    }
                }
                return closest;
            }
            
            // Recursive case
            int closest = Integer.MAX_VALUE / 2;  // Avoid overflow
            
            for (int i = start; i < n - k + 1; i++) {
                int subClosest = kSumClosestHelper(nums, target - nums[i], k - 1, i + 1);
                int currentSum = nums[i] + subClosest;
                
                if (Math.abs(currentSum - target) < Math.abs(closest - target)) {
                    closest = currentSum;
                }
                
                if (currentSum == target) return target;
            }
            
            return closest;
        }
    }

    // Time: O(n^2logn)
    // Space: O(logn)
    class Solution2_Sort_BinarySearch {
        public int threeSumClosest(int[] nums, int target) {
            Arrays.sort(nums);
            int closest = nums[0] + nums[1] + nums[2];
            
            for (int i = 0; i < nums.length - 2; i++) {
                for (int j = i + 1; j < nums.length - 1; j++) {
                    int twoSum = nums[i] + nums[j];
                    int complement = target - twoSum;
                    
                    // Binary search for closest to complement
                    int k = binarySearchClosest(nums, j + 1, nums.length - 1, complement);
                    int sum = twoSum + nums[k];
                    
                    if (Math.abs(sum - target) < Math.abs(closest - target)) {
                        closest = sum;
                    }
                }
            }
            
            return closest;
        }

        private int binarySearchClosest(int[] nums, int left, int right, int target) {
            int closest = left;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (Math.abs(nums[mid] - target) < Math.abs(nums[closest] - target)) {
                    closest = mid;
                }
                if (nums[mid] < target) {
                    left = mid + 1;
                } else if (nums[mid] > target) {
                    right = mid - 1;
                } else {
                    return mid;
                }
            }
            return closest;
        }
    }
}
