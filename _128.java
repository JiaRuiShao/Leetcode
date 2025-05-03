import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 128. Longest Consecutive Sequence
 */
public class _128 {
    class Solution1_Set {
        public int longestConsecutive(int[] nums) {
            // Convert to a hash set for quick lookup of elements
            Set<Integer> set = new HashSet<Integer>();
            for (int num : nums) {
                set.add(num);
            }

            int res = 0;

            for (int num : set) {
                if (set.contains(num - 1)) {
                    // num is not the first in the consecutive subsequence, skip it
                    continue;
                }
                // num is the first in the consecutive subsequence, start calculating the length of the subsequence
                int curNum = num;
                int curLen = 1;

                while (set.contains(curNum + 1)) {
                    curNum += 1;
                    curLen += 1;
                }
                // Update the length of the longest consecutive sequence
                res = Math.max(res, curLen);
            }

            return res;
        }
    }

    class Solution2_Union_Find {
        public int longestConsecutive(int[] nums) {
            UF uf = new UF(nums.length);
            // Map val to index in nums
            Map<Integer, Integer> valToIndex = new HashMap<>();
            
            for (int i = 0; i < nums.length; i++) {
                if (valToIndex.containsKey(nums[i])) {
                    continue;
                }
                
                if (valToIndex.containsKey(nums[i] - 1)) {
                    uf.union(i, valToIndex.get(nums[i] - 1));
                }
                
                if (valToIndex.containsKey(nums[i] + 1)) {
                    uf.union(i, valToIndex.get(nums[i] + 1));
                }
                
                valToIndex.put(nums[i], i);
            }
            
            return uf.getLargetComponentSize();
        }
        
        class UF {
            private int[] parent;
            private int[] size;
            
            public UF(int n) {
                parent = new int[n];
                size = new int[n];
                for (int i = 0; i < n; i++) {
                    parent[i] = i;
                    size[i] = 1;
                }
            }
            
            public void union(int x, int y) { // connected if consecutive
                int rootX = find(x);
                int rootY = find(y);
                if (rootX != rootY) {
                    parent[rootX] = rootY;
                    size[rootY] += size[rootX];
                }
            };
            
            private int find(int x) {
                if (parent[x] == x) {
                    return x;
                }
                
                return parent[x] = find(parent[x]);
            };
            
            public int getLargetComponentSize() {
                int maxSize = 0;
                for (int i = 0; i < parent.length; i++) {
                    if (parent[i] == i && size[i] > maxSize) {
                        maxSize = size[i];
                    }
                }
                
                return maxSize;
            }
        }
    }

    class Solution3_Union_Find_HashMap {
        public int longestConsecutive(int[] nums) {
            if (nums.length == 0) return 0;
            UF uf = new UF();
            Set<Integer> distincts = new HashSet<>();
            for (int num : nums) {
                if (distincts.contains(num - 1)) {
                    uf.union(num, num - 1);
                }
                if (distincts.contains(num + 1)) {
                    uf.union(num, num + 1);
                }
                distincts.add(num);
            }
            return uf.maxConnectedComponents;
        }
    
        private class UF {
            private Map<Integer, Integer> parents;
            private Map<Integer, Integer> connectedComponents;
            private int maxConnectedComponents;
    
            public UF() {
                parents = new HashMap<>();
                connectedComponents = new HashMap<>();
                maxConnectedComponents = 1;
            }
    
            public int findParent(int num) {
                if (!parents.containsKey(num)) {
                    parents.put(num, num);
                    connectedComponents.put(num, 1);
                    return num;
                }
                if (parents.get(num) != num) {
                    parents.put(num, findParent(parents.get(num)));
                }
                return parents.get(num);
            }
    
            public void union(int n1, int n2) {
                int p1 = findParent(n1);
                int p2 = findParent(n2);
                if (p1 != p2) {
                    parents.put(p1, p2);
                    connectedComponents.put(p2, connectedComponents.get(p2) + connectedComponents.get(p1));
                }
                maxConnectedComponents = Math.max(maxConnectedComponents, connectedComponents.get(p2));
            }
        }
    }
}
