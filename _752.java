import java.util.ArrayDeque;
import java.util.HashSet;
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
}
