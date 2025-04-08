/**
 * 509. Fibonacci Number
 */
public class _509 {
    /**
     * Time: O(n)
     * Space: O(n)
     */
    class Solution1_DP_Top_Down {
        public int fib(int n) {
            int[] fibNums = new int[n + 1]; // allocate n + 1 size because n here is like the index
            return findFibNum(fibNums, n);
        }
    
        private int findFibNum(int[] nums, int n) {
            if (n < 2) return n;
            if (nums[n] != 0) return nums[n]; // cached before
            nums[n] = findFibNum(nums, n - 1) + findFibNum(nums, n - 2);
            return nums[n];
        }
    }

    /**
     * Time: O(2^n)
     * Space: O(n)
     */
    class Solution2_Recursion {
        public int fib(int n) {
            if (n < 2) return n;
            return fib(n - 1) + fib(n - 2);
        }
    }

    /**
     * Time: O(n)
     * Space: O(n)
     */
    class Solution3_Iterative_Bottom_Up {
        public int fib(int n) {
            if (n < 2) return n;
            int[] fibNums = new int[n + 1];
            return findFibNum(fibNums, n);
        }
    
        private int findFibNum(int[] nums, int n) {
            nums[1] = 1;
            for (int i = 2; i <= n; i++) {
                nums[i] = nums[i - 1] + nums[i - 2];
            }
            return nums[n];
        }
    }

    /**
     * Time: O(n)
     * Space: O(1)
     */
    class Solution3_Iterative_Bottom_Up_Improved {
        public int fib(int n) {
            if (n < 2) return n;
            int curr = 0, prev1 = 1, prev2 = 0;
            for (int i = 2; i <= n; i++) { // ** i <= n **
                curr = prev1 + prev2;
                prev2 = prev1;
                prev1 = curr;
            }
            return curr;
        }
    }
}
