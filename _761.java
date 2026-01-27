import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 761. Special Binary String
 */
public class _761 {
    // Time: O(nlogn)
    // Space: O(n)
    class Solution1_Recursion {
        // binary version of valid parenthesis
        // 1 -> (
        // 0 -> )
        public String makeLargestSpecial(String s) {
            // Base case: empty or single pair
            if (s.length() <= 2) {
                return s;
            }
            
            List<String> substrings = new ArrayList<>();
            int count = 0;
            int start = 0;
            
            // Find all top-level special substrings
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '1') {
                    count++;
                } else {
                    count--;
                }
                
                // When count becomes 0, we found a complete special substring
                if (count == 0) {
                    // Recursively process the inner part (excluding outer 1 and 0)
                    String inner = s.substring(start + 1, i);
                    String processed = "1" + makeLargestSpecial(inner) + "0";
                    substrings.add(processed);
                    start = i + 1;
                }
            }
            
            // Sort in descending order to get lexicographically largest
            Collections.sort(substrings, Collections.reverseOrder());
            
            // Concatenate all substrings
            StringBuilder result = new StringBuilder();
            for (String sub : substrings) {
                result.append(sub);
            }
            
            return result.toString();
        }
    }
}
