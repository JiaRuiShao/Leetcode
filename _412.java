import java.util.ArrayList;
import java.util.List;

/**
 * 412. Fizz Buzz
 */
public class _412 {
    class Solution {
        public List<String> fizzBuzz(int n) {
            String fizzBuzz = "FizzBuzz";
            String fizz = "Fizz";
            String buzz = "Buzz";
            List<String> ans = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                String s = null;
                if (i % 3 == 0 && i % 5 == 0) {
                    s = fizzBuzz;
                } else if (i % 3 == 0) {
                    s = fizz;
                } else if (i % 5 == 0) {
                    s = buzz;
                } else {
                    s = String.valueOf(i);
                }
                ans.add(s);
            }
            return ans;
        }
    }
}
