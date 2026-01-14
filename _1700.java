import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Queue;

/**
 * 1700. Number of Students Unable to Eat Lunch
 */
public class _1700 {
    public class Solution1_Simulation {
        public int countStudents(int[] students, int[] sandwiches) {
            Deque<Integer> queue = new ArrayDeque<>();
            for (int pref : students) {
                queue.addLast(pref);
            }
            
            int sandwichIndex = 0;
            int rotations = 0;
            
            while (!queue.isEmpty() && rotations < queue.size()) {
                int studentPref = queue.peekFirst();
                int topSandwich = sandwiches[sandwichIndex];
                
                if (studentPref == topSandwich) {
                    queue.removeFirst();
                    sandwichIndex++;
                    rotations = 0;           // reset since someone ate
                } else {
                    queue.removeFirst();
                    queue.addLast(studentPref);
                    rotations++;
                }
            }
            
            return queue.size();
        }

        public int countStudents2(int[] students, int[] sandwiches) {
            Queue<Integer> q = new ArrayDeque<>();
            for (int student : students) {
                q.offer(student);
            }

            for (int i = 0; i < sandwiches.length; i++) {
                int tries = q.size();
                while (!q.isEmpty()) {
                    int student = q.poll();
                    if (student == sandwiches[i]) {
                        break;
                    }
                    q.offer(student);
                    if (--tries == 0) {
                        return q.size();
                    }
                }
            }
            return 0;
        }
    }

    class Solution2_Greedy {
        public int countStudents(int[] students, int[] sandwiches) {
            // count how many students prefer 0 and 1
            int[] needs = new int[2];
            for (int prefer : students) {
                needs[prefer]++;
            }

            // serve sandwiches in order until a type runs out
            for (int sandwich : sandwiches) {
                if (needs[sandwich]-- == 0) {
                    return needs[1-sandwich];
                }
            }
            return 0;
        }
    }

    class Solution3_My_Solution {
        public int countStudents(int[] students, int[] sandwiches) {
            int circularNeeds = (int) Arrays.stream(students).filter(s -> s == 0).count();
            int squareNeeds = students.length - circularNeeds;
            for (int sandwich : sandwiches) {
                if (sandwich == 0) {
                    if (circularNeeds == 0) break;
                    circularNeeds--;
                } else {
                    if (squareNeeds == 0) break;
                    squareNeeds--;
                }
            }
            return circularNeeds + squareNeeds;
        }
    }
}
