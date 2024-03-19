import java.util.Arrays;

/**
 * 360. Sort Transformed Array.
 */
public class _360 {

    class Solution1_Two_Pointers {
        public int[] sortTransformedArray(int[] nums, int a, int b, int c) {
            int len = nums.length;
            int[] sorted = new int[len];
            if (a == 0 && b >= 0 || a < 0) {
                sortTransformedArrayDownsideParabola(nums, sorted, a, b, c);
            } else {
                sortTransformedArrayUpsideParabola(nums, sorted, a, b, c);
            }
            return sorted;
        }
    
        private void sortTransformedArrayUpsideParabola(int[] nums, int[] sorted, int a, int b, int c) {
            int len = nums.length;
            int left = 0, right = len - 1, sortIdx = len - 1;
            double symmetricAxis = a == 0 ? 101 : -b / (double)(2 * a);
            while (left <= right) {
                if (Math.abs(nums[left] - symmetricAxis) >= Math.abs(nums[right] - symmetricAxis)) {
                    sorted[sortIdx--] = calculateQuadraticFunction(nums[left++], a, b, c);
                } else {
                    sorted[sortIdx--] = calculateQuadraticFunction(nums[right--], a, b, c);
                }
            }
        }
    
        private void sortTransformedArrayDownsideParabola(int[] nums, int[] sorted, int a, int b, int c) {
            int len = nums.length;
            int left = 0, right = len - 1, sortIdx = 0;
            double symmetricAxis = a == 0 ? 101 : -b / (double)(2 * a);
            while (left <= right) {
                if (Math.abs(nums[left] - symmetricAxis) >= Math.abs(nums[right] - symmetricAxis)) {
                    sorted[sortIdx++] = calculateQuadraticFunction(nums[left++], a, b, c);
                } else {
                    sorted[sortIdx++] = calculateQuadraticFunction(nums[right--], a, b, c);
                }
            }
        }
    
        private int calculateQuadraticFunction(int x, int a, int b, int c) {
            return a * (int) Math.pow(x, 2) + b * x + c;
        }
    }

    public static void main(String[] args) {
        // int[] nums = new int[]{-4,-2,2,4};
        // int a = 1, b = 3, c = 5;
        // int[] res = new _360().new Solution().sortTransformedArray(nums, a, b, c);
        // System.out.println(Arrays.toString(res));
        
        int[] nums = new int[]{-4,-2,2,4};
        int a = 0, b = 3, c = 5;
        int[] res = new _360().new Solution1_Two_Pointers().sortTransformedArray(nums, a, b, c);
        System.out.println(Arrays.toString(res));
    }
}
