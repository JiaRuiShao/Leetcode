import java.util.Arrays;

/**
 * 179. Largest Number
 */
public class _179 {
    // Time: O(nlognk)
    // Space: O(nk)
    class Solution1_Sort {
        public String largestNumber(int[] nums) {
            int n = nums.length;
            String[] strs = new String[n];
            for (int i = 0; i < n; i++) {
                strs[i] = String.valueOf(nums[i]);
            }

            Arrays.sort(strs, (a, b) -> {
                String ab = a + b, ba = b + a;
                return ba.compareTo(ab);
            });

            if (strs[0].equals("0")) {
                return "0";
            }

            StringBuilder largestNum = new StringBuilder();
            for (String s : strs) {
                largestNum.append(s);
            }
            return largestNum.toString();
        }
    }
}
