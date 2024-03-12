import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

/**
 * 496. Next Greater Element I.
 */
public class _496 {
    /**
     * Monotonic Stack & HashMap.
     * Time: O(n + m) = O(m) where n is the length of subset nums1 and m is the length of the array nums2
     * Space: O(2m) = O(m) where m is length of nums2
     */
    class Solution1 {
        private int[] findNGLMappings(int[] nums, Map<Integer, Integer> map, int[] ngl) {
            int[] res = new int[nums.length];
            for (int i = 0; i < nums.length; i++) {
                int idxInNums2 = map.get(nums[i]);
                res[i] = ngl[idxInNums2];
            }
            return res;
        }

        private int[] findNextGreaterElem(int[] nums, Map<Integer, Integer> map) {
            int n = nums.length;
            int[] ngl = new int[n];
            Deque<Integer> stack = new LinkedList<>();
            for (int i = n - 1; i >= 0; i--) {
                int num = nums[i];
                map.put(num, i);

                while (!stack.isEmpty() && stack.peek() <= num) {
                    stack.pop();
                }

                ngl[i] = stack.isEmpty() ? -1 : stack.peek();
                stack.push(num);
            }

            return ngl;
        }

        public int[] nextGreaterElement(int[] nums1, int[] nums2) {
            Map<Integer, Integer> valueIdxMap = new HashMap<>();
            int[] ngl = findNextGreaterElem(nums2, valueIdxMap);
            return findNGLMappings(nums1, valueIdxMap, ngl);
        }
    }

    class Solution2 {
        /**
         * Use monotonic stack to get the next greater elements for each number in O(n) time complexity.
         */
        public int[] nextGreaterElement(int[] nums1, int[] nums2) {
            Deque<Integer> stack = new LinkedList<>();
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = nums2.length - 1; i >= 0; i--) {
                while (!stack.isEmpty() && stack.peekLast() < nums2[i]) {
                    stack.pollLast(); // poll the elements that are less than the current elem who are on the right side
                }
                int nge = stack.isEmpty() ? -1 : stack.peekLast(); // next greater element for nums2[i]
                map.put(nums2[i], nge);
                stack.addLast(nums2[i]);
            }

            int n = nums1.length;
            for (int i = 0; i < n; i++) {
                nums1[i] = map.get(nums1[i]);
            }
            return nums1;
        }
    }
}
