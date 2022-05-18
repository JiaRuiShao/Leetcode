public class _167 {
    static class Solution {
        // use two pointers to solve the sorted twoSum questions
        public int[] twoSum(int[] nums, int target) {
            int i = 0;
            int j = nums.length - 1;
            while (i < j) {
                if (nums[i] + nums[j] == target) {
                    return new int[] {++i, ++j};
                } else if (nums[i] + nums[j] < target) {
                    i++;
                } else {
                    j--;
                }
            }
            return new int[2];
        }
    }
}
