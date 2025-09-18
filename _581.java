import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * 581. Shortest Unsorted Continuous Subarray
 */
public class _581 {

    // for each elem, go left to find the min index i that nums[i] > currVal; go right to find the max index j that nums[j] < currVal
    // return j - i + 1
    class Solution0_BruteForce {
            public int findUnsortedSubarray(int[] nums) {
            int l = nums.length, r = 0;
            for (int i = 0; i < nums.length - 1; i++) {
                for (int j = i + 1; j < nums.length; j++) {
                    if (nums[j] < nums[i]) {
                        r = Math.max(r, j);
                        l = Math.min(l, i);
                    }
                }
            }
            return r - l < 0 ? 0 : r - l + 1;
        }
    }

    class Solution1_Sorting {
        public int findUnsortedSubarray(int[] nums) {
            int n = nums.length;
            int[] sorted = new int[n];
            System.arraycopy(nums, 0, sorted, 0, n);
            Arrays.sort(sorted);
            
            int left = n, right = -1;
            for (int i = 0; i < n; i++) {
                if (sorted[i] != nums[i]) {
                    left = i;
                    break;
                }
            }

            for (int i = n - 1; i >= 0; i--) {
                if (sorted[i] != nums[i]) {
                    right = i;
                    break;
                }
            }

            return left == n ? 0 : right - left + 1;
        }
    }

    class Solution2_MonoStack {
        // [1,3,2,2,2], res = 4
        // [1,2,5,3,4], res = 3
        public int findUnsortedSubarray(int[] nums) {
            int n = nums.length;
            int start = n + 1, end = -1;
            Deque<Integer> minStk = new ArrayDeque<>(); // index queue with mono increasing order of nums[i]
            for (int i = 0; i < n; i++) {
                int num = nums[i];
                while (!minStk.isEmpty() && num < nums[minStk.peek()]) {
                    start = Math.min(start, minStk.pop());
                }
                minStk.push(i);
            }
            Deque<Integer> maxStk = new ArrayDeque<>(); // index queue with mono decreasing order of nums[i]
            for (int i = n - 1; i >= 0; i--) {
                int num = nums[i];
                while (!maxStk.isEmpty() && num > nums[maxStk.peek()]) {
                    end = Math.max(end, maxStk.pop());
                }
                maxStk.push(i);
            }
            return minStk.size() == n ? 0 : end - start + 1;
        }
    }

    // This is the optimal solution, we tracks a running max when traverse from left to right to record the rightmost boundary; tracks a running min when traverse from right to left to record the leftmost boundary
    class Solution3_Traversal {
        // [1,3,2,2,2,1]
        // [2,6,4,8,10,9,15]
        public int findUnsortedSubarray(int[] nums) {
            int n = nums.length;
            int start = n, end = -1, min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                int num = nums[i];
                if (num < max) {
                    end = i;
                } else if (num > max) {
                    max = num;
                }
            }

            for (int i = n - 1; i >= 0; i--) {
                int num = nums[i];
                if (num > min) {
                    start = i;
                } else if (num < min) {
                    min = num;
                }
            }
            return start == n ? 0 : end - start + 1;
        }
    }
}
