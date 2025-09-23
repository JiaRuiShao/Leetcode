/**
 * 1541. Minimum Insertions to Balance a Parentheses String
 */
public class _1541 {
    class Solution1 {
        public int minInsertions(String s) {
            int open = 0, close = 0;
            for (char c : s.toCharArray()) {
                if (c == '(') {
                    close += 2;
                    if (close % 2 == 1) {
                        open++;
                        close--;
                    }
                } else if (close-- == 0) {
                    open++;
                    close = 1;
                }
            }
            return open + close;
        }
    }

    class FollowUp {
        public int[] minInsertions(String s) {
            int openAdd = 0, closeAdd = 0, closeNeed = 0;
            for (char c : s.toCharArray()) {
                if (c == '(') {
                    closeNeed += 2;
                    if (closeNeed % 2 == 1) {
                        closeAdd++;
                        closeNeed--;
                    }
                } else if (closeNeed-- == 0) {
                    openAdd++;
                    closeNeed = 1;
                }
            }
            return new int[]{openAdd, closeAdd + closeNeed};
        }
    }
}