import java.util.Arrays;

/*
 * 204. Count Primes.
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
        int countPrimes(int n) {
            boolean[] isPrime = new boolean[n];
            Arrays.fill(isPrime, true);

            for (int i = 2; i < n; i++) {
                if (isPrime[i]) {
                    for (int j = 2 * i; j < n; j += i) {
                        isPrime[j] = false;
                    }
                }
            }

            int count = 0;
            for (int i = 2; i < n; i++) {
                if (isPrime[i]) {
                    count++;
                }
            }

            return count;
        }

    }

    class Solution4_Sieve_of_Eratosthenes_Improved {
        /**
         * Improved the search range of prime counter i and non prime counter j.
         * - Time: O(N * loglogN)
         * - Space: O(n)
         * @param n n
         * @return # of prime numbers within given n
         */
        public int countPrimes(int n) {
            boolean[] isPrime = new boolean[n];
            Arrays.fill(isPrime, true);
            for (int i = 2; i * i <= n; i++) {
                if (isPrime[i]) {
                    for (int j = i * i; j < n; j += i) {
                        isPrime[j] = false;
                    }
                }
            }

            int count = 0;
            for (int i = 2; i < n; i++) {
                if (isPrime[i]) {
                    count++;
                }
            }

            return count;
        }
    }

}
