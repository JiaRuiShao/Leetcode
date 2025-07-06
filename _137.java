import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 137. Single Number II
 * Solution 0: Brute Force T: O(n^2); S: O(1)
 * Solution 1: HashMap T: O(n); S: O(n)
 * Solution 2: Sorting + Traversal: T: O(nlogn); S: O(logn)
 * Solution 3: Counting Set Bits: T: O(n); S: O(1)
 * Solution 4: Bit Manipulation: T: O(n); S: O(1)
 */
public class _137 {
    public static class Solution1_HashMap {
        public int singleNumber(int[] nums) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int i : nums) {
                map.put(i, map.getOrDefault(i, 0) + 1);
            }
            for (int key : map.keySet()) {
                if (map.get(key) != 3) {
                    return key;
                }
            }
            return 0;
        }
    }

    // Sorting + Linear Traversal
    class Solution2_Sorting {
        public int singleNumber(int[] nums) {
            int n = nums.length;
            if (n == 1) return nums[0];
            Arrays.sort(nums);
            if (nums[n - 1] != nums[n - 2]) return nums[n - 1];
            for (int i = 1; i < n; i += 3) {
                if (nums[i] != nums[i - 1]) return nums[i - 1];
            }
            return -1;
        }
    }

    class Solution3_Set_Bits_Counting {
        public int singleNumber(int[] nums) {
            int mask = 1, single = 0;
            for (int i = 0; i < 32; i++) {
                int bits = 0;
                for (int num : nums) {
                    if ((num & mask) == mask) bits++;
                }
                if (bits % 3 != 0) {
                    single |= mask;
                }
                mask <<= 1;
            }
            return single;
        }
    }
    
    // Not intuitive at all
    class Solution4_Bit_Manipulation {
        public int singleNumber(int[] nums) {
            int ones = 0, twos = 0;
            for (int num : nums) {
                // ones ^ n adds n into ones if it's not there, or removes it if it is (toggle)
                // & ~twos exclude num from ones if this num is in twos
                ones = (ones ^ num) & ~twos;
                // twos ^ n toggles the bit into twos
                // & ~ones exclude num from twos if this num is in ones
                twos = (twos ^ num) & ~ones;
            }
            return ones;
        }
    }    
}
