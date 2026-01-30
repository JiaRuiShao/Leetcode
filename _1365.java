import java.util.Map;
import java.util.TreeMap;

/**
 * 1365. How Many Numbers Are Smaller Than the Current Number
 */
public class _1365 {
    // Time: O(nlogn)
    // Space: O(n)
    class Solution1_TreeMap {
        public int[] smallerNumbersThanCurrent(int[] nums) {
            TreeMap<Integer, Integer> numFreq = new TreeMap<>();
            for (int num : nums) {
                numFreq.put(num, numFreq.getOrDefault(num, 0) + 1);
            }

            int total = 0; // count of nums < curr num
            for (Map.Entry<Integer, Integer> entry : numFreq.entrySet()) {
                int count = entry.getValue();
                entry.setValue(total);
                total += count;
            }

            int n = nums.length;
            int[] smaller = new int[n];
            for (int i = 0; i < n; i++) {
                smaller[i] = numFreq.get(nums[i]);
            }
            return smaller;
        }
    }

    // We can use count sort since numbers in a small range
    // Time: O(n)
    // Space: O(n)
    class Solution2_CountSort {
        public int[] smallerNumbersThanCurrent(int[] nums) {
            int[] freq = new int[101];
            for (int num : nums) {
                freq[num]++;
            }

            int total = 0;
            for (int num = 0; num <= 100; num++) {
                int temp = freq[num];
                freq[num] = total;
                total += temp;
            }

            int[] smaller = new int[nums.length];
            for (int i = 0; i < nums.length; i++) {
                smaller[i] = freq[nums[i]];
            }
            return smaller;
        }
    }
}
