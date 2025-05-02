import java.util.Arrays;

/**
 * 259. 3Sum Smaller
 */
public class _259 {
    class Solution1_Sorting_Greedy_Two_Pointers {
        // [3,1,0,-2], 4
        // 3
        public int threeSumSmaller(int[] nums, int target) {
            Arrays.sort(nums);
            int n = nums.length, triplets = 0;
            for (int i = 0; i < n; i++) { // or n - 2
                int left = i + 1, right = n - 1;
                while (left < right) {
                    int sum = nums[i] + nums[left] + nums[right];
                    if (sum < target) {
                        triplets += right - left;
                        left++;
                        // right = n - 1;
                    } else {
                        right--;
                    }
                }
            }
            return triplets;
        }
    }
}
