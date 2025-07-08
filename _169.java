import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 169. Majority Element
 */
public class _169 {
    class Solution1_Boyer_Moore {
        public int majorityElement(int[] nums) {
            int vote = 0;
            int candidate = 0;
            for (int num : nums) {
                if (vote <= 0) { // when vote for last candidate became 0, we set curr num as new candidate
                    candidate = num;
                    vote = 1;
                } else if (candidate == num) {
                    vote++;
                } else {
                    vote--;
                }
            }
            return candidate;
        }
    }

    class Solution2_HashMap {
        public int majorityElement(int[] nums) {
            Map<Integer, Integer> map = new HashMap<>();
            int threshold = nums.length / 2;

            for (int num : nums) {
                map.put(num, map.getOrDefault(num, 0) + 1);
                if (map.get(num) > threshold) return num;
            }
            return -1; // won't happen per problem statement
        }
    }

    class Solution3_Sorting {
        public int majorityElement(int[] nums) {
            Arrays.sort(nums);
            return nums[nums.length / 2]; // majority element will always be at middle index
        }
    }

    class Solution4_Divide_And_Conquer {
        public int majorityElement(int[] nums) {
            return majority(nums, 0, nums.length - 1);
        }

        private int majority(int[] nums, int left, int right) {
            if (left == right) return nums[left];

            int mid = (left + right) / 2;
            int leftMajor = majority(nums, left, mid);
            int rightMajor = majority(nums, mid + 1, right);

            if (leftMajor == rightMajor) return leftMajor;

            return count(nums, leftMajor, left, right) > count(nums, rightMajor, left, right)
                    ? leftMajor : rightMajor;
        }

        private int count(int[] nums, int target, int left, int right) {
            int count = 0;
            for (int i = left; i <= right; i++) {
                if (nums[i] == target) count++;
            }
            return count;
        }
    }

    class Solution5_Brute_Force {
        public int majorityElement(int[] nums) {
            int n = nums.length;

            for (int i = 0; i < n; i++) {
                int count = 0;
                for (int j = i; j < n; j++) {
                    if (nums[j] == nums[i]) count++;
                }
                if (count > n / 2) return nums[i];
            }

            return -1; // per problem, this will never happen
        }
    }

    class Solution_Bit_Counting {
        public int majorityElement(int[] nums) {
            int n = nums.length, majority = 0, mask = 1;
    
            for (int i = 0; i < 32; i++) {
                int maskCount = 0;
                for (int num : nums) {
                    if ((num & mask) == mask) {
                        maskCount++;
                    }
                }
                if (maskCount > (n / 2)) {
                    majority |= mask;
                }
                mask <<= 1;
            }
    
            return majority;
        }
    }
}
