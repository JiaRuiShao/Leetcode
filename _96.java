public class _96 {
    static class Solution {
        public int numTrees(int n) {
            // calculate the num of BSTs formed by numbers in [1, n]
            return count(1, n);
        }

        /**
         * Time: O(N^2)
         * Space: O(N^2)
         *
         * @param lo low interval val
         * @param hi high interval val
         * @return the number of valid BST
         */
        int count(int lo, int hi) {
            // base case
            if (lo > hi) return 1; // null node

            int res = 0;
            for (int i = lo; i <= hi; i++) {
                // node with val i as root
                int left = count(lo, i - 1);
                int right = count(i + 1, hi);
                // the BST num is the left num count * right num count
                res += left * right;
            }

            return res;
        }
    }

    static class SolutionDP {
        // memo
        int[][] memo;

        int numTrees(int n) {
            // initialize the memo
            memo = new int[n + 1][n + 1];
            return count(1, n);
        }

        int count(int lo, int hi) {
            if (lo > hi) return 1;
            // check memo cache
            if (memo[lo][hi] != 0) {
                return memo[lo][hi];
            }

            int res = 0;
            for (int mid = lo; mid <= hi; mid++) {
                int left = count(lo, mid - 1);
                int right = count(mid + 1, hi);
                res += left * right;
            }
            // save the result in the memo
            memo[lo][hi] = res;

            return res;
        }
    }
}
