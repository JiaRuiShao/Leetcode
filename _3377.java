import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 3377. Digit Operations to Make Two Integers Equal
 */
public class _3377 {
    // Time: O(n*2^n)
    // Let N be max number value ≈ 10^m.len <= 10_000
    //     V be num of valid states
    // Time: O(NloglogN + VlogV)
    // Space: O(N+V)
    class Solution1_Dijkstra {
        public int minOperations(int n, int m) {
            // build boolean[] isPrime for up to 10^maxLen(m, n)
            // then Dijkstra to find path from n to m
            // (we don't use BFS here because it's weighted graph and not unweighted)
            // n = sum(transformed num)
            int len = String.valueOf(n).length();
            int maxNum = Math.powExact(10, len);
            boolean[] isPrime = isPrime(maxNum);
            if (isPrime[n] || isPrime[m]) return -1;
            if (m == n) return m;

            Map<Integer, Integer> minCost = new HashMap<>();
            PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
            minHeap.offer(new int[]{n, n});
            minCost.put(n, n);
            while (!minHeap.isEmpty()) {
                int[] curr = minHeap.poll();
                int num = curr[0], cost = curr[1];
                if (num == m) return cost;
                if (minCost.containsKey(num) && cost > minCost.get(num)) continue;
                for (int next : getNextNum(num)) { // each num has at most 6 neighbors (2 x 3)
                    if (isPrime[next]) continue;
                    int nextCost = cost + next;
                    if (!minCost.containsKey(next) || nextCost < minCost.get(next)) {
                        minCost.put(next, nextCost);
                        minHeap.offer(new int[]{next, nextCost});
                    }
                }
            }
            return -1;
        }

        private List<Integer> getNextNum(int num) {
            List<Integer> numbers = new ArrayList<>();
            StringBuilder s = new StringBuilder(String.valueOf(num));
            for (int i = 0; i < s.length(); i++) {
                char digit = s.charAt(i);
                if (digit > '0') {
                    s.setCharAt(i, (char) (digit - 1));
                    if (s.charAt(0) != '0') { // skip leading zero
                        numbers.add(Integer.valueOf(s.toString()));
                    }
                }
                if (digit < '9') {
                    s.setCharAt(i, (char) (digit + 1));
                    numbers.add(Integer.valueOf(s.toString()));
                }
                s.setCharAt(i, digit);
            }
            return numbers;
        }

        boolean[] isPrime(int n) {
            boolean[] isPrime = new boolean[n];
            Arrays.fill(isPrime, true);
            isPrime[0] = isPrime[1] = false;
            for (int num = 2; num * num <= n; num++) {
                if (isPrime[num]) {
                    for (int product = num * num; product < n; product += num) {
                        isPrime[product] = false;
                    }
                }
            }
            return isPrime;
        }
    }
}
