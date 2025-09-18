import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * 773. Sliding Puzzle
 */
public class _773 {
    // Time: O(S*b*L) where S is number of possible states; b is neighbor # for each state; L is the the total boards length 
    // Time: O((mn)!*3*mn) where mn = 6
    // Space: O(S*L) = O(mn*(mn)!)
    class Solution1_BFS {
        public int slidingPuzzle(int[][] board) {
            Set<String> visited = new HashSet<>();
            Queue<String> q = new ArrayDeque<>();
            int[][] neighbors = { // neighbor indices for index i
                {1, 3},
                {0, 2, 4},
                {1, 5},
                {0, 4},
                {1, 3, 5},
                {2, 4}
            };

            String start = toString(board);
            String end = toString(new int[][]{{1,2,3}, {4,5,0}});
            q.offer(start);
            visited.add(start);

            int moves = 0;
            while (!q.isEmpty()) {
                int size = q.size();
                for (int i = 0; i < size; i++) {
                    String state = q.poll();
                    if (state.equals(end)) {
                        return moves;
                    }
                    int idx = state.indexOf("0");
                    for (int nbr : neighbors[idx]) {
                        String nextState = swap(state, idx, nbr);
                        if (!visited.contains(nextState)) {
                            q.offer(nextState);
                            visited.add(nextState);
                        }
                    }
                }
                moves++;
            }
            return -1;
        }

        private String swap(String s, int i, int j) {
            StringBuilder sb = new StringBuilder(s);
            sb.setCharAt(i, s.charAt(j));
            sb.setCharAt(j, s.charAt(i));
            return sb.toString();
        }

        private String toString(int[][] board) {
            StringBuilder sb = new StringBuilder();
            for (int m = 0; m < board.length; m++) {
                for (int n = 0; n < board[0].length; n++) {
                    sb.append(board[m][n]);
                }
            }
            return sb.toString();
        }
    }

    // Time: O(L*3^L) where L is mn when there's no pruning
    class Solution2_DFS {
        int minMoves;

        public int slidingPuzzle(int[][] board) {
            minMoves = Integer.MAX_VALUE;
            for (int m = 0; m < board.length; m++) {
                for (int n = 0; n < board[0].length; n++) {
                    if (board[m][n] == 0) {
                        dfs(board, m, n, new HashMap<String, Integer>(), toString(new int[][]{{1,2,3}, {4,5,0}}), 0);
                        break;
                    }
                }
            }
            return minMoves == Integer.MAX_VALUE ? -1 : minMoves;
        }

        // notice that we can reach the target state multiple times via different path
        // this is why we NEED to use a Map to save the state and best moves for that state (not just a Set since the 2nd time could the optimized approach), i.e. 412503 → 412053 (move 0 left) versus 412503 → 402513 → 042513 → 542013 → 542103 → 502143 → 052143 → 152043 → 152403 → 102453 → 012453 → 412053
        private void dfs(int[][] board, int r, int c, Map<String, Integer> visited, String target, int moves) {
            if (r < 0 || c < 0 || r == 2 || c == 3) return;
            String curr = toString(board);
            if (curr.equals(target)) {
                minMoves = Math.min(minMoves, moves);
                return;
            }
            if (visited.containsKey(curr) && moves >= visited.get(curr)) {
                return;
            }
            visited.put(curr, moves);
            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            for (int[] dir : dirs) {
                int nr = r + dir[0], nc = c + dir[1];
                if (nr < 0 || nc < 0 || nr == 2 || nc == 3) continue;
                swap(board, r, c, nr, nc);
                dfs(board, nr, nc, visited, target, moves + 1);
                swap(board, r, c, nr, nc);
            }
        }

        private void swap(int[][] board, int r, int c, int nr, int nc) {
            int temp = board[r][c];
            board[r][c] = board[nr][nc];
            board[nr][nc] = temp;
        }

        private String toString(int[][] board) {
            StringBuilder sb = new StringBuilder();
            for (int m = 0; m < board.length; m++) {
                for (int n = 0; n < board[0].length; n++) {
                    sb.append(board[m][n]);
                }
            }
            return sb.toString();
        }
    }
}