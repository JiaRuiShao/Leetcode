import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * 496. Next Greater Element I
 */
public class _496 {
    class Solution1_BruteForce {
        public int[] nextGreaterElement(int[] nums1, int[] nums2) {
            int[] res = new int[nums1.length];
            for (int i = 0; i < nums1.length; i++) {
                int num = nums1[i];
                int pos = 0;
                for (int j = 0; j < nums2.length; j++) {
                    if (nums2[j] == num) {
                        pos = j;
                        break;
                    }
                }
                int ngl = -1;
                for (int j = pos + 1; j < nums2.length; j++) {
                    if (nums2[j] > num) {
                        ngl = nums2[j];
                        break;
                    }
                }
                res[i] = ngl;
            }
            return res;
        }
    }

    // Use monotonic stack to get the next greater elements for each number in O(n) time complexity
    class Solution2_MonoStack {
        public int[] nextGreaterElement(int[] nums1, int[] nums2) {
            Map<Integer, Integer> valToNGL = findNGL(nums2);
            int[] ngl = new int[nums1.length];
            for (int i = 0; i < nums1.length; i++) {
                ngl[i] = valToNGL.get(nums1[i]);
            }
            return ngl;
        }

        private Map<Integer, Integer> findNGL(int[] nums) {
            Map<Integer, Integer> map = new HashMap<>();
            Deque<Integer> maxStk = new ArrayDeque<>();
            for (int i = nums.length - 1; i >= 0; i--) {
                int num = nums[i];
                while (!maxStk.isEmpty() && maxStk.peek() <= num) {
                    maxStk.pop();
                }
                Integer ngl = maxStk.peek();
                map.put(num, ngl == null ? -1 : ngl);
                maxStk.push(num);
            }
            return map;
        }
    }
}
