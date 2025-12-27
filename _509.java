/**
 * 509. Fibonacci Number
 * 
 * - S1: BF recursively O(2^n), O(n)
 * - S2: BF recursively with memo O(n), O(n)
 * - S3: Iterative Bottom-Up with memo O(n), O(n)
 * - S4: Iterative Bottom-Up without memo O(n), O(1)
 * 
 * Followup:
 * - What if n is extremely large (n = 10^9, 10^18)? Use Matrix Exponentiation
 * Fibonacci recurrence can be expressed as matrix multiplication:
 * [F(n+1)]   [1  1]   [F(n)  ]
 * [F(n)  ] = [1  0] × [F(n-1)]
 * 
 * F(n+1) = 1×F(n) + 1×F(n-1)
 * F(n) = 1xF(n) + 0xF(n-1)
 * 
 * [F(n)  ]   [1  1]^(n-1)   [1]
 * [F(n-1)] = [1  0]       × [0]
 * 
 * Instead of computing the matrix product n times (O(n)), we use exponentiation by squaring:
 * to compute M^n, we don't multiply M by itself n times; we calculation M^(n/2), M^(n/4), ... until we reach M^1
 * 
 * This is why the time complexity is O(logn)
 */
public class _509 {
    /**
     * Time: O(2^n)
     * Space: O(n)
     */
    class Solution0_BF_Recursion {
        public int fib(int n) {
            if (n < 2) return n;
            return fib(n - 1) + fib(n - 2);
        }
    }

    /**
     * Time: O(n)
     * Space: O(n)
     */
    class Solution1_DP_Top_Down {
        public int fib(int n) {
            int[] fibNums = new int[n + 1]; // allocate n + 1 size because n here is like the index
            return findFibNum(fibNums, n);
        }
    
        private int findFibNum(int[] nums, int n) {
            if (n < 2) return n;
            if (nums[n] != 0) return nums[n]; // cached before
            nums[n] = findFibNum(nums, n - 1) + findFibNum(nums, n - 2);
            return nums[n];
        }
    }

    /**
     * Time: O(n)
     * Space: O(n)
     */
    class Solution3_Iterative_Bottom_Up {
        public int fib(int n) {
            if (n < 2) return n;
            int[] fibNums = new int[n + 1];
            return findFibNum(fibNums, n);
        }
    
        private int findFibNum(int[] nums, int n) {
            nums[1] = 1;
            for (int i = 2; i <= n; i++) {
                nums[i] = nums[i - 1] + nums[i - 2];
            }
            return nums[n];
        }
    }

    /**
     * Time: O(n)
     * Space: O(1)
     */
    class Solution3_Iterative_Bottom_Up_Improved {
        // f(i) + f(i+1) = f(i+2)
        public int fib(int n) {
            if (n < 2) return n;
            int fib = 0, prev1 = 1, prev2 = 0;
            for (int i = 2; i <= n; i++) { // ** i <= n **
                fib = prev1 + prev2;
                prev2 = prev1;
                prev1 = fib;
            }
            return fib;
        }
    }

    // Ref: https://leetcode.com/problems/fibonacci-number/solutions/362772/fibonacci-number-by-leetcode-rbcd/
    class Followup_MatrixMultiplication {
        public int fib(int n) {
            if (n <= 1) return n;
            long[][] result = matrixPower(new long[][] {{1, 1}, {1, 0}}, n);
            return (int) result[0][1];
        }

        private long[][] matrixPower(long[][] matrix, int n) {
            if (n == 1) return matrix;
            
            if (n % 2 == 0) {
                long[][] half = matrixPower(matrix, n / 2);
                return matrixMultiply(half, half);
            } else {
                long[][] half = matrixPower(matrix, n / 2);
                return matrixMultiply(matrixMultiply(half, half), matrix);
            }
        }

        // Multiply two 2x2 matrices
        private long[][] matrixMultiply(long[][] A, long[][] B) {
            long[][] C = new long[2][2];
            C[0][0] = A[0][0] * B[0][0] + A[0][1] * B[1][0];
            C[0][1] = A[0][0] * B[0][1] + A[0][1] * B[1][1];
            C[1][0] = A[1][0] * B[0][0] + A[1][1] * B[1][0];
            C[1][1] = A[1][0] * B[0][1] + A[1][1] * B[1][1];
            return C;
        }
    }
}
