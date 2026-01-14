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
        // Hash Table
        // Time: O(2n)
        // Space: O(n)
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
    // Time: O(nlogn) < O(18n)
    // Space: O(logn)
    class Solution2_Sorting {
        public int singleNumber(int[] nums) {
            int n = nums.length;
            if (n == 1) return nums[0];
            Arrays.sort(nums);
            if (nums[n - 1] != nums[n - 2]) return nums[n - 1]; // corner case where last elem is the single num
            for (int i = 1; i < n; i += 3) {
                if (nums[i] != nums[i - 1]) return nums[i - 1];
            }
            return -1;
        }
    }

    // Count set bits and take the bit that cannot be divisible by 3
    // Time: O(32n)
    // Space: O(1)
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
    
    // - State 0: not in ones or twos
    // - State 1: in ones, not in twos
    // - State 2: not in ones, in twos
    // - State 3 → State 0: in both (threes mask clears both)
    class Solution4_Bit_Manipulation {
        public int singleNumber(int[] nums) {
            int ones = 0;  // Bits that appeared 1 time (mod 3)
            int twos = 0;  // Bits that appeared 2 times (mod 3)
            
            for (int num : nums) {
                // Update twos: add bits that are in both num and ones
                twos |= ones & num;
                
                // Update ones: XOR with num
                ones ^= num;
                
                // Remove bits that appear 3 times (in both ones and twos)
                int threes = ones & twos;
                ones &= ~threes;
                twos &= ~threes;
            }
            
            return ones;
        }

        public int singleNumber2(int[] nums) {
            int ones = 0, twos = 0;
            
            for (int num : nums) {
                // First update twos, then ones
                twos = twos | (ones & num);
                ones = ones ^ num;
                
                // Common bits in ones and twos represent 3 appearances
                int common = ones & twos;
                ones = ones & ~common;
                twos = twos & ~common;
            }
            
            return ones;
        }
    }    
}
