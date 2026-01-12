import java.util.ArrayList;
import java.util.List;

/**
 * 967. Numbers With Same Consecutive Differences
 */
public class _967 {
    // Time: O(9*n*2^n) = O(n2^n)
    // Space: O(n)
    class Solution1_Backtrack_Permutation_ElementsUsedMoreThanOnce {
        public int[] numsSameConsecDiff(int n, int k) {
            List<Integer> res = new ArrayList<>();
            backtrack(n, k, 0, res);
            return res.stream().mapToInt(e -> e).toArray();
        }

        private void backtrack(int len, int digitDiff, int num, List<Integer> res) {
            int digits = getDigits(num);
            if (digits > len) return;
            if (digits == len) {
                res.add(num);
                return;
            }
            int lastDigit = num % 10;
            for (int digit = 0; digit <= 9; digit++) {
                if (num == 0 && digit == 0) continue;
                if (num > 0 && Math.abs(lastDigit - digit) != digitDiff) continue;
                // num = 10 * num + digit
                backtrack(len, digitDiff, 10 * num + digit, res);
                // num = num / 10;
            }
        }

        private int getDigits(int num) {
            if (num == 0) return 0;
            int digits = 0;
            while (num != 0) {
                num /= 10;
                digits++;
            }
            return digits;
        }
    }

    // Time: O(9*2^n) -- here it's 2^n not 9^n because for each level after 0, there's <= two next digits (last - k or last + k)
    // Space: O(n)
    class Solution1_Backtrack_Permutation_ElementsUsedMoreThanOnce_Improved {
        public int[] numsSameConsecDiff(int n, int k) {
            List<Integer> res = new ArrayList<>();
            for (int i = 1; i <= 9; i++) {
                backtrack(n, k, 1, i, res);
            }
            return res.stream().mapToInt(Integer::intValue).toArray();
        }

        private void backtrack(int n, int k, int len, int num, List<Integer> res) {
            if (len == n) {
                res.add(num);
                return;
            }

            int last = num % 10;

            if (last + k <= 9) {
                backtrack(n, k, len + 1, num * 10 + last + k, res);
            }
            if (k != 0 && last - k >= 0) {
                backtrack(n, k, len + 1, num * 10 + last - k, res);
            }
        }
    }

    class Solution2_DP {
        public int[] numsSameConsecDiff(int n, int k) {
            List<Integer> curr = new ArrayList<>();

            // base: 1-digit numbers
            for (int i = 1; i <= 9; i++) {
                curr.add(i);
            }

            // build numbers of length 2..n
            for (int len = 2; len <= n; len++) {
                List<Integer> next = new ArrayList<>();
                for (int num : curr) {
                    int last = num % 10;

                    if (last + k <= 9) {
                        next.add(num * 10 + last + k);
                    }
                    if (k != 0 && last - k >= 0) {
                        next.add(num * 10 + last - k);
                    }
                }
                curr = next;
            }

            return curr.stream().mapToInt(Integer::intValue).toArray();
        }
    }
}
