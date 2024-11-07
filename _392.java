/**
 * 392. Is Subsequence.
 */
public class _392 {
    public boolean isSubsequence(String s, String t) {
        if (t.length() < s.length()) {
            return false;
        }

        int ps = 0, pt = 0;
        while (ps < s.length() && pt < t.length()) {
            if (s.charAt(ps) == t.charAt(pt)) ps++;
            pt++;
        }
        return ps == s.length();
    }
}
