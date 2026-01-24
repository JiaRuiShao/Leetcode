import java.util.HashSet;
import java.util.Set;

/**
 * 27. Remove Element
 * 
 * Followup:
 * - Remove all duplicates? LC 26
 * - Remove duplicates at most k times? LC 80
 */
public class _27 {
    class Solution1_Two_Pointers {
        public int removeElement(int[] nums, int val) {
            int left = 0;
            // [0, left): nums != val
            for (int right = 0; right < nums.length; right++) {
                if (nums[right] != val) {
                    nums[left] = nums[right];
                    left++;
                }
            }
            return left;
        }
    }

    // Remove all duplicates so each elem appears only once
    class Followup_RemoveAllDuplicates {
        public int removeDuplicates_array_sorted(int[] nums) {
            int k = 0; // unique elems in [0, k]
            for (int i = 1; i < nums.length; i++) {
                if (nums[i] != nums[i - 1]) {
                    nums[++k] = nums[i];
                }
            }
            return k + 1;
        }

        public int removeDuplicatesUnsorted(int[] nums) {
            Set<Integer> seen = new HashSet<>();
            int k = 0;
            
            for (int i = 0; i < nums.length; i++) {
                if (!seen.contains(nums[i])) {
                    seen.add(nums[i]);
                    nums[k++] = nums[i];
                }
            }
            
            return k;
        }
    }

    class Followup_AllowDuplicatesAtMostKTimes {
        public int removeDuplicates(int[] nums, int maxCount) {
            if (nums.length <= maxCount) return nums.length;
            
            int k = maxCount;  // valid elems in [0, k)
            
            for (int i = maxCount; i < nums.length; i++) {
                // Compare with element maxCount positions back
                // **IMPORTANT: use k-maxCount to compare instead of i-maxCount**
                if (nums[i] != nums[k - maxCount]) {
                    nums[k++] = nums[i];
                }
            }
            
            return k;
        }
    }
}
