import java.util.HashSet;
import java.util.Set;

/**
 * 136. Single Number
 */
public class _136 {
    class Solution1_Bit_XOR {
        public int singleNumber(int[] nums) {
            int single = 0;
            for (int num : nums) {
                single ^= num;
            }
            return single;
        }
    }

    class Solution2_HashSet {
        public int singleNumber(int[] nums) {
            Set<Integer> set = new HashSet<>();
            for (int i : nums) {
                if (!set.add(i)) {
                    set.remove(i);
                }
            }
            return set.iterator().next();
        }
    }
}
