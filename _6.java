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

    class Solution2 {
        public String convert(String s, int numRows) {
            if (numRows == 1 || s.length() < numRows) return s;
            StringBuilder[] lines = new StringBuilder[numRows];
            for (int i = 0; i < numRows; i++) {
                lines[i] = new StringBuilder();
            }
    
            boolean goDown = false;
            int row = 0;
            for (int i = 0; i < s.length(); i++) {
                lines[row].append(s.charAt(i));
                if (row == 0 || row == numRows - 1) goDown = !goDown; // numRows >= 2
                row += goDown ? 1 : -1;
            }
    
            StringBuilder zigzag = new StringBuilder();
            for (StringBuilder line : lines) {
                zigzag.append(line);
            }
            return zigzag.toString();
        }
    }
}
