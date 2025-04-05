/**
 * 990. Satisfiability of Equality Equations
 */
public class _990 {
    class Solution1_Union_Find {
        /**
         * Connect all variables that are declared equal (==), then check all inequalities (!=); if any variables are found connected, return false (since it breaks the equalities).
         * Time: O(n)
         * Space: O(n)
         * @param equations
         * @return
         */
        public boolean equationsPossible(String[] equations) {
            char start = 'a';
            int[] parent = new int[26];
            for (int i = 0; i < 26; i++) {
                parent[i] = i;
            }
    
            for (String equation : equations) {
                if (equation.charAt(1) == '=') {
                    int n1 = equation.charAt(0) - start;
                    int n2 = equation.charAt(3) - start;
                    union(parent, n1, n2);
                }
            }  
    
            for (String equation : equations) {
                if (equation.charAt(1) == '!') {
                    int n1 = equation.charAt(0) - start;
                    int n2 = equation.charAt(3) - start;
                    /* we cannot directly compare parent[n1] with parent[n2] because the parent might not be updated to root node */
                    if (findParent(parent, n1) == findParent(parent, n2)) {
                        return false;
                    }
                }
            }
            return true; 
        }
    
        private void union(int[] parent, int n1, int n2) {
            int p1 = findParent(parent, n1);
            int p2 = findParent(parent, n2);
            if (p1 != p2) {
                parent[p1] = p2;
            }
        }
    
        private int findParent(int[] parent, int node) {
            if (parent[node] != node) {
                parent[node] = findParent(parent, parent[node]);
            }
            return parent[node];
        }
    }
}
