import java.util.Arrays;

/**
 * 452. Minimum Number of Arrows to Burst Balloons
 */
public class _452 {
    class Solution1_Sort {
        public int findMinArrowShots(int[][] points) {
            // sort by end asc
            Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));
            int i = 0;
            for (int j = 1; j < points.length; j++) {
                if (points[j][0] <= points[i][1]) {
                    points[i][0] = Math.max(points[i][0], points[j][0]);
                    points[i][1] = Math.min(points[i][1], points[j][1]);
                } else {
                    points[++i] = points[j];
                }
            }
            return i + 1;
        }
    }
}
