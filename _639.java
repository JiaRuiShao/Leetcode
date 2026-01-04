/**
 * 639. Decode Ways II
 */
public class _639 {
    // dp[i] = number of ways to decode s[0...i-1] 
    // dp[i] = ways to decode s[i-1] as single digit * dp[i-1] * dp[i-1]
    //         + ways to decode s[i-2:i] as two digits * dp[i-2]
    // dp[0] = 1  // empty string
    // dp[1] = 9 if s[0] == '*'
    //       = 0 if s[0] == '0'
    //       = 1 else
    class Solution1_DP {
        public int numDecodings(String s) {
            long MOD = 1_000_000_007;
            int n = s.length();
            long[] dp = new long[n + 1];
            
            // Base case
            dp[0] = 1;
            
            // First character
            if (s.charAt(0) == '*') {
                dp[1] = 9;
            } else if (s.charAt(0) == '0') {
                return 0;
            } else {
                dp[1] = 1;
            }
            
            // Fill the DP table
            for (int i = 2; i <= n; i++) {
                char curr = s.charAt(i - 1);
                char prev = s.charAt(i - 2);
                
                // One digit decoding
                long oneDigit = getOneDigitWays(curr);
                dp[i] = (oneDigit * dp[i - 1]) % MOD;
                
                // Two digit decoding
                long twoDigit = getTwoDigitWays(prev, curr);
                dp[i] = (dp[i] + twoDigit * dp[i - 2]) % MOD;
            }
            
            return (int) dp[n];
        }

        private long getOneDigitWays(char c) {
            if (c == '*') {
                return 9; // 1-9
            } else if (c == '0') {
                return 0; // invalid
            } else {
                return 1; // valid digit
            }
        }

        private long getTwoDigitWays(char first, char second) {
            // Case 1: ** 
            if (first == '*' && second == '*') {
                return 15; // 11-19 (9) + 21-26 (6)
            }
            
            // Case 2: *X (digit)
            if (first == '*') {
                if (second >= '0' && second <= '6') {
                    return 2; // 1X and 2X
                } else {
                    return 1; // only 1X
                }
            }
            
            // Case 3: X* (digit, star)
            if (second == '*') {
                if (first == '1') {
                    return 9; // 11-19
                } else if (first == '2') {
                    return 6; // 21-26
                } else {
                    return 0; // 3*-9* all > 26
                }
            }
            
            // Case 4: both digits
            int twoDigit = (first - '0') * 10 + (second - '0');
            if (twoDigit >= 10 && twoDigit <= 26) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    class Solution1_DP_SpaceOptimized {
        public int numDecodings(String s) {
            long MOD = 1_000_000_007;
            long prev2 = 1;
            long prev1 = s.charAt(0) == '*' ? 9 : (s.charAt(0) == '0' ? 0 : 1);
            
            if (prev1 == 0) return 0;
            
            for (int i = 1; i < s.length(); i++) {
                long curr = 0;
                char c = s.charAt(i);
                char p = s.charAt(i - 1);
                
                // Single digit
                if (c == '*') {
                    curr = (prev1 * 9) % MOD;
                } else if (c != '0') {
                    curr = prev1;
                }
                
                // Two digits
                if (p == '*' && c == '*') {
                    curr = (curr + prev2 * 15) % MOD;
                } else if (p == '*') {
                    curr = (curr + prev2 * (c <= '6' ? 2 : 1)) % MOD;
                } else if (c == '*') {
                    if (p == '1') {
                        curr = (curr + prev2 * 9) % MOD;
                    } else if (p == '2') {
                        curr = (curr + prev2 * 6) % MOD;
                    }
                } else {
                    int num = (p - '0') * 10 + (c - '0');
                    if (num >= 10 && num <= 26) {
                        curr = (curr + prev2) % MOD;
                    }
                }
                
                prev2 = prev1;
                prev1 = curr;
            }
            
            return (int) prev1;
        }
    }
}
