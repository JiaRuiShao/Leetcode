import java.util.ArrayList;
import java.util.List;

/**
 * 118. Pascal's Triangle
 */
public class _118 {
    class Solution1 {
        public List<List<Integer>> generate(int numRows) {
            List<List<Integer>> pascalTriangle = new ArrayList<>();
            pascalTriangle.add(new ArrayList<>());
            pascalTriangle.get(0).add(1);
            // num[r][i] = num[r-1][i-1] + num[r-1][i]
            for (int row = 1; row < numRows; row++) {
                List<Integer> prevRow = pascalTriangle.get(row - 1);
                List<Integer> currRow = new ArrayList<>();
                currRow.add(1);
                for (int col = 1; col < row; col++) {
                    currRow.add(prevRow.get(col - 1) + prevRow.get(col));
                }
                currRow.add(1);
                pascalTriangle.add(currRow);
            }
            return pascalTriangle;
        }
    }

    class Solution2 {
        public List<List<Integer>> generate(int numRows) {
            List<List<Integer>> pascalTriangle = new ArrayList<>();
            // num[r][i] = num[r-1][i-1] + num[r-1][i]
            for (int r = 0; r < numRows; r++) {
                List<Integer> row = new ArrayList<>();
                for (int c = 0; c <= r; c++) {
                    if (c == 0 || c == r) {
                        row.add(1);
                    } else {
                        row.add(pascalTriangle.get(r - 1).get(c - 1) + pascalTriangle.get(r - 1).get(c));
                    }
                }
                pascalTriangle.add(row);
            }
            return pascalTriangle;
        }
    }
}
