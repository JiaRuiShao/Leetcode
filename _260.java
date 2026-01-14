import java.util.HashMap;
import java.util.Map;

/**
 * 260. Single Number III
 */
public class _260 {
    class Solution1_Bit_Manipulation {
        public int[] singleNumber(int[] nums) {
            // get xor of a & b
            int xor = 0;
            for (int num : nums) {
                xor ^= num;
            }
    
            // find the bit set that differentiate a from b
            // int mask = 1;
            // while ((mask & xor) != mask) {
            //     mask <<= 1;
            // }
            int mask = (xor & -xor);
    
            // xor all numbers w/ & w/o this bit set
            // all other numbers will be cancelled out
            int[] res = new int[2];
            for (int num : nums) {
                if ((num & mask) == mask) {
                    res[0] ^= num;
                } else {
                    res[1] ^= num;
                }
            }
            return res;
        }
    }

    // this violates constant extra space requirement
    public static class Solution0_HashMap {
        public int[] singleNumber(int[] nums) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int i : nums) {
                map.put(i, map.getOrDefault(i, 0) + 1);
            }

            int[] res = new int[2];
            int index = 0;
            for (int key : map.keySet()) {
                if (map.get(key) == 1) {
                    res[index++] = key;
                }
                if (index == 2) {
                    break;
                }
            }
            return res;
        }
    }
}
