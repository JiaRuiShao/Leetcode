/**
 * 1929. Concatenation of Array
 */
public class _1929 {
    class Solution {
        public int[] getConcatenation(int[] nums) {
            int n = nums.length;
            int[] concat = new int[2 * n];
            int pos = 0;
            while (pos < concat.length) {
                concat[pos] = nums[pos % n];
                pos++;
            }
            return concat;
        }
    }
}
