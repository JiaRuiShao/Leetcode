import java.util.HashSet;
import java.util.Set;

/**
 * 898. Bitwise ORs of Subarrays
 */
public class _898 {
    class Solution0_BF_TLE {
        public int subarrayBitwiseORs(int[] arr) {
            Set<Integer> result = new HashSet<>();
            int n = arr.length;
            
            // Try all subarrays [i, j]
            for (int i = 0; i < n; i++) {
                int or = 0;
                for (int j = i; j < n; j++) {
                    or |= arr[j];
                    result.add(or);
                }
            }
            
            return result.size();
        }
    }
}
