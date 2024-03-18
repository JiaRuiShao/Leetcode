/**
 * 977. Squares of a Sorted Array.
 */
public class _977 {
    class Solution_Two_Pointers {
        public int[] sortedSquares(int[] nums) {
            int n = nums.length;
            int[] squares = new int[n];
            int left = 0, right = n - 1, squareIdx = n - 1;
            while (left <= right) {
                if (Math.abs(nums[left]) >= Math.abs(nums[right])) {
                    squares[squareIdx--] = (int) Math.pow(nums[left++], 2);
                } else {
                    squares[squareIdx--] = (int) Math.pow(nums[right--], 2);
                }
            }
            return squares;
        }
    }
}
