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

    /**
     * - a > 0, two ends are larger than the center
     * - a < 0, two ends are smaller than the center
     * - a = 0, this quadratic function becomes a linear function, which is mono increase/decrease, so b's sign is not important
     */
    class Solution2_Two_Pointers_Improved {
        public int[] sortTransformedArray(int[] nums, int a, int b, int c) {
            int n = nums.length, left = 0, right = n - 1;
            int[] sorted = new int[n];
            int idx = a > 0 ? n - 1 : 0;
    
            while (left <= right) {
                int leftQuad = quadratic(a, b, c, nums[left]);
                int rightQuad = quadratic(a, b, c, nums[right]);
                if (a > 0) {
                    if (leftQuad > rightQuad) {
                        sorted[idx--] = leftQuad;
                        left++;
                    } else {
                        sorted[idx--] = rightQuad;
                        right--;
                    }
                } else {
                    if (leftQuad < rightQuad) {
                        sorted[idx++] = leftQuad;
                        left++;
                    } else {
                        sorted[idx++] = rightQuad;
                        right--;
                    }
                }
            }
            return sorted;
        }
    
        private int quadratic(int a, int b, int c, int x) {
            return a * x * x + b * x + c;
        }
    }

    class Solution3_Two_Pointers_Improved {
        public int[] sortTransformedArray(int[] nums, int a, int b, int c) {
            // parabola: a >= 0, desc idx from n - 1; a < 0, asc idx from 0
            int n = nums.length;
            int[] sorted = new int[n];
            int left = 0, right = n - 1;
            if (a >= 0) {
                int idx = right;
                while (idx >= 0) {
                    int quadLeft = quadFunc(a, b, c, nums[left]);
                    int quadRight = quadFunc(a, b, c, nums[right]);
                    if (quadLeft >= quadRight) {
                        left++;
                        sorted[idx--] = quadLeft;
                    } else {
                        right--;
                        sorted[idx--] = quadRight;
                    }
                }
            } else {
                int idx = left;
                while (idx < n) {
                    int quadLeft = quadFunc(a, b, c, nums[left]);
                    int quadRight = quadFunc(a, b, c, nums[right]);
                    if (quadLeft <= quadRight) {
                        left++;
                        sorted[idx++] = quadLeft;
                    } else {
                        right--;
                        sorted[idx++] = quadRight;
                    }
                }
            }
            return sorted;
        }
    
        private int quadFunc(int a, int b, int c, int x) {
            return a * x * x + b * x + c;
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
