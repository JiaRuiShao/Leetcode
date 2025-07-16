import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 217. Contains Duplicate
 */
public class _217 {
    class Solution1_Set {
        // Time: O(n)
        // Space: O(n)
        public boolean containsDuplicate(int[] nums) {
            Set<Integer> set = new HashSet<>();
            for (int num : nums) {
                if (set.contains(num)) return true;
                set.add(num);
            }
            return false;
        }
    }

    class Solution2_Dual_Pivot_Quicksort {
        // Time: O(n log n)
        // Space: O(logn)
        public boolean containsDuplicate(int[] nums) {
            Arrays.sort(nums);
            for (int i = 1; i < nums.length; i++) {
                if (nums[i] == nums[i - 1]) return true;
            }
            return false;
        }
    }
}
