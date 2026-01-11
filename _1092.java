/**
 * 1092. Shortest Common Supersequence 
 */
public class _1092 {
    public String shortestCommonSupersequence(String str1, String str2) {
        int m = str1.length(), n = str2.length();
        
        // compute LCS
        int[][] dp = new int[m + 1][n + 1];
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        // Construct SCS by backtracking
        int len = m + n - dp[m][n];
        char[] scs = new char[len];
        int i = m, j = n, pos = len - 1;
        
        while (i > 0 && j > 0) {
            if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                // char in LCS
                scs[pos--] = str1.charAt(i - 1);
                i--; j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                // include str1[i-1]
                scs[pos--] = str1.charAt(i - 1);
                i--;
            } else {
                // include str2[j-1]
                scs[pos--] = str2.charAt(j - 1);
                j--;
            }
        }
        
        // add remaining characters
        while (i > 0) {
            scs[pos--] = str1.charAt(i - 1);
            i--;
        }
        while (j > 0) {
            scs[pos--] = str2.charAt(j - 1);
            j--;
        }
        
        return new String(scs);
    }
}
