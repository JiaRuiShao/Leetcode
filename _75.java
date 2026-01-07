/**
 * 75. Sort Colors
 */
public class _75 {
    class Solution0_TwoPass_UglyLogic {
        public void sortColors(int[] nums) {
            int n = nums.length;
            int left = 0, right = n - 1;
            while (left < right) {
                while (right > left && nums[right] == 2) right--;
                while (right > left && nums[left] != 2) left++;
                if (right > left) {
                    nums[left] = nums[right];
                    nums[right] = 2;
                    left++;
                    right--;
                }
            }

            left = 0;
            right = n - 1;
            while (right >= 0 && nums[right] == 2) right--;
            while (left < right) {
                while (right > left && nums[right] == 1) right--;
                while (right > left && nums[left] == 0) left++;
                if (right > left) {
                    nums[left] = nums[right];
                    nums[right] = 1;
                    left++;
                    right--;
                }
            }
        }
    }

    class Solution1_TwoPass_Count {
        public void sortColors(int[] nums) {
            int count0 = 0, count1 = 0, count2 = 0;
            
            // First pass: count each color
            for (int num : nums) {
                if (num == 0) count0++;
                else if (num == 1) count1++;
                else count2++;
            }
            
            // Second pass: overwrite array
            int index = 0;
            
            for (int i = 0; i < count0; i++) {
                nums[index++] = 0;
            }
            
            for (int i = 0; i < count1; i++) {
                nums[index++] = 1;
            }
            
            for (int i = 0; i < count2; i++) {
                nums[index++] = 2;
            }
        }

        public void sortColors2(int[] nums) {
            int[] count = new int[3];
            
            // Count
            for (int num : nums) {
                count[num]++;
            }
            
            // Overwrite
            int i = 0;
            for (int color = 0; color < 3; color++) {
                for (int j = 0; j < count[color]; j++) {
                    nums[i++] = color;
                }
            }
        }
    }

    // [0..left-1]    = 0
    // [left..i-1]    = 1
    // [i..right]     = not processed
    // [right+1..n-1] = 2
    class Solution2_DutchNationalFlag_OnePass {
        public void sortColors(int[] nums) {
            int n = nums.length;
            int left = 0; // range for 0 [0, left)
            int right = n - 1; // range for 2 (right, n-1]
            int pos = 0; // range for 1 [left, pos)
            while (pos <= right) {
                int num = nums[pos];
                if (num == 0) {
                    swap(nums, left, pos);
                    left++;
                    pos++;
                } else if (num == 2) {
                    swap(nums, pos, right);
                    right--;
                } else {
                    pos++;
                }
            }
        }

        private void swap(int[] nums, int l, int r) {
            int temp = nums[l];
            nums[l] = nums[r];
            nums[r] = temp;
        }
    }
}
