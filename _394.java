import java.util.Stack;

/**
 * 394. Decode String
 */
public class _394 {
    // Time: O(L) where L is output length
    // Space: O(L)
    class Solution1_Recursion {
        int pos;
        public String decodeString(String s) {
            pos = 0;
            return decode(s);
        }

        private String decode(String s) {
            StringBuilder sb = new StringBuilder();
            int k = 0;
            while (pos < s.length()) {
                char c = s.charAt(pos++);
                if (c >= 'a' && c <= 'z') {
                    sb.append(c);
                } else if (c >= '0' && c <= '9') {
                    k = 10 * k + (c - '0');
                } else if (c == '[') {
                    String toRepeat = decode(s);
                    repeatKTimes(sb, toRepeat, k);
                    k = 0;
                } else if (c == ']') {
                    break;
                }
            }
            return sb.toString();
        }

        private void repeatKTimes(StringBuilder sb, String toRepeat, int k) {
            for (int i = 1; i <= k; i++) {
                sb.append(toRepeat);
            }
        }
    }

    class Solution2_Stack {
        public String decodeString(String s) {
            Stack<Integer> countStack = new Stack<>();
            Stack<String> stringStack = new Stack<>();
            
            String currentString = "";
            int currentNum = 0;
            
            for (char c : s.toCharArray()) {
                if (Character.isDigit(c)) {
                    currentNum = currentNum * 10 + (c - '0');
                } else if (c == '[') {
                    countStack.push(currentNum);
                    stringStack.push(currentString);
                    currentNum = 0;
                    currentString = "";
                } else if (c == ']') {
                    int count = countStack.pop();
                    StringBuilder decoded = new StringBuilder(stringStack.pop());
                    
                    for (int i = 0; i < count; i++) {
                        decoded.append(currentString);
                    }
                    
                    currentString = decoded.toString();
                } else {
                    currentString += c;
                }
            }
            
            return currentString;
        }
    }
}
