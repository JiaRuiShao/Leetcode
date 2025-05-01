/**
 * 6. Zigzag Conversion
 */
public class _6 {
    class Solution1 {
        public String convert(String s, int numRows) {
            if (numRows == 1) return s;
            int  n = s.length();
            StringBuilder[] rows = new StringBuilder[numRows];
            for (int row = 0; row < numRows; row++) {
                rows[row] = new StringBuilder();
            }
            int i = 0;
            while (i < n) {
                // add vertial top down
                for (int j = 0; j < numRows && i < n; j++) {
                    rows[j].append(s.charAt(i++));
                }
                // add diagonal bottom up (A L & R I in the example where numRows = 4)
                for (int j = numRows - 2; j > 0 && i < n; j--) {
                    rows[j].append(s.charAt(i++));
                }
            }
            StringBuilder res = new StringBuilder();
            for (StringBuilder row : rows) {
                res.append(row);
            }
            return res.toString();
        }
    }

    public class Solution2 {
        public String convert(String s, int numRows) {
            if (numRows == 1 || numRows >= s.length()) return s;

            StringBuilder[] rows = new StringBuilder[Math.min(numRows, s.length())];
            for (int i = 0; i < rows.length; i++) rows[i] = new StringBuilder();
            
            int curRow = 0;
            boolean goingDown = false;
    
            for (char c : s.toCharArray()) {
                rows[curRow].append(c);
                if (curRow == 0 || curRow == numRows - 1) goingDown = !goingDown;
                curRow += goingDown ? 1 : -1;
            }
    
            StringBuilder result = new StringBuilder();
            for (StringBuilder row : rows) result.append(row);
            return result.toString();
        }
    }
}
