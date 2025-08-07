import java.util.ArrayList;
import java.util.List;

/**
 * 89. Gray Code
 */
public class _89 {
    // Time: O(n*2^n)
    // Space: O(2^n)
    class Solution1_Backtrack_Permutation_ElemUsedOnce_NoDedup {
        // 2^16 < 2^31 - 1 ==> int type
        // permutation -- elem used once -- dedup doesn't apply
        public List<Integer> grayCode(int n) {
            int size = 1 << n; // same as (int)Math.pow(2, n) for small n (n < 31)
            boolean[] visited = new boolean[size];
            List<Integer> selected = new ArrayList<>();
            backtrack(n, visited, selected, 0);
            return codes;
        }

        private List<Integer> codes;

        private void backtrack(int n, boolean[] visited, List<Integer> selected, int curr) {
            if (codes != null) return;
            if (selected.size() == visited.length) {
                if (bitDiffByOne(selected.get(0), selected.get(selected.size() - 1))) {
                    codes = new ArrayList<>(selected);
                }
                return;
            }
            if (visited[curr]) return;
            selected.add(curr);
            visited[curr] = true;
            for (int i = 0, setBit = 1; i < n; i++, setBit <<= 1) {
                int next = flipBit(curr, setBit);
                backtrack(n, visited, selected, next);
            }
            selected.remove(selected.size() - 1);
            visited[curr] = false;
        }

        private int flipBit(int num, int setBit) {
            return num ^ setBit;
        }

        private boolean bitDiffByOne(int n1, int n2) {
            return Integer.bitCount(n1 ^ n2) == 1;
        }
    }

    // Time: O(n*2^n)
    // Space: O(2^n)
    class Solution2_Backtrack_Permutation_ElemUsedOnce_NoDedup {
        // 2^16 < 2^31 - 1 ==> int type
        // permutation -- elem used once -- dedup doesn't apply
        public List<Integer> grayCode(int n) {
            int size = 1 << n; // same as (int)Math.pow(2, n) for small n (n < 31)
            boolean[] visited = new boolean[size];
            List<Integer> selected = new ArrayList<>();
            backtrack(n, visited, selected, 0);
            return codes;
        }

        private List<Integer> codes;

        private void backtrack(int n, boolean[] visited, List<Integer> selected, int curr) {
            if (codes != null) return;
            if (selected.size() == visited.length) {
                if (bitDiffByOne(n, selected.get(0), selected.get(selected.size() - 1))) codes = new ArrayList<>(selected);
                return;
            }
            if (visited[curr]) return;
            selected.add(curr);
            visited[curr] = true;
            for (int i = 0; i < n; i++) {
                int next = flipBit(curr, i);
                backtrack(n, visited, selected, next);
            }
            selected.remove(selected.size() - 1);
            visited[curr] = false;
        }

        private int flipBit(int num, int i) {
            return num ^ (1 << i); // 1 << i == 2^i
        }

        private boolean bitDiffByOne(int n, int a, int b) {
            int bitDiff = 0, mask = 1, axorb = a ^ b;
            for (int i = 0; i < n; i++) {
                if ((axorb & mask) == mask) {
                    bitDiff++;
                }
                mask <<= 1;
            }
            return bitDiff == 1;
        }
    }

    // get the prepend bit mask for each bit position
    // reflect(iterate backwards) the existing codes and prefix with the pre-calculated bit mask
    // example: n = 4
    // idx    gray#    binary
    // 0       0          0000
    // ------ add 0001 ------
    // 1       1          0001
    // ------ add 0010 ------
    // 2       3          0011
    // 3       2          0010
    // ------ add 0100 ------
    // 4       6          0110
    // 5       7          0111
    // 6       5          0101
    // 7       4          0100
    // ------ add 1000 ------
    // 8      12          1100
    // 9      13          1101
    // 10     15          1111
    // 11     14          1110
    // 12     10          1010
    // 13     11          1011
    // 14     9           1001
    // 15     8           1000
    class Solution3_Iterative_Reflect_Prefix {
        public List<Integer> grayCode(int n) {
            List<Integer> codes = new ArrayList<>();
            codes.add(0);
            for (int i = 0, mask = 1; i < n; i++, mask <<= 1) {
                int sz = codes.size();
                for (int j = sz - 1; j >= 0; j--) {
                    codes.add(codes.get(j) | mask);
                }
            }
            return codes;
        }
    }

    // output all valid gray code
    // Time: O((2^n)!)
    // Space: O(2^n)
    class FollowUp1_Backtrack_Permutation_ElemUsedOnce_NoDedup {
        public List<List<Integer>> grayCode(int n) {
            int size = 1 << n; // same as (int)Math.pow(2, n) for small n (n < 31)
            boolean[] visited = new boolean[size];
            List<List<Integer>> codes = new ArrayList<>();
            List<Integer> selected = new ArrayList<>();
            backtrack(n, visited, codes, selected, 0);
            return codes;
        }

        private void backtrack(int n, boolean[] visited, List<List<Integer>> codes, List<Integer> selected, int curr) {
            if (selected.size() == visited.length) {
                if (bitDiffByOne(n, selected.get(0), selected.get(selected.size() - 1))) {
                    codes.add(new ArrayList<>(selected));
                }
                return;
            }
            if (visited[curr]) return;
            selected.add(curr);
            visited[curr] = true;
            for (int i = 0; i < n; i++) {
                int next = flipBit(curr, i);
                backtrack(n, visited, codes, selected, next);
            }
            selected.remove(selected.size() - 1);
            visited[curr] = false;
        }

        private int flipBit(int num, int i) {
            return num ^ (1 << i); // 1 << i == 2^i
        }

        private boolean bitDiffByOne(int n, int a, int b) {
            int bitDiff = 0, mask = 1, axorb = a ^ b;
            for (int i = 0; i < n; i++) {
                if ((axorb & mask) == mask) {
                    bitDiff++;
                }
                mask <<= 1;
            }
            return bitDiff == 1;
        }
    }
}
