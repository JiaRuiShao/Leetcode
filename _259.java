import java.util.Arrays;

/**
 * 259. 3Sum Smaller
 */
public class _259 {
    // Fix the first elem, then use two pointers to greedily count satisfying triplets pairs
    // Time: O(nlogn + n^2)
    // Space: O(logn)
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

    // Time: O(nlogn + n^2*logn)
    // Space: O(1ogn)
    class Solution2_Binary_Search {
        // n1 + n2 + n3 < k ==> n2 + n3 < k - n1 ==>  n3 < k - n1 - n2
        public int threeSumSmaller(int[] nums, int target) {
            Arrays.sort(nums);
            int n = nums.length, triplets = 0;
            for (int i = 0; i < n - 1; i++) {
                triplets += findDoubles(nums, i + 1, n - 1, target - nums[i]);
            }  
            return triplets;
        }
    
        private int findDoubles(int[] nums, int left, int right, int k) {
            int count = 0;
            while (left < right) {
                count += binarySearch(nums, left + 1, right, k - nums[left]) - left;
                left++;
            }
            return count;
        }
    
        // find nums[k] < target, return index k
        private int binarySearch(int[] nums, int lo, int hi, int k) {
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                if (nums[mid] >= k) hi = mid - 1;
                else lo = mid + 1;
            }
            return hi;
        }
    }
}
