import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class _316 {
    class Solution {
        /**
         * Time: O(N)
         * Space: O(N)
         *
         * @param s the given string
         * @return the lexicographical unique letters as result String
         */
        public String removeDuplicateLetters(String s) {
            int[] freq = new int[26]; //will contain number of occurrences of character (i+'a')
            char[] ch = s.toCharArray();
            for (char c : ch) {  //count number of occurrences of character
                freq[c - 'a']++;
            }
            Deque<Character> st = new ArrayDeque<>(); // answer stack
            boolean[] visited = new boolean[26];
            int index;
            for (char c : ch) {
                freq[c - 'a']--;
                if (visited[c - 'a']) continue;
                while (!st.isEmpty() && c < st.peek() && freq[st.peek() - 'a'] > 0) {
                    visited[st.pop() - 'a'] = false; // pop the char out & update the visited
                }
                st.push(c); // add current char into the stack
                visited[c - 'a'] = true;
            }

            StringBuilder sb = new StringBuilder();
            // pop character from stack and build answer string from back
            while (!st.isEmpty()) {
                sb.insert(0, st.pop());
            }
            return sb.toString();
        }
    }

}
