import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 301. Remove Invalid Parentheses
 */
public class _301 {
    // Time: O(n*2^n)
    // Space: auxiliary O(n)
    class Solution1_Backtrack_By_Ball {
        private int minRemove;
        private Set<String> validStrings;

        public List<String> removeInvalidParentheses(String s) {
            minRemove = s.length();
            validStrings = new HashSet<>();
            char[] parentheses = s.toCharArray();
            backtrack(parentheses, 0, 0);
            return new ArrayList<>(validStrings);
        }

        private void backtrack(char[] parentheses, int idx, int removeTimes) {
            if (idx == parentheses.length) {
                if (isValidParentheses(parentheses)) {
                    if (removeTimes == minRemove) {
                        validStrings.add(convertToStr(parentheses));
                    } else if (removeTimes < minRemove) {
                        validStrings.clear();
                        validStrings.add(convertToStr(parentheses));
                        minRemove = removeTimes;
                    }
                }
                return;
            }
            // no remove for curr idx
            backtrack(parentheses, idx + 1, removeTimes);
            // remove if curr char is parenthesis
            char c = parentheses[idx];
            if (c == '(' || c == ')') {
                parentheses[idx] = '\0'; // mark as removal
                backtrack(parentheses, idx + 1, removeTimes + 1);
                parentheses[idx] = c;
            }
        }

        // we can use two pointers to count open & close parentheses if there's only one type of parenthesis
        /* private boolean isValidParentheses(char[] arr) {
            Deque<Character> stk = new ArrayDeque<>();
            for (int i = 0; i < arr.length; i++) {
                char c = arr[i];
                if (c == '\0' || c >= 'a' && c <= 'z') continue;
                else if (c == '(') stk.addLast(c);
                else if (stk.isEmpty()) return false;
                else stk.removeLast();
            }
            return stk.isEmpty();
        } */
        private boolean isValidParentheses(char[] arr) {
            int open = 0; // count open parentheses count
            for (int i = 0; i < arr.length; i++) {
                char c = arr[i];
                if (c == '\0' || c >= 'a' && c <= 'z') continue;
                if (c == '(') open++;
                else if (open == 0) return false;
                else open--;
            }
            return open == 0;
        }

        private String convertToStr(char[] arr) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == '\0') continue;
                sb.append(arr[i]);
            }
            return sb.toString();
        }
    }

    // we keep track of the open parentheses count; if it becomes negative, it's guaranteed to be invalid
    class Solution1_Backtrack_By_Ball_Early_Prune {
        private int minRemove;
        private Set<String> validStrings;

        public List<String> removeInvalidParentheses(String s) {
            minRemove = s.length();
            validStrings = new HashSet<>();
            char[] parentheses = s.toCharArray();
            backtrack(parentheses, 0, 0, 0);
            return new ArrayList<>(validStrings);
        }

        private void backtrack(char[] parentheses, int idx, int removeTimes, int open) {
            if (removeTimes > minRemove) return;
            if (open < 0) return;
            if (idx == parentheses.length) {
                if (isValidParentheses(parentheses)) {
                    if (removeTimes == minRemove) {
                        validStrings.add(convertToStr(parentheses));
                    } else if (removeTimes < minRemove) {
                        validStrings.clear();
                        validStrings.add(convertToStr(parentheses));
                        minRemove = removeTimes;
                    }
                }
                return;
            }
            char c = parentheses[idx];
            // remove if curr char is parenthesis
            if (c == '(' || c == ')') {
                parentheses[idx] = '\0'; // mark as removal
                backtrack(parentheses, idx + 1, removeTimes + 1, open);
                parentheses[idx] = c;
            }
            // no remove for curr idx
            if (c == ')') open--;
            if (c == '(') open++;
            backtrack(parentheses, idx + 1, removeTimes, open);
        }

        private boolean isValidParentheses(char[] arr) {
            int open = 0; // count open parentheses count
            for (int i = 0; i < arr.length; i++) {
                char c = arr[i];
                if (c == '\0' || c >= 'a' && c <= 'z') continue;
                if (c == '(') open++;
                else if (open == 0) return false;
                else open--;
            }
            return open == 0;
        }

        private String convertToStr(char[] arr) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == '\0') continue;
                sb.append(arr[i]);
            }
            return sb.toString();
        }
    }

    class Solution2_Backtrack_By_Ball_Target_Remove {
        public List<String> removeInvalidParentheses(String s) {
            int leftRem = 0, rightRem = 0;
            for (char c : s.toCharArray()) {
                if (c == '(') leftRem++;
                else if (c == ')') {
                    if (leftRem > 0) leftRem--;
                    else rightRem++;
                }
            }
            List<String> res = new ArrayList<>();
            backtrack(s, 0, new StringBuilder(), 0, leftRem, rightRem, res);
            return res;
        }

        private void backtrack(String s, int i, StringBuilder sb, int open,
                        int leftRem, int rightRem, List<String> res) {
            if (i == s.length()) {
                if (open == 0 && leftRem == 0 && rightRem == 0) {
                    res.add(sb.toString());
                }
                return;
            }

            char c = s.charAt(i);

            // Option 1: remove current parenthesis if we still need to remove
            if (c == '(' && leftRem > 0) {
                // skip duplicates: don't remove multiple identical parentheses in a row
                int j = i + 1;
                while (j < s.length() && s.charAt(j) == '(') j++;
                backtrack(s, j, sb, open, leftRem - 1, rightRem, res);
            }
            if (c == ')' && rightRem > 0) {
                int j = i + 1;
                while (j < s.length() && s.charAt(j) == ')') j++;
                backtrack(s, j, sb, open, leftRem, rightRem - 1, res);
            }

            // Option 2: keep current char
            sb.append(c);
            if (c != '(' && c != ')') {
                backtrack(s, i + 1, sb, open, leftRem, rightRem, res);
            } else if (c == '(') {
                backtrack(s, i + 1, sb, open + 1, leftRem, rightRem, res);
            } else if (open > 0) { // c == ')', only valid if there is a matching '('
                backtrack(s, i + 1, sb, open - 1, leftRem, rightRem, res);
            }
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    class Solution2_Backtrack_By_Ball_Target_Remove_Improved {
        public List<String> removeInvalidParentheses(String s) {
            int leftRem = 0, rightRem = 0;
            for (char c : s.toCharArray()) {
                if (c == '(') leftRem++;
                else if (c == ')') {
                    if (leftRem > 0) leftRem--;
                    else rightRem++;
                }
            }
            List<String> res = new ArrayList<>();
            dfs(s, 0, new StringBuilder(), 0, leftRem, rightRem, res, '\0');
            return res;
        }

        private void dfs(String s, int i, StringBuilder sb, int open,
                        int leftRem, int rightRem, List<String> res, char lastRemove) {
            if (leftRem < 0 || rightRem < 0 || open < 0) return;
            if (i == s.length()) {
                if (open == 0 && leftRem == 0 && rightRem == 0) {
                    res.add(sb.toString());
                }
                return;
            }

            char c = s.charAt(i);
            char emptyChar = '\0';
            if (c == '(') {
                dfs(s, i + 1, sb, open, leftRem - 1, rightRem, res, c); // not use -- remove
                if (lastRemove == '(') return; // skip the duplicate res
                dfs(s, i + 1, sb.append(c), open + 1, leftRem, rightRem, res, emptyChar); // use
            } else if (c == ')') {
                dfs(s, i + 1, sb, open, leftRem, rightRem - 1, res, c); // not use -- remove
                if (lastRemove == ')') return; // skip the duplicate res
                dfs(s, i + 1, sb.append(c), open - 1, leftRem, rightRem, res, emptyChar); // use
            } else {
                dfs(s, i + 1, sb.append(c), open, leftRem, rightRem, res, emptyChar); // do nothing for letter
            }

            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
