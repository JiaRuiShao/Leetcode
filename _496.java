import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class _496 {
    public static class Solution2 {
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
