import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 986. Interval List Intersections
 * 
 * Clarification:
 * - Clarify on pairwise discount
 * - Are intervals inclusive/closed?
 * 
 * Edge Cases:
 * - empty list
 * - single point intersection
 * - one used up before another
 * - no overlap at all
 * 
 * Followup:
 * - What is intervals within each list overlap? Merge each list then find intersections
 * - Find union instead of intersection? Combine and then merge
 * - Count intersections instead of returning them
 */
public class _986 {
    // Time: O(m+n)
    // Space: O(m+n)
    class Solution1_TwoPointers {
        public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
            List<int[]> intersect = new ArrayList<>();
            int i = 0, j = 0;
            while (i < firstList.length && j < secondList.length) {
                int start = Math.max(firstList[i][0], secondList[j][0]);
                int end = Math.min(firstList[i][1], secondList[j][1]);

                if (start <= end) {
                    intersect.add(new int[]{start, end});
                }

                if (end == firstList[i][1]) i++;
                else j++;
            }

            return intersect.toArray(new int[intersect.size()][]);
        }
    }

    class Followup_IntervalUnion {
        // Time: O((m+n) log(m+n))
        // Space: O(m+n)
        public int[][] intervalUnion(int[][] firstList, int[][] secondList) {
            // Combine both lists
            int[][] combined = new int[firstList.length + secondList.length][];
            System.arraycopy(firstList, 0, combined, 0, firstList.length);
            System.arraycopy(secondList, 0, combined, firstList.length, secondList.length);
            
            // Sort and merge (like Merge Intervals problem)
            Arrays.sort(combined, (a, b) -> Integer.compare(a[0], b[0]));
            
            List<int[]> result = new ArrayList<>();
            int[] current = combined[0];
            
            for (int i = 1; i < combined.length; i++) {
                if (combined[i][0] <= current[1]) {
                    current[1] = Math.max(current[1], combined[i][1]);
                } else {
                    result.add(current);
                    current = combined[i];
                }
            }
            result.add(current);
            
            return result.toArray(new int[result.size()][]);
        }
    }
}
