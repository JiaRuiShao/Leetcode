/**
 * 42. Trapping Rain Water
 */
public class _42 {
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

    // uses two pointers to tracks the maximum heights from both sides
    // calculate trapped water by moving the side with the smaller maximum height inward
    class Solution2_Two_Pointers {
        public int trap(int[] height) {
            int left = 0, right = height.length - 1;
            int maxLeft = 0, maxRight = 0, total = 0;
            while (left < right) {
                maxLeft = Math.max(maxLeft, height[left]);
                maxRight = Math.max(maxRight, height[right]);
                if (maxLeft < maxRight) {
                    total += maxLeft - height[left++];
                } else {
                    total += maxRight - height[right--];
                }
            }
            return total;
        }
    }
}
