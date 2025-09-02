import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * 1700. Number of Students Unable to Eat Lunch
 */
public class _1700 {
    public class Solution1_Queue {
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
    }

    class Solution2_Greedy {
        public int countStudents(int[] students, int[] sandwiches) {
            // count how many students prefer 0 and 1
            int[] count = new int[2];
            for (int type : students) {
                count[type]++;
            }
            // serve sandwiches in order until a type runs out
            for (int sandwich : sandwiches) {
                if (count[sandwich] > 0) {
                    count[sandwich]--;
                } else {
                    break;  // no one wants this sandwich
                }
            }
            // remaining students = sum of leftover prefs
            return count[0] + count[1];
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
