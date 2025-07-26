import java.util.ArrayList;
import java.util.List;

/**
 * 93. Restore IP Addresses
 */
public class _93 {
    // Time: O(n^2) -- recurse on at most 3 choices at each depth, up to 4 levels which is 3⁴ = 81 (a constant) recursive calls
    // Auxiliary Space: O(1)
    class Solution1_Backtrack_Combination_ElemUsedOnce_NoDedup {
        private final char DOT = '.';
        public List<String> restoreIpAddresses(String s) {
            // combination -- elem used once -- no need dedup
            // 0 is valid but 00 is not valid
            List<String> ipAddresses = new ArrayList<>();
            backtrack(s, ipAddresses, new ArrayList<>(), 0);
            return ipAddresses;
        }

        private void backtrack(String s, List<String> ips, List<String> ip, int start) {
            int n = s.length();
            if (start == n && ip.size() == 4) {
                ips.add(getIpAddress(ip));
                return;
            }
            if (start == n || ip.size() == 4) return;
            for (int i = start; i < n; i++) {
                String num = s.substring(start, i + 1);
                if (!isValidIpAddress(num)) continue;
                ip.add(num);
                backtrack(s, ips, ip, i + 1);
                ip.remove(ip.size() - 1);
            }
        }

        private String getIpAddress(List<String> ipList) {
            StringBuilder ip = new StringBuilder();
            for (int i = 0; i < ipList.size(); i++) {
                ip.append(ipList.get(i));
                if (i < ipList.size() - 1) {
                    ip.append(DOT);
                }
            }
            return ip.toString();
        }

        private boolean isValidIpAddress(String s) {
            if (s.length() > 3) return false; // prevent integer overflow
            int val = Integer.valueOf(s);
            if (val > 255) return false;
            if (val > 0 && s.charAt(0) == '0') return false; // leading zeros
            return val > 0 || (val == 0 && s.length() == 1);
        }
    }

    class Solution2_Backtrack_Combination_ElemUsedOnce_NoDedup {
        public List<String> restoreIpAddresses(String s) {
            // combination -- elem used once -- no need dedup
            // 0 is valid but 00 is not valid
            List<String> ipAddresses = new ArrayList<>();
            backtrack(s, ipAddresses, new ArrayList<>(), 0);
            return ipAddresses;
        }

        private void backtrack(String s, List<String> ips, List<String> ip, int start) {
            int n = s.length();
            if (start == n && ip.size() == 4) {
                ips.add(String.join(".", ip));
                return;
            }
            if (start == n || ip.size() == 4) return;
            for (int i = start; i < n; i++) {
                String num = s.substring(start, i + 1);
                if (!isValidIpAddress(num)) continue;
                ip.add(num);
                backtrack(s, ips, ip, i + 1);
                ip.remove(ip.size() - 1);
            }
        }

        private boolean isValidIpAddress(String s) {
            if (s.length() > 3) return false; // prevent integer overflow
            int val = Integer.valueOf(s);
            if (val > 255) return false;
            if (val > 0 && s.charAt(0) == '0') return false; // leading zeros
            return val > 0 || (val == 0 && s.length() == 1);
        }
    }
}
