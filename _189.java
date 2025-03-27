/**
 * 189. Rotate Array
 */
public class _189 {
    class Solution1_Optimal {
        // s0: brute force: nested for loop
        // s1: allocate space for a duplicate array
        // s2: recursion, visited[] or Set<> visited
        // s3: this solution
        public void rotate(int[] nums, int k) {
            int rotate = 0;
            int len = nums.length;
            k = k % len;

            for (int idx = 0; rotate < len; idx++) {
                int num = nums[idx];
                int nextIdx = idx;
                while (true) {
                    nextIdx = (nextIdx + k) % nums.length;
                    num = rotate(nums, k, nextIdx, num);
                    rotate++;
                    if (nextIdx == idx || rotate == len) break; // move to another index when this cycle complete
                }
            }
        }

        private int rotate(int[] nums, int k, int nextIdx, int num) {
            int nextNum = nums[nextIdx];
            nums[nextIdx] = num;
            return nextNum;
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
}
