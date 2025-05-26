/*
 * 204. Count Primes
 */
public class _204 {

    class Solution1_TLE {
        int countPrimes(int n) {
            int count = 0;
            for (int i = 2; i < n; i++) {
                if (isPrime(i)) {
                    count++;
                }
            }
            return count;
        }

        boolean isPrime(int n) {
            for (int i = 2; i < n; i++) {
                if (n % i == 0) {
                    // able to be divisible by other numbers
                    return false;
                }
            }
            return true;
        }
    }

    class Solution2_TLE_Improved {
        int countPrimes(int n) {
            int count = 0;
            for (int i = 2; i < n; i++) {
                if (isPrime(i)) {
                    count++;
                }
            }
            return count;
        }

        boolean isPrime(int n) {
            for (int i = 2; i * i <= n; i++) {
                if (n % i == 0) {
                    return false;
                }
            }
            return true;
        }
    }

    class Solution3_Sieve_of_Eratosthenes {
        public int countPrimes(int n) {
            int count = 0;
            boolean[] visited = new boolean[n];
            for (int i = 2; i < n; i++) {
                if (visited[i]) continue; // not a prime if visited
                count++;
                for (int j = i * 2; j < n; j += i) {
                    visited[j] = true;
                }
            }
            return count;
        }
    }

    class Solution4_Sieve_of_Eratosthenes_Improved {
        /**
         * Improved the search range of prime counter i and non-prime counter j.
         * - Time: O(N * loglogN)
         * - Space: O(N)
         * @param n N
         * @return # of prime numbers within given n
         */
        public int countPrimes(int n) {
            boolean[] visited = new boolean[n];
            for (int i = 2; i * i <= n; i++) { // sqrt(n) = O(n)
                if (visited[i]) continue;
                for (int j = i * i; j < n; j += i) { // O(loglogn)
                    visited[j] = true;
                }
            }

            int count = 0;
            for (int i = 2; i < n; i++) { // start counting from num 2
                if (!visited[i]) {
                    count++;
                }
            }

            return count;
        }
    }

}
