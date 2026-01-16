import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 42. Trapping Rain Water
 * For bar i, water it can trap: max(0, min(max(height[0...i-1], height[i...n-1])) - height[i])
 * 
 * - S0 BF: O(n^2), O(1)
 * - S1 Pre-calculated maxLeft[] and maxRight[]: O(n), O(n)
 * - S2 Greedy Two Pointers: O(n), O(1)
 * - S3 Mono Stack O(n), O(n)
 */
public class _42 {
    // Solution1: brute force improved, Time: O(n), Space: O(n)
    // use pre-calculated maxLeft[i] & maxRight[i] to eliminate the inner for loop in brute force approach
    // here we could also use mono stack to find next taller bar
    class Solution1_Prefix_Suffix {
        public int trap(int[] height) {
            int n = height.length;
            int[] leftMaxHeight = new int[n]; // max(height[0...i-1]
            int[] rightMaxHeight = new int[n]; // max(height[i+1...n-1])
            for (int i = 1; i < n; i++) {
                leftMaxHeight[i] = Math.max(leftMaxHeight[i - 1], height[i - 1]);
            }
            for (int i = n - 2; i >= 0; i--) {
                rightMaxHeight[i] = Math.max(rightMaxHeight[i + 1], height[i + 1]);
            }
            int total = 0;
            for (int i = 1; i < n - 1; i++) {
                int trapWater = Math.min(leftMaxHeight[i], rightMaxHeight[i]) - height[i];
                if (trapWater > 0) {
                    total += trapWater;
                }
            }
            return total;
        }
    }

    // Solution2: greedy two pointers to tracks the maximum heights from both ends, Time: O(n), Space: O(1)
    // calculate trapped water by moving the side with the smaller maximum height inward
    class Solution2_Two_Pointers {
        // trap[i] = min(max(height[0..i-1]), max(height[i+1...n-1])) - height[i]
        // maxLeft = maxHeight from left -- height[0..i-1]
        // rightMax = maxHeight from right -- height[i+1..n-1]
        public int trap1(int[] height) {
            int left = 0, right = height.length - 1;
            int maxLeft = 0, maxRight = 0, trapped = 0;
            while (left <= right) {
                if (maxLeft <= maxRight) {
                    trapped += Math.max(0, maxLeft - height[left]);
                    maxLeft = Math.max(maxLeft, height[left++]);
                } else {
                    trapped += Math.max(0, maxRight - height[right]);
                    maxRight = Math.max(maxRight, height[right--]);
                }
            }
            return trapped;
        }

        // here leftMaxHeight is maxHeight from left including height[i]
        public int trap2(int[] height) {
            int left = 0, right = height.length - 1, leftMaxHeight = 0, rightMaxHeight = 0, totalTrapped = 0;
            while (left <= right) {
                int leftHeight = height[left], rightHeight = height[right];
                leftMaxHeight = Math.max(leftHeight, leftMaxHeight);
                rightMaxHeight = Math.max(rightHeight, rightMaxHeight);
                if (leftMaxHeight < rightMaxHeight) {
                    totalTrapped += Math.max(0, leftMaxHeight - height[left++]);
                    // left++;
                } else {
                    totalTrapped += Math.max(0, rightMaxHeight - height[right--]);
                    // right--;
                }
            }
            return totalTrapped;
        }
    }

    class Solution3_MonoStack {
        // layer by layer filling -- no need for global max height from left & right
        // water[i] = (min(leftHigher, rightHigher) - height) * (rightHigherIdx - 1 - leftHigherIdx)
        // minStack to find higher left & higher right when popped out
        public int trap(int[] height) {
            int trapped = 0;
            // indices of bar with heights in mono decreasing order
            // fill water when found a taller bar
            Deque<Integer> minStk = new ArrayDeque<>();
            for (int i = 0; i < height.length; i++) {
                while (!minStk.isEmpty() && height[i] >= height[minStk.peek()]) {
                    int idx = minStk.pop();
                    if (minStk.isEmpty()) {
                        break; // no higher bar from left exists
                    }
                    int leftHigher = height[minStk.peek()];
                    int rightHigher = height[i];
                    int width = i - 1 - (minStk.peek());
                    int h = Math.min(leftHigher, rightHigher) - height[idx];
                    trapped += h * width;
                }
                minStk.push(i);
            }
            return trapped;
        }
    }
}
