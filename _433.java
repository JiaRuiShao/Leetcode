import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * 433. Minimum Genetic Mutation
 */
public class _433 {
    // Time: O(n^2*4^n)
    class Solution1_BFS {
        public int minMutation(String startGene, String endGene, String[] bank) {
            Set<String> valid = new HashSet<>();
            for (int i = 0; i < bank.length; i++) {
                valid.add(bank[i]);
            }
            char[] validChars = {'A', 'C', 'G', 'T'};
            Set<String> visited = new HashSet<>();
            Queue<String> mutation = new ArrayDeque<>();
            mutation.offer(startGene);
            visited.add(startGene);
            int steps = 0;
            while (!mutation.isEmpty()) {
                int size = mutation.size();
                for (int i = 0; i < size; i++) {
                    String gene = mutation.poll();
                    if (gene.equals(endGene)) {
                        return steps;
                    }
                    char[] arr = gene.toCharArray();
                    for (int j = 0; j < 8; j++) {
                        char old = arr[j];
                        for (char newChar : validChars) {
                            if (newChar == old) continue;
                            arr[j] = newChar;
                            String nextMutate = new String(arr);
                            if (!visited.contains(nextMutate) && valid.contains(nextMutate)) {
                                mutation.offer(nextMutate);
                                visited.add(nextMutate);
                            }
                        }
                        arr[j] = old;
                    }
                }
                steps++;
            }
            return -1;
        }
    }
}
