import java.util.Arrays;

/**
 * 204. Count Primes
 * 
 * - S0 BF: O(n^2)/O(n × √n), O(1)
 * - S1 Sieve of Eratosthenes: O(nloglogn), O(n)
 * - S2 Segment Sieve: O(nloglogn), O(√n)
 * 
 * Clarification:
 * - Is n inclusive or exclusive? Strictly less than n
 * - What to return for n =  0 or 1? Return 0
 * - Is 1 considered as a prime number? No 1 is not prime
 */
public class _204 {

    class Solution0_BF_TLE {
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

    class Solution0_BF_Improved_TLE {
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

    class Solution1_Sieve_of_Eratosthenes {
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

    // Time: O(n log log n)
    // Space: O(n)
    class Solution1_Sieve_of_Eratosthenes_Improved {
        public int countPrimes(int n) {
            int primeCount = 0;
            boolean[] visited = new boolean[n];
            for (int i = 2; i < n; i++) {
                if (!visited[i]) {
                    primeCount++;
                    // visited[i] = true, not necessary here since we're not coming back to visit this num
                    for (long j = (long)i * i; j < n; j += i) { /*IMPORTANT: cast j to long type to avoid integer overflow */
                        visited[(int)j] = true;
                    }
                }
            }
            return primeCount;
        }
    }

    // Time: O(n log log n)
    // Space: O(n)
    class Solution2_Sieve_of_Eratosthenes_Standard {
        public int countPrimes(int n) {
            int primeCount = 0;
            boolean[] isPrime = new boolean[n];
            Arrays.fill(isPrime, true);
            int sqrtN = (int) Math.sqrt(n);
            // only iterate to √n
            for (int num = 2; num <= sqrtN; num++) { // need <= here because Java truncate down for double
                if (isPrime[num]) {
                    // mark all multiples of this prime num as non-prime
                    for (int product = num * num; product < n; product += num) {
                        isPrime[product] = false;
                    }
                }
            }

            for (int num = 2; num < n; num++) {
                primeCount += (isPrime[num] ? 1 : 0);
            }
            return primeCount;
        }
    }
}
