import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 952. Largest Component Size by Common Factor
 */
public class _952 {
    // Time: O(n^2xlogm) where n is num of elems in nums and m is largest elem in nums
    // Space: O(n)
    class Solution1_UF_UnionByNum {
        public int largestComponentSize(int[] nums) {
            int n = nums.length;
            UnionFind uf = new UnionFind(n);
            
            // Connect numbers with common factors
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (gcd(nums[i], nums[j]) > 1) {
                        uf.union(i, j);
                    }
                }
            }
            
            // Count sizes of components
            Map<Integer, Integer> componentSize = new HashMap<>();
            int maxSize = 0;
            
            for (int i = 0; i < n; i++) {
                int root = uf.find(i);
                componentSize.put(root, componentSize.getOrDefault(root, 0) + 1);
                maxSize = Math.max(maxSize, componentSize.get(root));
            }
            
            return maxSize;
        }
        
        private int gcd(int a, int b) {
            while (b != 0) {
                int temp = b;
                b = a % b;
                a = temp;
            }
            return a;
        }
        
        class UnionFind {
            int[] parent;
            int[] rank;
            
            UnionFind(int n) {
                parent = new int[n];
                rank = new int[n];
                for (int i = 0; i < n; i++) {
                    parent[i] = i;
                }
            }
            
            int find(int x) {
                if (parent[x] != x) {
                    parent[x] = find(parent[x]);
                }
                return parent[x];
            }
            
            void union(int x, int y) {
                int rootX = find(x);
                int rootY = find(y);
                
                if (rootX != rootY) {
                    if (rank[rootX] < rank[rootY]) {
                        parent[rootX] = rootY;
                    } else if (rank[rootX] > rank[rootY]) {
                        parent[rootY] = rootX;
                    } else {
                        parent[rootY] = rootX;
                        rank[rootX]++;
                    }
                }
            }
        }
    }

    // Time: O(n×√max)
    // Space: O(max)
    class Solution2_UnionByPrimeFactor {
        public int largestComponentSize(int[] nums) {
            int maxVal = 0;
            for (int num : nums) {
                maxVal = Math.max(maxVal, num);
            }
            
            UnionFind uf = new UnionFind(maxVal + 1);
            
            // For each number, unite it with all its prime factors
            for (int num : nums) {
                for (int factor : getPrimeFactors(num)) {
                    uf.union(num, factor);
                }
            }
            
            // Count component sizes
            Map<Integer, Integer> componentSize = new HashMap<>();
            int maxSize = 0;
            
            for (int num : nums) {
                int root = uf.find(num);
                componentSize.put(root, componentSize.getOrDefault(root, 0) + 1);
                maxSize = Math.max(maxSize, componentSize.get(root));
            }
            
            return maxSize;
        }
        
        private List<Integer> getPrimeFactors(int n) {
            List<Integer> factors = new ArrayList<>();
            
            // Check for factor 2
            if (n % 2 == 0) {
                factors.add(2);
                while (n % 2 == 0) {
                    n /= 2;
                }
            }
            
            // Check odd factors from 3 onwards
            for (int i = 3; i * i <= n; i += 2) {
                if (n % i == 0) {
                    factors.add(i);
                    while (n % i == 0) {
                        n /= i;
                    }
                }
            }
            
            // If n is still > 1, then it's a prime factor
            if (n > 1) {
                factors.add(n);
            }
            
            return factors;
        }
        
        class UnionFind {
            int[] parent;
            int[] rank;
            
            UnionFind(int n) {
                parent = new int[n];
                rank = new int[n];
                for (int i = 0; i < n; i++) {
                    parent[i] = i;
                }
            }
            
            int find(int x) {
                if (parent[x] != x) {
                    parent[x] = find(parent[x]);
                }
                return parent[x];
            }
            
            void union(int x, int y) {
                int rootX = find(x);
                int rootY = find(y);
                
                if (rootX != rootY) {
                    if (rank[rootX] < rank[rootY]) {
                        parent[rootX] = rootY;
                    } else if (rank[rootX] > rank[rootY]) {
                        parent[rootY] = rootX;
                    } else {
                        parent[rootY] = rootX;
                        rank[rootX]++;
                    }
                }
            }
        }
    }
}
