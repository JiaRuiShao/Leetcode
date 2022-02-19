import java.util.*;

public class _239 {
    public static class Solution1 {

        /**
         * Time: O(N)
         * Space: O(K)
         *
         * @param nums the given int arr
         * @param k window size
         * @return the max sliding window
         */
        public int[] maxSlidingWindow(int[] nums, int k) {
            int n = nums.length, leftWindow;
            if (n == 0) {
                return nums;
            }
            int[] res = new int[n - k + 1];
            Deque<Integer> dq = new ArrayDeque<>();// monotonic decreasing queue
            for (int i = 0; i < n; i++) {
                leftWindow = i - k + 1;
                if (!dq.isEmpty() && dq.peekFirst() < leftWindow) {
                    dq.pollFirst(); // index out of window range
                }

                while (!dq.isEmpty() && nums[i] >= nums[dq.peekLast()]) {
                    dq.pollLast();
                }
                dq.offerLast(i); // add current element index into the queue

                if (leftWindow >= 0) {
                    res[leftWindow] = nums[dq.peekFirst()];
                }
            }
            return res;
        }
    }

    static class Solution2 {
        /* Monotonic Decreasing Queue */
        class MonotonicQueue {
            LinkedList<Integer> q = new LinkedList<>();
            public void push(int n) {
                // remove the left elements whose value are < current val
                while (!q.isEmpty() && q.getLast() < n) {
                    q.pollLast();
                }
                // add this val to the end
                q.addLast(n);
            }

            // O(1) time complexity, we are sure that the 1st element has the largest val
            public int max() {
                return q.getFirst();
            }

            public void pop(int n) {
                if (n == q.getFirst()) { // only pop the first element if it's the largest one
                    q.pollFirst();
                }
            }
        }

        int[] maxSlidingWindow(int[] nums, int k) {
            if (nums == null || nums.length == 0) return new int[0];
            MonotonicQueue window = new MonotonicQueue();
            int[] res = new int[nums.length - k + 1];

            for (int i = 0; i < nums.length; i++) {
                if (i < k - 1) {
                    // add the first (k - 1) elements
                    window.push(nums[i]);
                } else {
                    // move the window forward, add the new element
                    window.push(nums[i]);
                    // store the current max val
                    res[i - k + 1] = window.max();
                    // remove the old element (i - k + 1)
                    window.pop(nums[i - k + 1]);
                }
            }

            return res;
        }

    }

    public static class Solution3 {

        public int[] maxSlidingWindow(int[] nums, int k) {
            int n = nums.length, left;
            if (n == 0) {
                return nums;
            }
            int[] res = new int[n - k + 1];
            Deque<Integer> dq = new ArrayDeque<>();
            for (int i = 0; i < n; i++) {
                left = i - k + 1;

                while (!dq.isEmpty() && nums[i] > dq.peekLast()) {
                    dq.pollLast();
                }
                dq.offerLast(nums[i]);

                if (left >= 0) {
                    res[left] = dq.peekFirst();
                    if (!dq.isEmpty() && dq.peekFirst() == nums[left]) {
                        dq.pollFirst();
                    }
                }
            }
            return res;
        }
    }
}
