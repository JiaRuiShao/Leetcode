import java.util.Arrays;

/**
 * 189. Rotate Array
    // s0: brute force: nested for loop
    // s1: allocate space for a duplicate array
    // s2: recursion, visited[] or Set<> visited
    // s3: use a larger mutiplier as the key to encode the original val and the rotated value; however here the number could be too large
    // s4: circular update
    // s5: reverse 3 times: reverse entire array, reverse first k, reverse remaining (n - k)
 */
public class _189 {
    class Solution1_Reverse {
        public void rotate(int[] nums, int k) {
            int n = nums.length;
            k = (k % n + n) % n; /** followup: if k < 0 */
            reverse(nums, 0, n - 1);
            reverse(nums, 0, k - 1);
            reverse(nums, k, n - 1);
        }
    
        // reverse nums array in range [l, r]
        private void reverse(int[] nums, int l, int r) {
            while (l < r) {
                int temp = nums[l];
                nums[l] = nums[r];
                nums[r] = temp;
                l++;
                r--;
            }
        }
    }
    
    class Solution2_Circular_Update {
        // s0: brute force: nested for loop
        // s1: allocate space for a duplicate array
        // s2: recursion, visited[] or Set<> visited
        // s3: this solution
        public void rotate(int[] nums, int k) {
            k = k % nums.length;
            int n = nums.length, rotated = 0;
    
            for (int start = 0; start < n && rotated < n; start++) {
                int curr = start, prev = nums[curr];
                do {
                    int next = (curr + k) % n, temp = nums[next];
                    nums[next] = prev;
                    prev = temp;
                    curr = next;
                    rotated++;
                } while (curr != start);
            }
        }

    }

    public static class Solution2_Extra_Space {
        public void rotate(int[] nums, int k) {
            int len = nums.length;
            int[] tmp = new int[len];
            for (int i = 0; i < len; i++) {
                tmp[(i + k) % len] = nums[i];
            }
            System.arraycopy(tmp, 0, nums, 0, len);
        }
    }

    public static class Solution3_Brute_Force {
        public void rotate(int[] nums, int k) {
            int tmp;
            for (int i = 0; i < k; i++) {
                tmp = nums[nums.length - 1];
                for (int j = nums.length - 1; j > 0; j--) {
                    nums[j] = nums[j - 1];
                }
                nums[0] = tmp;
            }
        }
    }

    public static void main(String[] args) {
        Solution1_Reverse solution = new _189().new Solution1_Reverse();
        int[] nums = {1,2,3,4,5,6,7};
        solution.rotate(nums, -1);
        System.out.println(Arrays.toString(nums)); // [2, 3, 4, 5, 6, 7, 1]

        nums = new int[]{1,2,3,4,5,6,7};
        solution.rotate(nums, -8);
        System.out.println(Arrays.toString(nums)); // [2, 3, 4, 5, 6, 7, 1]

        nums = new int[]{1,2,3,4,5,6,7};
        solution.rotate(nums, 6);
        System.out.println(Arrays.toString(nums)); // [2, 3, 4, 5, 6, 7, 1]

        nums = new int[]{1,2,3,4,5,6,7};
        solution.rotate(nums, -2);
        System.out.println(Arrays.toString(nums)); // [3, 4, 5, 6, 7, 1, 2]
    }
}
