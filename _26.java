public class _26 {
    class Solution {
        public int removeDuplicates(int[] nums) {
            if (nums == null || nums.length == 0) return 0;
            int left = 0, right = 0; // unique nums are records at the left (& equal to) of the left right
            while (++right < nums.length) {
                if (nums[right] != nums[left]) {
                    left++;
                    if (left != right) nums[left] = nums[right];
                }
            }
            return left + 1;
        }
    }
}
