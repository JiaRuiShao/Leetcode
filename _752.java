import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * 752. Open the Lock
 */
public class _752 {
    // Time: O(10^n*8n) where n is target length, here is 4; we have 10^4 states here
    class Solution1_BFS {
        public int openLock(String[] deadends, String target) {
            Set<String> visited = new HashSet<>();
            for (String dead : deadends) {
                visited.add(dead);
            }
            String start = "0000";
            if (visited.contains(start)) {
                return -1;
            }
            Queue<String> q = new ArrayDeque<>();
            q.offer(start);
            visited.add(start);

            int turns = 0;
            while (!q.isEmpty()) {
                int size = q.size();
                for (int i = 0; i < size; i++) {
                    String curr = q.poll();
                    if (curr.equals(target)) {
                        return turns;
                    }
                    char[] arr = curr.toCharArray();
                    for (int digit = 0; digit < 4; digit++) {
                        String turnUp = turnUp(arr, digit);
                        String turnDown = turnDown(arr, digit);
                        if (!visited.contains(turnUp)) {
                            q.offer(turnUp);
                            visited.add(turnUp);
                        }
                        if (!visited.contains(turnDown)) {
                            q.offer(turnDown);
                            visited.add(turnDown);
                        }
                    }
                }
                turns++;
            }
            return -1;
        }

        private String turnUp(char[] arr, int idx) {
            char num = arr[idx];
            char next = num == '9' ? '0' : (char) (num + 1);
            arr[idx] = next;
            String res = new String(arr);
            arr[idx] = num;
            return res;
        }

        private String turnDown(char[] arr, int idx) {
            char num = arr[idx];
            char next = num == '0' ? '9' : (char) (num - 1);
            arr[idx] = next;
            String res = new String(arr);
            arr[idx] = num;
            return res;
        }

        // another way to write
        // private String turnUp(char[] arr, int idx) {
        //     int num = arr[idx] - '0';
        //     int next = num == 9 ? 0 : num + 1;
        //     arr[idx] = (char)('0' + next);
        //     String res = new String(arr);
        //     arr[idx] =  (char)('0' + num);
        //     return res;
        // }

        // private String turnDown(char[] arr, int idx) {
        //     int num = arr[idx] - '0';
        //     int next = num == 0 ? 9 : num - 1;
        //     arr[idx] = (char)('0' + next);
        //     String res = new String(arr);
        //     arr[idx] = (char)('0' + num);
        //     return res;
        // }
    }

    class Solution2_BidirectionalBFS {
        public int openLock(String[] deadends, String target) {
            Set<String> deads = new HashSet<>();
            for (String deadend : deadends) {
                deads.add(deadend);
            }

            String start = "0000";
            if (deads.contains(start)) return -1;
            Queue<String> q1 = new ArrayDeque<>();
            Queue<String> q2 = new ArrayDeque<>();
            Set<String> set1 = new HashSet<>();
            Set<String> set2 = new HashSet<>();
            q1.offer(start);
            set1.add(start);
            q2.offer(target);
            set2.add(target);

            int turn = 0;
            while (!q1.isEmpty() && !q2.isEmpty()) {
                int size = q1.size();
                for (int i = 0; i < size; i++) {
                    String curr = q1.poll();
                    if (set2.contains(curr)) return turn;
                    for (String next : getNeighbors(curr)) {
                        if (!deads.contains(next) && !set1.contains(next)) {
                            q1.offer(next);
                            set1.add(next);
                        }
                    }
                }
                if (q2.size() < q1.size()) {
                    Queue<String> temp = q1;
                    q1 = q2;
                    q2 = temp;
                    Set<String> tmp = set1;
                    set1 = set2;
                    set2 = tmp;
                }
                turn++;
            }
            return -1;
        }

        private List<String> getNeighbors(String s) {
            List<String> res = new ArrayList<>();
            char[] arr = s.toCharArray();
            for (int i = 0; i < arr.length; i++) {
                char old = arr[i];
                arr[i] = (old == '9') ? '0' : (char)(old + 1);
                res.add(new String(arr));
                arr[i] = (old == '0') ? '9' : (char)(old - 1);
                res.add(new String(arr));
                arr[i] = old;
            }
            return res;
        }
    }
}
