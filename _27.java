public class _27 {
    class Solution {
        public int removeElement(int[] nums, int val) {
            if (nums == null || nums.length == 0) return 0;
            int i = 0, j = nums.length - 1;
            while (j >= i) {
                while (i < nums.length && nums[i] != val) i++;
                while (j >= 0 && nums[j] == val) j--;
                if (i < j) {
                    nums[i] = nums[j];
                    nums[j] = val;
                }
            }
            return j + 1;
        }
    }
}
