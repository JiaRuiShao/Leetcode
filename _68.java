import java.util.ArrayList;
import java.util.List;

/**
 * 68. Text Justification
 */
public class _68 {
    class Solution1 {
        public List<String> fullJustify(String[] words, int maxWidth) {
            List<String> justified = new ArrayList<>();
            List<String> currLineWords = new ArrayList<>();
            int currLineWordLength = 0;
            for (int i = 0; i < words.length; ) {
                String word = words[i];
                if (currLineWordLength + currLineWords.size() + word.length() > maxWidth) {
                    justified.add(getPaddedLine(currLineWords, currLineWordLength, maxWidth));
                    currLineWords.clear();
                    currLineWordLength = 0;
                } else {
                    currLineWords.add(word);
                    currLineWordLength += word.length();
                    i++;
                }
            }
            justified.add(getPaddedLastLine(currLineWords, currLineWordLength, maxWidth));
            return justified;
        }

        private String getPaddedLine(List<String> words, int wordsLen, int width) {
            int spaces = width - wordsLen;
            int gaps = words.size() - 1;
            if (gaps == 0) { /* IMPORTANT*/
                return getPaddedLastLine(words, wordsLen, width);
            }
            int gapPadding = spaces / gaps;
            int extraPadding = spaces % gaps;
            String gapPaddingStr = " ".repeat(gapPadding); // introduced in Java 11
            // Q: will extra padding be evenly distributed as left as possible or padded for leftmost gap? as left as possible
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < words.size(); i++) {
                line.append(words.get(i));
                if (i < extraPadding) {
                    line.append(" ");
                }
                if (i < words.size() - 1) {
                    line.append(gapPaddingStr);
                }
            }
            return line.toString();
        }

        private String getPaddedLastLine(List<String> words, int wordsLen, int width) {
            int spaces = width - wordsLen;
            int gaps = words.size() - 1;
            int rightPadding = spaces - gaps;
            String rightPaddingStr = " ".repeat(rightPadding);
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < words.size(); i++) {
                line.append(words.get(i));
                if (i < words.size() - 1) {
                    line.append(" ");
                } else {
                    line.append(rightPaddingStr);
                }
            }
            return line.toString();
        }
    }
}
