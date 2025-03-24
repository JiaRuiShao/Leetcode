/**
 * 1700. Number of Students Unable to Eat Lunch
 */
public class _1700 {
    class Solution {
        public int countStudents(int[] students, int[] sandwiches) {
            int[] studentCount = new int[2];
            for (int type : students) {
                studentCount[type]++;
            }
            for (int type : sandwiches) {
                if (studentCount[type] == 0) {
                    return studentCount[0] + studentCount[1];
                }
                studentCount[type]--;
            }
            return 0;
        }
    }
}
