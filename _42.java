/**
 * 42. Trapping Rain Water
 * For bar i, water it can trap: max(0, min(max(height[0...i-1], height[i...n-1])) - height[i])
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
        public int trap(int[] height) {
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
}
