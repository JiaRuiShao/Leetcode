/**
 * 167. Two Sum II - Input Array Is Sorted.
 */
public class _167 {
    static class Solution {
        // use left & right pointers to solve the sorted twoSum questions
        public int[] twoSum(int[] numbers, int target) {
            int left = 0, right = numbers.length - 1;
            while (left <= right) {
                int sum = numbers[left] + numbers[right];
                if (sum == target) return new int[]{left + 1, right + 1};
                else if (sum < target) left++;
                else right--;
            }
            return new int[]{-1, -1};
        }
    }
}
